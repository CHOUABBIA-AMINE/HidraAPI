/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValueSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.dto
 *
 * @Description : Configuration value summary DTO.
 *
 */
package dz.sh.hidra.modules.configuration.application.dto;

import dz.sh.hidra.modules.configuration.domain.value.ConfigurationValueStatus;

import java.time.Instant;

/**
 * Configuration value summary DTO.
 */
public record ConfigurationValueSummaryDto(
        String id,
        String definitionId,
        String definitionVersionId,
        String environment,
        ConfigurationValueStatus status,
        Instant effectiveFrom,
        Instant effectiveTo
) {
}
