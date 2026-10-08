-- Monitoring-owned local integrity only; owner contracts validate Planning/Telemetry at application writes.
-- No external FK, guessed evidence, data rewrite, numeric recomputation or lifecycle eligibility policy.
DO $$ BEGIN
    IF EXISTS(SELECT 1 FROM hidra_monitoring_plan_actual_deviation d
        LEFT JOIN hidra_monitoring_evaluation e ON e.id=d.evaluation_id
        WHERE d.evaluation_id IS NOT NULL AND (
            e.id IS NULL
            OR (e.topology_asset_type IS NOT NULL AND d.topology_asset_type IS NOT NULL
                AND e.topology_asset_type <> d.topology_asset_type)
            OR (e.topology_asset_id IS NOT NULL AND e.topology_asset_id <> d.topology_asset_id)
            OR (e.telemetry_point_id IS NOT NULL AND d.telemetry_point_id IS NOT NULL
                AND e.telemetry_point_id <> d.telemetry_point_id)
        )) THEN RAISE EXCEPTION 'HMR-103: reconcile orphan or inconsistent legacy evaluation context'; END IF;
END $$;
ALTER TABLE hidra_monitoring_plan_actual_deviation
    ADD CONSTRAINT fk_hmr103_evaluation FOREIGN KEY(evaluation_id)
        REFERENCES hidra_monitoring_evaluation(id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE FUNCTION hmr103_deviation_context_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE evaluation hidra_monitoring_evaluation%ROWTYPE;
BEGIN
    IF NEW.evaluation_id IS NOT NULL THEN
        SELECT * INTO evaluation FROM hidra_monitoring_evaluation WHERE id=NEW.evaluation_id FOR SHARE;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'HMR-103: unknown evaluation' USING ERRCODE='23503';
        END IF;
        IF (evaluation.topology_asset_type IS NOT NULL AND NEW.topology_asset_type IS NOT NULL
                AND evaluation.topology_asset_type <> NEW.topology_asset_type)
            OR (evaluation.topology_asset_id IS NOT NULL AND evaluation.topology_asset_id <> NEW.topology_asset_id)
            OR (evaluation.telemetry_point_id IS NOT NULL AND NEW.telemetry_point_id IS NOT NULL
                AND evaluation.telemetry_point_id <> NEW.telemetry_point_id) THEN
            RAISE EXCEPTION 'HMR-103: evaluation/deviation context mismatch';
        END IF;
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr103_deviation_context BEFORE INSERT OR UPDATE ON hidra_monitoring_plan_actual_deviation
    FOR EACH ROW EXECUTE FUNCTION hmr103_deviation_context_guard();

CREATE FUNCTION hmr103_evaluation_context_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF ROW(NEW.plan_revision_id,NEW.topology_asset_type,NEW.topology_asset_id,NEW.telemetry_point_id)
        IS DISTINCT FROM ROW(OLD.plan_revision_id,OLD.topology_asset_type,OLD.topology_asset_id,OLD.telemetry_point_id)
        AND EXISTS(SELECT 1 FROM hidra_monitoring_plan_actual_deviation WHERE evaluation_id=OLD.id) THEN
        -- Revision/reading identity is cross-owner evidence. SQL cannot re-resolve it and must retain the checked context.
        RAISE EXCEPTION 'HMR-103: referenced evaluation context cannot be reassigned';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr103_evaluation_context BEFORE UPDATE ON hidra_monitoring_evaluation
    FOR EACH ROW EXECUTE FUNCTION hmr103_evaluation_context_guard();
CREATE FUNCTION hmr103_evaluation_truncate_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM hidra_monitoring_plan_actual_deviation WHERE evaluation_id IS NOT NULL) THEN
        RAISE EXCEPTION 'HMR-103: referenced evaluation cannot be truncated';
    END IF;
    RETURN NULL;
END $$;
CREATE TRIGGER tr_hmr103_evaluation_truncate BEFORE TRUNCATE ON hidra_monitoring_evaluation
    FOR EACH STATEMENT EXECUTE FUNCTION hmr103_evaluation_truncate_guard();
