/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit
 *
 * @Description : Defines audit module constants.
 *
 */
package dz.sh.hidra.modules.audit;

/**
 * Audit module constants.
 */
public final class AuditModule {

    public static final String MODULE_NAME = "audit";
    public static final String TABLE_PREFIX = "hidra_audit_";

    private AuditModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
