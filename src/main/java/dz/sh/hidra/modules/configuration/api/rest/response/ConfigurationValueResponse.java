/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValueResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.response
 *
 * @Description : REST response for configuration value.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.response;

import dz.sh.hidra.modules.configuration.domain.value.ConfigurationValueStatus;

import java.time.Instant;

/**
 * REST response for configuration value.
 */
public record ConfigurationValueResponse(
        String id,
        String definitionId,
        String definitionVersionId,
        String environment,
        ConfigurationValueStatus status,
        Instant effectiveFrom,
        Instant effectiveTo
) {
}
