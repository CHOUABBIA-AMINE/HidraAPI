-- HMR-028: ReportingLine semantic remediation.
-- Existing migrations remain immutable.
-- Canonical classification is catalog-backed through reporting_line_type_id.
-- The legacy reporting_line_type column remains only as a trigger-synchronized
-- compatibility mirror for established provisioning/migration SQL boundaries.

CREATE TABLE hidra_org_reporting_line_type (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    CONSTRAINT uq_hmr028_reporting_line_type_code UNIQUE (code)
);

INSERT INTO hidra_org_reporting_line_type (
    id,
    code,
    name_ar,
    name_fr,
    name_en,
    active,
    created_at,
    updated_at
) VALUES
    ('LINE', 'LINE', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('OPERATIONAL', 'OPERATIONAL', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('FUNCTIONAL', 'FUNCTIONAL', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ADMINISTRATIVE', 'ADMINISTRATIVE', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('TECHNICAL', 'TECHNICAL', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('DOTTED_LINE', 'DOTTED_LINE', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_org_reporting_line
        WHERE reporting_line_type NOT IN (
            'LINE',
            'OPERATIONAL',
            'FUNCTIONAL',
            'ADMINISTRATIVE',
            'TECHNICAL',
            'DOTTED_LINE'
        )
    ) THEN
        RAISE EXCEPTION
            'HMR-028 cannot migrate unsupported ReportingLine type values such as legacy TEMPORARY'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

ALTER TABLE hidra_org_reporting_line
    ADD COLUMN reporting_line_type_id varchar(80);

UPDATE hidra_org_reporting_line line
SET reporting_line_type_id = type.id
FROM hidra_org_reporting_line_type type
WHERE type.code = line.reporting_line_type;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_org_reporting_line
        WHERE reporting_line_type_id IS NULL
    ) THEN
        RAISE EXCEPTION
            'HMR-028 failed to backfill ReportingLine type catalog references'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

ALTER TABLE hidra_org_reporting_line
    ALTER COLUMN reporting_line_type_id SET NOT NULL;

ALTER TABLE hidra_org_reporting_line
    ADD CONSTRAINT fk_hmr028_reporting_line_type
    FOREIGN KEY (reporting_line_type_id)
    REFERENCES hidra_org_reporting_line_type (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_org_reporting_line
    VALIDATE CONSTRAINT fk_hmr028_reporting_line_type;

CREATE INDEX ix_hidra_org_reporting_line_type_id
    ON hidra_org_reporting_line (reporting_line_type_id);

DO $$
BEGIN
    IF EXISTS (
        SELECT source_id
        FROM hidra_org_reporting_line
        WHERE active = true
          AND source_type = 'EMPLOYEE'
          AND reporting_line_type_id = 'LINE'
        GROUP BY source_id
        HAVING count(*) > 1
    ) THEN
        RAISE EXCEPTION
            'HMR-028 cannot retain multiple active LINE relations for one employee source'
            USING ERRCODE = '23514';
    END IF;

    IF EXISTS (
        WITH RECURSIVE walk(start_type, start_id, node_type, node_id) AS (
            SELECT
                line.source_type,
                line.source_id,
                line.target_type,
                line.target_id
            FROM hidra_org_reporting_line line
            WHERE line.active = true
              AND line.reporting_line_type_id = 'LINE'

            UNION

            SELECT
                walk.start_type,
                walk.start_id,
                line.target_type,
                line.target_id
            FROM walk
            JOIN hidra_org_reporting_line line
              ON line.source_type = walk.node_type
             AND line.source_id = walk.node_id
            WHERE line.active = true
              AND line.reporting_line_type_id = 'LINE'
        )
        SELECT 1
        FROM walk
        WHERE node_type = start_type
          AND node_id = start_id
    ) THEN
        RAISE EXCEPTION
            'HMR-028 cannot retain an active LINE reporting cycle'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

CREATE UNIQUE INDEX uq_hmr028_employee_active_line
    ON hidra_org_reporting_line (source_id)
    WHERE active = true
      AND source_type = 'EMPLOYEE'
      AND reporting_line_type_id = 'LINE';

CREATE OR REPLACE FUNCTION hmr028_guard_reporting_line()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
    resolved_type_id varchar(80);
    resolved_type_code varchar(120);
BEGIN
    -- Preserve the established ORG-046 discriminator failure surface before
    -- resolving the compatibility type code.
    IF NEW.source_type IS NULL
       OR NEW.source_type NOT IN ('EMPLOYEE', 'POSITION', 'ORGANIZATION_UNIT') THEN
        RAISE EXCEPTION 'ck_org_reporting_source_type: unsupported reporting source type'
            USING ERRCODE = '23514';
    END IF;

    IF NEW.target_type IS NULL
       OR NEW.target_type NOT IN ('EMPLOYEE', 'POSITION', 'ORGANIZATION_UNIT') THEN
        RAISE EXCEPTION 'ck_org_reporting_target_type: unsupported reporting target type'
            USING ERRCODE = '23514';
    END IF;

    IF NEW.reporting_line_type_id IS NULL THEN
        IF NEW.reporting_line_type IS NULL OR btrim(NEW.reporting_line_type) = '' THEN
            RAISE EXCEPTION 'ReportingLine type catalog reference is required'
                USING ERRCODE = '23514';
        END IF;

        SELECT type.id, type.code
        INTO resolved_type_id, resolved_type_code
        FROM hidra_org_reporting_line_type type
        WHERE type.code = NEW.reporting_line_type
          AND type.active = true;

        IF NOT FOUND THEN
            RAISE EXCEPTION
                'ReportingLine type must resolve to an active catalog entry: %',
                NEW.reporting_line_type
                USING ERRCODE = '23514';
        END IF;

        NEW.reporting_line_type_id := resolved_type_id;
    ELSE
        SELECT type.id, type.code
        INTO resolved_type_id, resolved_type_code
        FROM hidra_org_reporting_line_type type
        WHERE type.id = NEW.reporting_line_type_id
          AND type.active = true;

        IF NOT FOUND THEN
            RAISE EXCEPTION
                'ReportingLine type id must resolve to an active catalog entry: %',
                NEW.reporting_line_type_id
                USING ERRCODE = '23514';
        END IF;

        IF NEW.reporting_line_type IS NULL OR btrim(NEW.reporting_line_type) = '' THEN
            NEW.reporting_line_type := resolved_type_code;
        ELSIF NEW.reporting_line_type <> resolved_type_code THEN
            RAISE EXCEPTION
                'ReportingLine type id/code reference is inconsistent'
                USING ERRCODE = '23514';
        END IF;
    END IF;

    IF NEW.source_type = 'EMPLOYEE'
       AND NOT EXISTS (
           SELECT 1
           FROM hidra_org_employee employee
           WHERE employee.id = NEW.source_id
             AND employee.status = 'ACTIVE'
       ) THEN
        RAISE EXCEPTION
            'ReportingLine source employee must exist and be ACTIVE: %',
            NEW.source_id
            USING ERRCODE = '23503';
    ELSIF NEW.source_type = 'POSITION'
       AND NOT EXISTS (
           SELECT 1 FROM hidra_org_position position WHERE position.id = NEW.source_id
       ) THEN
        RAISE EXCEPTION
            'ReportingLine source position must exist: %',
            NEW.source_id
            USING ERRCODE = '23503';
    ELSIF NEW.source_type = 'ORGANIZATION_UNIT'
       AND NOT EXISTS (
           SELECT 1 FROM hidra_org_unit unit WHERE unit.id = NEW.source_id
       ) THEN
        RAISE EXCEPTION
            'ReportingLine source organization unit must exist: %',
            NEW.source_id
            USING ERRCODE = '23503';
    END IF;

    IF NEW.target_type = 'EMPLOYEE'
       AND NOT EXISTS (
           SELECT 1
           FROM hidra_org_employee employee
           WHERE employee.id = NEW.target_id
             AND employee.status = 'ACTIVE'
       ) THEN
        RAISE EXCEPTION
            'ReportingLine target employee must exist and be ACTIVE: %',
            NEW.target_id
            USING ERRCODE = '23503';
    ELSIF NEW.target_type = 'POSITION'
       AND NOT EXISTS (
           SELECT 1 FROM hidra_org_position position WHERE position.id = NEW.target_id
       ) THEN
        RAISE EXCEPTION
            'ReportingLine target position must exist: %',
            NEW.target_id
            USING ERRCODE = '23503';
    ELSIF NEW.target_type = 'ORGANIZATION_UNIT'
       AND NOT EXISTS (
           SELECT 1 FROM hidra_org_unit unit WHERE unit.id = NEW.target_id
       ) THEN
        RAISE EXCEPTION
            'ReportingLine target organization unit must exist: %',
            NEW.target_id
            USING ERRCODE = '23503';
    END IF;

    IF NEW.active = true
       AND resolved_type_code = 'LINE'
       AND NEW.source_type = 'EMPLOYEE'
       AND EXISTS (
           SELECT 1
           FROM hidra_org_reporting_line line
           WHERE line.id <> NEW.id
             AND line.active = true
             AND line.source_type = 'EMPLOYEE'
             AND line.source_id = NEW.source_id
             AND line.reporting_line_type_id = 'LINE'
       ) THEN
        RAISE EXCEPTION
            'Employee may have only one active LINE reporting line: %',
            NEW.source_id
            USING ERRCODE = '23505';
    END IF;

    IF NEW.active = true
       AND resolved_type_code = 'LINE'
       AND EXISTS (
           WITH RECURSIVE reachable(subject_type, subject_id) AS (
               SELECT NEW.target_type::varchar(80), NEW.target_id::varchar(80)

               UNION

               SELECT line.target_type, line.target_id
               FROM hidra_org_reporting_line line
               JOIN reachable current_subject
                 ON line.source_type = current_subject.subject_type
                AND line.source_id = current_subject.subject_id
               WHERE line.id <> NEW.id
                 AND line.active = true
                 AND line.reporting_line_type_id = 'LINE'
           )
           SELECT 1
           FROM reachable
           WHERE subject_type = NEW.source_type
             AND subject_id = NEW.source_id
       ) THEN
        RAISE EXCEPTION
            'Active LINE reporting relation would create a reporting cycle'
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr028_guard_reporting_line
    BEFORE INSERT OR UPDATE
    ON hidra_org_reporting_line
    FOR EACH ROW
    EXECUTE FUNCTION hmr028_guard_reporting_line();
