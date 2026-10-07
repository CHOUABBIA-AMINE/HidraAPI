-- HMR-057 and accepted narrow prerequisite: correct request FKs without rewriting evidence.
ALTER TABLE hidra_reporting_run DROP CONSTRAINT fk_hra111_reporting_019;
ALTER TABLE hidra_reporting_run ADD CONSTRAINT hmr057_request_fk FOREIGN KEY(report_request_id) REFERENCES hidra_reporting_request(id);
ALTER TABLE hidra_reporting_parameter_value DROP CONSTRAINT fk_hra111_reporting_012;
ALTER TABLE hidra_reporting_parameter_value ADD CONSTRAINT hmr057_parameter_request_fk FOREIGN KEY(report_request_id) REFERENCES hidra_reporting_request(id);
ALTER TABLE hidra_reporting_request ADD CONSTRAINT hmr057_request_definition_unique UNIQUE(id,report_definition_id);
ALTER TABLE hidra_reporting_run ADD CONSTRAINT hmr057_request_definition_fk FOREIGN KEY(report_request_id,report_definition_id) REFERENCES hidra_reporting_request(id,report_definition_id);
ALTER TABLE hidra_reporting_run ADD CONSTRAINT hmr057_terminal_evidence CHECK(
    (status<>'COMPLETED' OR completed_at IS NOT NULL) AND
    (status<>'FAILED' OR (failure_reason IS NOT NULL AND length(btrim(failure_reason))>0)));
DO $$ BEGIN
    IF EXISTS(SELECT 1 FROM hidra_reporting_run r JOIN hidra_reporting_report_template_version v ON v.id=r.template_version_id
        JOIN hidra_reporting_report_template t ON t.id=v.report_template_id WHERE t.report_definition_id<>r.report_definition_id)
    THEN RAISE EXCEPTION 'Legacy ReportRun template lineage is inconsistent'; END IF;
END $$;
CREATE OR REPLACE FUNCTION hmr013_validate_report_run_queue()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
    request_row hidra_reporting_request%ROWTYPE;
    definition_row hidra_reporting_report_definition%ROWTYPE;
BEGIN
    IF TG_OP = 'UPDATE' AND ROW(NEW.report_request_id,NEW.report_definition_id,NEW.template_version_id)
        IS NOT DISTINCT FROM ROW(OLD.report_request_id,OLD.report_definition_id,OLD.template_version_id)
        AND NOT (NEW.status='QUEUED' AND OLD.status<>'QUEUED') THEN RETURN NEW; END IF;
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


DROP TRIGGER trg_hmr013_reporting_run_queue_gate ON hidra_reporting_run;
CREATE TRIGGER trg_hmr013_reporting_run_queue_gate BEFORE INSERT OR UPDATE OF report_request_id,report_definition_id,template_version_id,status
ON hidra_reporting_run FOR EACH ROW EXECUTE FUNCTION hmr013_validate_report_run_queue();
CREATE FUNCTION hmr057_validate_run_evidence() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE template_definition varchar(80); request_status varchar(40); d record;
BEGIN
    SELECT t.report_definition_id INTO template_definition
    FROM hidra_reporting_report_template_version v JOIN hidra_reporting_report_template t ON t.id=v.report_template_id
    WHERE v.id=NEW.template_version_id FOR SHARE OF v,t;
    IF template_definition IS NULL OR template_definition<>NEW.report_definition_id THEN
        RAISE EXCEPTION 'ReportRun template lineage must match definition' USING ERRCODE='23514'; END IF;
    IF TG_OP='UPDATE' AND ROW(NEW.report_request_id,NEW.report_definition_id,NEW.template_version_id)
        IS NOT DISTINCT FROM ROW(OLD.report_request_id,OLD.report_definition_id,OLD.template_version_id)
        AND NOT (NEW.status='QUEUED' AND OLD.status<>'QUEUED') THEN RETURN NEW; END IF;
    SELECT status INTO request_status FROM hidra_reporting_request WHERE id=NEW.report_request_id FOR SHARE;
    IF request_status NOT IN ('SUBMITTED','APPROVED') OR request_status IS NULL THEN
        RAISE EXCEPTION 'ReportRequest is not eligible for new queue' USING ERRCODE='23514'; END IF;
    FOR d IN SELECT * FROM hidra_reporting_parameter_definition
        WHERE report_definition_id=NEW.report_definition_id AND active AND required FOR SHARE LOOP
        IF NOT EXISTS(SELECT 1 FROM hidra_reporting_parameter_value v
            WHERE v.report_request_id=NEW.report_request_id AND v.parameter_definition_id=d.id AND v.parameter_code=d.code
            AND num_nonnulls(v.value_text,v.value_number,v.value_boolean,v.value_date,v.value_date_time,v.value_json)=1
            AND CASE v.value_type
                WHEN 'TEXT' THEN v.value_text IS NOT NULL AND length(btrim(v.value_text))>0
                WHEN 'REFERENCE' THEN v.value_text IS NOT NULL AND length(btrim(v.value_text))>0
                WHEN 'NUMBER' THEN v.value_number IS NOT NULL
                WHEN 'BOOLEAN' THEN v.value_boolean IS NOT NULL
                WHEN 'DATE' THEN v.value_date IS NOT NULL
                WHEN 'DATE_TIME' THEN v.value_date_time IS NOT NULL
                WHEN 'JSON' THEN v.value_json IS NOT NULL
                ELSE FALSE END)
        THEN RAISE EXCEPTION 'Required concrete parameter missing or invalid: %',d.code USING ERRCODE='23514'; END IF;
    END LOOP;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr057_run_evidence BEFORE INSERT OR UPDATE ON hidra_reporting_run FOR EACH ROW EXECUTE FUNCTION hmr057_validate_run_evidence();
CREATE FUNCTION hmr057_preserve_template_lineage() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_TABLE_NAME='hidra_reporting_report_template' THEN
        IF EXISTS(SELECT 1 FROM hidra_reporting_run r JOIN hidra_reporting_report_template_version v ON v.id=r.template_version_id
            WHERE v.report_template_id=NEW.id AND r.report_definition_id<>NEW.report_definition_id)
        THEN RAISE EXCEPTION 'Template reparent would invalidate ReportRun lineage' USING ERRCODE='23514'; END IF;
    ELSE
        IF EXISTS(SELECT 1 FROM hidra_reporting_run r JOIN hidra_reporting_report_template t ON t.id=NEW.report_template_id
            WHERE r.template_version_id=NEW.id AND r.report_definition_id<>t.report_definition_id)
        THEN RAISE EXCEPTION 'Version reparent would invalidate ReportRun lineage' USING ERRCODE='23514'; END IF;
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr057_template_lineage BEFORE UPDATE OF report_definition_id ON hidra_reporting_report_template FOR EACH ROW EXECUTE FUNCTION hmr057_preserve_template_lineage();
CREATE TRIGGER hmr057_version_lineage BEFORE UPDATE OF report_template_id ON hidra_reporting_report_template_version FOR EACH ROW EXECUTE FUNCTION hmr057_preserve_template_lineage();
