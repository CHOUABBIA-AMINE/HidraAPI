-- T36 CI repair: create a real unapproved Topology-owned Workflow target.
-- This empty store is an immutable proposed engineering decision, NEVER an
-- approved pressure limit or a T38 writer. No role grants or business data seed.
-- Preserve V20261121_001 exactly; only add constraints to its insert admission.

CREATE TABLE public.hidra_topology_t36_pressure_candidate (
 candidate_id uuid PRIMARY KEY,
 candidate_kind varchar(16) NOT NULL
     CHECK (candidate_kind IN ('REVISION','SUPERSEDE','WITHDRAW')),
 company_party_id varchar(80) NOT NULL
     REFERENCES public.hidra_party_party(id),
 pipeline_id varchar(80) NOT NULL
     REFERENCES public.hidra_topology_pipeline(id),
 section_id varchar(80) NOT NULL DEFAULT '',
 pipeline_system_id varchar(80) NOT NULL
     REFERENCES public.hidra_topology_pipeline_system(id),
 condition_code varchar(80) NOT NULL
     CHECK (condition_code ~ '^[A-Za-z0-9][A-Za-z0-9_.:-]{0,79}$'),
 upper_pressure numeric(24,12),
 pressure_unit varchar(80),
 pressure_basis varchar(16),
 effective_from timestamptz NOT NULL,
 effective_until timestamptz,
 retired_revision_id uuid
     REFERENCES public.hidra_topology_t36_pressure_revision(revision_id),
 successor_revision_id uuid,
 baseline_receipt_id uuid NOT NULL,
 baseline_manifest_digest char(64) NOT NULL
     CHECK (baseline_manifest_digest ~ '^[0-9a-f]{64}$'),
 engineering_source_revision varchar(80) NOT NULL
     CHECK (engineering_source_revision ~ '^[A-Za-z0-9][A-Za-z0-9_.:-]{0,79}$'),
 submitted_by varchar(80) NOT NULL
     CHECK (submitted_by ~ '^[A-Za-z0-9][A-Za-z0-9_.:-]{0,79}$'),
 created_at timestamptz NOT NULL,
 CONSTRAINT t36_candidate_shape CHECK (
     (candidate_kind='REVISION'
      AND upper_pressure IS NOT NULL AND upper_pressure>0 AND pressure_unit IS NOT NULL
      AND pressure_basis IN ('GAUGE','ABSOLUTE')
      AND successor_revision_id IS NULL
      AND (effective_until IS NULL OR effective_until>effective_from))
   OR
     (candidate_kind='SUPERSEDE' AND upper_pressure IS NULL
      AND pressure_unit IS NULL AND pressure_basis IS NULL
      AND effective_until IS NULL AND retired_revision_id IS NOT NULL
      AND successor_revision_id IS NOT NULL
      AND successor_revision_id<>retired_revision_id)
   OR
     (candidate_kind='WITHDRAW' AND upper_pressure IS NULL
      AND pressure_unit IS NULL AND pressure_basis IS NULL
      AND effective_until IS NULL AND retired_revision_id IS NOT NULL
      AND successor_revision_id IS NULL)
 ),
 CONSTRAINT t36_candidate_no_self_retirement
    CHECK(retired_revision_id IS NULL OR retired_revision_id<>candidate_id),
 CONSTRAINT t36_candidate_pressure_policy
    FOREIGN KEY(company_party_id,condition_code,pressure_unit)
    REFERENCES public.hidra_topology_t36_condition_policy
    (company_party_id,condition_code,pressure_unit),
 CONSTRAINT t36_candidate_exact_source
    FOREIGN KEY(company_party_id,pipeline_system_id,baseline_receipt_id)
    REFERENCES public.hidra_topology_baseline_publication
    (company_party_id,pipeline_system_id,receipt_id)
);

