-- HMR-013: ReportDefinition semantic remediation.
-- Existing migrations remain immutable.

CREATE UNIQUE INDEX uk_hmr013_reporting_report_definition_code
    ON hidra_reporting_report_definition (code);

ALTER TABLE hidra_reporting_report_definition
    ADD COLUMN status varchar(40);

UPDATE hidra_reporting_report_definition
SET status = CASE WHEN active THEN 'ACTIVE' ELSE 'RETIRED' END
WHERE status IS NULL;

ALTER TABLE hidra_reporting_report_definition
    ALTER COLUMN status SET NOT NULL;

ALTER TABLE hidra_reporting_report_definition
    ADD CONSTRAINT ck_hmr013_reporting_definition_status
    CHECK (status IN ('DRAFT', 'ACTIVE', 'RETIRED')) NOT VALID;

ALTER TABLE hidra_reporting_report_definition
    ADD CONSTRAINT ck_hmr013_reporting_definition_name_fr
    CHECK (btrim(name_fr) <> '') NOT VALID;

ALTER TABLE hidra_reporting_report_definition
    ADD CONSTRAINT ck_hmr013_reporting_definition_owner_module
    CHECK (
        owner_module IN (
            'alarm', 'analytics', 'assets', 'audit', 'configuration', 'custody',
            'documents', 'hse', 'identity', 'incident', 'integration', 'integrity',
            'leakdetection', 'monitoring', 'notification', 'organization', 'party',
            'planning', 'reporting', 'risk', 'simulation', 'telemetry', 'topology',
            'workflow'
        )
    ) NOT VALID;

ALTER TABLE hidra_reporting_report_definition
    ADD CONSTRAINT fk_hmr013_reporting_definition_current_template_version
    FOREIGN KEY (current_template_version_id)
    REFERENCES hidra_reporting_report_template_version (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_reporting_report_definition
    VALIDATE CONSTRAINT ck_hmr013_reporting_definition_status;
ALTER TABLE hidra_reporting_report_definition
    VALIDATE CONSTRAINT ck_hmr013_reporting_definition_name_fr;
ALTER TABLE hidra_reporting_report_definition
    VALIDATE CONSTRAINT ck_hmr013_reporting_definition_owner_module;
ALTER TABLE hidra_reporting_report_definition
    VALIDATE CONSTRAINT fk_hmr013_reporting_definition_current_template_version;

DROP INDEX IF EXISTS ix_hidra_reporting_report_definition_active;

ALTER TABLE hidra_reporting_report_definition
    DROP COLUMN active;

CREATE INDEX ix_hmr013_reporting_report_definition_status
    ON hidra_reporting_report_definition (status);

CREATE UNIQUE INDEX uk_hmr013_reporting_active_template_version
    ON hidra_reporting_report_template_version (report_template_id)
    WHERE status = 'ACTIVE';

CREATE OR REPLACE FUNCTION hmr013_validate_report_definition()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM hidra_reporting_catalog_entry c
        WHERE c.id = NEW.report_category_id
          AND c.catalog_name = 'REPORT_CATEGORY'
          AND c.active = TRUE
    ) THEN
        RAISE EXCEPTION
            'ReportDefinition report_category_id must reference an ACTIVE REPORT_CATEGORY entry: %',
            NEW.report_category_id
            USING ERRCODE = '23514';
    END IF;

    IF NEW.current_template_version_id IS NOT NULL THEN
        IF NOT EXISTS (
            SELECT 1
            FROM hidra_reporting_report_template_version v
            JOIN hidra_reporting_report_template t
              ON t.id = v.report_template_id
            WHERE v.id = NEW.current_template_version_id
              AND t.report_definition_id = NEW.id
              AND (
                  NEW.status <> 'ACTIVE'
                  OR (v.status = 'ACTIVE' AND t.active = TRUE)
              )
        ) THEN
            RAISE EXCEPTION
                'ReportDefinition current template version must belong to the definition and satisfy lifecycle eligibility'
                USING ERRCODE = '23514';
        END IF;
    END IF;

    IF NEW.status = 'ACTIVE'
       AND NEW.restricted = TRUE
       AND NOT EXISTS (
           SELECT 1
           FROM hidra_reporting_access_policy p
           WHERE p.report_definition_id = NEW.id
             AND p.restricted = TRUE
             AND btrim(p.permission_code) <> ''
       ) THEN
        RAISE EXCEPTION
            'ACTIVE restricted ReportDefinition requires an explicit Reporting access policy'
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr013_reporting_definition_integrity
    BEFORE INSERT OR UPDATE OF report_category_id, status, current_template_version_id, restricted
    ON hidra_reporting_report_definition
    FOR EACH ROW
    EXECUTE FUNCTION hmr013_validate_report_definition();

