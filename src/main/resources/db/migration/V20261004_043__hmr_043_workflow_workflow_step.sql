-- HMR-043: WorkflowStep semantic remediation.
-- Enforce deterministic ordering/identity and protect the nullable SCC-02 default rule reference.

ALTER TABLE hidra_workflow_step
    ADD CONSTRAINT ck_hmr043_workflow_step_order_non_negative
    CHECK (step_order >= 0)
    NOT VALID;

ALTER TABLE hidra_workflow_step
    VALIDATE CONSTRAINT ck_hmr043_workflow_step_order_non_negative;

ALTER TABLE hidra_workflow_step
    ADD CONSTRAINT uq_hmr043_workflow_step_definition_code
    UNIQUE (definition_id, code);

ALTER TABLE hidra_workflow_step
    ADD CONSTRAINT uq_hmr043_workflow_step_definition_order
    UNIQUE (definition_id, step_order);

ALTER TABLE hidra_workflow_step
    ADD CONSTRAINT fk_hmr043_workflow_step_default_assignment_rule
    FOREIGN KEY (default_assignment_rule_id)
    REFERENCES hidra_workflow_step_assignment_rule (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_workflow_step
    VALIDATE CONSTRAINT fk_hmr043_workflow_step_default_assignment_rule;
