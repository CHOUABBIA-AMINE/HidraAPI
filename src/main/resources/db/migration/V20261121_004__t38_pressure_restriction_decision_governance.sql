-- Topology owns the request and stream. T36 candidates are immutable proposals.

CREATE TABLE public.hidra_topology_pressure_decision_stream (
 company_party_id varchar(80) NOT NULL REFERENCES public.hidra_party_party(id),
 pipeline_system_id varchar(80) NOT NULL REFERENCES public.hidra_topology_pipeline_system(id),
 pipeline_id varchar(80) NOT NULL REFERENCES public.hidra_topology_pipeline(id),
 section_id varchar(80) NOT NULL DEFAULT '',
 condition_code varchar(80) NOT NULL,
 publication_version bigint NOT NULL DEFAULT 0 CHECK (publication_version >= 0),
 PRIMARY KEY (company_party_id,pipeline_system_id,pipeline_id,section_id,condition_code)
);

CREATE TABLE public.hidra_topology_pressure_decision_request (
 request_id uuid PRIMARY KEY,
 company_party_id varchar(80) NOT NULL,
 pipeline_system_id varchar(80) NOT NULL,
 pipeline_id varchar(80) NOT NULL,
 section_id varchar(80) NOT NULL,
 condition_code varchar(80) NOT NULL,
 expected_version bigint NOT NULL CHECK (expected_version >= 0),
 revision_candidate_id uuid UNIQUE REFERENCES public.hidra_topology_t36_pressure_candidate(candidate_id),
 termination_candidate_id uuid UNIQUE REFERENCES public.hidra_topology_t36_pressure_candidate(candidate_id),
 CHECK (revision_candidate_id IS NOT NULL OR termination_candidate_id IS NOT NULL),
 CHECK (revision_candidate_id IS NULL OR termination_candidate_id IS NULL
     OR revision_candidate_id <> termination_candidate_id),
 FOREIGN KEY (company_party_id,pipeline_system_id,pipeline_id,section_id,condition_code)
   REFERENCES public.hidra_topology_pressure_decision_stream
);

CREATE TABLE public.hidra_topology_pressure_decision_publication (
 request_id uuid PRIMARY KEY REFERENCES public.hidra_topology_pressure_decision_request(request_id),
 stream_version bigint NOT NULL CHECK (stream_version > 0),
 revision_id uuid UNIQUE REFERENCES public.hidra_topology_t36_pressure_revision(revision_id),
 termination_id uuid UNIQUE REFERENCES public.hidra_topology_t36_pressure_termination(event_id),
 recorded_at timestamptz NOT NULL,
 CHECK (revision_id IS NOT NULL OR termination_id IS NOT NULL)
);

CREATE TABLE public.hidra_topology_pressure_decision_witness (
 request_id uuid NOT NULL REFERENCES public.hidra_topology_pressure_decision_publication(request_id),
 position smallint NOT NULL CHECK (position IN (1,2)),
 candidate_id uuid NOT NULL UNIQUE REFERENCES public.hidra_topology_t36_pressure_candidate(candidate_id),
 instance_id varchar(80) NOT NULL,
 task_id varchar(80) NOT NULL,
 action_id varchar(80) NOT NULL UNIQUE,
 audit_event_id varchar(80) NOT NULL UNIQUE,
 PRIMARY KEY (request_id,position)
);

CREATE FUNCTION public.hidra_topology_t38_lock_stream(
 company varchar, system_id varchar, pipeline varchar, section varchar, condition varchar
) RETURNS bigint LANGUAGE plpgsql SECURITY DEFINER
SET search_path=pg_catalog,pg_temp AS $$
DECLARE result bigint;
BEGIN
 INSERT INTO public.hidra_topology_pressure_decision_stream
  (company_party_id,pipeline_system_id,pipeline_id,section_id,condition_code)
 VALUES (company,system_id,pipeline,section,condition) ON CONFLICT DO NOTHING;
 SELECT publication_version INTO STRICT result
 FROM public.hidra_topology_pressure_decision_stream
 WHERE (company_party_id,pipeline_system_id,pipeline_id,section_id,condition_code)
     =(company,system_id,pipeline,section,condition)
 FOR UPDATE;
 RETURN result;
END;
$$;

