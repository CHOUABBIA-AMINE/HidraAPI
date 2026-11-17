-- T23: forward-only synthetic Analytics metric execution; no operator grants or historical backfill.
-- Both capabilities require a separately authorized current LOCAL principal and paired exact scopes.
INSERT INTO public.hidra_identity_permission
    (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
    ('t23-deviation-execute','analytics:deviation-metric:execute','analytics','DEVIATION_METRIC','execute',true,'ACTIVE',now(),now()),
    ('t23-deviation-read','analytics:deviation-metric:read','analytics','DEVIATION_METRIC','read',true,'ACTIVE',now(),now())
ON CONFLICT (code) DO NOTHING;

CREATE TABLE public.hidra_analytics_deviation_metric_receipt (
    run_id varchar(80) PRIMARY KEY
        REFERENCES public.hidra_analytics_metric_evaluation_run(id) ON DELETE RESTRICT,
    value_id varchar(80) UNIQUE
        REFERENCES public.hidra_analytics_metric_value(id) ON DELETE RESTRICT,
    company_party_id varchar(96) NOT NULL CHECK (length(btrim(company_party_id))>0),
    facility_id varchar(80) NOT NULL CHECK (length(btrim(facility_id))>0),
    definition_id varchar(80) NOT NULL
        REFERENCES public.hidra_analytics_metric_definition(id) ON DELETE RESTRICT,
    definition_version_id varchar(80) NOT NULL
        REFERENCES public.hidra_analytics_metric_definition_version(id) ON DELETE RESTRICT,
    evaluation_id varchar(80) NOT NULL CHECK (length(btrim(evaluation_id))>0),
    rule_id varchar(80) NOT NULL CHECK (length(btrim(rule_id))>0),
    rule_revision bigint NOT NULL CHECK (rule_revision>=1),
    telemetry_point_id varchar(80) NOT NULL,
    source_outcome varchar(48) NOT NULL CHECK (length(btrim(source_outcome))>0),
    unit_id varchar(80) NOT NULL,
    actual_value numeric,
    expected_value numeric,
    target_id varchar(80),
    target_revision_id varchar(80),
    approval_id varchar(80),
    trusted_reading_id varchar(80),
    raw_reading_id varchar(80),
    assessment_id varchar(80),
    binding_id varchar(80),
    evaluated_at timestamptz NOT NULL,
    source_recorded_at timestamptz NOT NULL,
    method_revision varchar(12) NOT NULL CHECK (method_revision='1'),
    actor_id varchar(80) NOT NULL CHECK (length(btrim(actor_id))>0),
    created_at timestamptz NOT NULL,
    CONSTRAINT uq_t23_deviation_replay UNIQUE
        (company_party_id,facility_id,definition_version_id,evaluation_id)
);
CREATE INDEX ix_t23_deviation_scoped_read
    ON public.hidra_analytics_deviation_metric_receipt(company_party_id,facility_id,run_id);

-- Preserve original run/value and their immutable receipt. Check both sides at transaction commit,
-- after Spring Data and JDBC have flushed, including any generic API aliases.
CREATE FUNCTION public.hidra_analytics_t23_verify() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE
    rid varchar(80);
    saved public.hidra_analytics_deviation_metric_receipt%ROWTYPE;
    parent public.hidra_analytics_metric_evaluation_run%ROWTYPE;
    def record;
    value_row public.hidra_analytics_metric_value%ROWTYPE;
    count_values bigint;
    reserved_method boolean;
BEGIN
    rid := CASE WHEN TG_TABLE_NAME='hidra_analytics_deviation_metric_receipt'
                THEN NEW.run_id
                WHEN TG_TABLE_NAME='hidra_analytics_metric_evaluation_run' THEN NEW.id
                ELSE NEW.metric_evaluation_run_id END;
    SELECT * INTO saved FROM public.hidra_analytics_deviation_metric_receipt WHERE run_id=rid;
    IF NOT FOUND THEN
        -- No ordinary starter or alias can produce a reserved T23 run without its receipt.
        IF TG_TABLE_NAME='hidra_analytics_metric_evaluation_run' THEN
            -- Missing legacy catalog rows are not classified as T23.
            SELECT EXISTS (
                SELECT 1
                FROM public.hidra_analytics_metric_definition_version v
                JOIN public.hidra_analytics_metric_definition d
                  ON d.id=v.metric_definition_id
                WHERE v.id=NEW.metric_definition_version_id
                  AND d.code='ABSOLUTE_DEVIATION'
                  AND v.version_number=1
                  AND v.formula_expression='ABSOLUTE_DEVIATION_V1'
            ) INTO reserved_method;
            IF reserved_method THEN
                RAISE EXCEPTION 'T23 reserved metric run requires sealed owner receipt';
            END IF;
        END IF;
        RETURN NEW;
    END IF;

    SELECT * INTO parent FROM public.hidra_analytics_metric_evaluation_run WHERE id=rid;
    IF NOT FOUND OR parent.metric_definition_version_id IS DISTINCT FROM saved.definition_version_id
         OR parent.scope_type IS DISTINCT FROM 'FACILITY' OR parent.scope_id IS DISTINCT FROM saved.facility_id
         OR parent.period_start IS DISTINCT FROM saved.evaluated_at
         OR parent.period_end IS DISTINCT FROM saved.evaluated_at
         OR parent.run_status NOT IN ('COMPLETED','FAILED')
         OR parent.completed_at IS NULL OR parent.records_read IS DISTINCT FROM 1 THEN
        RAISE EXCEPTION 'T23 run/receipt scope, period or terminal state mismatch';
    END IF;
    SELECT d.code,d.id AS def_id,d.unit_id,v.version_number,v.formula_expression INTO def
      FROM public.hidra_analytics_metric_definition_version v
      JOIN public.hidra_analytics_metric_definition d ON d.id=v.metric_definition_id
      WHERE v.id=saved.definition_version_id;
    IF NOT FOUND OR def.code IS DISTINCT FROM 'ABSOLUTE_DEVIATION' OR def.version_number IS DISTINCT FROM 1
         OR def.formula_expression IS DISTINCT FROM 'ABSOLUTE_DEVIATION_V1'
         OR def.def_id IS DISTINCT FROM saved.definition_id OR def.unit_id IS DISTINCT FROM saved.unit_id THEN
        RAISE EXCEPTION 'T23 receipt has wrong metric method or version';
    END IF;
    SELECT count(*) INTO count_values FROM public.hidra_analytics_metric_value WHERE metric_evaluation_run_id=rid;
    IF parent.run_status='COMPLETED' THEN
        IF saved.source_outcome<>'COMPUTED' OR saved.actual_value IS NULL
            OR saved.expected_value IS NULL OR saved.value_id IS NULL
            OR parent.error_code IS NOT NULL OR parent.records_produced IS DISTINCT FROM 1
            OR count_values<>1 THEN
            RAISE EXCEPTION 'T23 successful run must have exactly one qualified value';
        END IF;
        SELECT * INTO value_row FROM public.hidra_analytics_metric_value WHERE id=saved.value_id;
        IF NOT FOUND OR value_row.metric_evaluation_run_id IS DISTINCT FROM rid
            OR value_row.metric_definition_id IS DISTINCT FROM saved.definition_id
            OR value_row.metric_definition_version_id IS DISTINCT FROM saved.definition_version_id
            OR value_row.scope_type IS DISTINCT FROM 'FACILITY' OR value_row.scope_id IS DISTINCT FROM saved.facility_id
            OR value_row.period_start IS DISTINCT FROM saved.evaluated_at
            OR value_row.period_end IS DISTINCT FROM saved.evaluated_at OR value_row.unit_id IS DISTINCT FROM saved.unit_id
            OR value_row.value_numeric IS NULL OR value_row.value_text IS NOT NULL
            OR value_row.quality_status IS DISTINCT FROM 'READY'
            OR value_row.value_numeric IS DISTINCT FROM abs(saved.actual_value-saved.expected_value) THEN
            RAISE EXCEPTION 'T23 sealed numeric value does not match source evidence';
        END IF;
    ELSE
        IF saved.value_id IS NOT NULL OR count_values<>0 OR parent.records_produced IS DISTINCT FROM 0
            OR parent.error_code NOT IN ('SOURCE_UNAVAILABLE','VALUE_OUT_OF_RANGE') THEN
            RAISE EXCEPTION 'T23 failed run must not produce a value';
        END IF;
    END IF;
    RETURN NEW;
END $$;

CREATE CONSTRAINT TRIGGER tr_t23_run_sealed
    AFTER INSERT OR UPDATE ON public.hidra_analytics_metric_evaluation_run
    DEFERRABLE INITIALLY DEFERRED FOR EACH ROW
    EXECUTE FUNCTION public.hidra_analytics_t23_verify();
CREATE CONSTRAINT TRIGGER tr_t23_receipt_sealed
    AFTER INSERT ON public.hidra_analytics_deviation_metric_receipt
    DEFERRABLE INITIALLY DEFERRED FOR EACH ROW
    EXECUTE FUNCTION public.hidra_analytics_t23_verify();
CREATE CONSTRAINT TRIGGER tr_t23_value_sealed
    AFTER INSERT OR UPDATE ON public.hidra_analytics_metric_value
    DEFERRABLE INITIALLY DEFERRED FOR EACH ROW
    EXECUTE FUNCTION public.hidra_analytics_t23_verify();

CREATE FUNCTION public.hidra_analytics_t23_frozen() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE
    rid varchar(80);
BEGIN
    IF TG_TABLE_NAME='hidra_analytics_deviation_metric_receipt' THEN
        RAISE EXCEPTION 'T23 immutable receipt cannot be changed';
    END IF;
    IF TG_TABLE_NAME='hidra_analytics_metric_evaluation_run' THEN
        rid:=CASE WHEN TG_OP='DELETE' THEN OLD.id ELSE NEW.id END;
    ELSE
        rid:=CASE WHEN TG_OP='DELETE' THEN OLD.metric_evaluation_run_id ELSE NEW.metric_evaluation_run_id END;
        IF TG_OP='UPDATE' AND OLD.metric_evaluation_run_id<>NEW.metric_evaluation_run_id
           AND EXISTS (SELECT 1 FROM public.hidra_analytics_deviation_metric_receipt
                       WHERE run_id=OLD.metric_evaluation_run_id) THEN
            RAISE EXCEPTION 'T23 linked value cannot be reparented';
        END IF;
    END IF;
    IF EXISTS (SELECT 1 FROM public.hidra_analytics_deviation_metric_receipt WHERE run_id=rid) THEN
        RAISE EXCEPTION 'T23 committed metric run and value are frozen';
    END IF;
    IF TG_OP='DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_t23_receipt_frozen
    BEFORE UPDATE OR DELETE ON public.hidra_analytics_deviation_metric_receipt
    FOR EACH ROW EXECUTE FUNCTION public.hidra_analytics_t23_frozen();
CREATE TRIGGER tr_t23_run_frozen
    BEFORE UPDATE OR DELETE ON public.hidra_analytics_metric_evaluation_run
    FOR EACH ROW EXECUTE FUNCTION public.hidra_analytics_t23_frozen();
CREATE TRIGGER tr_t23_value_frozen
    BEFORE UPDATE OR DELETE ON public.hidra_analytics_metric_value
    FOR EACH ROW EXECUTE FUNCTION public.hidra_analytics_t23_frozen();

CREATE FUNCTION public.hidra_analytics_t23_restrict_truncate() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM public.hidra_analytics_deviation_metric_receipt) THEN
        RAISE EXCEPTION 'T23 historical Analytics metric receipts prevent truncate';
    END IF;
    RETURN NULL;
END $$;
CREATE TRIGGER tr_t23_receipt_no_truncate
    BEFORE TRUNCATE ON public.hidra_analytics_deviation_metric_receipt
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_analytics_t23_restrict_truncate();
CREATE TRIGGER tr_t23_run_no_truncate
    BEFORE TRUNCATE ON public.hidra_analytics_metric_evaluation_run
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_analytics_t23_restrict_truncate();
CREATE TRIGGER tr_t23_value_no_truncate
    BEFORE TRUNCATE ON public.hidra_analytics_metric_value
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_analytics_t23_restrict_truncate();
