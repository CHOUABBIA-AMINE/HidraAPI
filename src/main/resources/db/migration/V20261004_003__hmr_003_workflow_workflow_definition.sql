-- HMR-003: WorkflowDefinition semantic remediation
-- Enforce intrinsic version minimum and race-safe business identity uniqueness.
-- Existing Flyway migrations remain immutable.

ALTER TABLE hidra_workflow_definition
    ADD CONSTRAINT ck_hmr003_workflow_definition_version_positive
    CHECK (version >= 1) NOT VALID;

ALTER TABLE hidra_workflow_definition
    VALIDATE CONSTRAINT ck_hmr003_workflow_definition_version_positive;

CREATE UNIQUE INDEX uk_hmr003_workflow_definition_code_version
    ON hidra_workflow_definition (code, version);