CREATE FUNCTION public.hidra_topology_t38_lock_source(
 company varchar, system_id varchar, pipeline varchar, section varchar, receipt uuid, digest varchar
) RETURNS boolean LANGUAGE plpgsql SECURITY DEFINER
SET search_path=pg_catalog,pg_temp AS $$
BEGIN
 -- Hold the relevant current Identity and Organization state until publication commits.
 LOCK TABLE public.hidra_identity_user, public.hidra_identity_login_session,
     public.hidra_identity_local_credential, public.hidra_identity_permission,
     public.hidra_identity_user_permission_grant, public.hidra_identity_role,
     public.hidra_identity_user_role_grant, public.hidra_identity_role_permission_grant,
     public.hidra_identity_group, public.hidra_identity_group_role_grant,
     public.hidra_identity_user_group_membership, public.hidra_identity_authorization_policy,
     public.hidra_identity_authorization_policy_version, public.hidra_identity_authorization_policy_rule,
     public.hidra_identity_subject_security_attribute,
     public.hidra_org_employee, public.hidra_org_operational_scope, public.hidra_org_responsibility_assignment
 IN SHARE MODE NOWAIT;
 PERFORM 1
 FROM public.hidra_topology_pipeline p
 JOIN public.hidra_topology_pipeline_system sys ON sys.id=p.pipeline_system_id
 JOIN public.hidra_topology_foundation_system_owner o ON o.pipeline_system_id=sys.id
 JOIN public.hidra_topology_pipeline_authoring_revision r
   ON r.pipeline_id=p.id AND r.pipeline_system_id=sys.id
 WHERE p.id=pipeline AND sys.id=system_id AND o.company_party_id=company
   AND r.company_party_id=company AND sys.status='ACTIVE' AND p.status='ACTIVE'
   AND r.revision=(SELECT max(ar.revision) FROM public.hidra_topology_pipeline_authoring_revision ar
                   WHERE ar.pipeline_id=p.id)
 FOR SHARE OF p,sys,o,r;
 IF NOT FOUND THEN RETURN false; END IF;
 IF section<>'' THEN
   PERFORM 1 FROM public.hidra_topology_pipeline_segment s
   WHERE s.id=section AND s.pipeline_id=pipeline AND s.status='ACTIVE' FOR SHARE;
   IF NOT FOUND THEN RETURN false; END IF;
 END IF;
 PERFORM 1
 FROM public.hidra_topology_baseline_publication p
 JOIN public.hidra_topology_baseline_submission s
   USING (company_party_id,pipeline_system_id,submission_id)
 WHERE p.company_party_id=company AND p.pipeline_system_id=system_id
   AND p.receipt_id=receipt AND s.canonical_sha256=digest
 FOR SHARE OF p,s;
 RETURN FOUND;
END;
$$;

CREATE FUNCTION public.hidra_topology_t38_guard(
 request_id_value uuid, version_value bigint
) RETURNS boolean LANGUAGE sql STABLE SECURITY DEFINER
SET search_path=pg_catalog,pg_temp AS $$
 SELECT EXISTS (
   SELECT 1 FROM public.hidra_topology_pressure_decision_request r
   JOIN public.hidra_topology_pressure_decision_stream s
    USING (company_party_id,pipeline_system_id,pipeline_id,section_id,condition_code)
   LEFT JOIN public.hidra_topology_pressure_decision_publication p ON p.request_id=r.request_id
   JOIN public.hidra_topology_pipeline pipe ON pipe.id=r.pipeline_id
   JOIN public.hidra_topology_pipeline_system sys ON sys.id=pipe.pipeline_system_id
   JOIN public.hidra_topology_foundation_system_owner owner ON owner.pipeline_system_id=sys.id
   JOIN public.hidra_topology_pipeline_authoring_revision latest ON latest.pipeline_id=pipe.id
   WHERE r.request_id=request_id_value AND s.publication_version=version_value
     AND pipe.status='ACTIVE' AND sys.status='ACTIVE'
     AND sys.id=r.pipeline_system_id AND owner.company_party_id=r.company_party_id
     AND latest.pipeline_system_id=sys.id AND latest.company_party_id=r.company_party_id
     AND latest.revision=(SELECT max(ar.revision) FROM public.hidra_topology_pipeline_authoring_revision ar
                          WHERE ar.pipeline_id=pipe.id)
     AND (r.section_id='' OR EXISTS (
        SELECT 1 FROM public.hidra_topology_pipeline_segment seg
        WHERE seg.id=r.section_id AND seg.pipeline_id=pipe.id AND seg.status='ACTIVE'))
     AND (p.request_id IS NULL OR
          (p.revision_id IS NOT DISTINCT FROM r.revision_candidate_id
           AND p.termination_id IS NOT DISTINCT FROM r.termination_candidate_id
           AND (SELECT count(*) FROM public.hidra_topology_pressure_decision_witness w
                WHERE w.request_id=p.request_id
                  AND w.candidate_id IN (r.revision_candidate_id,r.termination_candidate_id))
               = num_nonnulls(r.revision_candidate_id,r.termination_candidate_id)))
 );
