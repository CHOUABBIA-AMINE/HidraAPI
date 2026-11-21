-- T36 / G4: fresh-schema, Topology-owned, immutable read projection.
-- No operating restrictions, synthetic runtime fixtures, or approval actors are seeded.
-- Workflow, Audit, and Identity continue to own their original authorization records.
-- T38 must provide a protected insert contract; T36 provides no application writer.

CREATE TABLE public.hidra_topology_t36_condition_policy (
 company_party_id varchar(80) NOT NULL REFERENCES public.hidra_party_party(id),
 condition_code varchar(80) NOT NULL CHECK (condition_code ~ '^[A-Za-z0-9][A-Za-z0-9_.:-]{0,79}$'),
 pressure_unit varchar(80) NOT NULL CHECK (pressure_unit ~ '^[A-Za-z0-9][A-Za-z0-9_.:-]{0,79}$'),
 recorded_at timestamptz NOT NULL,
 PRIMARY KEY (company_party_id,condition_code,pressure_unit)
);

CREATE TABLE public.hidra_topology_t36_pressure_revision (
 revision_id uuid PRIMARY KEY,
 company_party_id varchar(80) NOT NULL REFERENCES public.hidra_party_party(id),
 pipeline_id varchar(80) NOT NULL REFERENCES public.hidra_topology_pipeline(id),
 section_id varchar(80) NOT NULL DEFAULT '',
 pipeline_system_id varchar(80) NOT NULL REFERENCES public.hidra_topology_pipeline_system(id),
 condition_code varchar(80) NOT NULL,
 upper_pressure numeric(24,12) NOT NULL CHECK (upper_pressure>0),
 pressure_unit varchar(80) NOT NULL,
 pressure_basis varchar(16) NOT NULL CHECK (pressure_basis IN ('GAUGE','ABSOLUTE')),
 effective_from timestamptz NOT NULL,
 effective_until timestamptz,
 recorded_at timestamptz NOT NULL,
 baseline_receipt_id uuid NOT NULL,
 baseline_manifest_digest char(64) NOT NULL CHECK (baseline_manifest_digest ~ '^[0-9a-f]{64}$'),
 engineering_source_revision varchar(80) NOT NULL CHECK (btrim(engineering_source_revision)<>''),
 decision_action_id varchar(80) NOT NULL UNIQUE CHECK (btrim(decision_action_id)<>''),
 audit_event_id varchar(80) NOT NULL UNIQUE CHECK (btrim(audit_event_id)<>''),
 submitted_by varchar(80) NOT NULL CHECK (btrim(submitted_by)<>''),
 reviewed_by varchar(80) NOT NULL CHECK (btrim(reviewed_by)<>'' AND reviewed_by<>submitted_by),
 approved_at timestamptz NOT NULL,
 supersedes_revision_id uuid REFERENCES public.hidra_topology_t36_pressure_revision(revision_id),
 CONSTRAINT t36_revision_scope UNIQUE(company_party_id,pipeline_id,section_id,condition_code,revision_id),
 CONSTRAINT t36_revision_interval CHECK(effective_until IS NULL OR effective_until>effective_from),
 CONSTRAINT t36_revision_recorded CHECK(recorded_at>=approved_at),
 CONSTRAINT t36_revision_pressure_unit FOREIGN KEY(company_party_id,condition_code,pressure_unit)
    REFERENCES public.hidra_topology_t36_condition_policy(company_party_id,condition_code,pressure_unit),
 CONSTRAINT t36_revision_source FOREIGN KEY(company_party_id,pipeline_system_id,baseline_receipt_id)
    REFERENCES public.hidra_topology_baseline_publication(company_party_id,pipeline_system_id,receipt_id)
);
CREATE INDEX t36_revision_history_idx
 ON public.hidra_topology_t36_pressure_revision
 (company_party_id,pipeline_id,section_id,condition_code,recorded_at,revision_id);

