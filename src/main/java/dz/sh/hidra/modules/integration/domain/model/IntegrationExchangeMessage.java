/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationExchangeMessage
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Inbound/outbound exchange message envelope.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Inbound/outbound exchange message envelope.
     *
         * @param id id
     * @param jobRunId jobRunId
     * @param externalSystemId externalSystemId
     * @param endpointId endpointId
     * @param direction direction
     * @param messageTypeId messageTypeId
     * @param externalMessageId externalMessageId
     * @param payloadFormatId payloadFormatId
     * @param payloadStorageMode payloadStorageMode
     * @param payloadSanitized payloadSanitized
     * @param payloadReference payloadReference
     * @param payloadHash payloadHash
     * @param contentLengthBytes contentLengthBytes
     * @param receivedOrSentAt receivedOrSentAt
     * @param correlationId correlationId
     * @param status status
     * @param createdAt createdAt
     */
    public record IntegrationExchangeMessage(
            String id,
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
        ExchangeMessageStatus status,
        Instant createdAt
    ) {

        public IntegrationExchangeMessage {
        id = normalize(id);
        jobRunId = normalize(jobRunId);
        externalSystemId = normalize(externalSystemId);
        endpointId = normalize(endpointId);
        messageTypeId = normalize(messageTypeId);
        externalMessageId = normalize(externalMessageId);
        payloadFormatId = normalize(payloadFormatId);
        payloadSanitized = normalize(payloadSanitized);
        payloadReference = normalize(payloadReference);
        payloadHash = normalize(payloadHash);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
