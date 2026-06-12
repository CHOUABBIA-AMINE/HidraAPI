/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditOperation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.value
 *
 * @Description : Defines AuditOperation values.
 *
 */
package dz.sh.hidra.modules.audit.domain.value;

/**
 * Defines AuditOperation values.
 */
public enum AuditOperation {
    CREATE, UPDATE, DELETE, READ, EXPORT, APPROVE, REJECT, CORRECT, ESCALATE, DELEGATE, LOGIN
}
