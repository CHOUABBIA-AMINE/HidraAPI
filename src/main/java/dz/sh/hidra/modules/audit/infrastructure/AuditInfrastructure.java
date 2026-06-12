/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditInfrastructure
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure
 *
 * @Description : Audit infrastructure constants.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure;

/**
 * Audit infrastructure constants.
 */
public final class AuditInfrastructure {

    public static final String TABLE_PREFIX = "hidra_audit_";

    private AuditInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
