-- HMR-086: no fabricated reasons, expiry or suspended-state conversion.
ALTER TABLE hidra_identity_authorization_delegation_grant ADD COLUMN IF NOT EXISTS reason text;
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_identity_authorization_delegation_grant d
        WHERE d.reason IS NULL OR btrim(d.reason) = '' OR d.valid_to IS NULL
           OR d.valid_to < d.valid_from OR d.status NOT IN ('ACTIVE','REVOKED','EXPIRED')
           OR (d.role_id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM hidra_identity_role r WHERE r.id = d.role_id))
           OR (d.permission_id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM hidra_identity_permission p WHERE p.id = d.permission_id))) THEN
        RAISE EXCEPTION 'HMR-086 preflight failed: operator reconciliation of delegation evidence required' USING ERRCODE = '23514';
    END IF;
END $$;
ALTER TABLE hidra_identity_authorization_delegation_grant
    ALTER COLUMN reason SET NOT NULL,
    ALTER COLUMN valid_to SET NOT NULL,
    ADD CONSTRAINT ck_hmr086_reason CHECK (btrim(reason) <> ''),
    ADD CONSTRAINT ck_hmr086_status CHECK (status IN ('ACTIVE','REVOKED','EXPIRED')),
    ADD CONSTRAINT ck_hmr086_validity CHECK (valid_to >= valid_from),
    ADD CONSTRAINT fk_hmr086_role FOREIGN KEY (role_id) REFERENCES hidra_identity_role(id),
    ADD CONSTRAINT fk_hmr086_permission FOREIGN KEY (permission_id) REFERENCES hidra_identity_permission(id);
