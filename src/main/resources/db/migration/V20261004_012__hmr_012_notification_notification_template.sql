-- HMR-012: NotificationTemplate semantic remediation.
-- Existing migrations remain immutable.

CREATE UNIQUE INDEX uk_hmr012_notification_template_code
    ON hidra_notification_template (code);

CREATE UNIQUE INDEX uk_hmr012_notification_template_version_number
    ON hidra_notification_template_version (template_id, version_number);

ALTER TABLE hidra_notification_template
    ADD CONSTRAINT ck_hmr012_notification_template_name_fr
    CHECK (btrim(name_fr) <> '') NOT VALID;

ALTER TABLE hidra_notification_template
    ADD CONSTRAINT fk_hmr012_notification_template_category
    FOREIGN KEY (category_id)
    REFERENCES hidra_notification_catalog_entry (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_notification_template
    ADD CONSTRAINT fk_hmr012_notification_template_default_channel
    FOREIGN KEY (default_channel_id)
    REFERENCES hidra_notification_channel (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_notification_template
    VALIDATE CONSTRAINT ck_hmr012_notification_template_name_fr;
ALTER TABLE hidra_notification_template
    VALIDATE CONSTRAINT fk_hmr012_notification_template_category;
ALTER TABLE hidra_notification_template
    VALIDATE CONSTRAINT fk_hmr012_notification_template_default_channel;

CREATE OR REPLACE FUNCTION hmr012_validate_notification_template()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM hidra_notification_catalog_entry c
        WHERE c.id = NEW.template_type_id
          AND c.catalog_name = 'TEMPLATE_TYPE'
    ) THEN
        RAISE EXCEPTION 'NotificationTemplate template_type_id must belong to TEMPLATE_TYPE: %',
            NEW.template_type_id USING ERRCODE = '23514';
    END IF;

    IF NEW.category_id IS NOT NULL
       AND NOT EXISTS (
           SELECT 1
           FROM hidra_notification_catalog_entry c
           WHERE c.id = NEW.category_id
             AND c.catalog_name = 'NOTIFICATION_CATEGORY'
       ) THEN
        RAISE EXCEPTION 'NotificationTemplate category_id must belong to NOTIFICATION_CATEGORY: %',
            NEW.category_id USING ERRCODE = '23514';
    END IF;

    IF NEW.default_channel_id IS NOT NULL
       AND NOT EXISTS (
           SELECT 1
           FROM hidra_notification_channel ch
           WHERE ch.id = NEW.default_channel_id
             AND ch.active = TRUE
       ) THEN
        RAISE EXCEPTION 'NotificationTemplate default_channel_id must reference an ACTIVE channel: %',
            NEW.default_channel_id USING ERRCODE = '23514';
    END IF;

    IF NEW.status = 'ACTIVE' AND NEW.current_version IS NULL THEN
        RAISE EXCEPTION 'ACTIVE NotificationTemplate requires current_version'
            USING ERRCODE = '23514';
    END IF;

    IF NEW.current_version IS NOT NULL THEN
        IF NEW.current_version <= 0 THEN
            RAISE EXCEPTION 'NotificationTemplate current_version must be positive'
                USING ERRCODE = '23514';
        END IF;

        IF NEW.status = 'ACTIVE' THEN
            IF NOT EXISTS (
                SELECT 1
                FROM hidra_notification_template_version v
                WHERE v.template_id = NEW.id
                  AND v.version_number = NEW.current_version
                  AND v.status = 'ACTIVE'
            ) THEN
                RAISE EXCEPTION
                    'ACTIVE NotificationTemplate current_version must identify an ACTIVE version of the same template'
                    USING ERRCODE = '23514';
            END IF;
        ELSIF NOT EXISTS (
            SELECT 1
            FROM hidra_notification_template_version v
            WHERE v.template_id = NEW.id
              AND v.version_number = NEW.current_version
        ) THEN
            RAISE EXCEPTION
                'NotificationTemplate current_version must identify a version of the same template'
                USING ERRCODE = '23514';
        END IF;
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr012_notification_template_integrity
    BEFORE INSERT OR UPDATE OF template_type_id, category_id, default_channel_id, status, current_version
    ON hidra_notification_template
    FOR EACH ROW
    EXECUTE FUNCTION hmr012_validate_notification_template();

CREATE OR REPLACE FUNCTION hmr012_validate_template_selection()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NEW.template_id IS NULL THEN
        IF NEW.template_version_id IS NOT NULL THEN
            RAISE EXCEPTION 'template_version_id requires template_id'
                USING ERRCODE = '23514';
        END IF;
        RETURN NEW;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM hidra_notification_template t
        WHERE t.id = NEW.template_id
          AND t.status = 'ACTIVE'
    ) THEN
        RAISE EXCEPTION 'New notification generation requires an ACTIVE template: %',
            NEW.template_id USING ERRCODE = '23514';
    END IF;

    IF TG_TABLE_NAME = 'hidra_notification_message'
       AND NEW.template_version_id IS NULL THEN
        RAISE EXCEPTION 'A message using a template must reference the exact template version'
            USING ERRCODE = '23514';
    END IF;

    IF NEW.template_version_id IS NOT NULL
       AND NOT EXISTS (
           SELECT 1
           FROM hidra_notification_template_version v
           WHERE v.id = NEW.template_version_id
             AND v.template_id = NEW.template_id
             AND v.status = 'ACTIVE'
       ) THEN
        RAISE EXCEPTION
            'template_version_id must identify an ACTIVE version belonging to template_id'
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr012_notification_request_template_selection
    BEFORE INSERT OR UPDATE OF template_id, template_version_id
    ON hidra_notification_request
    FOR EACH ROW
    EXECUTE FUNCTION hmr012_validate_template_selection();

CREATE TRIGGER trg_hmr012_notification_message_template_selection
    BEFORE INSERT OR UPDATE OF template_id, template_version_id
    ON hidra_notification_message
    FOR EACH ROW
    EXECUTE FUNCTION hmr012_validate_template_selection();
