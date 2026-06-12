/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAuditOutboxStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Defines WorkflowAuditOutboxStatus values.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

/**
 * Defines WorkflowAuditOutboxStatus values.
 */
public enum WorkflowAuditOutboxStatus {
    PENDING, EMITTED, FAILED
}
