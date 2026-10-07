-- HMR-056: local nullable references and correlated endpoint ownership.
-- Existing invalid evidence aborts validation; do not rewrite history.
DO $$ BEGIN
    IF EXISTS(SELECT 1 FROM hidra_integration_exchange_message m
            LEFT JOIN hidra_integration_catalog_entry t ON t.id=m.message_type_id
            LEFT JOIN hidra_integration_catalog_entry f ON f.id=m.payload_format_id
            WHERE t.id IS NULL OR t.catalog_name<>'MESSAGE_TYPE' OR f.id IS NULL OR f.catalog_name<>'PAYLOAD_FORMAT') THEN
        RAISE EXCEPTION 'Legacy Integration message catalog families require explicit reconciliation';
    END IF;
END $$;
ALTER TABLE hidra_integration_external_endpoint ADD CONSTRAINT hmr056_endpoint_owner_unique UNIQUE(id,external_system_id);
ALTER TABLE hidra_integration_exchange_message ADD CONSTRAINT hmr056_run_fk FOREIGN KEY(job_run_id) REFERENCES hidra_integration_job_run(id);
ALTER TABLE hidra_integration_exchange_message ADD CONSTRAINT hmr056_endpoint_owner_fk FOREIGN KEY(endpoint_id,external_system_id) REFERENCES hidra_integration_external_endpoint(id,external_system_id);
CREATE FUNCTION hmr056_message_catalog_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF NOT EXISTS(SELECT 1 FROM hidra_integration_catalog_entry WHERE id=NEW.message_type_id AND catalog_name='MESSAGE_TYPE' AND active)
        OR NOT EXISTS(SELECT 1 FROM hidra_integration_catalog_entry WHERE id=NEW.payload_format_id AND catalog_name='PAYLOAD_FORMAT' AND active) THEN
        RAISE EXCEPTION 'Integration message catalogs must be active in their exact families' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr056_message_catalog BEFORE INSERT OR UPDATE ON hidra_integration_exchange_message FOR EACH ROW EXECUTE FUNCTION hmr056_message_catalog_guard();
