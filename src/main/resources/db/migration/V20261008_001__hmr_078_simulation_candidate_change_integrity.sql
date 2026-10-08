-- HMR-078: descriptive candidate-change fields and exact catalog family.
-- Existing candidate/type FKs remain intact. Topology target resolution belongs to its owner.
LOCK TABLE hidra_simulation_candidate_change, hidra_simulation_catalog_entry IN SHARE ROW EXCLUSIVE MODE;
DO $$ BEGIN
    IF EXISTS (
        SELECT 1 FROM hidra_simulation_candidate_change c
        LEFT JOIN hidra_simulation_catalog_entry t ON t.id=c.change_type_id
        WHERE t.id IS NULL OR t.catalog_name <> 'SIMULATION_CHANGE_TYPE'
           OR c.target_type IS NULL OR btrim(c.target_type) NOT IN ('PIPELINE','SEGMENT','FACILITY','EQUIPMENT','NODE','CONNECTION')
           OR c.target_id IS NULL OR c.target_id !~ '[^[:space:]]'
           OR c.after_value IS NULL OR c.after_value !~ '[^[:space:]]'
    ) THEN RAISE EXCEPTION 'HMR-078 invalid legacy candidate change; reconcile without invented targets/values'; END IF;
END $$;
ALTER TABLE hidra_simulation_candidate_change ADD CONSTRAINT hmr078_required_change CHECK (
    target_type IS NOT NULL AND btrim(target_type) IN ('PIPELINE','SEGMENT','FACILITY','EQUIPMENT','NODE','CONNECTION')
    AND target_id IS NOT NULL AND target_id ~ '[^[:space:]]' AND after_value IS NOT NULL AND after_value ~ '[^[:space:]]'
);
CREATE FUNCTION hmr078_validate_change_type() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE t hidra_simulation_catalog_entry%ROWTYPE; require_active boolean := true;
BEGIN
    IF TG_OP='UPDATE' THEN require_active := NEW.change_type_id IS DISTINCT FROM OLD.change_type_id; END IF;
    SELECT * INTO t FROM hidra_simulation_catalog_entry WHERE id=NEW.change_type_id FOR SHARE;
    IF t.id IS NULL OR t.catalog_name<>'SIMULATION_CHANGE_TYPE' OR (require_active AND NOT t.active) THEN
        RAISE EXCEPTION 'Candidate change requires eligible SIMULATION_CHANGE_TYPE' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr078_change_type BEFORE INSERT OR UPDATE ON hidra_simulation_candidate_change
FOR EACH ROW EXECUTE FUNCTION hmr078_validate_change_type();
CREATE FUNCTION hmr078_preserve_change_type_family() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF NEW.catalog_name IS DISTINCT FROM OLD.catalog_name AND EXISTS (
        SELECT 1 FROM hidra_simulation_candidate_change WHERE change_type_id=OLD.id
    ) THEN RAISE EXCEPTION 'Referenced candidate change catalog family cannot change' USING ERRCODE='23514'; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr078_catalog_family BEFORE UPDATE OF catalog_name ON hidra_simulation_catalog_entry
FOR EACH ROW EXECUTE FUNCTION hmr078_preserve_change_type_family();
