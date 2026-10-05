-- HMR-048: ReportRequest semantic remediation.
-- Dynamic authorization, workflow approval and Organization ownership remain application-authoritative.
-- Existing HRA-111 same-module ReportRequest -> ReportDefinition integrity is not duplicated.

ALTER TABLE hidra_reporting_request
    ADD CONSTRAINT ck_hmr048_reporting_request_organization_unit_nonblank
    CHECK (organization_unit_id IS NULL OR btrim(organization_unit_id) <> '')
    NOT VALID;

ALTER TABLE hidra_reporting_request
    VALIDATE CONSTRAINT ck_hmr048_reporting_request_organization_unit_nonblank;

ALTER TABLE hidra_reporting_request
    ADD CONSTRAINT ck_hmr048_reporting_request_workflow_reference_nonblank
    CHECK (workflow_reference_id IS NULL OR btrim(workflow_reference_id) <> '')
    NOT VALID;

ALTER TABLE hidra_reporting_request
    VALIDATE CONSTRAINT ck_hmr048_reporting_request_workflow_reference_nonblank;
