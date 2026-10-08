-- Forward-only. 019 survives a failed transactional 020 so approved metadata can be provisioned.
-- No historical repair or cross-owner FK. Existing revision/catalog FKs remain unchanged.
DO $$ BEGIN
    IF EXISTS (
        SELECT 1 FROM hidra_planning_plan_target t
        LEFT JOIN hidra_planning_plan_revision r ON r.id=t.revision_id
        LEFT JOIN hidra_planning_nomination n ON n.id=t.nomination_id
        LEFT JOIN hidra_planning_plan_scenario s ON s.id=t.scenario_id
        LEFT JOIN hidra_planning_catalog_entry c ON c.id=t.target_type_id
        LEFT JOIN hidra_planning_target_value_policy p ON p.target_type_id=t.target_type_id
        WHERE r.id IS NULL OR c.id IS NULL OR c.catalog_name <> 'TARGET_TYPE' OR p.target_type_id IS NULL
          OR (t.nomination_id IS NOT NULL AND (n.id IS NULL OR n.revision_id <> t.revision_id))
          OR (t.scenario_id IS NOT NULL AND (s.id IS NULL OR s.revision_id <> t.revision_id))
          OR nullif(btrim(t.topology_asset_type),'') IS NULL
          OR nullif(btrim(t.topology_asset_id),'') IS NULL OR nullif(btrim(t.topology_asset_code),'') IS NULL
          OR t.valid_to < t.valid_from
          OR (p.representation_kind='NUMERIC' AND (t.target_value IS NULL OR nullif(btrim(t.unit_id),'') IS NULL))
          OR (p.representation_kind='TEXT' AND nullif(btrim(t.target_text_value),'') IS NULL)
    ) THEN RAISE EXCEPTION 'HMR-094: reconcile legacy integrity and provision approved target value mappings before retrying 020'; END IF;
END $$;

ALTER TABLE hidra_planning_nomination ADD CONSTRAINT uq_hmr094_nomination_revision UNIQUE(id, revision_id);
ALTER TABLE hidra_planning_plan_scenario ADD CONSTRAINT uq_hmr094_scenario_revision UNIQUE(id, revision_id);
ALTER TABLE hidra_planning_plan_target
    ADD CONSTRAINT fk_hmr094_revision FOREIGN KEY(revision_id) REFERENCES hidra_planning_plan_revision(id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_hmr094_nomination_revision FOREIGN KEY(nomination_id,revision_id)
        REFERENCES hidra_planning_nomination(id,revision_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
    ADD CONSTRAINT fk_hmr094_scenario_revision FOREIGN KEY(scenario_id,revision_id)
        REFERENCES hidra_planning_plan_scenario(id,revision_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
    ADD CONSTRAINT fk_hmr094_value_policy FOREIGN KEY(target_type_id)
        REFERENCES hidra_planning_target_value_policy(target_type_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
    ADD CONSTRAINT ck_hmr094_required_topology_type CHECK(nullif(btrim(topology_asset_type),'') IS NOT NULL),
    ADD CONSTRAINT ck_hmr094_validity CHECK(valid_to >= valid_from);

CREATE FUNCTION hmr094_target_write_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE
    family varchar(80); catalog_active boolean; policy_active boolean; representation varchar(16);
    unchanged boolean := false;
BEGIN
    IF TG_OP='UPDATE' THEN
        unchanged := ROW(NEW.target_type_id,NEW.target_value,NEW.target_text_value,NEW.unit_id)
            IS NOT DISTINCT FROM ROW(OLD.target_type_id,OLD.target_value,OLD.target_text_value,OLD.unit_id);
    END IF;
    SELECT catalog_name,active INTO family,catalog_active
        FROM hidra_planning_catalog_entry WHERE id=NEW.target_type_id FOR SHARE;
    SELECT representation_kind,active INTO representation,policy_active
        FROM hidra_planning_target_value_policy WHERE target_type_id=NEW.target_type_id FOR SHARE;
    IF family IS DISTINCT FROM 'TARGET_TYPE' OR representation IS NULL THEN
        RAISE EXCEPTION 'HMR-094: exact TARGET_TYPE and approved value policy required';
    END IF;
    IF NOT unchanged AND (NOT catalog_active OR NOT policy_active) THEN
        RAISE EXCEPTION 'HMR-094: fresh value semantics require active catalog/policy';
    END IF;
    IF (representation='NUMERIC' AND (NEW.target_value IS NULL OR nullif(btrim(NEW.unit_id),'') IS NULL))
        OR (representation='TEXT' AND nullif(btrim(NEW.target_text_value),'') IS NULL) THEN
        RAISE EXCEPTION 'HMR-094: target representation is incomplete';
    END IF;
    -- Composite local FKs enforce nullable nomination/scenario revision compatibility, including races.
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr094_target_write BEFORE INSERT OR UPDATE ON hidra_planning_plan_target
    FOR EACH ROW EXECUTE FUNCTION hmr094_target_write_guard();

CREATE FUNCTION hmr094_policy_mutation_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP='DELETE' THEN
        IF EXISTS(SELECT 1 FROM hidra_planning_plan_target WHERE target_type_id=OLD.target_type_id) THEN
            RAISE EXCEPTION 'HMR-094: used representation policy cannot be deleted';
        END IF;
        RETURN OLD;
    END IF;
    IF ROW(NEW.target_type_id,NEW.representation_kind) IS DISTINCT FROM ROW(OLD.target_type_id,OLD.representation_kind)
        AND EXISTS(SELECT 1 FROM hidra_planning_plan_target WHERE target_type_id=OLD.target_type_id) THEN
        RAISE EXCEPTION 'HMR-094: used representation policy cannot be reassigned';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr094_policy_mutation BEFORE UPDATE OR DELETE ON hidra_planning_target_value_policy
    FOR EACH ROW EXECUTE FUNCTION hmr094_policy_mutation_guard();

CREATE FUNCTION hmr094_catalog_mutation_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP='DELETE' THEN
        IF EXISTS(SELECT 1 FROM hidra_planning_plan_target WHERE target_type_id=OLD.id) THEN
            RAISE EXCEPTION 'HMR-094: used target type cannot be deleted';
        END IF;
        RETURN OLD;
    END IF;
    IF ROW(NEW.id,NEW.catalog_name) IS DISTINCT FROM ROW(OLD.id,OLD.catalog_name)
        AND EXISTS(SELECT 1 FROM hidra_planning_plan_target WHERE target_type_id=OLD.id) THEN
        RAISE EXCEPTION 'HMR-094: used target type family cannot be reassigned';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr094_catalog_mutation BEFORE UPDATE OR DELETE ON hidra_planning_catalog_entry
    FOR EACH ROW EXECUTE FUNCTION hmr094_catalog_mutation_guard();

CREATE FUNCTION hmr094_metadata_truncate_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM hidra_planning_plan_target) THEN
        RAISE EXCEPTION 'HMR-094: used target metadata cannot be truncated';
    END IF;
    RETURN NULL;
END $$;
CREATE TRIGGER tr_hmr094_policy_truncate BEFORE TRUNCATE ON hidra_planning_target_value_policy
    FOR EACH STATEMENT EXECUTE FUNCTION hmr094_metadata_truncate_guard();
CREATE TRIGGER tr_hmr094_catalog_truncate BEFORE TRUNCATE ON hidra_planning_catalog_entry
    FOR EACH STATEMENT EXECUTE FUNCTION hmr094_metadata_truncate_guard();
