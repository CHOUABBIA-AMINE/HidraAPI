-- HMR-079: local recommendation references, required content and catalog coherence.
-- Publication/Audit atomicity is enforced by the owner application operation, not a foreign-module FK.
LOCK TABLE hidra_simulation_recommendation, hidra_simulation_catalog_entry,
    hidra_simulation_optimization_candidate IN SHARE ROW EXCLUSIVE MODE;
DO $$ BEGIN
    IF EXISTS (
        SELECT 1 FROM hidra_simulation_recommendation r
        LEFT JOIN hidra_simulation_catalog_entry t ON t.id=r.recommendation_type_id
        LEFT JOIN hidra_simulation_catalog_entry c ON c.id=r.confidence_level_id
        LEFT JOIN hidra_simulation_optimization_candidate p ON p.id=r.candidate_id
        WHERE r.title IS NULL OR r.title !~ '[^[:space:]]' OR r.description IS NULL OR r.description !~ '[^[:space:]]'
           OR r.created_at IS NULL OR t.id IS NULL OR t.catalog_name<>'SIMULATION_RECOMMENDATION_TYPE'
           OR (r.confidence_level_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'SIMULATION_CONFIDENCE_LEVEL'))
           OR (r.candidate_id IS NOT NULL AND p.id IS NULL)
    ) THEN RAISE EXCEPTION 'HMR-079 invalid legacy recommendation; reconcile without invented content/references'; END IF;
END $$;
ALTER TABLE hidra_simulation_recommendation ADD CONSTRAINT hmr079_required_content CHECK (
    title IS NOT NULL AND title ~ '[^[:space:]]' AND description IS NOT NULL AND description ~ '[^[:space:]]' AND created_at IS NOT NULL
);
ALTER TABLE hidra_simulation_recommendation ADD CONSTRAINT hmr079_candidate_fk
    FOREIGN KEY(candidate_id) REFERENCES hidra_simulation_optimization_candidate(id);
ALTER TABLE hidra_simulation_recommendation ADD CONSTRAINT hmr079_confidence_fk
    FOREIGN KEY(confidence_level_id) REFERENCES hidra_simulation_catalog_entry(id);
CREATE FUNCTION hmr079_validate_recommendation_catalogs() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE t hidra_simulation_catalog_entry%ROWTYPE; c hidra_simulation_catalog_entry%ROWTYPE;
    type_active boolean := true; confidence_active boolean := true;
BEGIN
    IF TG_OP='UPDATE' THEN
        type_active := NEW.recommendation_type_id IS DISTINCT FROM OLD.recommendation_type_id;
        confidence_active := NEW.confidence_level_id IS DISTINCT FROM OLD.confidence_level_id;
    END IF;
    SELECT * INTO t FROM hidra_simulation_catalog_entry WHERE id=NEW.recommendation_type_id FOR SHARE;
    IF t.id IS NULL OR t.catalog_name<>'SIMULATION_RECOMMENDATION_TYPE' OR (type_active AND NOT t.active) THEN
        RAISE EXCEPTION 'Recommendation requires eligible SIMULATION_RECOMMENDATION_TYPE' USING ERRCODE='23514';
    END IF;
    IF NEW.confidence_level_id IS NOT NULL THEN
        SELECT * INTO c FROM hidra_simulation_catalog_entry WHERE id=NEW.confidence_level_id FOR SHARE;
        IF c.id IS NULL OR c.catalog_name<>'SIMULATION_CONFIDENCE_LEVEL' OR (confidence_active AND NOT c.active) THEN
            RAISE EXCEPTION 'Recommendation requires eligible SIMULATION_CONFIDENCE_LEVEL' USING ERRCODE='23514';
        END IF;
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr079_recommendation_catalogs BEFORE INSERT OR UPDATE ON hidra_simulation_recommendation
FOR EACH ROW EXECUTE FUNCTION hmr079_validate_recommendation_catalogs();
CREATE FUNCTION hmr079_preserve_recommendation_catalog_family() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF NEW.catalog_name IS DISTINCT FROM OLD.catalog_name AND EXISTS (
        SELECT 1 FROM hidra_simulation_recommendation
        WHERE recommendation_type_id=OLD.id OR confidence_level_id=OLD.id
    ) THEN RAISE EXCEPTION 'Referenced recommendation catalog family cannot change' USING ERRCODE='23514'; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr079_catalog_family BEFORE UPDATE OF catalog_name ON hidra_simulation_catalog_entry
FOR EACH ROW EXECUTE FUNCTION hmr079_preserve_recommendation_catalog_family();
