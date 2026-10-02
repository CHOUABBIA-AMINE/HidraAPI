/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationExchangeMessageSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.dto
 *
 * @Description : Integration exchange message summary DTO.
 *
 */
package dz.sh.hidra.modules.integration.application.dto;

import dz.sh.hidra.modules.integration.domain.value.ExchangeMessageStatus;
import dz.sh.hidra.modules.integration.domain.value.IntegrationDirection;

import java.time.Instant;

/**
 * Integration exchange message summary DTO.
 */
public record IntegrationExchangeMessageSummaryDto(
        String id,
        String jobRunId,
        String externalSystemId,
        IntegrationDirection direction,
        String externalMessageId,
        String payloadHash,
        ExchangeMessageStatus status,
        Instant receivedOrSentAt
) {
}
