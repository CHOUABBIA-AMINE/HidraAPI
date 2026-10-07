-- Forward version evidence and optional lineage. No cross-module approval FK.
ALTER TABLE hidra_documents_document_version
    ADD CONSTRAINT ck_hmr068_positive_version CHECK(version_number>=1),
    ADD CONSTRAINT uq_hmr068_document_number UNIQUE(document_id,version_number),
    ADD CONSTRAINT ck_hmr068_mime CHECK(btrim(mime_type)<>''),
    ADD CONSTRAINT ck_hmr068_filename CHECK(btrim(original_filename)<>''),
    ADD CONSTRAINT ck_hmr068_checksum_algorithm CHECK(btrim(checksum_algorithm)<>''),
    ADD CONSTRAINT ck_hmr068_checksum_value CHECK(btrim(checksum_value)<>''),
    ADD CONSTRAINT ck_hmr068_uploader_display CHECK(btrim(uploaded_by_display_name_snapshot)<>''),
    ADD CONSTRAINT ck_hmr068_no_self_supersession CHECK(superseded_by_version_id IS NULL OR btrim(superseded_by_version_id)<>btrim(id)),
    ADD CONSTRAINT fk_hmr068_supersession FOREIGN KEY(superseded_by_version_id)
        REFERENCES hidra_documents_document_version(id) ON DELETE RESTRICT;
-- Existing HRA-111 document/storage-object FKs and zero-byte content semantics remain.
