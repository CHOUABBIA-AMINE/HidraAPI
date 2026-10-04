-- HMR-011: Permission semantic remediation.
-- Existing migrations remain immutable.

CREATE UNIQUE INDEX uk_hmr011_identity_permission_code
    ON hidra_identity_permission (code);

ALTER TABLE hidra_identity_permission
    ADD CONSTRAINT ck_hmr011_identity_permission_code_format
    CHECK (code ~ '^[a-z0-9-]+:[a-z0-9-]+:[a-z0-9-]+$') NOT VALID;

ALTER TABLE hidra_identity_permission
    ADD CONSTRAINT ck_hmr011_identity_permission_domain
    CHECK (btrim(permission_domain) <> '') NOT VALID;

ALTER TABLE hidra_identity_permission
    ADD CONSTRAINT ck_hmr011_identity_permission_resource_type
    CHECK (resource_type IS NOT NULL AND btrim(resource_type) <> '') NOT VALID;

ALTER TABLE hidra_identity_permission
    ADD CONSTRAINT ck_hmr011_identity_permission_action
    CHECK (btrim(action) <> '') NOT VALID;

ALTER TABLE hidra_identity_permission
    VALIDATE CONSTRAINT ck_hmr011_identity_permission_code_format;
ALTER TABLE hidra_identity_permission
    VALIDATE CONSTRAINT ck_hmr011_identity_permission_domain;
ALTER TABLE hidra_identity_permission
    VALIDATE CONSTRAINT ck_hmr011_identity_permission_resource_type;
ALTER TABLE hidra_identity_permission
    VALIDATE CONSTRAINT ck_hmr011_identity_permission_action;

ALTER TABLE hidra_identity_permission
    ALTER COLUMN resource_type SET NOT NULL;

CREATE OR REPLACE FUNCTION hmr011_require_active_identity_permission()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NEW.permission_id IS NOT NULL
       AND NEW.status = 'ACTIVE'
       AND NOT EXISTS (
           SELECT 1
           FROM hidra_identity_permission p
           WHERE p.id = NEW.permission_id
             AND p.status = 'ACTIVE'
       ) THEN
        RAISE EXCEPTION
            'Active authorization grant/mapping requires an ACTIVE Permission: %',
            NEW.permission_id
            USING ERRCODE = '23514';
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr011_role_permission_active
    BEFORE INSERT OR UPDATE OF permission_id, status
    ON hidra_identity_role_permission_grant
    FOR EACH ROW
    EXECUTE FUNCTION hmr011_require_active_identity_permission();

CREATE TRIGGER trg_hmr011_user_permission_active
    BEFORE INSERT OR UPDATE OF permission_id, status
    ON hidra_identity_user_permission_grant
    FOR EACH ROW
    EXECUTE FUNCTION hmr011_require_active_identity_permission();

CREATE TRIGGER trg_hmr011_delegation_permission_active
    BEFORE INSERT OR UPDATE OF permission_id, status
    ON hidra_identity_authorization_delegation_grant
    FOR EACH ROW
    EXECUTE FUNCTION hmr011_require_active_identity_permission();

CREATE TRIGGER trg_hmr011_external_permission_mapping_active
    BEFORE INSERT OR UPDATE OF permission_id, status
    ON hidra_identity_external_permission_mapping
    FOR EACH ROW
    EXECUTE FUNCTION hmr011_require_active_identity_permission();
