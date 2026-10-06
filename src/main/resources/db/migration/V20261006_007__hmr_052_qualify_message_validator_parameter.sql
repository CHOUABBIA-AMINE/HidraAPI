-- HMR-052 corrective follow-up to CI #571: qualify the function parameter.
-- Preserve V20261006_005 and all previously published migration checksums.
CREATE OR REPLACE FUNCTION hmr052_message_valid(
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
                           WHERE mv.message_id = hmr052_message_valid.message_id
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
