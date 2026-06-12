/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationModule
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration
 *
 * @Description : Defines integration module constants.
 *
 */
package dz.sh.hidra.modules.integration;

/**
 * Integration module constants.
 */
public final class IntegrationModule {

    public static final String MODULE_NAME = "integration";
    public static final String TABLE_PREFIX = "hidra_integration_";

    private IntegrationModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
