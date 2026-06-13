/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordExchangeMessageRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.request
 *
 * @Description : REST request for record exchange message.
 *
 */
package dz.sh.hidra.modules.integration.api.rest.request;

import dz.sh.hidra.modules.integration.domain.value.ExchangeMessageStatus;
import dz.sh.hidra.modules.integration.domain.value.IntegrationDirection;
import dz.sh.hidra.modules.integration.domain.value.PayloadStorageMode;
import java.time.Instant;

/**
 * REST request for record exchange message.
 */
public record RecordExchangeMessageRequest(
        String jobRunId,
        String externalSystemId,
        String endpointId,
        IntegrationDirection direction,
        String messageTypeId,
        String externalMessageId,
        String payloadFormatId,
        PayloadStorageMode payloadStorageMode,
        String payloadSanitized,
        String payloadReference,
        String payloadHash,
        Long contentLengthBytes,
        Instant receivedOrSentAt,
        String correlationId,
        ExchangeMessageStatus status
) {
}
