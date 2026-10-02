/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditEventStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.value
 *
 * @Description : Defines AuditEventStatus values.
 *
 */
package dz.sh.hidra.modules.audit.domain.value;

/**
 * Defines AuditEventStatus values.
 */
public enum AuditEventStatus {
    RECORDED, SEALED, REDACTED, EXPORT_LOCKED
}
