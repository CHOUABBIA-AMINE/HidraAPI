-- HMR-004: Party semantic remediation
-- Enforce race-safe uniqueness of the Party business code.
-- Existing Flyway migrations remain immutable.

CREATE UNIQUE INDEX uk_hmr004_party_code
    ON hidra_party_party (code);
