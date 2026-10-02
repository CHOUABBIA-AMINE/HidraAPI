/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Defines WorkflowTaskStatus values.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

/**
 * Defines WorkflowTaskStatus values.
 */
public enum WorkflowTaskStatus {
    OPEN, CLAIMED, IN_REVIEW, APPROVED, REJECTED, RETURNED, DELEGATED, ESCALATED, CANCELLED, EXPIRED
}
