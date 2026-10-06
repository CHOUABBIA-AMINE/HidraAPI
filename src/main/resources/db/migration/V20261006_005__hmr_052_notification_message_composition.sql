-- HMR-052: forward-only composition and rendering-input protection.
-- No legacy row is repaired, deleted or assigned an invented rendering version.
CREATE FUNCTION hmr052_message_valid(
    message_id text, owning_request text, recipient text, template text,
    exact_version text, priority text, message_status text)
RETURNS boolean LANGUAGE plpgsql AS $$
DECLARE
    schema_json jsonb;
    required_name jsonb;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM hidra_notification_request_recipient r
                   WHERE r.id = recipient AND r.request_id = owning_request) THEN
        RETURN false;
    END IF;
    IF priority IS NOT NULL THEN
        PERFORM 1 FROM hidra_notification_catalog_entry c
            WHERE c.id = priority AND c.catalog_name = 'NOTIFICATION_PRIORITY' AND c.active FOR SHARE;
        IF NOT FOUND THEN RETURN false; END IF;
    END IF;
    IF template IS NULL AND exact_version IS NULL THEN
        RETURN true; -- Non-template content remains permitted; no universal template requirement.
    END IF;
    IF template IS NULL OR exact_version IS NULL THEN RETURN false; END IF;
    SELECT v.variable_schema_json INTO schema_json
        FROM hidra_notification_template_version v
        WHERE v.id = exact_version AND v.template_id = template FOR SHARE;
    IF NOT FOUND THEN RETURN false; END IF;
    IF message_status IN ('READY', 'SCHEDULED', 'DISPATCHING', 'SENT', 'DELIVERED',
                          'READ', 'ACKNOWLEDGED', 'RETRY_PENDING') THEN
        IF schema_json IS NULL THEN RETURN true; END IF;
        IF jsonb_typeof(schema_json) <> 'object' THEN RETURN false; END IF;
        IF NOT (schema_json ? 'required') THEN RETURN true; END IF;
        IF jsonb_typeof(schema_json->'required') <> 'array' THEN RETURN false; END IF;
        FOR required_name IN SELECT value FROM jsonb_array_elements(schema_json->'required') LOOP
            IF jsonb_typeof(required_name) <> 'string' OR btrim(required_name #>> '{}') = '' THEN
                RETURN false;
            END IF;
            IF NOT EXISTS (SELECT 1 FROM hidra_notification_message_variable mv
                           WHERE mv.message_id = message_id
                             AND mv.variable_name = required_name #>> '{}'
                             AND mv.value_snapshot IS NOT NULL
                             AND btrim(mv.value_snapshot) <> '') THEN
                RETURN false;
            END IF;
        END LOOP;
    END IF;
    RETURN true;
END;
$$;

DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_notification_message m
               WHERE NOT hmr052_message_valid(m.id, m.request_id, m.recipient_id,
                   m.template_id, m.template_version_id, m.priority_id, m.status)) THEN
        RAISE EXCEPTION 'HMR-052 preflight failed: invalid message composition or rendering inputs'
            USING ERRCODE = '23514';
    END IF;
END $$;

ALTER TABLE hidra_notification_request_recipient
    ADD CONSTRAINT uk_hmr052_recipient_request UNIQUE (id, request_id);
ALTER TABLE hidra_notification_message
    ADD CONSTRAINT fk_hmr052_recipient_request FOREIGN KEY (recipient_id, request_id)
    REFERENCES hidra_notification_request_recipient (id, request_id) ON DELETE RESTRICT;
ALTER TABLE hidra_notification_template_version
    ADD CONSTRAINT uk_hmr052_version_template UNIQUE (id, template_id);