CREATE TABLE public.hidra_topology_t36_pressure_termination (
 event_id uuid PRIMARY KEY,
 company_party_id varchar(80) NOT NULL,
 pipeline_id varchar(80) NOT NULL,
 section_id varchar(80) NOT NULL DEFAULT '',
 pipeline_system_id varchar(80) NOT NULL,
 condition_code varchar(80) NOT NULL,
 retired_revision_id uuid NOT NULL,
 event_kind varchar(16) NOT NULL CHECK(event_kind IN ('SUPERSEDE','WITHDRAW')),
 successor_revision_id uuid,
 effective_at timestamptz NOT NULL,
 recorded_at timestamptz NOT NULL,
 baseline_receipt_id uuid NOT NULL,
 baseline_manifest_digest char(64) NOT NULL CHECK(baseline_manifest_digest ~ '^[0-9a-f]{64}$'),
 engineering_source_revision varchar(80) NOT NULL CHECK(btrim(engineering_source_revision)<>''),
 decision_action_id varchar(80) NOT NULL UNIQUE CHECK(btrim(decision_action_id)<>''),
 audit_event_id varchar(80) NOT NULL UNIQUE CHECK(btrim(audit_event_id)<>''),
 submitted_by varchar(80) NOT NULL CHECK(btrim(submitted_by)<>''),
 reviewed_by varchar(80) NOT NULL CHECK(btrim(reviewed_by)<>'' AND reviewed_by<>submitted_by),
 approved_at timestamptz NOT NULL,
 CONSTRAINT t36_termination_shape CHECK(
   (event_kind='SUPERSEDE' AND successor_revision_id IS NOT NULL
      AND successor_revision_id<>retired_revision_id)
   OR (event_kind='WITHDRAW' AND successor_revision_id IS NULL)),
 CONSTRAINT t36_termination_recorded CHECK(recorded_at>=approved_at),
 CONSTRAINT t36_termination_prior FOREIGN KEY(company_party_id,pipeline_id,section_id,condition_code,retired_revision_id)
  REFERENCES public.hidra_topology_t36_pressure_revision
    (company_party_id,pipeline_id,section_id,condition_code,revision_id),
 CONSTRAINT t36_termination_successor FOREIGN KEY(company_party_id,pipeline_id,section_id,condition_code,successor_revision_id)
  REFERENCES public.hidra_topology_t36_pressure_revision
    (company_party_id,pipeline_id,section_id,condition_code,revision_id),
 CONSTRAINT t36_termination_source FOREIGN KEY(company_party_id,pipeline_system_id,baseline_receipt_id)
  REFERENCES public.hidra_topology_baseline_publication(company_party_id,pipeline_system_id,receipt_id),
 CONSTRAINT t36_termination_unique_prior UNIQUE(retired_revision_id)
);
CREATE INDEX t36_termination_history_idx
 ON public.hidra_topology_t36_pressure_termination
 (company_party_id,pipeline_id,section_id,condition_code,recorded_at,event_id);

-- Verify owner facts and real source + Workflow + Audit witnesses *at insert*.
-- These checks do not make a supplied UUID, boolean or role label an approval.
-- The runtime adapter reads only Topology projection rows and never foreign owner tables.
CREATE FUNCTION public.hidra_topology_t36_admission_guard()
RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER
SET search_path=pg_catalog,pg_temp
AS $$
DECLARE
 target_ref text; expected_event text; expected_action text;
 wf record; audit_row record; source_row record; target_row record;