-- Candidate creation is an owner-authorized T38 concern; before T38, only
-- disposable migration-test owners insert synthetic candidate declarations.
-- The trigger rejects source spoofing and wrong canonical target *before*
-- any Workflow review can discover a pending candidate.
CREATE FUNCTION public.hidra_topology_t36_candidate_admission()
RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER
SET search_path=pg_catalog,pg_temp
AS $$
DECLARE actual_company text; actual_system text; actual_digest text;
BEGIN
 IF NEW.created_at<>date_trunc('microseconds',NEW.created_at)
    OR NEW.effective_from<>date_trunc('microseconds',NEW.effective_from)
    OR NEW.effective_until IS NOT NULL
         AND NEW.effective_until<>date_trunc('microseconds',NEW.effective_until) THEN
   RAISE EXCEPTION 'T36 candidate requires exact microsecond instants'
       USING ERRCODE='23514';
 END IF;
 SELECT r.company_party_id,p.pipeline_system_id
 INTO actual_company,actual_system
 FROM public.hidra_topology_pipeline p
 JOIN public.hidra_topology_pipeline_system sys
     ON sys.id=p.pipeline_system_id AND sys.status='ACTIVE'
 JOIN public.hidra_topology_foundation_system_owner o
     ON o.pipeline_system_id=sys.id
 JOIN public.hidra_topology_pipeline_authoring_revision r
     ON r.pipeline_id=p.id AND r.pipeline_system_id=sys.id
 WHERE p.id=NEW.pipeline_id AND p.status='ACTIVE'
   AND r.revision=(SELECT max(x.revision)
        FROM public.hidra_topology_pipeline_authoring_revision x
        WHERE x.pipeline_id=p.id)
   AND r.company_party_id=o.company_party_id;
 IF NOT FOUND OR actual_company<>NEW.company_party_id
       OR actual_system<>NEW.pipeline_system_id
       OR NEW.section_id<>'' AND NOT EXISTS (
         SELECT 1 FROM public.hidra_topology_pipeline_segment seg
         WHERE seg.id=NEW.section_id AND seg.pipeline_id=NEW.pipeline_id
           AND seg.status='ACTIVE') THEN
   RAISE EXCEPTION 'T36 candidate canonical target unavailable'
       USING ERRCODE='23514';
 END IF;
 SELECT s.canonical_sha256 INTO actual_digest
 FROM public.hidra_topology_baseline_publication p
 JOIN public.hidra_topology_baseline_submission s
 ON s.company_party_id=p.company_party_id
 AND s.pipeline_system_id=p.pipeline_system_id
 AND s.submission_id=p.submission_id
 WHERE p.receipt_id=NEW.baseline_receipt_id
   AND p.company_party_id=NEW.company_party_id
   AND p.pipeline_system_id=NEW.pipeline_system_id
   AND p.published_at<=NEW.created_at;
 IF NOT FOUND OR actual_digest<>NEW.baseline_manifest_digest THEN
   RAISE EXCEPTION 'T36 candidate source publication mismatch' USING ERRCODE='23514';
 END IF;
 IF NOT EXISTS(
   SELECT 1 FROM public.hidra_topology_t36_condition_policy policy
   WHERE policy.company_party_id=NEW.company_party_id
     AND policy.condition_code=NEW.condition_code
     AND (NEW.candidate_kind<>'REVISION' OR policy.pressure_unit=NEW.pressure_unit))
 THEN RAISE EXCEPTION 'T36 candidate pressure condition not admitted'
       USING ERRCODE='23514';
 END IF;
 RETURN NEW;
END;
$$;
CREATE TRIGGER t36_candidate_admission BEFORE INSERT
 ON public.hidra_topology_t36_pressure_candidate
 FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_t36_candidate_admission();

CREATE TRIGGER t36_candidate_immutable BEFORE UPDATE OR DELETE
 ON public.hidra_topology_t36_pressure_candidate
 FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_t36_immutable();
