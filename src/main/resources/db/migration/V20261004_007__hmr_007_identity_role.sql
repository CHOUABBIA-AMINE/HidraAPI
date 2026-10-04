-- HMR-007: Identity Role semantic remediation.
-- Enforce race-safe uniqueness for the Role business code.

CREATE UNIQUE INDEX uk_hmr007_identity_role_code
    ON hidra_identity_role (code);