$$;

CREATE TRIGGER t38_requests_immutable BEFORE UPDATE OR DELETE
 ON public.hidra_topology_pressure_decision_request
 FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_t36_immutable();
CREATE TRIGGER t38_requests_no_truncate BEFORE TRUNCATE
 ON public.hidra_topology_pressure_decision_request
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_t36_immutable();
CREATE TRIGGER t38_publications_immutable BEFORE UPDATE OR DELETE
 ON public.hidra_topology_pressure_decision_publication
 FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_t36_immutable();
CREATE TRIGGER t38_publications_no_truncate BEFORE TRUNCATE
 ON public.hidra_topology_pressure_decision_publication
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_t36_immutable();
CREATE TRIGGER t38_witnesses_immutable BEFORE UPDATE OR DELETE
 ON public.hidra_topology_pressure_decision_witness
 FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_t36_immutable();
CREATE TRIGGER t38_witnesses_no_truncate BEFORE TRUNCATE
 ON public.hidra_topology_pressure_decision_witness
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_t36_immutable();

REVOKE ALL ON public.hidra_topology_pressure_decision_stream,
 public.hidra_topology_pressure_decision_request,public.hidra_topology_pressure_decision_publication,
 public.hidra_topology_pressure_decision_witness FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_t38_lock_stream(varchar,varchar,varchar,varchar,varchar) FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_t38_lock_source(varchar,varchar,varchar,varchar,uuid,varchar) FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_t38_guard(uuid,bigint) FROM PUBLIC;

CREATE FUNCTION public.hidra_topology_t38_submit(
 request_key uuid, company varchar, system_id varchar, pipeline varchar, section varchar, condition varchar,
 expected bigint, revision_key uuid, termination_key uuid, predecessor uuid,
 pressure numeric, unit varchar, basis varchar, effective_from_value timestamptz, effective_until_value timestamptz,
 receipt uuid, digest varchar, source_revision varchar, submitter varchar, submitted_at timestamptz
) RETURNS uuid LANGUAGE plpgsql SECURITY DEFINER
SET search_path=pg_catalog,pg_temp AS $$
BEGIN
 IF (revision_key IS NULL AND termination_key IS NULL)
    OR (termination_key IS NULL AND predecessor IS NOT NULL)
    OR (termination_key IS NOT NULL AND predecessor IS NULL)
    OR (revision_key IS NOT NULL AND termination_key IS NOT NULL AND revision_key=termination_key)
    OR (revision_key IS NULL AND num_nonnulls(pressure,unit,basis,effective_until_value)<>0) THEN
    RAISE EXCEPTION 'Invalid restriction request shape' USING ERRCODE='23514';
 END IF;
 IF public.hidra_topology_t38_lock_stream(company,system_id,pipeline,section,condition)<>expected THEN
    RAISE EXCEPTION 'Restriction stream version conflict' USING ERRCODE='23514';
 END IF;
 IF NOT public.hidra_topology_t38_lock_source(company,system_id,pipeline,section,receipt,digest) THEN
    RAISE EXCEPTION 'Restriction source unavailable' USING ERRCODE='23514';
 END IF;
 IF revision_key IS NOT NULL THEN
   INSERT INTO public.hidra_topology_t36_pressure_candidate
    (candidate_id,candidate_kind,company_party_id,pipeline_id,section_id,pipeline_system_id,condition_code,
     upper_pressure,pressure_unit,pressure_basis,effective_from,effective_until,retired_revision_id,
     baseline_receipt_id,baseline_manifest_digest,engineering_source_revision,submitted_by,created_at)
   VALUES (revision_key,'REVISION',company,pipeline,section,system_id,condition,pressure,unit,basis,
           effective_from_value,effective_until_value,predecessor,receipt,digest,source_revision,submitter,submitted_at);
 END IF;
 IF termination_key IS NOT NULL THEN
   INSERT INTO public.hidra_topology_t36_pressure_candidate
    (candidate_id,candidate_kind,company_party_id,pipeline_id,section_id,pipeline_system_id,condition_code,
     effective_from,retired_revision_id,successor_revision_id,baseline_receipt_id,baseline_manifest_digest,
     engineering_source_revision,submitted_by,created_at)
   VALUES (termination_key,CASE WHEN revision_key IS NULL THEN 'WITHDRAW' ELSE 'SUPERSEDE' END,
           company,pipeline,section,system_id,condition,effective_from_value,predecessor,revision_key,
           receipt,digest,source_revision,submitter,submitted_at);
 END IF;
 INSERT INTO public.hidra_topology_pressure_decision_request
  (request_id,company_party_id,pipeline_system_id,pipeline_id,section_id,condition_code,expected_version,
   revision_candidate_id,termination_candidate_id)
 VALUES (request_key,company,system_id,pipeline,section,condition,expected,revision_key,termination_key);
 RETURN request_key;
