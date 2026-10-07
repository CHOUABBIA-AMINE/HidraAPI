-- Neutral external targets; only Documents-owned correlation is relational.
ALTER TABLE hidra_documents_target_link
    ADD CONSTRAINT ck_hmr084_target_module CHECK(btrim(target_module)<>''),
    ADD CONSTRAINT fk_hmr084_linked_version FOREIGN KEY(document_id,document_version_id)
        REFERENCES hidra_documents_document_version(document_id,id) ON DELETE RESTRICT;
-- Existing HRA-111 parent-document and link-role FKs remain.
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_documents_target_link l LEFT JOIN hidra_documents_catalog_entry c ON c.id=l.link_role_id
        WHERE c.id IS NULL OR c.catalog_name<>'DOCUMENT_LINK_ROLE') THEN
        RAISE EXCEPTION 'HMR-084: reconcile invalid DOCUMENT_LINK_ROLE references';
    END IF;
END $$;
CREATE FUNCTION hmr084_link_catalog_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    PERFORM hmr067_require_catalog(NEW.link_role_id,'DOCUMENT_LINK_ROLE');RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr084_link_catalog BEFORE INSERT OR UPDATE ON hidra_documents_target_link
    FOR EACH ROW EXECUTE FUNCTION hmr084_link_catalog_guard();
