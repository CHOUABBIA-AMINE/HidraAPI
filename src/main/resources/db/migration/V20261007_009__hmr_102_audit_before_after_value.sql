-- HMR-102: field evidence cannot be replaced or retain raw masked values.
DO $$ BEGIN
    IF EXISTS(SELECT 1 FROM hidra_audit_before_after_value b LEFT JOIN hidra_audit_catalog_entry c ON c.id=b.mask_reason_id
        WHERE b.mask_reason_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'MASK_REASON')) THEN
        RAISE EXCEPTION 'Legacy Audit masking reason requires explicit owner reconciliation';
    END IF;
END $$;
ALTER TABLE hidra_audit_before_after_value ADD CONSTRAINT hmr102_event_fk FOREIGN KEY(audit_event_id) REFERENCES hidra_audit_event(id);
ALTER TABLE hidra_audit_before_after_value ADD CONSTRAINT hmr102_mask_fk FOREIGN KEY(mask_reason_id) REFERENCES hidra_audit_catalog_entry(id);
ALTER TABLE hidra_audit_before_after_value ADD CONSTRAINT hmr102_field_required CHECK(length(btrim(field_path))>0);
ALTER TABLE hidra_audit_before_after_value ADD CONSTRAINT hmr102_no_raw_masked CHECK(
    NOT (masked OR value_type='MASKED' OR regexp_replace(lower(normalize(field_path,NFKC)),'[^a-z0-9]','','g')
        ~ '(password|token|secret|privatekey|credential|apikey|session|authorization|cookie)')
    OR (before_value_text IS NULL AND after_value_text IS NULL));
CREATE FUNCTION hmr102_mask_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF NEW.mask_reason_id IS NOT NULL THEN PERFORM hmr083_require_catalog(NEW.mask_reason_id,'MASK_REASON'); END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr102_value_insert BEFORE INSERT ON hidra_audit_before_after_value FOR EACH ROW EXECUTE FUNCTION hmr102_mask_guard();
CREATE TRIGGER hmr102_value_immutable BEFORE UPDATE OR DELETE ON hidra_audit_before_after_value FOR EACH ROW EXECUTE FUNCTION hmr095_forbid_evidence_mutation();
