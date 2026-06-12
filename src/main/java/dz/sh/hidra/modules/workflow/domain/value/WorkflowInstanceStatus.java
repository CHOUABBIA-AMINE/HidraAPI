/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Defines WorkflowInstanceStatus values.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

/**
 * Defines WorkflowInstanceStatus values.
 */
public enum WorkflowInstanceStatus {
    DRAFT, STARTED, IN_PROGRESS, WAITING, COMPLETED, CANCELLED, FAILED
}
