/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationInfrastructure
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure
 *
 * @Description : Configuration infrastructure constants.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure;

/**
 * Configuration infrastructure constants.
 */
public final class ConfigurationInfrastructure {

    public static final String TABLE_PREFIX = "hidra_configuration_";

    private ConfigurationInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
