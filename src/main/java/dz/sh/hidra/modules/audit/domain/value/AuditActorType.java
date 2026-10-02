/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditActorType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.value
 *
 * @Description : Defines AuditActorType values.
 *
 */
package dz.sh.hidra.modules.audit.domain.value;

/**
 * Defines AuditActorType values.
 */
public enum AuditActorType {
    USER, SYSTEM, INTEGRATION, SCHEDULED_JOB, SERVICE_ACCOUNT
}