BEGIN
 IF NEW.recorded_at<>date_trunc('microseconds',NEW.recorded_at)
    OR NEW.approved_at<>date_trunc('microseconds',NEW.approved_at)
    OR NEW.reviewed_by=NEW.submitted_by THEN
   RAISE EXCEPTION 'T36 invalid evidence precision or reviewer separation' USING ERRCODE='23514';
 END IF;
 IF TG_TABLE_NAME='hidra_topology_t36_pressure_revision' THEN
   target_ref:=NEW.revision_id::text;
   expected_event:='OPERATING_PRESSURE_RESTRICTION_APPROVED';
   expected_action:='APPROVE_OPERATING_PRESSURE_RESTRICTION';
   IF NEW.effective_from<>date_trunc('microseconds',NEW.effective_from)
      OR NEW.effective_until IS NOT NULL
         AND NEW.effective_until<>date_trunc('microseconds',NEW.effective_until)
      OR NEW.supersedes_revision_id=NEW.revision_id THEN
     RAISE EXCEPTION 'T36 invalid revision interval/lineage' USING ERRCODE='23514';
   END IF;
 ELSE
   target_ref:=NEW.event_id::text;
   expected_event:=CASE NEW.event_kind
     WHEN 'SUPERSEDE' THEN 'OPERATING_PRESSURE_RESTRICTION_SUPERSEDED'
     ELSE 'OPERATING_PRESSURE_RESTRICTION_WITHDRAWN' END;
   expected_action:=CASE NEW.event_kind
     WHEN 'SUPERSEDE' THEN 'SUPERSEDE_OPERATING_PRESSURE_RESTRICTION'
     ELSE 'WITHDRAW_OPERATING_PRESSURE_RESTRICTION' END;
   IF NEW.effective_at<>date_trunc('microseconds',NEW.effective_at) THEN
     RAISE EXCEPTION 'T36 invalid termination precision' USING ERRCODE='23514';
   END IF;
 END IF;
 -- Canonical owner: latest active authored Pipeline/Section association.
 SELECT p.pipeline_system_id, r.company_party_id INTO target_row
 FROM public.hidra_topology_pipeline p
 JOIN public.hidra_topology_pipeline_system s ON s.id=p.pipeline_system_id
 JOIN public.hidra_topology_foundation_system_owner o ON o.pipeline_system_id=s.id
 JOIN public.hidra_topology_pipeline_authoring_revision r
   ON r.pipeline_id=p.id AND r.pipeline_system_id=s.id
 WHERE p.id=NEW.pipeline_id AND p.status='ACTIVE' AND s.status='ACTIVE'
   AND r.revision=(SELECT max(v.revision)
                  FROM public.hidra_topology_pipeline_authoring_revision v
                  WHERE v.pipeline_id=p.id)
   AND o.company_party_id=r.company_party_id;
 IF NOT FOUND OR target_row.pipeline_system_id<>NEW.pipeline_system_id
   OR target_row.company_party_id<>NEW.company_party_id
   OR (NEW.section_id<>'' AND NOT EXISTS(
      SELECT 1 FROM public.hidra_topology_pipeline_segment seg
      WHERE seg.id=NEW.section_id AND seg.pipeline_id=NEW.pipeline_id AND seg.status='ACTIVE')) THEN
   RAISE EXCEPTION 'T36 canonical target ownership mismatch' USING ERRCODE='23514';
 END IF;
 SELECT s.canonical_sha256 INTO source_row
 FROM public.hidra_topology_baseline_publication p
 JOIN public.hidra_topology_baseline_submission s
   ON s.company_party_id=p.company_party_id
  AND s.pipeline_system_id=p.pipeline_system_id AND s.submission_id=p.submission_id
 WHERE p.company_party_id=NEW.company_party_id
   AND p.pipeline_system_id=NEW.pipeline_system_id
   AND p.receipt_id=NEW.baseline_receipt_id AND p.published_at<=NEW.recorded_at;
 IF NOT FOUND OR source_row.canonical_sha256<>NEW.baseline_manifest_digest THEN
   RAISE EXCEPTION 'T36 source publication mismatch' USING ERRCODE='23514';
 END IF;
 -- Existing Workflow enforces active definition, bound target, task sequencing,
 -- actor evidence and immutable action. No T36 Workflow side table is invented.
 SELECT a.actor_id, a.acted_at, i.started_by_actor_id INTO wf
 FROM public.hidra_workflow_action a
 JOIN public.hidra_workflow_instance i ON i.id=a.instance_id
 JOIN public.hidra_workflow_type_catalog tgt ON tgt.id=i.target_type_id
 JOIN public.hidra_workflow_type_catalog pur ON pur.id=i.workflow_purpose_id
 WHERE a.id=NEW.decision_action_id
   AND a.action_type='APPROVE' AND a.decision='APPROVE'
   AND i.target_module='topology' AND i.target_id=target_ref
   AND tgt.catalog_name='WORKFLOW_TARGET_TYPE' AND tgt.code='OPERATING_PRESSURE_RESTRICTION'
   AND pur.catalog_name='WORKFLOW_PURPOSE' AND pur.code='OPERATING_PRESSURE_RESTRICTION_APPROVAL';
 IF NOT FOUND OR wf.actor_id IS DISTINCT FROM NEW.reviewed_by
   OR wf.started_by_actor_id IS DISTINCT FROM NEW.submitted_by
   OR wf.acted_at IS DISTINCT FROM NEW.approved_at THEN
   RAISE EXCEPTION 'T36 independent Workflow approval mismatch' USING ERRCODE='23514';
 END IF;
 -- Audit owner receipt is immutable and must bind the same exact Workflow
 -- decision, target, reviewer, action and typed event. No synthetic Audit UUID.
 SELECT e.id INTO audit_row
 FROM public.hidra_audit_event e
 JOIN public.hidra_audit_catalog_entry kind ON kind.id=e.event_type_id
 WHERE e.id=NEW.audit_event_id AND e.target_module='topology'
   AND e.target_type='OPERATING_PRESSURE_RESTRICTION'
   AND e.target_id=target_ref AND e.workflow_action_id=NEW.decision_action_id
   AND e.actor_id=NEW.reviewed_by AND e.action_code=expected_action
   AND e.operation='APPROVE' AND kind.catalog_name='EVENT_TYPE'
   AND kind.code=expected_event AND e.occurred_at<=NEW.recorded_at;
 IF NOT FOUND THEN
   RAISE EXCEPTION 'T36 Audit event binding mismatch' USING ERRCODE='23514';
 END IF;
 IF TG_TABLE_NAME='hidra_topology_t36_pressure_termination' THEN
   SELECT * INTO target_row FROM public.hidra_topology_t36_pressure_revision r
   WHERE r.revision_id=NEW.retired_revision_id;
   IF NOT FOUND OR target_row.pipeline_system_id<>NEW.pipeline_system_id
       OR NEW.effective_at<target_row.effective_from THEN
     RAISE EXCEPTION 'T36 invalid prior effective revision' USING ERRCODE='23514';
   END IF;
   IF NEW.event_kind='SUPERSEDE' AND NOT EXISTS(
      SELECT 1 FROM public.hidra_topology_t36_pressure_revision next
      WHERE next.revision_id=NEW.successor_revision_id
        AND next.supersedes_revision_id=NEW.retired_revision_id
        AND next.effective_from=NEW.effective_at) THEN
     RAISE EXCEPTION 'T36 successor effective lineage mismatch' USING ERRCODE='23514';
   END IF;
 ELSIF NEW.supersedes_revision_id IS NOT NULL AND NOT EXISTS(
    SELECT 1 FROM public.hidra_topology_t36_pressure_revision prior
    WHERE prior.revision_id=NEW.supersedes_revision_id
      AND (prior.company_party_id,prior.pipeline_id,prior.section_id,
           prior.pipeline_system_id,prior.condition_code)
          =(NEW.company_party_id,NEW.pipeline_id,NEW.section_id,
            NEW.pipeline_system_id,NEW.condition_code)) THEN
    RAISE EXCEPTION 'T36 successor scope mismatch' USING ERRCODE='23514';
 END IF;
 RETURN NEW;
