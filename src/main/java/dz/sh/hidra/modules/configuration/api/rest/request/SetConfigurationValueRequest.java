/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SetConfigurationValueRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.request
 *
 * @Description : REST request to set configuration value.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.request;

import java.time.Instant;

/**
 * REST request to set configuration value.
 */
public record SetConfigurationValueRequest(
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
