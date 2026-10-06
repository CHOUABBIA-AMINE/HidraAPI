-- HMR-063: preserve legacy evidence and reject incompatible usernames/duplicates.
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_identity_user WHERE username IS NULL OR btrim(username) = '')
       OR EXISTS (SELECT 1 FROM hidra_identity_user GROUP BY username HAVING count(*) > 1)
       OR EXISTS (SELECT 1 FROM hidra_identity_user WHERE email_address IS NOT NULL GROUP BY email_address HAVING count(*) > 1) THEN
        RAISE EXCEPTION 'HMR-063 preflight failed: invalid or duplicate user identity' USING ERRCODE = '23514';
    END IF;
END $$;
ALTER TABLE hidra_identity_user ADD CONSTRAINT ck_hmr063_username CHECK (btrim(username) <> '');
ALTER TABLE hidra_identity_user ADD CONSTRAINT uk_identity_user_username UNIQUE (username);
ALTER TABLE hidra_identity_user ADD CONSTRAINT uk_identity_user_email UNIQUE (email_address);
-- PostgreSQL unique constraints allow multiple NULL emails. Employee is Organization-owned: no FK.
