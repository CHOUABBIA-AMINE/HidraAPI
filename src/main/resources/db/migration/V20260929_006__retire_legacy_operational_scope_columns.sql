-- ORG-032 - Retire obsolete Organization operational-scope compatibility columns.
-- Module: organization
--
-- HidraAPI is greenfield: no deployed legacy Organization database exists.
-- Earlier migrations are immutable and remain replayable; this migration defines the final schema.
-- Fail before destructive DDL if an intermediate database contains a responsibility without the
-- canonical OperationalScope registry identity.

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_org_responsibility_assignment
        WHERE scope_id IS NULL
    ) THEN
        RAISE EXCEPTION
            'ORG-032 preflight failed: hidra_org_responsibility_assignment.scope_id contains NULL values';
    END IF;
END
$$;

DROP INDEX ix_hidra_org_unit_operational_scope_id;
DROP INDEX ix_hidra_org_employee_assignment_operational_scope_id;
DROP INDEX ix_hidra_org_responsibility_assignment_operational_scope_id;

ALTER TABLE hidra_org_unit
    DROP COLUMN operational_scope_type,
    DROP COLUMN operational_scope_id,
    DROP COLUMN operational_scope_code,
    DROP COLUMN operational_scope_name;

ALTER TABLE hidra_org_employee_assignment
    DROP COLUMN operational_scope_type,
    DROP COLUMN operational_scope_id,
    DROP COLUMN operational_scope_code,
    DROP COLUMN operational_scope_name;

ALTER TABLE hidra_org_responsibility_assignment
    ALTER COLUMN scope_id SET NOT NULL,
    DROP CONSTRAINT ck_org_responsibility_canonical_temporal,
    DROP COLUMN operational_scope_type,
    DROP COLUMN operational_scope_id,
    DROP COLUMN operational_scope_code,
    DROP COLUMN operational_scope_name,
    ADD CONSTRAINT ck_org_responsibility_canonical_temporal
        CHECK (valid_to IS NULL OR valid_to > valid_from);
