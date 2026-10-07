-- HMR-083: additive export admission; legacy evidence is never silently rewritten.
CREATE FUNCTION hmr083_require_catalog(p_id text, p_family text) RETURNS void
LANGUAGE plpgsql AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM hidra_audit_catalog_entry WHERE id=p_id AND catalog_name=p_family AND active) THEN
        RAISE EXCEPTION 'Audit catalog reference must be active in the required family' USING ERRCODE='23514';
    END IF;
END $$;

CREATE FUNCTION hmr083_json_depth(p_json jsonb) RETURNS integer LANGUAGE plpgsql IMMUTABLE AS $$
DECLARE item jsonb; depth integer := 0;
BEGIN
    IF jsonb_typeof(p_json)='object' THEN
        FOR item IN SELECT value FROM jsonb_each(p_json) LOOP depth := greatest(depth,hmr083_json_depth(item)); END LOOP;
        RETURN depth+1;
    ELSIF jsonb_typeof(p_json)='array' THEN
        FOR item IN SELECT value FROM jsonb_array_elements(p_json) LOOP depth := greatest(depth,hmr083_json_depth(item)); END LOOP;
        RETURN depth+1;
    END IF;
    RETURN 0;
END $$;

DO $$ BEGIN
    IF EXISTS(SELECT 1 FROM hidra_audit_export_request e LEFT JOIN hidra_audit_catalog_entry c ON c.id=e.purpose_id
            WHERE c.id IS NULL OR c.catalog_name <> 'EXPORT_PURPOSE') THEN
        RAISE EXCEPTION 'Legacy Audit export purpose requires explicit owner reconciliation';
    END IF;
END $$;
ALTER TABLE hidra_audit_export_request ADD CONSTRAINT hmr083_purpose_fk FOREIGN KEY(purpose_id) REFERENCES hidra_audit_catalog_entry(id);
ALTER TABLE hidra_audit_export_request ADD CONSTRAINT hmr083_required_filter_format CHECK (
    length(btrim(format))>0 AND jsonb_typeof(filter_json) IN ('object','array')
    AND octet_length(filter_json::text)<=65536 AND hmr083_json_depth(filter_json)<=32);
CREATE FUNCTION hmr083_export_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    PERFORM hmr083_require_catalog(NEW.purpose_id,'EXPORT_PURPOSE');
    IF NEW.status <> 'REQUESTED' THEN
        RAISE EXCEPTION 'Audit export lifecycle requires separately admitted authorization' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr083_export_insert_guard BEFORE INSERT ON hidra_audit_export_request FOR EACH ROW EXECUTE FUNCTION hmr083_export_guard();
