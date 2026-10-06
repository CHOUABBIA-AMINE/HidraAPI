-- HMR-074: align an already-mandatory domain reference with persistence.
-- Preserve the existing Organization-owned FK; never fabricate missing identities.
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM hidra_org_delegation WHERE responsibility_assignment_id IS NULL) THEN
        RAISE EXCEPTION 'HMR-074 preflight failed: null hidra_org_delegation.responsibility_assignment_id; resolve existing data before migration';
    END IF;
END $$;

ALTER TABLE hidra_org_delegation
    ALTER COLUMN responsibility_assignment_id SET NOT NULL;