END;
$$;

CREATE FUNCTION public.hidra_topology_t38_publish(
 request_key uuid, expected bigint, recorded timestamptz, instances varchar[], tasks varchar[],
 actions varchar[], audits varchar[], reviewers varchar[], action_times timestamptz[]
) RETURNS bigint LANGUAGE plpgsql SECURITY DEFINER
SET search_path=pg_catalog,pg_temp AS $$
DECLARE
 req public.hidra_topology_pressure_decision_request%ROWTYPE;
 candidate public.hidra_topology_t36_pressure_candidate%ROWTYPE;
 candidate_keys uuid[];
 idx integer;
BEGIN
 SELECT * INTO STRICT req FROM public.hidra_topology_pressure_decision_request WHERE request_id=request_key;
 IF req.expected_version<>expected OR public.hidra_topology_t38_lock_stream(req.company_party_id,
    req.pipeline_system_id,req.pipeline_id,req.section_id,req.condition_code)<>expected THEN
    RAISE EXCEPTION 'Restriction stream version conflict' USING ERRCODE='23514';
 END IF;
 candidate_keys := array_remove(ARRAY[req.revision_candidate_id,req.termination_candidate_id],NULL::uuid);
 IF cardinality(candidate_keys) IS DISTINCT FROM cardinality(instances)
    OR cardinality(candidate_keys) IS DISTINCT FROM cardinality(tasks)
    OR cardinality(candidate_keys) IS DISTINCT FROM cardinality(actions)
    OR cardinality(candidate_keys) IS DISTINCT FROM cardinality(audits)
    OR cardinality(candidate_keys) IS DISTINCT FROM cardinality(reviewers)
    OR cardinality(candidate_keys) IS DISTINCT FROM cardinality(action_times) THEN
    RAISE EXCEPTION 'Exact decision evidence required' USING ERRCODE='23514';
 END IF;
 FOR idx IN 1..cardinality(candidate_keys) LOOP
   SELECT * INTO STRICT candidate FROM public.hidra_topology_t36_pressure_candidate
   WHERE candidate_id=candidate_keys[idx]
     AND (company_party_id,pipeline_system_id,pipeline_id,section_id,condition_code)
         =(req.company_party_id,req.pipeline_system_id,req.pipeline_id,req.section_id,req.condition_code);
   IF NOT public.hidra_topology_t38_lock_source(candidate.company_party_id,candidate.pipeline_system_id,
      candidate.pipeline_id,candidate.section_id,candidate.baseline_receipt_id,candidate.baseline_manifest_digest) THEN
      RAISE EXCEPTION 'Restriction source unavailable' USING ERRCODE='23514';
   END IF;
   IF candidate.retired_revision_id IS NOT NULL AND NOT EXISTS (
      SELECT 1 FROM public.hidra_topology_t36_pressure_revision prior
       WHERE prior.revision_id=candidate.retired_revision_id
         AND (prior.company_party_id,prior.pipeline_system_id,prior.pipeline_id,prior.section_id,prior.condition_code)
             =(req.company_party_id,req.pipeline_system_id,req.pipeline_id,req.section_id,req.condition_code)
         AND prior.effective_from<=candidate.effective_from
         AND (prior.effective_until IS NULL OR candidate.effective_from<prior.effective_until)
         AND NOT EXISTS (SELECT 1 FROM public.hidra_topology_t36_pressure_termination retired
                         WHERE retired.retired_revision_id=prior.revision_id)
   ) THEN RAISE EXCEPTION 'Exact effective predecessor required' USING ERRCODE='23514'; END IF;

   -- Workflow supplies a final action. The T36 triggers independently check the bound
   -- candidate, current source, reviewer and exact Audit row during each projection insert.
   IF NOT EXISTS (
     SELECT 1 FROM public.hidra_workflow_action a
     WHERE a.id=actions[idx] AND a.instance_id=instances[idx] AND a.task_id=tasks[idx]
       AND a.actor_id=reviewers[idx] AND a.acted_at=action_times[idx]
   ) THEN RAISE EXCEPTION 'Exact Workflow decision unavailable' USING ERRCODE='23514'; END IF;
   IF candidate.candidate_kind='REVISION' THEN
     IF EXISTS (
       SELECT 1 FROM public.hidra_topology_t36_pressure_revision previous
       LEFT JOIN public.hidra_topology_t36_pressure_termination retired
        ON retired.retired_revision_id=previous.revision_id
       WHERE (previous.company_party_id,previous.pipeline_system_id,previous.pipeline_id,previous.section_id,previous.condition_code)
              =(req.company_party_id,req.pipeline_system_id,req.pipeline_id,req.section_id,req.condition_code)
         AND previous.revision_id IS DISTINCT FROM candidate.retired_revision_id
         AND (candidate.effective_until IS NULL OR previous.effective_from<candidate.effective_until)
         AND (previous.effective_until IS NULL OR candidate.effective_from<previous.effective_until)
         AND (retired.effective_at IS NULL OR candidate.effective_from<retired.effective_at)
     ) THEN RAISE EXCEPTION 'Overlapping restriction interval' USING ERRCODE='23514'; END IF;
     INSERT INTO public.hidra_topology_t36_pressure_revision
      (revision_id,company_party_id,pipeline_id,section_id,pipeline_system_id,condition_code,upper_pressure,
       pressure_unit,pressure_basis,effective_from,effective_until,recorded_at,baseline_receipt_id,
       baseline_manifest_digest,engineering_source_revision,decision_action_id,audit_event_id,submitted_by,
       reviewed_by,approved_at,supersedes_revision_id)
     VALUES (candidate.candidate_id,req.company_party_id,req.pipeline_id,req.section_id,req.pipeline_system_id,
       req.condition_code,candidate.upper_pressure,candidate.pressure_unit,candidate.pressure_basis,
       candidate.effective_from,candidate.effective_until,recorded,candidate.baseline_receipt_id,
       candidate.baseline_manifest_digest,candidate.engineering_source_revision,actions[idx],audits[idx],
       candidate.submitted_by,reviewers[idx],action_times[idx],candidate.retired_revision_id);
   ELSE
     INSERT INTO public.hidra_topology_t36_pressure_termination
      (event_id,company_party_id,pipeline_id,section_id,pipeline_system_id,condition_code,retired_revision_id,
       event_kind,successor_revision_id,effective_at,recorded_at,baseline_receipt_id,baseline_manifest_digest,
       engineering_source_revision,decision_action_id,audit_event_id,submitted_by,reviewed_by,approved_at)
     VALUES (candidate.candidate_id,req.company_party_id,req.pipeline_id,req.section_id,req.pipeline_system_id,
       req.condition_code,candidate.retired_revision_id,candidate.candidate_kind,candidate.successor_revision_id,
       candidate.effective_from,recorded,candidate.baseline_receipt_id,candidate.baseline_manifest_digest,
       candidate.engineering_source_revision,actions[idx],audits[idx],candidate.submitted_by,reviewers[idx],action_times[idx]);
   END IF;
 END LOOP;
 INSERT INTO public.hidra_topology_pressure_decision_publication
  (request_id,stream_version,revision_id,termination_id,recorded_at)
 VALUES (request_key,expected+1,req.revision_candidate_id,req.termination_candidate_id,recorded);
 FOR idx IN 1..cardinality(candidate_keys) LOOP
   INSERT INTO public.hidra_topology_pressure_decision_witness
     (request_id,position,candidate_id,instance_id,task_id,action_id,audit_event_id)
   VALUES (request_key,idx,candidate_keys[idx],instances[idx],tasks[idx],actions[idx],audits[idx]);
 END LOOP;
 UPDATE public.hidra_topology_pressure_decision_stream SET publication_version=expected+1
 WHERE (company_party_id,pipeline_system_id,pipeline_id,section_id,condition_code)
       =(req.company_party_id,req.pipeline_system_id,req.pipeline_id,req.section_id,req.condition_code);
 RETURN expected+1;