CREATE OR REPLACE FUNCTION hmr013_reporting_request_policy_matches(
    definition_id varchar,
    actor_id varchar,
    role_code varchar,
    organization_unit_id varchar
)
RETURNS boolean
LANGUAGE sql
STABLE
AS $$
    SELECT EXISTS (
        SELECT 1
        FROM hidra_reporting_access_policy p
        WHERE p.report_definition_id = definition_id
          AND p.restricted = TRUE
          AND btrim(p.permission_code) <> ''
          AND (
              p.scope_type = 'GLOBAL'
              OR (p.scope_type = 'ACTOR' AND p.scope_reference_id = actor_id)
              OR (p.scope_type = 'ROLE' AND p.scope_reference_id = role_code)
              OR (p.scope_type = 'ORGANIZATION_UNIT' AND p.scope_reference_id = organization_unit_id)
          )
    );
$$;

CREATE OR REPLACE FUNCTION hmr013_validate_report_request()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
    definition_status varchar(40);
    definition_restricted boolean;
BEGIN
    SELECT d.status, d.restricted
      INTO definition_status, definition_restricted
      FROM hidra_reporting_report_definition d
     WHERE d.id = NEW.report_definition_id;

    IF definition_status IS NULL OR definition_status <> 'ACTIVE' THEN
        RAISE EXCEPTION
            'New report requests require an ACTIVE ReportDefinition: %',
            NEW.report_definition_id
            USING ERRCODE = '23514';
    END IF;

    IF definition_restricted = TRUE
       AND NOT hmr013_reporting_request_policy_matches(
           NEW.report_definition_id,
           NEW.requested_by_actor_id,
           NEW.requested_by_role_code_snapshot,
           NEW.organization_unit_id
       ) THEN
        RAISE EXCEPTION
            'Restricted report request requires an explicit matching Reporting access policy'
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr013_reporting_request_definition_gate
    BEFORE INSERT OR UPDATE OF report_definition_id, requested_by_actor_id,
        requested_by_role_code_snapshot, organization_unit_id
    ON hidra_reporting_request
    FOR EACH ROW
    EXECUTE FUNCTION hmr013_validate_report_request();

CREATE OR REPLACE FUNCTION hmr013_validate_report_run_queue()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
    request_row hidra_reporting_request%ROWTYPE;
    definition_row hidra_reporting_report_definition%ROWTYPE;
BEGIN
    SELECT *
      INTO request_row
      FROM hidra_reporting_request r
     WHERE r.id = NEW.report_request_id;

    IF request_row.id IS NULL THEN
        RAISE EXCEPTION 'ReportRun report_request_id must reference an existing ReportRequest: %',
            NEW.report_request_id USING ERRCODE = '23514';
    END IF;

    IF request_row.report_definition_id <> NEW.report_definition_id THEN
        RAISE EXCEPTION 'ReportRun definition must match its ReportRequest definition'
            USING ERRCODE = '23514';
    END IF;

    SELECT *
      INTO definition_row
      FROM hidra_reporting_report_definition d
     WHERE d.id = NEW.report_definition_id;

    IF definition_row.status <> 'ACTIVE' THEN
        RAISE EXCEPTION 'Queued ReportRun requires an ACTIVE ReportDefinition'
            USING ERRCODE = '23514';
    END IF;

    IF definition_row.restricted = TRUE
       AND NOT hmr013_reporting_request_policy_matches(
           NEW.report_definition_id,
           request_row.requested_by_actor_id,
           request_row.requested_by_role_code_snapshot,
           request_row.organization_unit_id
       ) THEN
        RAISE EXCEPTION 'Restricted ReportRun requires an explicit matching Reporting access policy'
            USING ERRCODE = '23514';
    END IF;

    IF definition_row.requires_approval = TRUE
       AND (
           request_row.status <> 'APPROVED'
           OR request_row.workflow_reference_id IS NULL
           OR btrim(request_row.workflow_reference_id) = ''
       ) THEN
        RAISE EXCEPTION
            'Approval-required ReportDefinition cannot be queued without APPROVED request state and Workflow reference'
            USING ERRCODE = '23514';
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM hidra_reporting_report_template_version v
        JOIN hidra_reporting_report_template t
          ON t.id = v.report_template_id
        WHERE v.id = NEW.template_version_id
          AND v.status = 'ACTIVE'
          AND t.active = TRUE
          AND t.report_definition_id = NEW.report_definition_id
    ) THEN
        RAISE EXCEPTION
            'ReportRun template_version_id must be an ACTIVE version belonging to the selected definition'
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr013_reporting_run_queue_gate
    BEFORE INSERT OR UPDATE OF report_request_id, report_definition_id, template_version_id
    ON hidra_reporting_run
    FOR EACH ROW
    EXECUTE FUNCTION hmr013_validate_report_run_queue();
