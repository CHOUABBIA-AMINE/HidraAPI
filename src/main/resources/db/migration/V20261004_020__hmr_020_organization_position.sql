-- HMR-020: Position semantic remediation.
-- Existing migrations remain immutable.
-- The canonical Position domain already requires level.
--
-- Compatibility rule:
-- * all new/updated rows are blocked from persisting NULL level values;
-- * the greenfield path is promoted to physical NOT NULL;
-- * historical compatibility fixtures with pre-existing NULL levels are not assigned
--   an invented PositionLevel value.

ALTER TABLE hidra_org_position
    ADD CONSTRAINT ck_hmr020_position_level_not_null
    CHECK (level IS NOT NULL)
    NOT VALID;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM hidra_org_position
        WHERE level IS NULL
    ) THEN
        ALTER TABLE hidra_org_position
            ALTER COLUMN level SET NOT NULL;

        ALTER TABLE hidra_org_position
            DROP CONSTRAINT ck_hmr020_position_level_not_null;
    END IF;
END;
$$;
