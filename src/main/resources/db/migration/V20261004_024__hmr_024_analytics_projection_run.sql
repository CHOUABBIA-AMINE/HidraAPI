-- HMR-024: AnalyticsProjectionRun semantic remediation.
-- Existing migrations remain immutable.
-- Capture durable projection-definition computation lineage and terminal evidence.

ALTER TABLE hidra_analytics_projection_run
    ADD COLUMN projection_definition_version varchar(120);

UPDATE hidra_analytics_projection_run r
SET projection_definition_version =
        to_char(d.updated_at AT TIME ZONE 'UTC', 'YYYYMMDDHH24MISS.US')
        || '-' ||
        md5(jsonb_build_array(
            d.projection_type,
            d.calculation_policy,
            d.refresh_policy,
            d.retention_policy
        )::text)
FROM hidra_analytics_projection_definition d
WHERE d.id = r.projection_definition_id;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_analytics_projection_run
        WHERE projection_definition_version IS NULL
           OR btrim(projection_definition_version) = ''
    ) THEN
        RAISE EXCEPTION
            'HMR-024 cannot backfill AnalyticsProjectionRun projection definition version'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

ALTER TABLE hidra_analytics_projection_run
    ALTER COLUMN projection_definition_version SET NOT NULL;

ALTER TABLE hidra_analytics_projection_run
    ADD CONSTRAINT ck_hmr024_projection_definition_version_nonblank
    CHECK (btrim(projection_definition_version) <> '')
    NOT VALID;

ALTER TABLE hidra_analytics_projection_run
    ADD CONSTRAINT ck_hmr024_failed_run_diagnostics
    CHECK (
        run_status <> 'FAILED'
        OR NULLIF(btrim(error_code), '') IS NOT NULL
        OR NULLIF(btrim(error_message), '') IS NOT NULL
    )
    NOT VALID;

ALTER TABLE hidra_analytics_projection_run
    ADD CONSTRAINT ck_hmr024_successful_run_watermark
    CHECK (
        run_status NOT IN ('COMPLETED', 'COMPLETED_WITH_WARNINGS')
        OR NULLIF(btrim(source_watermark), '') IS NOT NULL
    )
    NOT VALID;

ALTER TABLE hidra_analytics_projection_run
    VALIDATE CONSTRAINT ck_hmr024_projection_definition_version_nonblank;

ALTER TABLE hidra_analytics_projection_run
    VALIDATE CONSTRAINT ck_hmr024_failed_run_diagnostics;

ALTER TABLE hidra_analytics_projection_run
    VALIDATE CONSTRAINT ck_hmr024_successful_run_watermark;
