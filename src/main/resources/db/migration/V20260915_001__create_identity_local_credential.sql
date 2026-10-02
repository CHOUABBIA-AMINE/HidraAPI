-- HIDRA identity LOCAL credential persistence
-- Module: identity
-- Forward-only migration for AUTH-009.

CREATE TABLE IF NOT EXISTS hidra_identity_local_credential (
    id varchar(80) PRIMARY KEY,
    user_id varchar(80) NOT NULL,
    password_hash varchar(255) NOT NULL,
    credential_status varchar(40) NOT NULL,
    password_changed_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    CONSTRAINT uk_hidra_identity_local_credential_user UNIQUE (user_id),
    CONSTRAINT fk_hidra_identity_local_credential_user
        FOREIGN KEY (user_id) REFERENCES hidra_identity_user (id)
);

CREATE INDEX IF NOT EXISTS ix_hidra_identity_local_credential_status
    ON hidra_identity_local_credential (credential_status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_local_credential_updated_at
    ON hidra_identity_local_credential (updated_at);
