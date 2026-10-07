-- HMR-095: immutable event ledger; no governed update exception is admitted.
DO $$ BEGIN
    IF EXISTS(SELECT 1 FROM hidra_audit_event e
        LEFT JOIN hidra_audit_catalog_entry t ON t.id=e.event_type_id
        LEFT JOIN hidra_audit_catalog_entry c ON c.id=e.event_category_id
        LEFT JOIN hidra_audit_catalog_entry s ON s.id=e.severity_id
        LEFT JOIN hidra_audit_catalog_entry r ON r.id=e.reason_id
        WHERE t.id IS NULL OR t.catalog_name<>'EVENT_TYPE' OR c.id IS NULL OR c.catalog_name<>'EVENT_CATEGORY'
        OR (e.severity_id IS NOT NULL AND (s.id IS NULL OR s.catalog_name<>'SEVERITY'))
        OR (e.reason_id IS NOT NULL AND (r.id IS NULL OR r.catalog_name<>'DECISION_REASON'))) THEN
        RAISE EXCEPTION 'Legacy Audit event catalog families require explicit reconciliation';
    END IF;
END $$;
ALTER TABLE hidra_audit_event ADD CONSTRAINT hmr095_type_fk FOREIGN KEY(event_type_id) REFERENCES hidra_audit_catalog_entry(id);
ALTER TABLE hidra_audit_event ADD CONSTRAINT hmr095_category_fk FOREIGN KEY(event_category_id) REFERENCES hidra_audit_catalog_entry(id);
ALTER TABLE hidra_audit_event ADD CONSTRAINT hmr095_severity_fk FOREIGN KEY(severity_id) REFERENCES hidra_audit_catalog_entry(id);
ALTER TABLE hidra_audit_event ADD CONSTRAINT hmr095_reason_fk FOREIGN KEY(reason_id) REFERENCES hidra_audit_catalog_entry(id);
ALTER TABLE hidra_audit_event ADD CONSTRAINT hmr095_required_modules CHECK(length(btrim(source_module))>0 AND length(btrim(target_module))>0 AND length(btrim(target_type))>0);
ALTER TABLE hidra_audit_event ADD CONSTRAINT hmr095_payload_budget CHECK(payload_json IS NULL OR
    (jsonb_typeof(payload_json) IN ('object','array') AND octet_length(payload_json::text)<=65536 AND hmr083_json_depth(payload_json)<=32));
CREATE FUNCTION hmr095_event_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    PERFORM hmr083_require_catalog(NEW.event_type_id,'EVENT_TYPE');
    PERFORM hmr083_require_catalog(NEW.event_category_id,'EVENT_CATEGORY');
    IF NEW.severity_id IS NOT NULL THEN PERFORM hmr083_require_catalog(NEW.severity_id,'SEVERITY'); END IF;
    IF NEW.reason_id IS NOT NULL THEN PERFORM hmr083_require_catalog(NEW.reason_id,'DECISION_REASON'); END IF;
    RETURN NEW;
END $$;
CREATE FUNCTION hmr095_forbid_evidence_mutation() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    RAISE EXCEPTION 'Audit evidence is append-only; UPDATE and DELETE are forbidden' USING ERRCODE='23514';
END $$;
CREATE TRIGGER hmr095_event_insert BEFORE INSERT ON hidra_audit_event FOR EACH ROW EXECUTE FUNCTION hmr095_event_guard();
CREATE TRIGGER hmr095_event_immutable BEFORE UPDATE OR DELETE ON hidra_audit_event FOR EACH ROW EXECUTE FUNCTION hmr095_forbid_evidence_mutation();
