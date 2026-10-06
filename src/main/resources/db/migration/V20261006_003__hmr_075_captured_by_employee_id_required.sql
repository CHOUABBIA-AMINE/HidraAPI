-- HMR-075: align an already-mandatory domain reference with persistence.
-- Preserve the existing Organization-owned FK; never fabricate missing identities.
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM hidra_org_hierarchy_snapshot WHERE captured_by_employee_id IS NULL) THEN
        RAISE EXCEPTION 'HMR-075 preflight failed: null hidra_org_hierarchy_snapshot.captured_by_employee_id; resolve existing data before migration';
    END IF;
END $$;

ALTER TABLE hidra_org_hierarchy_snapshot
    ALTER COLUMN captured_by_employee_id SET NOT NULL;