CREATE TRIGGER t36_candidate_no_truncate BEFORE TRUNCATE
 ON public.hidra_topology_t36_pressure_candidate
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_t36_immutable();

-- A Workflow decision for a raw arbitrary target UUID cannot qualify a limit.
-- The exact pre-review content and source are compared with the later
-- immutable, separately approved publication, including successor lineage.
CREATE FUNCTION public.hidra_topology_t36_candidate_publication_guard()
RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER
SET search_path=pg_catalog,pg_temp
AS $$
DECLARE approved_id uuid; proposed record;
BEGIN
 approved_id:=CASE
     WHEN TG_TABLE_NAME='hidra_topology_t36_pressure_revision' THEN NEW.revision_id
     ELSE NEW.event_id END;
 SELECT c.* INTO proposed
 FROM public.hidra_topology_t36_pressure_candidate c
 WHERE c.candidate_id=approved_id;
 IF NOT FOUND THEN
   RAISE EXCEPTION 'T36 immutable review target missing' USING ERRCODE='23514';
 END IF;
 IF (proposed.company_party_id,proposed.pipeline_id,proposed.section_id,
       proposed.pipeline_system_id,proposed.condition_code,
       proposed.baseline_receipt_id,proposed.baseline_manifest_digest,
       proposed.engineering_source_revision,proposed.submitted_by)
    IS DISTINCT FROM
       (NEW.company_party_id,NEW.pipeline_id,NEW.section_id,
        NEW.pipeline_system_id,NEW.condition_code,
        NEW.baseline_receipt_id,NEW.baseline_manifest_digest,
        NEW.engineering_source_revision,NEW.submitted_by)
    OR proposed.created_at>NEW.approved_at
    OR NOT EXISTS (
        SELECT 1 FROM public.hidra_workflow_action a
        JOIN public.hidra_workflow_instance i ON i.id=a.instance_id
        WHERE a.id=NEW.decision_action_id
          AND i.target_id=approved_id::text
          AND i.target_code_snapshot='t36-v1:'||approved_id::text)
 THEN
   RAISE EXCEPTION 'T36 approved action does not bind exact candidate'
       USING ERRCODE='23514';
 END IF;
 IF TG_TABLE_NAME='hidra_topology_t36_pressure_revision' THEN
   IF proposed.candidate_kind<>'REVISION'
       OR (proposed.upper_pressure,proposed.pressure_unit,proposed.pressure_basis,
           proposed.effective_from,proposed.effective_until,
           proposed.retired_revision_id)
       IS DISTINCT FROM
          (NEW.upper_pressure,NEW.pressure_unit,NEW.pressure_basis,
           NEW.effective_from,NEW.effective_until,NEW.supersedes_revision_id)
   THEN RAISE EXCEPTION 'T36 reviewed pressure proposal differs from publication'
       USING ERRCODE='23514'; END IF;
 ELSE
   IF proposed.candidate_kind<>NEW.event_kind
       OR (proposed.effective_from,proposed.retired_revision_id,
           proposed.successor_revision_id)
       IS DISTINCT FROM
          (NEW.effective_at,NEW.retired_revision_id,NEW.successor_revision_id)
   THEN RAISE EXCEPTION 'T36 reviewed termination differs from publication'
       USING ERRCODE='23514'; END IF;
 END IF;
 RETURN NEW;
END;
$$;
CREATE TRIGGER z_t36_revision_candidate_publication BEFORE INSERT
 ON public.hidra_topology_t36_pressure_revision
 FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_t36_candidate_publication_guard();
CREATE TRIGGER z_t36_termination_candidate_publication BEFORE INSERT
 ON public.hidra_topology_t36_pressure_termination
 FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_t36_candidate_publication_guard();

REVOKE ALL ON public.hidra_topology_t36_pressure_candidate FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_t36_candidate_admission() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_t36_candidate_publication_guard() FROM PUBLIC;
