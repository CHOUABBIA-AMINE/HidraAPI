-- HMR-008: DocumentStorageObject semantic remediation.
-- Existing migrations remain immutable; HRA-111 already supplies the provider-row FK.

ALTER TABLE hidra_documents_storage_object
    ADD CONSTRAINT ck_hmr008_storage_content_length
    CHECK (content_length_bytes >= 0) NOT VALID;

ALTER TABLE hidra_documents_storage_object
    ADD CONSTRAINT ck_hmr008_storage_required_metadata
    CHECK (
        btrim(content_type) <> ''
        AND btrim(checksum_algorithm) <> ''
        AND btrim(checksum_value) <> ''
        AND btrim(object_key) <> ''
    ) NOT VALID;

ALTER TABLE hidra_documents_storage_object
    ADD CONSTRAINT ck_hmr008_storage_object_key_opaque
    CHECK (position('://' in object_key) = 0 AND position('?' in object_key) = 0) NOT VALID;

ALTER TABLE hidra_documents_storage_object
    VALIDATE CONSTRAINT ck_hmr008_storage_content_length;

ALTER TABLE hidra_documents_storage_object
    VALIDATE CONSTRAINT ck_hmr008_storage_required_metadata;

ALTER TABLE hidra_documents_storage_object
    VALIDATE CONSTRAINT ck_hmr008_storage_object_key_opaque;
