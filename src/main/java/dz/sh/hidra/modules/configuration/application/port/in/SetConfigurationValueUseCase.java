/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SetConfigurationValueUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.in
 *
 * @Description : Use case for setting configuration values.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.in;

import dz.sh.hidra.modules.configuration.application.command.SetConfigurationValueCommand;
import dz.sh.hidra.modules.configuration.application.dto.ConfigurationValueSummaryDto;

/**
 * Use case for setting configuration values.
 */
public interface SetConfigurationValueUseCase {

    ConfigurationValueSummaryDto setConfigurationValue(SetConfigurationValueCommand command);
}
