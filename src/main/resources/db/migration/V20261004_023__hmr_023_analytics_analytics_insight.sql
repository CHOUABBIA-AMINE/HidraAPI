-- HMR-023: AnalyticsInsight semantic remediation.
-- Existing migrations remain immutable.
-- Enforce governed insight classification, mandatory scope discriminator,
-- severity catalog-family integrity, and populated direct-source lineage.

ALTER TABLE hidra_analytics_insight
    ADD CONSTRAINT ck_hmr023_analytics_insight_type_nonblank
    CHECK (btrim(insight_type) <> '')
    NOT VALID;

ALTER TABLE hidra_analytics_insight
    ADD CONSTRAINT ck_hmr023_analytics_insight_scope_type_nonblank
    CHECK (btrim(scope_type) <> '')
    NOT VALID;

ALTER TABLE hidra_analytics_insight
    VALIDATE CONSTRAINT ck_hmr023_analytics_insight_type_nonblank;

ALTER TABLE hidra_analytics_insight
    VALIDATE CONSTRAINT ck_hmr023_analytics_insight_scope_type_nonblank;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_analytics_insight i
        WHERE NOT EXISTS (
            SELECT 1
            FROM hidra_analytics_catalog_entry c
            WHERE c.catalog_name = 'INSIGHT_TYPE'
              AND c.code = i.insight_type
              AND c.active = true
        )
    ) THEN
        RAISE EXCEPTION
            'HMR-023 cannot retain AnalyticsInsight rows with ungoverned INSIGHT_TYPE values'
            USING ERRCODE = '23514';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_analytics_insight i
        WHERE i.severity_id IS NOT NULL
          AND NOT EXISTS (
              SELECT 1
              FROM hidra_analytics_catalog_entry c
              WHERE c.id = i.severity_id
                AND c.catalog_name = 'ANALYTICS_SEVERITY'
                AND c.active = true
          )
    ) THEN
        RAISE EXCEPTION
            'HMR-023 cannot retain AnalyticsInsight rows with invalid ANALYTICS_SEVERITY references'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

ALTER TABLE hidra_analytics_insight
    ADD CONSTRAINT fk_hmr023_analytics_insight_severity
    FOREIGN KEY (severity_id)
    REFERENCES hidra_analytics_catalog_entry (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_analytics_insight
    ADD CONSTRAINT fk_hmr023_analytics_insight_projection_snapshot
    FOREIGN KEY (source_projection_snapshot_id)
    REFERENCES hidra_analytics_projection_snapshot (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_analytics_insight
    ADD CONSTRAINT fk_hmr023_analytics_insight_trend_analysis
    FOREIGN KEY (source_trend_analysis_id)
    REFERENCES hidra_analytics_trend_analysis (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_analytics_insight
    ADD CONSTRAINT fk_hmr023_analytics_insight_model_run
    FOREIGN KEY (source_model_run_id)
    REFERENCES hidra_analytics_model_run (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_analytics_insight
    VALIDATE CONSTRAINT fk_hmr023_analytics_insight_severity;

ALTER TABLE hidra_analytics_insight
    VALIDATE CONSTRAINT fk_hmr023_analytics_insight_projection_snapshot;

ALTER TABLE hidra_analytics_insight
    VALIDATE CONSTRAINT fk_hmr023_analytics_insight_trend_analysis;

ALTER TABLE hidra_analytics_insight
    VALIDATE CONSTRAINT fk_hmr023_analytics_insight_model_run;

CREATE OR REPLACE FUNCTION hmr023_validate_analytics_insight_catalogs()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM hidra_analytics_catalog_entry c
        WHERE c.catalog_name = 'INSIGHT_TYPE'
          AND c.code = NEW.insight_type
          AND c.active = true
    ) THEN
        RAISE EXCEPTION
            'AnalyticsInsight insight_type must resolve to an active INSIGHT_TYPE catalog entry: %',
            NEW.insight_type
            USING ERRCODE = '23514';
    END IF;

    IF NEW.severity_id IS NOT NULL
       AND NOT EXISTS (
           SELECT 1
           FROM hidra_analytics_catalog_entry c
           WHERE c.id = NEW.severity_id
             AND c.catalog_name = 'ANALYTICS_SEVERITY'
             AND c.active = true
       ) THEN
        RAISE EXCEPTION
            'AnalyticsInsight severity_id must resolve to an active ANALYTICS_SEVERITY catalog entry: %',
            NEW.severity_id
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr023_analytics_insight_catalogs
    BEFORE INSERT OR UPDATE OF insight_type, severity_id
    ON hidra_analytics_insight
    FOR EACH ROW
    EXECUTE FUNCTION hmr023_validate_analytics_insight_catalogs();
