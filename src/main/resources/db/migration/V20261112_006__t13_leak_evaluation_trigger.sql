-- T13 human-declared suspected leak identity. Does not assert any leak-physics result.
-- Monitoring remains the authoritative owner of evaluation records (no cross-module FK).
CREATE TABLE public.hidra_leak_detection_evaluation_trigger (
  evaluation_id varchar(80) PRIMARY KEY,
  candidate_id varchar(80) NOT NULL UNIQUE
    REFERENCES public.hidra_leak_detection_candidate(id) ON DELETE RESTRICT,
  rule_id varchar(80) NOT NULL,
  rule_revision bigint NOT NULL CHECK (rule_revision > 0),
  company_party_id varchar(80) NOT NULL,
  asset_type varchar(80) NOT NULL,
  asset_id varchar(80) NOT NULL,
  unit_id varchar(80) NOT NULL,
  target_id varchar(80) NOT NULL,
  approval_id varchar(80) NOT NULL,
  trusted_reading_id varchar(80) NOT NULL,
  evaluated_at timestamptz NOT NULL CHECK (isfinite(evaluated_at)),
  profile_id varchar(80) NOT NULL,
  declared_confidence numeric(10,6) NOT NULL
    CHECK (declared_confidence >= 0 AND declared_confidence <= 1),
  reason varchar(500) NOT NULL CHECK (btrim(reason) <> ''),
  actor_id varchar(80) NOT NULL,
  created_at timestamptz NOT NULL CHECK (isfinite(created_at)),
  CHECK (btrim(evaluation_id) <> '' AND btrim(rule_id) <> '' AND
    btrim(company_party_id) <> '' AND btrim(asset_type) <> '' AND btrim(asset_id) <> ''
    AND btrim(unit_id) <> '' AND btrim(target_id) <> '' AND btrim(approval_id) <> ''
    AND btrim(trusted_reading_id) <> '' AND btrim(profile_id) <> '' AND btrim(actor_id) <> '')
);
CREATE INDEX ix_t13_leak_trigger_rule ON public.hidra_leak_detection_evaluation_trigger(rule_id,rule_revision);

CREATE FUNCTION public.t13_leak_trigger_insert_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM public.hidra_leak_detection_candidate c
      WHERE c.id = NEW.candidate_id AND c.profile_id = NEW.profile_id
        AND c.run_id IS NULL AND c.status = 'NEW'
        AND c.topology_asset_type = NEW.asset_type
        AND c.topology_asset_id = NEW.asset_id
        AND c.suspected_at = NEW.evaluated_at
        AND c.first_evidence_at = NEW.evaluated_at
        AND c.confidence_score = NEW.declared_confidence
        AND c.summary = NEW.reason
  ) THEN
    RAISE EXCEPTION 'T13: candidate origin and immutable trigger disagree';
  END IF;
  RETURN NEW;
END $$;
CREATE TRIGGER trg_t13_leak_trigger_insert_guard
  BEFORE INSERT ON public.hidra_leak_detection_evaluation_trigger
  FOR EACH ROW EXECUTE FUNCTION public.t13_leak_trigger_insert_guard();

CREATE FUNCTION public.t13_leak_trigger_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
  RAISE EXCEPTION 'T13: immutable evaluation-trigger evidence';
END $$;
CREATE TRIGGER trg_t13_leak_trigger_no_change
  BEFORE UPDATE OR DELETE ON public.hidra_leak_detection_evaluation_trigger
  FOR EACH ROW EXECUTE FUNCTION public.t13_leak_trigger_immutable();
CREATE TRIGGER trg_t13_leak_trigger_no_truncate
  BEFORE TRUNCATE ON public.hidra_leak_detection_evaluation_trigger
  FOR EACH STATEMENT EXECUTE FUNCTION public.t13_leak_trigger_immutable();

