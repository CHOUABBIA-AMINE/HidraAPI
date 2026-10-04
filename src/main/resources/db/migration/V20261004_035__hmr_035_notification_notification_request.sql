-- HMR-035: NotificationRequest semantic remediation.
-- Existing migrations remain immutable.

ALTER TABLE hidra_notification_request
    ADD CONSTRAINT ck_hmr035_notification_request_source_context
    CHECK (
        btrim(source_module) <> ''
        AND btrim(source_event_type) <> ''
    );

ALTER TABLE hidra_notification_request
    ADD CONSTRAINT fk_hmr035_notification_request_priority
        FOREIGN KEY (priority_id)
        REFERENCES hidra_notification_catalog_entry (id)
        ON DELETE RESTRICT;

ALTER TABLE hidra_notification_request
    ADD CONSTRAINT fk_hmr035_notification_request_policy
        FOREIGN KEY (policy_id)
        REFERENCES hidra_notification_policy (id)
        ON DELETE RESTRICT;

ALTER TABLE hidra_notification_request
    ADD CONSTRAINT fk_hmr035_notification_request_template
        FOREIGN KEY (template_id)
        REFERENCES hidra_notification_template (id)
        ON DELETE RESTRICT;

ALTER TABLE hidra_notification_request
    ADD CONSTRAINT fk_hmr035_notification_request_template_version
        FOREIGN KEY (template_version_id)
        REFERENCES hidra_notification_template_version (id)
        ON DELETE RESTRICT;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_notification_request request
        LEFT JOIN hidra_notification_catalog_entry category
          ON category.id = request.category_id
        WHERE category.id IS NULL
           OR category.catalog_name <> 'NOTIFICATION_CATEGORY'
    ) THEN
        RAISE EXCEPTION
            'HMR-035 cannot retain NotificationRequest rows outside NOTIFICATION_CATEGORY'
            USING ERRCODE = '23514';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_notification_request request
        LEFT JOIN hidra_notification_catalog_entry priority
          ON priority.id = request.priority_id
        WHERE request.priority_id IS NOT NULL
          AND (
              priority.id IS NULL
              OR priority.catalog_name <> 'NOTIFICATION_PRIORITY'
          )
    ) THEN
        RAISE EXCEPTION
            'HMR-035 cannot retain NotificationRequest priority rows outside NOTIFICATION_PRIORITY'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

CREATE OR REPLACE FUNCTION hmr035_guard_notification_request_catalogs()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM hidra_notification_catalog_entry category
        WHERE category.id = NEW.category_id
          AND category.catalog_name = 'NOTIFICATION_CATEGORY'
    ) THEN
        RAISE EXCEPTION
            'NotificationRequest category_id must resolve to NOTIFICATION_CATEGORY: %',
            NEW.category_id
            USING ERRCODE = '23514';
    END IF;

    IF NEW.priority_id IS NOT NULL
       AND NOT EXISTS (
           SELECT 1
           FROM hidra_notification_catalog_entry priority
           WHERE priority.id = NEW.priority_id
             AND priority.catalog_name = 'NOTIFICATION_PRIORITY'
       ) THEN
        RAISE EXCEPTION
            'NotificationRequest priority_id must resolve to NOTIFICATION_PRIORITY: %',
            NEW.priority_id
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr035_guard_notification_request_catalogs
    BEFORE INSERT OR UPDATE OF category_id, priority_id
    ON hidra_notification_request
    FOR EACH ROW
    EXECUTE FUNCTION hmr035_guard_notification_request_catalogs();
