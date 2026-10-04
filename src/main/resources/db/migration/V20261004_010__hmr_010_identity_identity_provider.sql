-- HMR-010: IdentityProvider semantic remediation.
-- Existing migrations remain immutable.

CREATE UNIQUE INDEX uk_hmr010_identity_provider_code
    ON hidra_identity_provider (code);

ALTER TABLE hidra_identity_provider
    ADD CONSTRAINT ck_hmr010_identity_provider_name
    CHECK (btrim(name) <> '') NOT VALID;

ALTER TABLE hidra_identity_provider
    ADD CONSTRAINT ck_hmr010_identity_provider_active_oidc_issuer
    CHECK (
        status <> 'ACTIVE'
        OR provider_type <> 'OIDC'
        OR (issuer_uri IS NOT NULL AND btrim(issuer_uri) <> '')
    ) NOT VALID;

CREATE UNIQUE INDEX uk_hmr010_identity_provider_active_oidc_issuer
    ON hidra_identity_provider (issuer_uri)
    WHERE provider_type = 'OIDC'
      AND status = 'ACTIVE';

CREATE UNIQUE INDEX uk_hmr010_identity_provider_active_local
    ON hidra_identity_provider ((1))
    WHERE provider_type = 'LOCAL'
      AND status = 'ACTIVE';

CREATE UNIQUE INDEX uk_hmr010_identity_provider_active_directory
    ON hidra_identity_provider ((1))
    WHERE provider_type IN ('LDAP', 'ACTIVE_DIRECTORY')
      AND status = 'ACTIVE';

ALTER TABLE hidra_identity_provider
    VALIDATE CONSTRAINT ck_hmr010_identity_provider_name;

ALTER TABLE hidra_identity_provider
    VALIDATE CONSTRAINT ck_hmr010_identity_provider_active_oidc_issuer;