ALTER TABLE hidra_notification_message
    ADD CONSTRAINT fk_hmr052_version_template FOREIGN KEY (template_version_id, template_id)
    REFERENCES hidra_notification_template_version (id, template_id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_hmr052_template FOREIGN KEY (template_id)
    REFERENCES hidra_notification_template (id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_hmr052_priority FOREIGN KEY (priority_id)
    REFERENCES hidra_notification_catalog_entry (id) ON DELETE RESTRICT;

CREATE FUNCTION hmr052_guard_message() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF NOT hmr052_message_valid(NEW.id, NEW.request_id, NEW.recipient_id,
            NEW.template_id, NEW.template_version_id, NEW.priority_id, NEW.status) THEN
        RAISE EXCEPTION 'HMR-052 invalid message composition or required rendering variables'
            USING ERRCODE = '23514';
    END IF;
    IF TG_OP = 'UPDATE' AND OLD.status <> 'DRAFT' AND NEW.status = 'DRAFT' THEN
        RAISE EXCEPTION 'HMR-052 a promoted message cannot revert to DRAFT' USING ERRCODE = '23514';
    END IF;
    -- Retain the exact rendering identity after it has become sendable.
    IF TG_OP = 'UPDATE' AND OLD.status <> 'DRAFT'
       AND (NEW.template_id, NEW.template_version_id) IS DISTINCT FROM
           (OLD.template_id, OLD.template_version_id) THEN
        RAISE EXCEPTION 'HMR-052 rendering version is immutable after DRAFT' USING ERRCODE = '23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER trg_hmr052_message BEFORE INSERT OR UPDATE ON hidra_notification_message
    FOR EACH ROW EXECUTE FUNCTION hmr052_guard_message();

-- Serialize variable changes with message promotion; preserve inputs after DRAFT.
CREATE FUNCTION hmr052_guard_variables() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE parent_id text; parent_status text;
BEGIN
    FOR parent_id IN
        SELECT DISTINCT id FROM unnest(ARRAY[
            CASE WHEN TG_OP <> 'INSERT' THEN OLD.message_id END,
            CASE WHEN TG_OP <> 'DELETE' THEN NEW.message_id END]) AS ids(id)
        WHERE id IS NOT NULL ORDER BY id
    LOOP
        SELECT status INTO parent_status FROM hidra_notification_message
            WHERE id = parent_id FOR UPDATE;
        IF FOUND AND parent_status <> 'DRAFT' THEN
            RAISE EXCEPTION 'HMR-052 message variables are immutable after DRAFT' USING ERRCODE = '23514';
        END IF;
    END LOOP;
    IF TG_OP = 'DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER trg_hmr052_variables BEFORE INSERT OR UPDATE OR DELETE
    ON hidra_notification_message_variable FOR EACH ROW EXECUTE FUNCTION hmr052_guard_variables();

CREATE FUNCTION hmr052_guard_version_evidence() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP = 'UPDATE' AND OLD.status IN ('ACTIVE', 'RETIRED') AND NEW.status = 'DRAFT' THEN
        RAISE EXCEPTION 'HMR-052 activated version cannot revert to DRAFT' USING ERRCODE = '23514';
    END IF;
    IF OLD.status IN ('ACTIVE', 'RETIRED') OR EXISTS (
        SELECT 1 FROM hidra_notification_message WHERE template_version_id = OLD.id) THEN
        IF TG_OP = 'DELETE' THEN
            RAISE EXCEPTION 'HMR-052 referenced or activated rendering version cannot be deleted' USING ERRCODE = '23514';
        END IF;
        IF (NEW.id, NEW.template_id, NEW.version_number, NEW.subject_template, NEW.body_template,
            NEW.content_format, NEW.variable_schema_json) IS DISTINCT FROM
           (OLD.id, OLD.template_id, OLD.version_number, OLD.subject_template, OLD.body_template,
            OLD.content_format, OLD.variable_schema_json) THEN
            RAISE EXCEPTION 'HMR-052 rendering version content is immutable' USING ERRCODE = '23514';
        END IF;
    END IF;
    IF TG_OP = 'DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER trg_hmr052_version_evidence BEFORE UPDATE OR DELETE
    ON hidra_notification_template_version FOR EACH ROW EXECUTE FUNCTION hmr052_guard_version_evidence();

CREATE FUNCTION hmr052_guard_priority_family() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF NEW.catalog_name IS DISTINCT FROM OLD.catalog_name AND EXISTS (
        SELECT 1 FROM hidra_notification_message WHERE priority_id = OLD.id) THEN
        RAISE EXCEPTION 'HMR-052 referenced message priority cannot change catalog family' USING ERRCODE = '23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER trg_hmr052_priority_family BEFORE UPDATE ON hidra_notification_catalog_entry
    FOR EACH ROW EXECUTE FUNCTION hmr052_guard_priority_family();
