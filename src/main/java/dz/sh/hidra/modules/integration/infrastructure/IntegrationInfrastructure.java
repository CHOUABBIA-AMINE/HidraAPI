/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationInfrastructure
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure
 *
 * @Description : Integration infrastructure constants.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure;

/**
 * Integration infrastructure constants.
 */
public final class IntegrationInfrastructure {

    public static final String TABLE_PREFIX = "hidra_integration_";

    private IntegrationInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
