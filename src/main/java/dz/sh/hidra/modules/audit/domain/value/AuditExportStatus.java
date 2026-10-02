/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditExportStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.value
 *
 * @Description : Defines AuditExportStatus values.
 *
 */
package dz.sh.hidra.modules.audit.domain.value;

/**
 * Defines AuditExportStatus values.
 */
public enum AuditExportStatus {
    REQUESTED, APPROVED, REJECTED, RUNNING, COMPLETED, FAILED, EXPIRED
}
