/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActionType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Defines WorkflowActionType values.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

/**
 * Defines WorkflowActionType values.
 */
public enum WorkflowActionType {
    START, ASSIGN, CLAIM, APPROVE, REJECT, REQUEST_CORRECTION, CORRECT, RETURN, DELEGATE, ESCALATE, CANCEL, COMMENT, COMPLETE
}
