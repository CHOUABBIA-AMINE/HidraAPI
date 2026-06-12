/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets
 *
 * @Description : Defines assets module constants.
 *
 */
package dz.sh.hidra.modules.assets;

/**
 * Assets module constants.
 */
public final class AssetsModule {

    public static final String MODULE_NAME = "assets";
    public static final String TABLE_PREFIX = "hidra_asset_";

    private AssetsModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
