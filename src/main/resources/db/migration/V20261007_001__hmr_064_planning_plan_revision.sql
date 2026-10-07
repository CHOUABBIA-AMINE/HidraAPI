-- Forward-only HMR-064. Invalid legacy data aborts; no fabricated lineage/catalogs.
ALTER TABLE hidra_planning_plan_revision
    ADD CONSTRAINT ck_hmr064_positive_number CHECK (revision_number > 0),
    ADD CONSTRAINT uq_hmr064_plan_number UNIQUE (plan_id, revision_number),
    ADD CONSTRAINT ck_hmr064_no_self_base CHECK (base_revision_id IS NULL OR btrim(base_revision_id) <> btrim(id)),
    ADD CONSTRAINT fk_hmr064_base FOREIGN KEY (base_revision_id) REFERENCES hidra_planning_plan_revision(id) ON DELETE RESTRICT;
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_planning_plan_revision r LEFT JOIN hidra_planning_catalog_entry c ON c.id=r.change_reason_code_id
        WHERE r.change_reason_code_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name <> 'REVISION_REASON')) THEN
        RAISE EXCEPTION 'HMR-064: reconcile invalid REVISION_REASON references';
    END IF;
END $$;
CREATE FUNCTION hmr064_revision_write_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP = 'DELETE' THEN
        IF OLD.status = 'APPROVED' THEN RAISE EXCEPTION 'HMR-064: approved revision is immutable'; END IF;
        RETURN OLD;
    END IF;
    IF TG_OP = 'UPDATE' THEN
        IF OLD.status = 'APPROVED' AND NEW IS DISTINCT FROM OLD THEN
            RAISE EXCEPTION 'HMR-064: approved revision is immutable';
        END IF;
    END IF;
    IF NEW.change_reason_code_id IS NOT NULL AND NOT EXISTS (
        SELECT 1 FROM hidra_planning_catalog_entry WHERE id=NEW.change_reason_code_id
        AND catalog_name='REVISION_REASON' AND active) THEN
        RAISE EXCEPTION 'HMR-064: active REVISION_REASON entry required';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr064_revision_write BEFORE INSERT OR UPDATE OR DELETE ON hidra_planning_plan_revision
    FOR EACH ROW EXECUTE FUNCTION hmr064_revision_write_guard();
CREATE FUNCTION hmr064_revision_truncate_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM hidra_planning_plan_revision WHERE status='APPROVED') THEN
        RAISE EXCEPTION 'HMR-064: cannot truncate approved revisions';
    END IF;
    RETURN NULL;
END $$;
CREATE TRIGGER tr_hmr064_revision_truncate BEFORE TRUNCATE ON hidra_planning_plan_revision
    FOR EACH STATEMENT EXECUTE FUNCTION hmr064_revision_truncate_guard();
