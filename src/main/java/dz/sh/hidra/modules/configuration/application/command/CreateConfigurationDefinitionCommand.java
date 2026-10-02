/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateConfigurationDefinitionCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.command
 *
 * @Description : Command to create a configuration definition.
 *
 */
package dz.sh.hidra.modules.configuration.application.command;

import dz.sh.hidra.modules.configuration.domain.value.ConfigurationSensitivity;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationValueType;

/**
 * Command to create a configuration definition.
 */
public record CreateConfigurationDefinitionCommand(
        String namespaceId,
        String key,
        String displayNameFr,
        String displayNameAr,
        String displayNameEn,
        ConfigurationValueType valueType,
        ConfigurationSensitivity sensitivity,
        boolean scoped,
        boolean requiresApproval,
        String defaultValue,
        String description
) {
}
