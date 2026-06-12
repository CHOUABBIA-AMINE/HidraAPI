/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalSystemSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.dto
 *
 * @Description : External system summary DTO.
 *
 */
package dz.sh.hidra.modules.integration.application.dto;

import dz.sh.hidra.modules.integration.domain.value.ExternalSystemStatus;
import dz.sh.hidra.modules.integration.domain.value.IntegrationCriticality;
import dz.sh.hidra.modules.integration.domain.value.IntegrationEnvironment;

import java.time.Instant;

/**
 * External system summary DTO.
 */
public record ExternalSystemSummaryDto(
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
