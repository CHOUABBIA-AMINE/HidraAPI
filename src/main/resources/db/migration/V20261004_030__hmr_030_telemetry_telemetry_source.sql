-- HMR-030: TelemetrySource semantic remediation.
-- Existing migrations remain immutable.
-- Enforce unique source identity, active catalog families, required French naming,
-- source-specific lifecycle semantics, and secret-free source references.

CREATE OR REPLACE FUNCTION hmr030_contains_secret_material(value text)
RETURNS boolean
LANGUAGE sql
IMMUTABLE
PARALLEL SAFE
AS $$
    SELECT value IS NOT NULL
       AND (
            lower(value) LIKE '%x-amz-signature=%'
         OR lower(value) LIKE '%x-amz-credential=%'
         OR lower(value) LIKE '%access_token=%'
         OR lower(value) LIKE '%signature=%'
         OR lower(value) LIKE '%credential=%'
         OR lower(value) LIKE '%password=%'
         OR lower(value) LIKE '%secret=%'
         OR value ~* '^[a-z][a-z0-9+.-]*://[^/?#]*@'
       );
$$;

DO $$
BEGIN
    IF EXISTS (
        SELECT code
        FROM hidra_telemetry_source
        GROUP BY code
        HAVING count(*) > 1
    ) THEN
        RAISE EXCEPTION
            'HMR-030 cannot retain duplicate TelemetrySource codes'
            USING ERRCODE = '23505';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_telemetry_source
        WHERE btrim(name_fr) = ''
    ) THEN
        RAISE EXCEPTION
            'HMR-030 cannot retain TelemetrySource rows with blank French names'
            USING ERRCODE = '23514';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_telemetry_source
        WHERE status NOT IN (
            'DRAFT',
            'ACTIVE',
            'INACTIVE',
            'SUSPENDED',
            'RETIRED'
        )
    ) THEN
        RAISE EXCEPTION
            'HMR-030 cannot retain unsupported TelemetrySource lifecycle states'
            USING ERRCODE = '23514';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_telemetry_source source
        WHERE NOT EXISTS (
            SELECT 1
            FROM hidra_telemetry_type_catalog entry
            WHERE entry.id = source.source_type_id
              AND entry.catalog_name = 'SOURCE_TYPE'
              AND entry.active = true
        )
    ) THEN
        RAISE EXCEPTION
            'HMR-030 cannot retain TelemetrySource rows with invalid active SOURCE_TYPE references'
            USING ERRCODE = '23514';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_telemetry_source source
        WHERE NOT EXISTS (
            SELECT 1
            FROM hidra_telemetry_type_catalog entry
            WHERE entry.id = source.protocol_id
              AND entry.catalog_name = 'PROTOCOL'
              AND entry.active = true
        )
    ) THEN
        RAISE EXCEPTION
            'HMR-030 cannot retain TelemetrySource rows with invalid active PROTOCOL references'
            USING ERRCODE = '23514';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_telemetry_source
        WHERE hmr030_contains_secret_material(endpoint_uri)
           OR hmr030_contains_secret_material(external_reference)
    ) THEN
        RAISE EXCEPTION
            'HMR-030 cannot retain TelemetrySource secret material in endpoint/reference fields'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

CREATE UNIQUE INDEX uq_hmr030_telemetry_source_code
    ON hidra_telemetry_source (code);

ALTER TABLE hidra_telemetry_source
    ADD CONSTRAINT ck_hmr030_telemetry_source_name_fr_nonblank
    CHECK (btrim(name_fr) <> '')
    NOT VALID;

ALTER TABLE hidra_telemetry_source
    ADD CONSTRAINT ck_hmr030_telemetry_source_status
    CHECK (
        status IN (
            'DRAFT',
            'ACTIVE',
            'INACTIVE',
            'SUSPENDED',
            'RETIRED'
        )
    )
    NOT VALID;

ALTER TABLE hidra_telemetry_source
    ADD CONSTRAINT ck_hmr030_telemetry_source_secret_free
    CHECK (
        NOT hmr030_contains_secret_material(endpoint_uri)
        AND NOT hmr030_contains_secret_material(external_reference)
    )
    NOT VALID;

ALTER TABLE hidra_telemetry_source
    VALIDATE CONSTRAINT ck_hmr030_telemetry_source_name_fr_nonblank;

ALTER TABLE hidra_telemetry_source
    VALIDATE CONSTRAINT ck_hmr030_telemetry_source_status;

ALTER TABLE hidra_telemetry_source
    VALIDATE CONSTRAINT ck_hmr030_telemetry_source_secret_free;

CREATE OR REPLACE FUNCTION hmr030_validate_telemetry_source_catalogs()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM hidra_telemetry_type_catalog entry
        WHERE entry.id = NEW.source_type_id
          AND entry.catalog_name = 'SOURCE_TYPE'
          AND entry.active = true
    ) THEN
        RAISE EXCEPTION
            'TelemetrySource source_type_id must resolve to an active SOURCE_TYPE entry: %',
            NEW.source_type_id
            USING ERRCODE = '23514';
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM hidra_telemetry_type_catalog entry
        WHERE entry.id = NEW.protocol_id
          AND entry.catalog_name = 'PROTOCOL'
          AND entry.active = true
    ) THEN
        RAISE EXCEPTION
            'TelemetrySource protocol_id must resolve to an active PROTOCOL entry: %',
            NEW.protocol_id
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr030_telemetry_source_catalogs
    BEFORE INSERT OR UPDATE OF source_type_id, protocol_id
    ON hidra_telemetry_source
    FOR EACH ROW
    EXECUTE FUNCTION hmr030_validate_telemetry_source_catalogs();
