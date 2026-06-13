/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.controller
 *
 * @Description : Framework-neutral configuration controller contract.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.controller;
import dz.sh.hidra.modules.configuration.api.rest.request.*;
import dz.sh.hidra.modules.configuration.api.rest.response.*;

/**
 * Framework-neutral configuration controller contract.
 */
public interface ConfigurationController {
    ConfigurationDefinitionResponse createConfigurationDefinition(CreateConfigurationDefinitionRequest request);
    FeatureFlagResponse createFeatureFlag(CreateFeatureFlagRequest request);
    ConfigurationValueResponse setConfigurationValue(SetConfigurationValueRequest request);
}
