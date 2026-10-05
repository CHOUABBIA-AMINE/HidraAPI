-- HMR-047: ExternalSystem semantic remediation.
-- Concurrency-safe code uniqueness is authoritative in PostgreSQL.
-- Organization ownership remains application-authoritative; no cross-module FK is introduced.

CREATE UNIQUE INDEX IF NOT EXISTS ux_hmr047_integration_external_system_code
    ON hidra_integration_external_system (code);
