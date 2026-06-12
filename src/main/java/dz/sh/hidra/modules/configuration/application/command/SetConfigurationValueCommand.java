/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SetConfigurationValueCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.command
 *
 * @Description : Command to set a configuration value.
 *
 */
package dz.sh.hidra.modules.configuration.application.command;

import java.time.Instant;

/**
 * Command to set a configuration value.
 */
public record SetConfigurationValueCommand(
        String definitionId,
        String definitionVersionId,
        String environment,
        String rawValue,
        String jsonValue,
        String secretReference,
        Instant effectiveFrom,
        Instant effectiveTo,
        String createdByActorId
) {
}
