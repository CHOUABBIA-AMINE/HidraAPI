-- HMR-082: preserve published SQL and fail closed on incoherent historical evidence.
CREATE TABLE hidra_hse_catalog_field_policy (
    field_role varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL CHECK (btrim(catalog_name) <> ''),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
-- Intentionally unseeded: HSE owner must approve the actual CAPA action-type family.
CREATE FUNCTION hmr082_assert_case_lifecycle(case_id varchar) RETURNS void LANGUAGE plpgsql AS $$
DECLARE p hidra_hse_case%ROWTYPE;
BEGIN
    SELECT * INTO p FROM hidra_hse_case WHERE id=case_id;
    IF NOT FOUND THEN
        IF EXISTS (SELECT 1 FROM hidra_hse_closure WHERE hse_case_id=case_id)
            OR EXISTS (SELECT 1 FROM hidra_hse_case_status_history WHERE hse_case_id=case_id) THEN
            RAISE EXCEPTION 'HMR-082: missing parent for HSE evidence %',case_id;
        END IF;
        RETURN;
    END IF;
    IF (p.status='CLOSED') <> (p.closed_at IS NOT NULL) THEN
        RAISE EXCEPTION 'HMR-082: CLOSED/time mismatch for case %',case_id;
    END IF;
    IF p.status='CLOSED' AND NOT EXISTS (
        SELECT 1 FROM hidra_hse_closure c WHERE c.hse_case_id=p.id AND c.closed_at=p.closed_at
    ) THEN RAISE EXCEPTION 'HMR-082: CLOSED case lacks closure evidence %',case_id; END IF;
    IF EXISTS (
        SELECT 1 FROM hidra_hse_closure c WHERE c.hse_case_id=p.id AND
        (p.status<>'CLOSED' OR c.closed_at IS DISTINCT FROM p.closed_at
         OR NOT c.impact_assessed OR NOT c.capa_completed OR NOT c.evidence_reviewed
         OR btrim(c.closed_by_actor_id)='' OR NOT EXISTS (
            SELECT 1 FROM hidra_hse_case_status_history h WHERE h.hse_case_id=p.id
              AND h.correlation_id=c.id AND h.new_status='CLOSED'
              AND h.old_status IS NOT NULL AND h.old_status NOT IN ('CLOSED','CANCELLED')
              AND h.changed_at=c.closed_at AND h.changed_by_actor_id=c.closed_by_actor_id
              AND h.changed_by_display_name_snapshot IS NOT DISTINCT FROM c.closed_by_display_name_snapshot
         ))
    ) THEN RAISE EXCEPTION 'HMR-082: incoherent closure/history for case %',case_id; END IF;
    IF EXISTS (
        SELECT 1 FROM hidra_hse_case_status_history h WHERE h.hse_case_id=p.id AND h.new_status='CLOSED'
        AND NOT EXISTS (SELECT 1 FROM hidra_hse_closure c WHERE c.id=h.correlation_id AND c.hse_case_id=p.id
            AND c.closed_at=h.changed_at AND c.closed_by_actor_id=h.changed_by_actor_id
            AND c.closed_by_display_name_snapshot IS NOT DISTINCT FROM h.changed_by_display_name_snapshot)
    ) THEN RAISE EXCEPTION 'HMR-082: unmatched CLOSED history for case %',case_id; END IF;
END $$;
DO $$ DECLARE r record; BEGIN
    FOR r IN SELECT id FROM hidra_hse_case UNION SELECT hse_case_id FROM hidra_hse_closure
        UNION SELECT hse_case_id FROM hidra_hse_case_status_history LOOP
        PERFORM hmr082_assert_case_lifecycle(r.id);
    END LOOP;
END $$;
ALTER TABLE hidra_hse_case ADD CONSTRAINT ck_hmr082_closed_time CHECK ((status='CLOSED')=(closed_at IS NOT NULL));
CREATE FUNCTION hmr082_validate_lifecycle_event() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_TABLE_NAME='hidra_hse_case' THEN
        IF TG_OP<>'INSERT' THEN PERFORM hmr082_assert_case_lifecycle(OLD.id); END IF;
        IF TG_OP<>'DELETE' THEN PERFORM hmr082_assert_case_lifecycle(NEW.id); END IF;
    ELSE
        IF TG_OP<>'INSERT' THEN PERFORM hmr082_assert_case_lifecycle(OLD.hse_case_id); END IF;
        IF TG_OP<>'DELETE' THEN PERFORM hmr082_assert_case_lifecycle(NEW.hse_case_id); END IF;
    END IF;
    RETURN NULL;
END $$;
CREATE CONSTRAINT TRIGGER hmr082_case_consistency AFTER INSERT OR UPDATE OR DELETE ON hidra_hse_case
    DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION hmr082_validate_lifecycle_event();
CREATE CONSTRAINT TRIGGER hmr082_closure_consistency AFTER INSERT OR UPDATE OR DELETE ON hidra_hse_closure
    DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION hmr082_validate_lifecycle_event();
CREATE CONSTRAINT TRIGGER hmr082_history_consistency AFTER INSERT OR UPDATE OR DELETE ON hidra_hse_case_status_history
    DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION hmr082_validate_lifecycle_event();
CREATE FUNCTION hmr082_guard_closed_tuple() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF OLD.status='CLOSED' AND (NEW.status IS DISTINCT FROM OLD.status OR NEW.closed_at IS DISTINCT FROM OLD.closed_at) THEN
        RAISE EXCEPTION 'HMR-082: recorded CLOSED lifecycle is immutable';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr082_closed_tuple BEFORE UPDATE ON hidra_hse_case FOR EACH ROW EXECUTE FUNCTION hmr082_guard_closed_tuple();
CREATE FUNCTION hmr082_history_append_only() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN RAISE EXCEPTION 'HMR-082: HSE status history is append-only'; END $$;
CREATE TRIGGER hmr082_history_immutable BEFORE UPDATE OR DELETE ON hidra_hse_case_status_history
    FOR EACH ROW EXECUTE FUNCTION hmr082_history_append_only();
CREATE TRIGGER hmr082_history_no_truncate BEFORE TRUNCATE ON hidra_hse_case_status_history
    FOR EACH STATEMENT EXECUTE FUNCTION hmr082_history_append_only();
