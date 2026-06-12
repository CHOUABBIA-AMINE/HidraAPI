/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateConfigurationDefinitionUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.in
 *
 * @Description : Use case for creating configuration definitions.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.in;

import dz.sh.hidra.modules.configuration.application.command.CreateConfigurationDefinitionCommand;
import dz.sh.hidra.modules.configuration.application.dto.ConfigurationDefinitionSummaryDto;

/**
 * Use case for creating configuration definitions.
 */
public interface CreateConfigurationDefinitionUseCase {

    ConfigurationDefinitionSummaryDto createConfigurationDefinition(CreateConfigurationDefinitionCommand command);
}
