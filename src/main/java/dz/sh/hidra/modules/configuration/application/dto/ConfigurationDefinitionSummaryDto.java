/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDefinitionSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.dto
 *
 * @Description : Configuration definition summary DTO.
 *
 */
package dz.sh.hidra.modules.configuration.application.dto;

import dz.sh.hidra.modules.configuration.domain.value.ConfigurationDefinitionStatus;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationSensitivity;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationValueType;

/**
 * Configuration definition summary DTO.
 */
public record ConfigurationDefinitionSummaryDto(
        String id,
        String namespaceId,
        String key,
        String displayNameFr,
        ConfigurationValueType valueType,
        ConfigurationSensitivity sensitivity,
        ConfigurationDefinitionStatus status,
        boolean scoped,
        boolean requiresApproval
) {
}
