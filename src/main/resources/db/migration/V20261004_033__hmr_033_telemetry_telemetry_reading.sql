-- HMR-033: TelemetryReading semantic remediation.
-- Existing migrations remain immutable.
-- Protect optional same-module traceability references, quality catalog family,
-- and the authoritative raw-reading value-shape policy.

ALTER TABLE hidra_telemetry_reading
    ADD CONSTRAINT fk_hmr033_reading_ingestion_batch
        FOREIGN KEY (ingestion_batch_id)
        REFERENCES hidra_telemetry_ingestion_batch (id);

ALTER TABLE hidra_telemetry_reading
    ADD CONSTRAINT fk_hmr033_reading_external_tag_mapping
        FOREIGN KEY (external_tag_mapping_id)
        REFERENCES hidra_telemetry_external_tag_mapping (id);

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_telemetry_reading reading
        LEFT JOIN hidra_telemetry_type_catalog quality
          ON quality.id = reading.quality_code_id
        WHERE quality.id IS NULL
           OR quality.catalog_name <> 'QUALITY_CODE'
    ) THEN
        RAISE EXCEPTION
            'HMR-033 cannot retain TelemetryReading rows whose quality_code_id is outside QUALITY_CODE'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

CREATE OR REPLACE FUNCTION hmr033_guard_telemetry_reading_quality()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM hidra_telemetry_type_catalog quality
        WHERE quality.id = NEW.quality_code_id
          AND quality.catalog_name = 'QUALITY_CODE'
    ) THEN
        RAISE EXCEPTION
            'TelemetryReading quality_code_id must resolve to QUALITY_CODE: %',
            NEW.quality_code_id
            USING ERRCODE = '23514';
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr033_guard_telemetry_reading_quality
    BEFORE INSERT OR UPDATE OF quality_code_id
    ON hidra_telemetry_reading
    FOR EACH ROW
    EXECUTE FUNCTION hmr033_guard_telemetry_reading_quality();

ALTER TABLE hidra_telemetry_reading
    ADD CONSTRAINT ck_hmr033_reading_value_shape
    CHECK (
        (
            CASE WHEN numeric_value IS NOT NULL THEN 1 ELSE 0 END
          + CASE WHEN text_value IS NOT NULL AND btrim(text_value) <> '' THEN 1 ELSE 0 END
          + CASE WHEN boolean_value IS NOT NULL THEN 1 ELSE 0 END
        ) <= 1
        AND (
            (
                CASE WHEN numeric_value IS NOT NULL THEN 1 ELSE 0 END
              + CASE WHEN text_value IS NOT NULL AND btrim(text_value) <> '' THEN 1 ELSE 0 END
              + CASE WHEN boolean_value IS NOT NULL THEN 1 ELSE 0 END
            ) = 1
            OR (
                state IN ('REJECTED', 'QUARANTINED')
                AND (
                    CASE WHEN numeric_value IS NOT NULL THEN 1 ELSE 0 END
                  + CASE WHEN text_value IS NOT NULL AND btrim(text_value) <> '' THEN 1 ELSE 0 END
                  + CASE WHEN boolean_value IS NOT NULL THEN 1 ELSE 0 END
                ) = 0
            )
        )
    );
