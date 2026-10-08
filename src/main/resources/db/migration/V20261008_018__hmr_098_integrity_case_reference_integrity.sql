-- HMR-098: preserve optionality, explicit taxonomy and real historical evidence.
DO $$ DECLARE family varchar(80); BEGIN
    IF EXISTS (SELECT 1 FROM hidra_integrity_case c LEFT JOIN hidra_integrity_pipeline_defect d ON d.id=c.primary_defect_id
        WHERE c.primary_defect_id IS NOT NULL AND d.id IS NULL) THEN
        RAISE EXCEPTION 'HMR-098: reconcile orphan primary-defect reference from real evidence';
    END IF;
    IF EXISTS (SELECT 1 FROM hidra_integrity_case WHERE closed_at<opened_at) THEN
        RAISE EXCEPTION 'HMR-098: reconcile legacy openedAt/closedAt ordering from real evidence';
    END IF;
    IF EXISTS (SELECT 1 FROM hidra_integrity_case) THEN
        SELECT catalog_name INTO family FROM hidra_integrity_catalog_field_policy WHERE field_role='CASE_TYPE' AND active;
        IF NOT FOUND THEN RAISE EXCEPTION 'HMR-098: existing cases require owner-approved active CASE_TYPE mapping; provision metadata after 017 then retry'; END IF;
        IF EXISTS (SELECT 1 FROM hidra_integrity_case c LEFT JOIN hidra_integrity_catalog_entry t ON t.id=c.case_type_id
            WHERE t.id IS NULL OR t.catalog_name<>family) THEN
            RAISE EXCEPTION 'HMR-098: reconcile legacy case-type family from real evidence';
        END IF;
    END IF;
END $$;
ALTER TABLE hidra_integrity_case ADD CONSTRAINT fk_hmr098_primary_defect
    FOREIGN KEY (primary_defect_id) REFERENCES hidra_integrity_pipeline_defect(id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_case VALIDATE CONSTRAINT fk_hmr098_primary_defect;
ALTER TABLE hidra_integrity_case ADD CONSTRAINT ck_hmr098_case_time CHECK (closed_at IS NULL OR closed_at>=opened_at);
CREATE FUNCTION hmr098_guard_case_type() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE family varchar(80); mapping_active boolean; t hidra_integrity_catalog_entry%ROWTYPE; fresh boolean;
BEGIN
    fresh:=TG_OP='INSERT';
    IF TG_OP='UPDATE' THEN fresh:=NEW.case_type_id IS DISTINCT FROM OLD.case_type_id; END IF;
    SELECT catalog_name,active INTO family,mapping_active FROM hidra_integrity_catalog_field_policy WHERE field_role='CASE_TYPE' FOR SHARE;
    IF NOT FOUND OR (fresh AND NOT mapping_active) THEN RAISE EXCEPTION 'HMR-098: explicit eligible case-type mapping required'; END IF;
    SELECT * INTO t FROM hidra_integrity_catalog_entry WHERE id=NEW.case_type_id FOR SHARE;
    IF NOT FOUND OR t.catalog_name<>family OR (fresh AND NOT t.active) THEN
        RAISE EXCEPTION 'HMR-098: exact eligible Integrity case-type catalog family required';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr098_case_type BEFORE INSERT OR UPDATE ON hidra_integrity_case
    FOR EACH ROW EXECUTE FUNCTION hmr098_guard_case_type();
CREATE FUNCTION hmr098_protect_used_mapping() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF OLD.field_role='CASE_TYPE' AND EXISTS (SELECT 1 FROM hidra_integrity_case) THEN
        IF TG_OP='DELETE' THEN RAISE EXCEPTION 'HMR-098: used case mapping cannot be erased'; END IF;
        IF NEW.field_role IS DISTINCT FROM OLD.field_role OR NEW.catalog_name IS DISTINCT FROM OLD.catalog_name THEN
            RAISE EXCEPTION 'HMR-098: used case mapping identity cannot be reassigned';
        END IF;
    END IF;
    IF TG_OP='DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr098_used_mapping BEFORE UPDATE OR DELETE ON hidra_integrity_catalog_field_policy
    FOR EACH ROW EXECUTE FUNCTION hmr098_protect_used_mapping();
CREATE FUNCTION hmr098_protect_used_catalog_family() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP='DELETE' THEN
        IF EXISTS (SELECT 1 FROM hidra_integrity_case WHERE case_type_id=OLD.id) THEN
            RAISE EXCEPTION 'HMR-098: used case-type catalog cannot be deleted';
        END IF;
        RETURN OLD;
    END IF;
    IF (NEW.id IS DISTINCT FROM OLD.id OR NEW.catalog_name IS DISTINCT FROM OLD.catalog_name)
        AND EXISTS (SELECT 1 FROM hidra_integrity_case WHERE case_type_id=OLD.id) THEN
        RAISE EXCEPTION 'HMR-098: referenced case-type catalog identity/family cannot be reassigned';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr098_used_catalog_family BEFORE UPDATE OR DELETE ON hidra_integrity_catalog_entry
    FOR EACH ROW EXECUTE FUNCTION hmr098_protect_used_catalog_family();
CREATE FUNCTION hmr098_no_taxonomy_truncate() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN RAISE EXCEPTION 'HMR-098: Integrity case taxonomy cannot be truncated'; END $$;
CREATE TRIGGER hmr098_policy_no_truncate BEFORE TRUNCATE ON hidra_integrity_catalog_field_policy
    FOR EACH STATEMENT EXECUTE FUNCTION hmr098_no_taxonomy_truncate();
CREATE TRIGGER hmr098_catalog_no_truncate BEFORE TRUNCATE ON hidra_integrity_catalog_entry
    FOR EACH STATEMENT EXECUTE FUNCTION hmr098_no_taxonomy_truncate();
-- Existing case-type FK remains. No severity family, mandatory defect, external FK,
-- defect status/topology equality or stronger case-status/closedAt invariant is invented.
