/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalSystemResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.response
 *
 * @Description : REST response for external system.
 *
 */
package dz.sh.hidra.modules.integration.api.rest.response;

import dz.sh.hidra.modules.integration.domain.value.ExternalSystemStatus;
import dz.sh.hidra.modules.integration.domain.value.IntegrationCriticality;
import dz.sh.hidra.modules.integration.domain.value.IntegrationEnvironment;

import java.time.Instant;

/**
 * REST response for external system.
 */
public record ExternalSystemResponse(
        String id,
        String code,
        String nameFr,
        String systemTypeId,
        IntegrationEnvironment environment,
        IntegrationCriticality criticality,
        ExternalSystemStatus status,
        Instant createdAt
) {
}