CREATE FUNCTION public.t13_leak_candidate_origin_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
  IF EXISTS (SELECT 1 FROM public.hidra_leak_detection_evaluation_trigger t
             WHERE t.candidate_id = OLD.id)
      AND (NEW.profile_id IS DISTINCT FROM OLD.profile_id
       OR NEW.run_id IS DISTINCT FROM OLD.run_id
       OR NEW.candidate_number IS DISTINCT FROM OLD.candidate_number
       OR NEW.topology_asset_type IS DISTINCT FROM OLD.topology_asset_type
       OR NEW.topology_asset_id IS DISTINCT FROM OLD.topology_asset_id
       OR NEW.topology_asset_code IS DISTINCT FROM OLD.topology_asset_code
       OR NEW.suspected_at IS DISTINCT FROM OLD.suspected_at
       OR NEW.first_evidence_at IS DISTINCT FROM OLD.first_evidence_at
       OR NEW.confidence_score IS DISTINCT FROM OLD.confidence_score
       OR NEW.severity_level IS DISTINCT FROM OLD.severity_level
       OR NEW.summary IS DISTINCT FROM OLD.summary) THEN
    RAISE EXCEPTION 'T13: linked candidate origin is immutable';
  END IF;
  RETURN NEW;
END $$;
CREATE TRIGGER trg_t13_leak_candidate_origin_guard
  BEFORE UPDATE ON public.hidra_leak_detection_candidate
  FOR EACH ROW EXECUTE FUNCTION public.t13_leak_candidate_origin_immutable();

CREATE UNIQUE INDEX uq_t13_leak_monitoring_evidence
  ON public.hidra_leak_detection_evidence_link(candidate_id,evidence_reference_id)
  WHERE evidence_type = 'MONITORING_DEVIATION';

CREATE FUNCTION public.t13_leak_evidence_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
  IF TG_OP='INSERT' THEN
    IF NEW.evidence_type='MONITORING_DEVIATION'
       AND EXISTS (SELECT 1 FROM public.hidra_leak_detection_evaluation_trigger t
                   WHERE t.candidate_id = NEW.candidate_id)
       AND NOT EXISTS (SELECT 1 FROM public.hidra_leak_detection_evaluation_trigger t
                       WHERE t.candidate_id = NEW.candidate_id AND t.evaluation_id = NEW.evidence_reference_id) THEN
      RAISE EXCEPTION 'T13: evidence must refer to original evaluation';
    END IF;
    RETURN NEW;
  END IF;
  IF OLD.evidence_type='MONITORING_DEVIATION' AND
     EXISTS (SELECT 1 FROM public.hidra_leak_detection_evaluation_trigger t
             WHERE t.candidate_id=OLD.candidate_id) THEN
    RAISE EXCEPTION 'T13: linked evidence is immutable';
  END IF;
  IF TG_OP='UPDATE' THEN RETURN NEW; END IF;
  RETURN OLD;
END $$;
CREATE TRIGGER trg_t13_leak_evidence_guard
  BEFORE INSERT OR UPDATE OR DELETE ON public.hidra_leak_detection_evidence_link
  FOR EACH ROW EXECUTE FUNCTION public.t13_leak_evidence_guard();

CREATE FUNCTION public.t13_leak_evidence_no_truncate() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
  IF EXISTS (SELECT 1 FROM public.hidra_leak_detection_evidence_link l
             JOIN public.hidra_leak_detection_evaluation_trigger t ON t.candidate_id=l.candidate_id
             WHERE l.evidence_type='MONITORING_DEVIATION') THEN
    RAISE EXCEPTION 'T13: protected evidence cannot be truncated';
  END IF;
  RETURN NULL;
END $$;
CREATE TRIGGER trg_t13_leak_evidence_no_truncate
  BEFORE TRUNCATE ON public.hidra_leak_detection_evidence_link
  FOR EACH STATEMENT EXECUTE FUNCTION public.t13_leak_evidence_no_truncate();
