/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordExchangeMessageCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.command
 *
 * @Description : Command to record an integration exchange message.
 *
 */
package dz.sh.hidra.modules.integration.application.command;

import dz.sh.hidra.modules.integration.domain.value.ExchangeMessageStatus;
import dz.sh.hidra.modules.integration.domain.value.IntegrationDirection;
import dz.sh.hidra.modules.integration.domain.value.PayloadStorageMode;

import java.time.Instant;

/**
 * Command to record an integration exchange message.
 */
public record RecordExchangeMessageCommand(
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
