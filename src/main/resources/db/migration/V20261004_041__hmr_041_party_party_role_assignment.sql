-- HMR-041: PartyRoleAssignment semantic remediation.
-- Preserve historical assignments while preventing concurrent duplicate ACTIVE rows.

CREATE UNIQUE INDEX IF NOT EXISTS ux_hmr041_party_role_assignment_active
    ON hidra_party_role_assignment (party_id, role_id)
    WHERE status = 'ACTIVE';
