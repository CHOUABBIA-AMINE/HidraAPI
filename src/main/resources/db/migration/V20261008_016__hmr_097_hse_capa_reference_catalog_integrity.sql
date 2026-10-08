-- HMR-097: explicitly configured owner taxonomy; never infer or seed the catalog family.
DO $$ DECLARE family varchar(80); BEGIN
    IF EXISTS (SELECT 1 FROM hidra_hse_capa) THEN
        SELECT catalog_name INTO family FROM hidra_hse_catalog_field_policy WHERE field_role='CAPA_ACTION_TYPE' AND active;
        IF NOT FOUND THEN RAISE EXCEPTION 'HMR-097: existing CAPA requires owner-approved active CAPA_ACTION_TYPE mapping; provision policy after 014 and retry'; END IF;
        IF EXISTS (SELECT 1 FROM hidra_hse_capa c LEFT JOIN hidra_hse_catalog_entry t ON t.id=c.action_type_id
            LEFT JOIN hidra_hse_case p ON p.id=c.hse_case_id WHERE t.id IS NULL OR t.catalog_name<>family OR p.id IS NULL) THEN
            RAISE EXCEPTION 'HMR-097: reconcile legacy CAPA parent/action-type family from actual evidence';
        END IF;
    END IF;
END $$;
CREATE FUNCTION hmr097_guard_capa_write() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE family varchar(80); mapping_active boolean; t hidra_hse_catalog_entry%ROWTYPE; fresh boolean;
BEGIN
    PERFORM 1 FROM hidra_hse_case WHERE id=NEW.hse_case_id FOR UPDATE;
    IF NOT FOUND THEN RAISE EXCEPTION 'HMR-097: unknown HSE case %',NEW.hse_case_id; END IF;
    fresh:=TG_OP='INSERT';
    IF TG_OP='UPDATE' THEN fresh:=NEW.action_type_id IS DISTINCT FROM OLD.action_type_id; END IF;
    SELECT catalog_name,active INTO family,mapping_active FROM hidra_hse_catalog_field_policy
        WHERE field_role='CAPA_ACTION_TYPE' FOR SHARE;
    IF NOT FOUND OR (fresh AND NOT mapping_active) THEN RAISE EXCEPTION 'HMR-097: explicit eligible CAPA action-type mapping required'; END IF;
    SELECT * INTO t FROM hidra_hse_catalog_entry WHERE id=NEW.action_type_id FOR SHARE;
    IF NOT FOUND OR t.catalog_name<>family OR (fresh AND NOT t.active) THEN
        RAISE EXCEPTION 'HMR-097: exact eligible CAPA action-type family required';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr097_capa_write BEFORE INSERT OR UPDATE ON hidra_hse_capa
    FOR EACH ROW EXECUTE FUNCTION hmr097_guard_capa_write();
CREATE FUNCTION hmr097_protect_used_mapping() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF OLD.field_role='CAPA_ACTION_TYPE' AND EXISTS (SELECT 1 FROM hidra_hse_capa) THEN
        IF TG_OP='DELETE' THEN RAISE EXCEPTION 'HMR-097: used CAPA mapping cannot be erased'; END IF;
        IF NEW.field_role IS DISTINCT FROM OLD.field_role OR NEW.catalog_name IS DISTINCT FROM OLD.catalog_name THEN
            RAISE EXCEPTION 'HMR-097: used CAPA mapping identity cannot be reassigned';
        END IF;
    END IF;
    IF TG_OP='DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr097_used_mapping BEFORE UPDATE OR DELETE ON hidra_hse_catalog_field_policy
    FOR EACH ROW EXECUTE FUNCTION hmr097_protect_used_mapping();
CREATE FUNCTION hmr097_protect_used_catalog_family() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF NEW.catalog_name IS DISTINCT FROM OLD.catalog_name AND EXISTS (SELECT 1 FROM hidra_hse_capa WHERE action_type_id=OLD.id) THEN
        RAISE EXCEPTION 'HMR-097: referenced CAPA catalog family cannot be reassigned';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr097_used_catalog_family BEFORE UPDATE ON hidra_hse_catalog_entry
    FOR EACH ROW EXECUTE FUNCTION hmr097_protect_used_catalog_family();
CREATE FUNCTION hmr097_policy_no_truncate() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN RAISE EXCEPTION 'HMR-097: catalog field policy cannot be truncated'; END $$;
CREATE TRIGGER hmr097_policy_no_truncate BEFORE TRUNCATE ON hidra_hse_catalog_field_policy
    FOR EACH STATEMENT EXECUTE FUNCTION hmr097_policy_no_truncate();
-- HRA-111 same-module parent/catalog FKs remain authoritative; no external-owner FKs.
