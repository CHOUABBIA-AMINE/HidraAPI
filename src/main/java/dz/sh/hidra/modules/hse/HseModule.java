/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse
 *
 * @Description : Defines HSE module constants.
 *
 */
package dz.sh.hidra.modules.hse;

/**
 * HSE module constants.
 */
public final class HseModule {

    public static final String MODULE_NAME = "hse";
    public static final String TABLE_PREFIX = "hidra_hse_";

    private HseModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