END;
$$;

CREATE TRIGGER t36_revision_qualify BEFORE INSERT ON public.hidra_topology_t36_pressure_revision
 FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_t36_admission_guard();
CREATE TRIGGER t36_termination_qualify BEFORE INSERT ON public.hidra_topology_t36_pressure_termination
 FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_t36_admission_guard();

CREATE FUNCTION public.hidra_topology_t36_immutable()
RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog,pg_temp AS $$
BEGIN RAISE EXCEPTION 'T36 qualified restriction history is immutable' USING ERRCODE='23514';
END; $$;
CREATE TRIGGER t36_conditions_immutable BEFORE UPDATE OR DELETE ON public.hidra_topology_t36_condition_policy
 FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_t36_immutable();
CREATE TRIGGER t36_conditions_truncate BEFORE TRUNCATE ON public.hidra_topology_t36_condition_policy
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_t36_immutable();
CREATE TRIGGER t36_revisions_immutable BEFORE UPDATE OR DELETE ON public.hidra_topology_t36_pressure_revision
 FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_t36_immutable();
CREATE TRIGGER t36_revisions_truncate BEFORE TRUNCATE ON public.hidra_topology_t36_pressure_revision
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_t36_immutable();
CREATE TRIGGER t36_terminations_immutable BEFORE UPDATE OR DELETE ON public.hidra_topology_t36_pressure_termination
 FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_t36_immutable();
CREATE TRIGGER t36_terminations_truncate BEFORE TRUNCATE ON public.hidra_topology_t36_pressure_termination
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_t36_immutable();

REVOKE ALL ON public.hidra_topology_t36_condition_policy,
 public.hidra_topology_t36_pressure_revision,
 public.hidra_topology_t36_pressure_termination FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_t36_admission_guard() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_t36_immutable() FROM PUBLIC;
