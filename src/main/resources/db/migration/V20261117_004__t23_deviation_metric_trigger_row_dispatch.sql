-- T23 CI repair: defer the same checks without resolving unrelated NEW record fields.
CREATE OR REPLACE FUNCTION public.hidra_analytics_t23_verify() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE
    rid varchar(80);
    saved public.hidra_analytics_deviation_metric_receipt%ROWTYPE;
    parent public.hidra_analytics_metric_evaluation_run%ROWTYPE;
    def record;
    value_row public.hidra_analytics_metric_value%ROWTYPE;
    count_values bigint;
    reserved_method boolean;
BEGIN
    -- A shared trigger's NEW record has only the columns of the firing table.
    IF TG_TABLE_NAME = 'hidra_analytics_deviation_metric_receipt' THEN
        rid := NEW.run_id;
    ELSIF TG_TABLE_NAME = 'hidra_analytics_metric_evaluation_run' THEN
        rid := NEW.id;
    ELSE
        rid := NEW.metric_evaluation_run_id;
    END IF;
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

