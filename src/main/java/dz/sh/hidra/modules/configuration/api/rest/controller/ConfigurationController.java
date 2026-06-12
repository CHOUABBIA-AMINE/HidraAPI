/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationController
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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

import dz.sh.hidra.modules.configuration.api.rest.request.CreateConfigurationDefinitionRequest;
import dz.sh.hidra.modules.configuration.api.rest.request.SetConfigurationValueRequest;
import dz.sh.hidra.modules.configuration.api.rest.response.ConfigurationDefinitionResponse;
import dz.sh.hidra.modules.configuration.api.rest.response.ConfigurationValueResponse;

/**
 * Framework-neutral configuration controller contract.
 */
public interface ConfigurationController {

    ConfigurationDefinitionResponse createConfigurationDefinition(CreateConfigurationDefinitionRequest request);

    ConfigurationValueResponse setConfigurationValue(SetConfigurationValueRequest request);
}
