-- HMR-032: OrganizationUnit semantic remediation.
-- Existing migrations remain immutable.
-- Enforce mandatory effective start, selectable unit types for new/reclassified
-- units, and acyclic hierarchy mutations/provisioning.

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_org_unit
        WHERE valid_from IS NULL
    ) THEN
        RAISE EXCEPTION
            'HMR-032 cannot retain OrganizationUnit rows with null valid_from'
            USING ERRCODE = '23514';
    END IF;

    IF EXISTS (
        WITH RECURSIVE walk(start_id, id, parent_unit_id, path, cycle) AS (
            SELECT
                unit.id,
                unit.id,
                unit.parent_unit_id,
                ARRAY[unit.id]::varchar[],
                false
            FROM hidra_org_unit unit
            WHERE unit.parent_unit_id IS NOT NULL

            UNION ALL

            SELECT
                walk.start_id,
                parent.id,
                parent.parent_unit_id,
                walk.path || parent.id,
                parent.id = ANY(walk.path)
            FROM walk
            JOIN hidra_org_unit parent
              ON parent.id = walk.parent_unit_id
            WHERE walk.parent_unit_id IS NOT NULL
              AND NOT walk.cycle
        )
        SELECT 1
        FROM walk
        WHERE cycle = true
    ) THEN
        RAISE EXCEPTION
            'HMR-032 cannot retain cyclic OrganizationUnit hierarchy'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

ALTER TABLE hidra_org_unit
    ALTER COLUMN valid_from SET NOT NULL;

CREATE OR REPLACE FUNCTION hmr032_guard_organization_unit()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
    validate_type boolean := false;
BEGIN
    IF TG_OP = 'INSERT' THEN
        validate_type := true;
    ELSIF NEW.unit_type_id IS DISTINCT FROM OLD.unit_type_id THEN
        validate_type := true;
    END IF;

    IF validate_type
       AND NOT EXISTS (
           SELECT 1
           FROM hidra_org_unit_type unit_type
           WHERE unit_type.id = NEW.unit_type_id
             AND unit_type.active = true
       ) THEN
        RAISE EXCEPTION
            'OrganizationUnit unit_type_id must resolve to an active selectable type: %',
            NEW.unit_type_id
            USING ERRCODE = '23514';
    END IF;

    IF NEW.parent_unit_id IS NOT NULL THEN
        IF NEW.parent_unit_id = NEW.id THEN
            RAISE EXCEPTION
                'OrganizationUnit cannot be its own parent'
                USING ERRCODE = '23514';
        END IF;

        IF EXISTS (
            WITH RECURSIVE ancestry(id, parent_unit_id, path, cycle) AS (
                SELECT
                    parent.id,
                    parent.parent_unit_id,
                    ARRAY[parent.id]::varchar[],
                    false
                FROM hidra_org_unit parent
                WHERE parent.id = NEW.parent_unit_id

                UNION ALL

                SELECT
                    parent.id,
                    parent.parent_unit_id,
                    ancestry.path || parent.id,
                    parent.id = ANY(ancestry.path)
                FROM ancestry
                JOIN hidra_org_unit parent
                  ON parent.id = ancestry.parent_unit_id
                WHERE ancestry.parent_unit_id IS NOT NULL
                  AND NOT ancestry.cycle
            )
            SELECT 1
            FROM ancestry
            WHERE id = NEW.id
               OR cycle = true
        ) THEN
            RAISE EXCEPTION
                'OrganizationUnit hierarchy must not contain cycles'
                USING ERRCODE = '23514';
        END IF;
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr032_guard_organization_unit
    BEFORE INSERT OR UPDATE OF unit_type_id, parent_unit_id
    ON hidra_org_unit
    FOR EACH ROW
    EXECUTE FUNCTION hmr032_guard_organization_unit();
