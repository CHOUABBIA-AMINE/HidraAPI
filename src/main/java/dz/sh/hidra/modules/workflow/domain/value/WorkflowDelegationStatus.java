/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDelegationStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Defines WorkflowDelegationStatus values.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

/**
 * Defines WorkflowDelegationStatus values.
 */
public enum WorkflowDelegationStatus {
    REQUESTED, ACCEPTED, DECLINED, EFFECTIVE, CANCELLED
}
