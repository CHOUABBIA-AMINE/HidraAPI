-- HMR-088: bounded direct grants; preserve role-grant SUSPENDED and optional ends.
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_identity_user_permission_grant
        WHERE grant_reason IS NULL OR btrim(grant_reason) = '' OR valid_to IS NULL
           OR valid_to < valid_from OR status NOT IN ('ACTIVE','REVOKED','EXPIRED')) THEN
        RAISE EXCEPTION 'HMR-088 preflight failed: reconcile direct grant reason/end/status without fabrication' USING ERRCODE = '23514';
    END IF;
END $$;
ALTER TABLE hidra_identity_user_permission_grant
    ALTER COLUMN valid_to SET NOT NULL,
    ADD CONSTRAINT ck_hmr088_reason CHECK (btrim(grant_reason) <> ''),
    ADD CONSTRAINT ck_hmr088_validity CHECK (valid_to >= valid_from),
    ADD CONSTRAINT ck_hmr088_status CHECK (status IN ('ACTIVE','REVOKED','EXPIRED'));
