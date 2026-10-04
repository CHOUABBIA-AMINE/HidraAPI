-- HMR-016: MetricEvaluationRun semantic remediation.
-- Existing migrations remain immutable.

ALTER TABLE hidra_analytics_metric_evaluation_run
    ADD CONSTRAINT ck_hmr016_metric_run_scope_type
    CHECK (btrim(scope_type) <> '') NOT VALID;

ALTER TABLE hidra_analytics_metric_evaluation_run
    ADD CONSTRAINT ck_hmr016_metric_run_scope_id
    CHECK (scope_id IS NOT NULL AND btrim(scope_id) <> '') NOT VALID;

ALTER TABLE hidra_analytics_metric_evaluation_run
    ADD CONSTRAINT ck_hmr016_metric_run_non_negative_counts
    CHECK (
        (records_read IS NULL OR records_read >= 0)
        AND (records_produced IS NULL OR records_produced >= 0)
    ) NOT VALID;

ALTER TABLE hidra_analytics_metric_evaluation_run
    ADD CONSTRAINT ck_hmr016_metric_run_completion
    CHECK (
        completed_at IS NULL
        OR completed_at >= started_at
    ) NOT VALID;

ALTER TABLE hidra_analytics_metric_evaluation_run
    ADD CONSTRAINT ck_hmr016_metric_run_terminal_completed_at
    CHECK (
        run_status NOT IN ('COMPLETED', 'COMPLETED_WITH_WARNINGS', 'FAILED', 'CANCELLED')
        OR completed_at IS NOT NULL
    ) NOT VALID;

ALTER TABLE hidra_analytics_metric_evaluation_run
    ADD CONSTRAINT ck_hmr016_metric_run_failed_evidence
    CHECK (
        run_status <> 'FAILED'
        OR NULLIF(btrim(error_code), '') IS NOT NULL
        OR NULLIF(btrim(error_message), '') IS NOT NULL
        OR NULLIF(btrim(correlation_id), '') IS NOT NULL
    ) NOT VALID;

ALTER TABLE hidra_analytics_metric_evaluation_run
    VALIDATE CONSTRAINT ck_hmr016_metric_run_scope_type;
ALTER TABLE hidra_analytics_metric_evaluation_run
    VALIDATE CONSTRAINT ck_hmr016_metric_run_scope_id;
ALTER TABLE hidra_analytics_metric_evaluation_run
    VALIDATE CONSTRAINT ck_hmr016_metric_run_non_negative_counts;
ALTER TABLE hidra_analytics_metric_evaluation_run
    VALIDATE CONSTRAINT ck_hmr016_metric_run_completion;
ALTER TABLE hidra_analytics_metric_evaluation_run
    VALIDATE CONSTRAINT ck_hmr016_metric_run_terminal_completed_at;
ALTER TABLE hidra_analytics_metric_evaluation_run
    VALIDATE CONSTRAINT ck_hmr016_metric_run_failed_evidence;

CREATE OR REPLACE FUNCTION hmr016_validate_metric_version_period()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
    version_valid_from timestamptz;
    version_valid_to timestamptz;
BEGIN
    SELECT v.valid_from, v.valid_to
      INTO version_valid_from, version_valid_to
      FROM hidra_analytics_metric_definition_version v
     WHERE v.id = NEW.metric_definition_version_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION
            'MetricEvaluationRun metric definition version must exist: %',
            NEW.metric_definition_version_id
            USING ERRCODE = '23514';
    END IF;

    IF version_valid_from IS NOT NULL AND NEW.period_start < version_valid_from THEN
        RAISE EXCEPTION
            'MetricEvaluationRun period starts before metric definition version validity'
            USING ERRCODE = '23514';
    END IF;

    IF version_valid_to IS NOT NULL AND NEW.period_end > version_valid_to THEN
        RAISE EXCEPTION
            'MetricEvaluationRun period ends after metric definition version validity'
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr016_metric_run_version_period
    BEFORE INSERT OR UPDATE OF metric_definition_version_id, period_start, period_end
    ON hidra_analytics_metric_evaluation_run
    FOR EACH ROW
    EXECUTE FUNCTION hmr016_validate_metric_version_period();

CREATE OR REPLACE FUNCTION hmr016_protect_terminal_metric_run()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF OLD.run_status IN ('COMPLETED', 'COMPLETED_WITH_WARNINGS', 'FAILED', 'CANCELLED')
       AND NEW.run_status <> OLD.run_status THEN
        RAISE EXCEPTION
            'Terminal MetricEvaluationRun outcome cannot be changed or reopened'
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr016_metric_run_terminal_immutable
    BEFORE UPDATE OF run_status
    ON hidra_analytics_metric_evaluation_run
    FOR EACH ROW
    EXECUTE FUNCTION hmr016_protect_terminal_metric_run();
