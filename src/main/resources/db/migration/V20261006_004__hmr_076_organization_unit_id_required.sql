-- HMR-076: align an already-mandatory domain reference with persistence.
-- Preserve the existing Organization-owned FK; never fabricate missing identities.
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM hidra_org_shift_assignment WHERE organization_unit_id IS NULL) THEN
        RAISE EXCEPTION 'HMR-076 preflight failed: null hidra_org_shift_assignment.organization_unit_id; resolve existing data before migration';
    END IF;
END $$;

ALTER TABLE hidra_org_shift_assignment
    ALTER COLUMN organization_unit_id SET NOT NULL;
