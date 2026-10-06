-- HMR-053: creation-time trust gate, provenance and immutable binding snapshots.
-- Historical lifecycle/binding state is not reconstructed from today's mutable point state.
DO $$ BEGIN
    IF EXISTS (
        SELECT 1 FROM hidra_telemetry_trusted_reading t
        LEFT JOIN hidra_telemetry_reading r ON r.id = t.reading_id
        LEFT JOIN hidra_telemetry_quality_assessment a ON a.id = t.quality_assessment_id
        LEFT JOIN hidra_telemetry_type_catalog c ON c.id = t.quality_code_id
        WHERE r.id IS NULL OR a.id IS NULL OR c.id IS NULL
           OR t.point_id IS DISTINCT FROM r.point_id
           OR (t.reading_id, t.point_id, t.quality_code_id, t.trust_level) IS DISTINCT FROM
              (a.reading_id, a.point_id, a.resolved_quality_code_id, a.trust_level)
           OR a.assessment_status <> 'PASSED' OR t.trust_level NOT IN ('MEDIUM', 'HIGH', 'CERTIFIED')
           OR c.catalog_name <> 'QUALITY_CODE') THEN
        RAISE EXCEPTION 'HMR-053 preflight failed: inconsistent trusted provenance or quality assessment'
            USING ERRCODE = '23514';
    END IF;
END $$;
ALTER TABLE hidra_telemetry_reading ADD CONSTRAINT uk_hmr053_reading_point UNIQUE (id, point_id);
ALTER TABLE hidra_telemetry_quality_assessment ADD CONSTRAINT uk_hmr053_assessment_evidence
    UNIQUE (id, reading_id, point_id, resolved_quality_code_id, trust_level);
