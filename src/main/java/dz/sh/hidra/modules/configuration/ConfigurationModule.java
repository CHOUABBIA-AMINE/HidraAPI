/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationModule
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration
 *
 * @Description : Defines configuration module constants.
 *
 */
package dz.sh.hidra.modules.configuration;

/**
 * Configuration module constants.
 */
public final class ConfigurationModule {

    public static final String MODULE_NAME = "configuration";
    public static final String TABLE_PREFIX = "hidra_configuration_";

    private ConfigurationModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
