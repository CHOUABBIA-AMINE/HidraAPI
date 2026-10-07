-- SCC-04 forward order: revisions exist before nullable plan -> revision composition FKs.
ALTER TABLE hidra_planning_plan_revision ADD CONSTRAINT uq_hmr065_revision_parent UNIQUE (plan_id,id);
ALTER TABLE hidra_planning_operational_plan
    ADD CONSTRAINT uq_hmr065_plan_code UNIQUE(code),
    ADD CONSTRAINT ck_hmr065_name CHECK (btrim(name_fr) <> ''),
    ADD CONSTRAINT ck_hmr065_scope_type CHECK (btrim(topology_scope_type) <> ''),
    ADD CONSTRAINT fk_hmr065_current_revision FOREIGN KEY (id,current_revision_id)
        REFERENCES hidra_planning_plan_revision(plan_id,id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_hmr065_approved_revision FOREIGN KEY (id,approved_revision_id)
        REFERENCES hidra_planning_plan_revision(plan_id,id) ON DELETE RESTRICT;
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_planning_operational_plan p LEFT JOIN hidra_planning_catalog_entry c ON c.id=p.plan_type_id
        WHERE c.id IS NULL OR c.catalog_name <> 'PLAN_TYPE') THEN
        RAISE EXCEPTION 'HMR-065: reconcile invalid PLAN_TYPE references';
    END IF;
END $$;
CREATE FUNCTION hmr065_plan_catalog_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM hidra_planning_catalog_entry WHERE id=NEW.plan_type_id
        AND catalog_name='PLAN_TYPE' AND active) THEN
        RAISE EXCEPTION 'HMR-065: active PLAN_TYPE entry required';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr065_plan_catalog BEFORE INSERT OR UPDATE ON hidra_planning_operational_plan
    FOR EACH ROW EXECUTE FUNCTION hmr065_plan_catalog_guard();
