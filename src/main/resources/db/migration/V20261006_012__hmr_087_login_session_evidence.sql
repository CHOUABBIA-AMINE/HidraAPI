-- HMR-087: explicit session protocol and independent termination evidence.
-- Operators must supply known legacy protocol evidence before replay if legacy sessions exist.
-- Do not infer LOCAL/SYSTEM from a missing provider, or ended_at from last_seen_at.
ALTER TABLE hidra_identity_login_session ADD COLUMN IF NOT EXISTS session_type varchar(80);
ALTER TABLE hidra_identity_login_session ADD COLUMN IF NOT EXISTS ended_at timestamptz;
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_identity_login_session
        WHERE session_type IS NULL OR session_type NOT IN ('LOCAL','LDAP','OIDC','SAML2','OAUTH2','API_TOKEN','SYSTEM')) THEN
        RAISE EXCEPTION 'HMR-087 preflight failed: explicit legacy session protocol reconciliation required' USING ERRCODE = '23514';
    END IF;
END $$;
ALTER TABLE hidra_identity_login_session
    ALTER COLUMN session_type SET NOT NULL,
    ADD CONSTRAINT ck_hmr087_protocol CHECK (session_type IN ('LOCAL','LDAP','OIDC','SAML2','OAUTH2','API_TOKEN','SYSTEM'));
