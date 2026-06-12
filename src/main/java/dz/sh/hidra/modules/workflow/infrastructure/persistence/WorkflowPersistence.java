/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowPersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence
 *
 * @Description : Workflow database table constants.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence;

/**
 * Workflow database table constants.
 */
public final class WorkflowPersistence {

    public static final String WORKFLOW_CATALOG_ENTRY_TABLE = "hidra_workflow_type_catalog";
    public static final String WORKFLOW_CATALOG_TRANSLATION_TABLE = "hidra_workflow_type_translation";
    public static final String WORKFLOW_DEFINITION_TABLE = "hidra_workflow_definition";
    public static final String WORKFLOW_STEP_TABLE = "hidra_workflow_step";
    public static final String WORKFLOW_TRANSITION_TABLE = "hidra_workflow_transition";
    public static final String WORKFLOW_INSTANCE_TABLE = "hidra_workflow_instance";
    public static final String WORKFLOW_TASK_TABLE = "hidra_workflow_task";
    public static final String WORKFLOW_ASSIGNMENT_TABLE = "hidra_workflow_assignment";
    public static final String WORKFLOW_ACTION_TABLE = "hidra_workflow_action";
    public static final String WORKFLOW_DELEGATION_TABLE = "hidra_workflow_delegation";
    public static final String WORKFLOW_ESCALATION_RULE_TABLE = "hidra_workflow_escalation_rule";
    public static final String WORKFLOW_COMMENT_TABLE = "hidra_workflow_comment";
    public static final String WORKFLOW_STATE_HISTORY_TABLE = "hidra_workflow_state_history";
    public static final String WORKFLOW_DEFINITION_TARGET_BINDING_TABLE = "hidra_workflow_definition_target_binding";
    public static final String WORKFLOW_STEP_ASSIGNMENT_RULE_TABLE = "hidra_workflow_step_assignment_rule";
    public static final String WORKFLOW_SLA_POLICY_TABLE = "hidra_workflow_sla_policy";
    public static final String WORKFLOW_AUDIT_OUTBOX_REFERENCE_TABLE = "hidra_workflow_audit_outbox_reference";

    private WorkflowPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