ALTER TABLE hidra_telemetry_trusted_reading
    ADD CONSTRAINT fk_hmr053_reading_point FOREIGN KEY (reading_id, point_id)
        REFERENCES hidra_telemetry_reading (id, point_id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_hmr053_assessment_evidence
        FOREIGN KEY (quality_assessment_id, reading_id, point_id, quality_code_id, trust_level)
        REFERENCES hidra_telemetry_quality_assessment
            (id, reading_id, point_id, resolved_quality_code_id, trust_level) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_hmr053_unit FOREIGN KEY (unit_id) REFERENCES hidra_telemetry_unit (id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_hmr053_batch FOREIGN KEY (ingestion_batch_id)
        REFERENCES hidra_telemetry_ingestion_batch (id) ON DELETE RESTRICT;

CREATE FUNCTION hmr053_guard_trusted_creation() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE
    v_raw hidra_telemetry_reading%ROWTYPE;
    v_point hidra_telemetry_point%ROWTYPE;
    v_assessment hidra_telemetry_quality_assessment%ROWTYPE;
    v_has_binding boolean;
BEGIN
    IF TG_OP = 'UPDATE' THEN
        IF NEW IS DISTINCT FROM OLD THEN
            RAISE EXCEPTION 'HMR-053 trusted evidence and binding snapshots cannot be rewritten' USING ERRCODE = '23514';
        END IF;
        RETURN NEW; -- No-op saves do not re-evaluate historical eligibility against today's state.
    END IF;
    SELECT * INTO v_raw FROM hidra_telemetry_reading WHERE id = NEW.reading_id FOR SHARE;
    IF NOT FOUND THEN RAISE EXCEPTION 'HMR-053 missing raw reading' USING ERRCODE = '23514'; END IF;
    SELECT * INTO v_point FROM hidra_telemetry_point WHERE id = NEW.point_id FOR SHARE;
    IF NOT FOUND THEN RAISE EXCEPTION 'HMR-053 missing point' USING ERRCODE = '23514'; END IF;
    SELECT * INTO v_assessment FROM hidra_telemetry_quality_assessment WHERE id = NEW.quality_assessment_id FOR SHARE;
    IF NOT FOUND THEN RAISE EXCEPTION 'HMR-053 missing assessment' USING ERRCODE = '23514'; END IF;
    IF v_point.status <> 'ACTIVE' OR v_assessment.assessment_status <> 'PASSED'
       OR v_assessment.trust_level NOT IN ('MEDIUM', 'HIGH', 'CERTIFIED')
       OR NEW.point_id IS DISTINCT FROM v_raw.point_id
       OR (NEW.reading_id, NEW.point_id, NEW.quality_code_id, NEW.trust_level) IS DISTINCT FROM
          (v_assessment.reading_id, v_assessment.point_id, v_assessment.resolved_quality_code_id, v_assessment.trust_level) THEN
        RAISE EXCEPTION 'HMR-053 requires consistent PASSED assessment, acceptable trust and ACTIVE point'
            USING ERRCODE = '23514';
    END IF;
    PERFORM 1 FROM hidra_telemetry_type_catalog c
        WHERE c.id = NEW.quality_code_id AND c.catalog_name = 'QUALITY_CODE' AND c.active FOR SHARE;
    IF NOT FOUND THEN RAISE EXCEPTION 'HMR-053 quality code must be an active QUALITY_CODE' USING ERRCODE = '23514'; END IF;
    IF NEW.unit_id IS DISTINCT FROM v_point.unit_id OR NEW.ingestion_batch_id IS DISTINCT FROM v_raw.ingestion_batch_id
       OR (NEW.numeric_value, NEW.text_value, NEW.boolean_value, NEW.source_timestamp) IS DISTINCT FROM
          (v_raw.numeric_value, v_raw.text_value, v_raw.boolean_value, v_raw.source_timestamp) THEN
        RAISE EXCEPTION 'HMR-053 trusted values and provenance must derive from source evidence' USING ERRCODE = '23514';
    END IF;
    SELECT EXISTS (SELECT 1 FROM hidra_telemetry_point_binding b
                   WHERE b.point_id = NEW.point_id AND b.active AND b.valid_from <= NEW.trusted_at
                     AND (b.valid_to IS NULL OR NEW.trusted_at < b.valid_to)) INTO v_has_binding;
    IF v_has_binding THEN
        IF NOT EXISTS (SELECT 1 FROM hidra_telemetry_point_binding b
                       WHERE b.point_id = NEW.point_id AND b.active AND b.valid_from <= NEW.trusted_at
                         AND (b.valid_to IS NULL OR NEW.trusted_at < b.valid_to)
                         AND (b.topology_asset_type_code, b.topology_asset_id, b.topology_asset_code, b.topology_snapshot_id)
                             IS NOT DISTINCT FROM
                             (NEW.topology_asset_type_code, NEW.topology_asset_id, NEW.topology_asset_code, NEW.topology_snapshot_id)) THEN
            RAISE EXCEPTION 'HMR-053 topology snapshot must match an applicable active binding' USING ERRCODE = '23514';
        END IF;
    ELSIF NEW.topology_asset_type_code IS NOT NULL OR NEW.topology_asset_id IS NOT NULL
          OR NEW.topology_asset_code IS NOT NULL OR NEW.topology_snapshot_id IS NOT NULL THEN
        RAISE EXCEPTION 'HMR-053 no active binding supports the supplied topology snapshot' USING ERRCODE = '23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER trg_hmr053_trusted_creation BEFORE INSERT OR UPDATE ON hidra_telemetry_trusted_reading
    FOR EACH ROW EXECUTE FUNCTION hmr053_guard_trusted_creation();

-- Serialize changes, including new binding rows, with trust-time capture on the point.
CREATE FUNCTION hmr053_serialize_binding_change() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE v_point_id text;
BEGIN
    FOR v_point_id IN SELECT DISTINCT id FROM unnest(ARRAY[
        CASE WHEN TG_OP <> 'INSERT' THEN OLD.point_id END,
        CASE WHEN TG_OP <> 'DELETE' THEN NEW.point_id END]) AS points(id)
        WHERE id IS NOT NULL ORDER BY id LOOP
        PERFORM 1 FROM hidra_telemetry_point WHERE id = v_point_id FOR UPDATE;
    END LOOP;
    IF TG_OP = 'DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER trg_hmr053_binding_serialization BEFORE INSERT OR UPDATE OR DELETE
    ON hidra_telemetry_point_binding FOR EACH ROW EXECUTE FUNCTION hmr053_serialize_binding_change();
