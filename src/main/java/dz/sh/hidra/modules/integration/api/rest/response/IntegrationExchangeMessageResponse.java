/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationExchangeMessageResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.response
 *
 * @Description : REST response for integration exchange message.
 *
 */
package dz.sh.hidra.modules.integration.api.rest.response;

import dz.sh.hidra.modules.integration.domain.value.ExchangeMessageStatus;
import dz.sh.hidra.modules.integration.domain.value.IntegrationDirection;
import java.time.Instant;

/**
 * REST response for integration exchange message.
 */
public record IntegrationExchangeMessageResponse(
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
