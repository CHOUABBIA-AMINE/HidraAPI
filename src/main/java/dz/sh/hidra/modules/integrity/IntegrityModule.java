/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity
 *
 * @Description : Defines integrity module constants.
 *
 */
package dz.sh.hidra.modules.integrity;

/**
 * Integrity module constants.
 */
public final class IntegrityModule {

    public static final String MODULE_NAME = "integrity";
    public static final String TABLE_PREFIX = "hidra_integrity_";

    private IntegrityModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
