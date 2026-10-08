-- HMR-096: the loaded guard and parent serialization apply to every authoritative close.
CREATE FUNCTION hmr096_guard_closure_insert() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE p hidra_hse_case%ROWTYPE;
BEGIN
    SELECT * INTO p FROM hidra_hse_case WHERE id=NEW.hse_case_id FOR UPDATE;
    IF NOT FOUND OR p.status IN ('CLOSED','CANCELLED') THEN
        RAISE EXCEPTION 'HMR-096: missing or already closed/cancelled HSE parent %',NEW.hse_case_id;
    END IF;
    IF NOT NEW.impact_assessed OR NOT NEW.capa_completed OR NOT NEW.evidence_reviewed THEN
        RAISE EXCEPTION 'HMR-096: impact/CAPA/evidence attestations required';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr096_closure_insert BEFORE INSERT ON hidra_hse_closure
    FOR EACH ROW EXECUTE FUNCTION hmr096_guard_closure_insert();
CREATE FUNCTION hmr096_guard_history_insert() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE p hidra_hse_case%ROWTYPE;
BEGIN
    SELECT * INTO p FROM hidra_hse_case WHERE id=NEW.hse_case_id FOR UPDATE;
    IF NOT FOUND THEN RAISE EXCEPTION 'HMR-096: missing history parent'; END IF;
    IF NEW.new_status='CLOSED' AND (NEW.old_status IS DISTINCT FROM p.status OR p.status IN ('CLOSED','CANCELLED')) THEN
        RAISE EXCEPTION 'HMR-096: closure history must record actual previous status';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr096_history_insert BEFORE INSERT ON hidra_hse_case_status_history
    FOR EACH ROW EXECUTE FUNCTION hmr096_guard_history_insert();
CREATE FUNCTION hmr096_closure_append_only() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN RAISE EXCEPTION 'HMR-096: closure evidence cannot be overwritten or erased'; END $$;
CREATE TRIGGER hmr096_closure_immutable BEFORE UPDATE OR DELETE ON hidra_hse_closure
    FOR EACH ROW EXECUTE FUNCTION hmr096_closure_append_only();
CREATE TRIGGER hmr096_closure_no_truncate BEFORE TRUNCATE ON hidra_hse_closure
    FOR EACH STATEMENT EXECUTE FUNCTION hmr096_closure_append_only();
CREATE TRIGGER hmr096_case_no_truncate BEFORE TRUNCATE ON hidra_hse_case
    FOR EACH STATEMENT EXECUTE FUNCTION hmr096_closure_append_only();
-- No invented one-closure-row-per-case UNIQUE constraint; lock+guard serialize closure.
