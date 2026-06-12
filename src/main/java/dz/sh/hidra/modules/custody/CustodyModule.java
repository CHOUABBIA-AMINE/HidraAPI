/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody
 *
 * @Description : Defines custody module constants.
 *
 */
package dz.sh.hidra.modules.custody;

/**
 * Custody module constants.
 */
public final class CustodyModule {

    public static final String MODULE_NAME = "custody";
    public static final String TABLE_PREFIX = "hidra_custody_";

    private CustodyModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
