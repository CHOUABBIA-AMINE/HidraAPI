/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditAccessType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.value
 *
 * @Description : Defines AuditAccessType values.
 *
 */
package dz.sh.hidra.modules.audit.domain.value;

/**
 * Defines AuditAccessType values.
 */
public enum AuditAccessType {
    SEARCH, VIEW, EXPORT, VERIFY_SEAL, ARCHIVE, PURGE_REQUEST
}
