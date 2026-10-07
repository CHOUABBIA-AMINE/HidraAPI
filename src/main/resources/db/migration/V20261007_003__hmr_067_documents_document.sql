-- Forward SCC-05: local version composite identity precedes nullable header pointer.
ALTER TABLE hidra_documents_document_version ADD CONSTRAINT uq_hmr067_version_parent UNIQUE(document_id,id);
ALTER TABLE hidra_documents_document
    ADD CONSTRAINT uq_hmr067_document_code UNIQUE(code),
    ADD CONSTRAINT ck_hmr067_title CHECK (btrim(title_fr)<>''),
    ADD CONSTRAINT ck_hmr067_creator_display CHECK (btrim(created_by_display_name_snapshot)<>''),
    ADD CONSTRAINT ck_hmr067_owner_tuple CHECK (
        (owner_module IS NULL AND owner_target_type_code IS NULL AND owner_target_id IS NULL) OR
        (owner_module IS NOT NULL AND btrim(owner_module)<>'' AND owner_target_type_code IS NOT NULL
         AND btrim(owner_target_type_code)<>'' AND owner_target_id IS NOT NULL AND btrim(owner_target_id)<>'')),
    ADD CONSTRAINT fk_hmr067_current_version FOREIGN KEY(id,current_version_id)
        REFERENCES hidra_documents_document_version(document_id,id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_hmr067_category FOREIGN KEY(document_category_id)
        REFERENCES hidra_documents_catalog_entry(id) ON DELETE RESTRICT;
-- Existing HRA-111 mandatory type/classification FKs remain unchanged.
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_documents_document d LEFT JOIN hidra_documents_catalog_entry t ON t.id=d.document_type_id
        LEFT JOIN hidra_documents_catalog_entry c ON c.id=d.classification_id
        LEFT JOIN hidra_documents_catalog_entry k ON k.id=d.document_category_id
        WHERE t.id IS NULL OR t.catalog_name<>'DOCUMENT_TYPE' OR c.id IS NULL OR c.catalog_name<>'DOCUMENT_CLASSIFICATION'
        OR (d.document_category_id IS NOT NULL AND (k.id IS NULL OR k.catalog_name<>'DOCUMENT_CATEGORY'))) THEN
        RAISE EXCEPTION 'HMR-067: reconcile invalid document catalog families';
    END IF;
END $$;
CREATE FUNCTION hmr067_require_catalog(entry_id text,family text) RETURNS void LANGUAGE plpgsql AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM hidra_documents_catalog_entry WHERE id=entry_id AND catalog_name=family AND active) THEN
        RAISE EXCEPTION 'HMR-067: active % entry required',family;
    END IF;
END $$;
CREATE FUNCTION hmr067_document_catalog_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    PERFORM hmr067_require_catalog(NEW.document_type_id,'DOCUMENT_TYPE');
    PERFORM hmr067_require_catalog(NEW.classification_id,'DOCUMENT_CLASSIFICATION');
    IF NEW.document_category_id IS NOT NULL THEN PERFORM hmr067_require_catalog(NEW.document_category_id,'DOCUMENT_CATEGORY'); END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr067_document_catalog BEFORE INSERT OR UPDATE ON hidra_documents_document
    FOR EACH ROW EXECUTE FUNCTION hmr067_document_catalog_guard();
