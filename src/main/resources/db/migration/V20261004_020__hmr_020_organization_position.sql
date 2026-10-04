-- HMR-020: Position semantic remediation.
-- Existing migrations remain immutable.
-- The canonical Position domain already requires level; greenfield persistence is aligned here.

ALTER TABLE hidra_org_position
    ALTER COLUMN level SET NOT NULL;