END;
$$;

REVOKE ALL ON FUNCTION public.hidra_topology_t38_submit(uuid,varchar,varchar,varchar,varchar,varchar,
 bigint,uuid,uuid,uuid,numeric,varchar,varchar,timestamptz,timestamptz,uuid,varchar,varchar,varchar,timestamptz) FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_t38_publish(
 uuid,bigint,timestamptz,varchar[],varchar[],varchar[],varchar[],varchar[],timestamptz[]) FROM PUBLIC;

INSERT INTO public.hidra_identity_permission
 (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
SELECT 't38_pressure_'||replace(action_value,'-','_'),
       'topology:pressure-restriction:'||action_value,'topology',
       'OPERATING_PRESSURE_RESTRICTION',action_value,true,'ACTIVE',now(),now()
FROM (VALUES ('submit'),('review'),('publish'),('evidence-read')) AS required(action_value)
ON CONFLICT (code) DO NOTHING;

INSERT INTO public.hidra_audit_catalog_entry
 (id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
VALUES ('t38_pressure_revision','EVENT_TYPE','OPERATING_PRESSURE_RESTRICTION_APPROVED',true,0,true,now(),now()),
       ('t38_pressure_supersede','EVENT_TYPE','OPERATING_PRESSURE_RESTRICTION_SUPERSEDED',true,0,true,now(),now()),
       ('t38_pressure_withdraw','EVENT_TYPE','OPERATING_PRESSURE_RESTRICTION_WITHDRAWN',true,0,true,now(),now()),
       ('t38_pressure_governance','EVENT_CATEGORY','OPERATING_PRESSURE_RESTRICTION',true,0,true,now(),now());

CREATE FUNCTION public.hidra_t38_guard_pressure_audit_event()
RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER
SET search_path=pg_catalog,pg_temp AS $$
BEGIN
 IF NOT COALESCE(NEW.target_type='OPERATING_PRESSURE_RESTRICTION',false)
    AND NOT COALESCE(NEW.event_type_id IN ('t38_pressure_revision','t38_pressure_supersede','t38_pressure_withdraw'),false)
    AND NOT COALESCE(NEW.action_code IN ('APPROVE_OPERATING_PRESSURE_RESTRICTION',
                                        'SUPERSEDE_OPERATING_PRESSURE_RESTRICTION',
                                        'WITHDRAW_OPERATING_PRESSURE_RESTRICTION'),false) THEN
    RETURN NEW;
 END IF;
 IF NOT pg_catalog.has_function_privilege(session_user,
     'public.hidra_topology_t38_publish(uuid,bigint,timestamptz,varchar[],varchar[],varchar[],varchar[],varchar[],timestamptz[])',
     'EXECUTE') THEN
    RAISE EXCEPTION 'Pressure Audit evidence requires the protected publication credential' USING ERRCODE='42501';
 END IF;
 RETURN NEW;
END;
$$;
CREATE TRIGGER t38_audit_protected_pressure_witness BEFORE INSERT ON public.hidra_audit_event
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t38_guard_pressure_audit_event();
REVOKE ALL ON FUNCTION public.hidra_t38_guard_pressure_audit_event() FROM PUBLIC;
