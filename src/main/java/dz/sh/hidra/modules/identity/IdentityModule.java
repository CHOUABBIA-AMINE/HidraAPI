/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity
 *
 * @Description : Defines identity module constants.
 *
 */
package dz.sh.hidra.modules.identity;

/**
 * Identity module constants.
 */
public final class IdentityModule {

    public static final String MODULE_NAME = "identity";
    public static final String TABLE_PREFIX = "hidra_identity_";

    private IdentityModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
