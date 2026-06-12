/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditSealVerificationStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.value
 *
 * @Description : Defines AuditSealVerificationStatus values.
 *
 */
package dz.sh.hidra.modules.audit.domain.value;

/**
 * Defines AuditSealVerificationStatus values.
 */
public enum AuditSealVerificationStatus {
    NOT_VERIFIED, VALID, INVALID, BROKEN_CHAIN
}
