-- T12: Alarm-owned deterministic manual trigger and retained source provenance.
-- Does not write or reference a Monitoring-owned relation.
CREATE TABLE public.hidra_alarm_evaluation_trigger (
    evaluation_id varchar(80) PRIMARY KEY,
    alarm_id varchar(80) NOT NULL UNIQUE REFERENCES public.hidra_alarm(id) ON DELETE RESTRICT,
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
    alarm_type_id varchar(80) NOT NULL,
    severity_id varchar(80) NOT NULL,
    title_fr varchar(255) NOT NULL CHECK (btrim(title_fr) <> ''),
    actor_id varchar(80) NOT NULL,
    created_at timestamptz NOT NULL CHECK (isfinite(created_at)),
    CHECK (btrim(evaluation_id) <> '' AND btrim(rule_id) <> ''
        AND btrim(company_party_id) <> '' AND btrim(asset_type) <> ''
        AND btrim(asset_id) <> '' AND btrim(unit_id) <> ''
        AND btrim(target_id) <> '' AND btrim(approval_id) <> ''
        AND btrim(trusted_reading_id) <> '' AND btrim(actor_id) <> '')
);

CREATE INDEX ix_t12_trigger_rule ON public.hidra_alarm_evaluation_trigger(rule_id, rule_revision);

CREATE FUNCTION public.t12_trigger_insert_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM public.hidra_alarm a WHERE a.id = NEW.alarm_id
            AND a.source_type = 'MANUAL'
            AND a.monitoring_evaluation_id = NEW.evaluation_id
            AND a.source_reference_id IS NULL
            AND a.topology_asset_type_code = NEW.asset_type
            AND a.topology_asset_id = NEW.asset_id
            AND a.alarm_type_id = NEW.alarm_type_id
            AND a.severity_id = NEW.severity_id
            AND a.title_fr = NEW.title_fr
    ) THEN
        RAISE EXCEPTION 'T12: Alarm trigger and original Alarm identity disagree';
    END IF;
    RETURN NEW;
END $$;

CREATE TRIGGER trg_t12_trigger_insert_guard
    BEFORE INSERT ON public.hidra_alarm_evaluation_trigger
    FOR EACH ROW EXECUTE FUNCTION public.t12_trigger_insert_guard();

CREATE FUNCTION public.t12_trigger_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    RAISE EXCEPTION 'T12: immutable evaluation-trigger evidence';
END $$;

CREATE TRIGGER trg_t12_trigger_no_change
    BEFORE UPDATE OR DELETE ON public.hidra_alarm_evaluation_trigger
    FOR EACH ROW EXECUTE FUNCTION public.t12_trigger_immutable();
CREATE TRIGGER trg_t12_trigger_no_truncate
    BEFORE TRUNCATE ON public.hidra_alarm_evaluation_trigger
    FOR EACH STATEMENT EXECUTE FUNCTION public.t12_trigger_immutable();

-- Only freeze T12-origin provenance, not ordinary Alarm state or lifecycle transitions.
CREATE FUNCTION public.t12_alarm_origin_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM public.hidra_alarm_evaluation_trigger t WHERE t.alarm_id = OLD.id)
        AND (NEW.monitoring_evaluation_id IS DISTINCT FROM OLD.monitoring_evaluation_id
          OR NEW.source_type IS DISTINCT FROM OLD.source_type
          OR NEW.source_reference_id IS DISTINCT FROM OLD.source_reference_id
          OR NEW.topology_asset_type_code IS DISTINCT FROM OLD.topology_asset_type_code
          OR NEW.topology_asset_id IS DISTINCT FROM OLD.topology_asset_id
          OR NEW.alarm_type_id IS DISTINCT FROM OLD.alarm_type_id
          OR NEW.severity_id IS DISTINCT FROM OLD.severity_id
          OR NEW.title_fr IS DISTINCT FROM OLD.title_fr
          OR NEW.first_detected_at IS DISTINCT FROM OLD.first_detected_at) THEN
        RAISE EXCEPTION 'T12: linked Alarm origin is immutable';
    END IF;
    RETURN NEW;
END $$;

CREATE TRIGGER trg_t12_alarm_origin_guard
    BEFORE UPDATE ON public.hidra_alarm
    FOR EACH ROW EXECUTE FUNCTION public.t12_alarm_origin_immutable();

CREATE FUNCTION public.t12_alarm_evidence_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        IF NEW.evidence_type = 'MONITORING_EVALUATION'
            AND EXISTS (SELECT 1 FROM public.hidra_alarm_evaluation_trigger t WHERE t.alarm_id = NEW.alarm_id)
            AND NOT EXISTS (SELECT 1 FROM public.hidra_alarm_evaluation_trigger t
                WHERE t.alarm_id = NEW.alarm_id AND t.evaluation_id = NEW.evidence_reference_id) THEN
            RAISE EXCEPTION 'T12: evidence link must use original evaluation identity';
        END IF;
        RETURN NEW;
    END IF;
    IF OLD.evidence_type = 'MONITORING_EVALUATION'
        AND EXISTS (SELECT 1 FROM public.hidra_alarm_evaluation_trigger t WHERE t.alarm_id = OLD.alarm_id) THEN
        RAISE EXCEPTION 'T12: source evidence link is immutable';
    END IF;
    IF TG_OP = 'UPDATE' THEN
        RETURN NEW;
    END IF;
    RETURN OLD;
END $$;

CREATE TRIGGER trg_t12_evidence_guard
    BEFORE INSERT OR UPDATE OR DELETE ON public.hidra_alarm_evidence_link
    FOR EACH ROW EXECUTE FUNCTION public.t12_alarm_evidence_guard();

CREATE UNIQUE INDEX uq_t12_alarm_evaluation_link
    ON public.hidra_alarm_evidence_link (alarm_id, evidence_reference_id)
    WHERE evidence_type = 'MONITORING_EVALUATION';

CREATE FUNCTION public.t12_alarm_evidence_no_truncate() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM public.hidra_alarm_evidence_link l
        JOIN public.hidra_alarm_evaluation_trigger t ON t.alarm_id = l.alarm_id
        WHERE l.evidence_type = 'MONITORING_EVALUATION') THEN
        RAISE EXCEPTION 'T12: protected evidence cannot be truncated';
    END IF;
    RETURN NULL;
END $$;
CREATE TRIGGER trg_t12_evidence_no_truncate
    BEFORE TRUNCATE ON public.hidra_alarm_evidence_link
    FOR EACH STATEMENT EXECUTE FUNCTION public.t12_alarm_evidence_no_truncate();
