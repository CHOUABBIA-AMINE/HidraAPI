/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationExchangeMessage
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationExchangeMessage id must not be blank.");
        }
        // HRA-051 required: externalSystemId
        if (externalSystemId == null || externalSystemId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationExchangeMessage external system id must not be blank.");
        }
        // HRA-051 required: direction
        if (direction == null) {
            throw new InvalidIntegrationValueException("IntegrationExchangeMessage direction must not be null.");
        }
        // HRA-051 required: messageTypeId
        if (messageTypeId == null || messageTypeId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationExchangeMessage message type id must not be blank.");
        }
        // HRA-051 required: payloadFormatId
        if (payloadFormatId == null || payloadFormatId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationExchangeMessage payload format id must not be blank.");
        }
        // HRA-051 required: payloadStorageMode
        if (payloadStorageMode == null) {
            throw new InvalidIntegrationValueException("IntegrationExchangeMessage payload storage mode must not be null.");
        }
        // HRA-051 required: payloadHash
        if (payloadHash == null || payloadHash.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationExchangeMessage payload hash must not be blank.");
        }
        // HRA-051 required: receivedOrSentAt
        if (receivedOrSentAt == null) {
            throw new InvalidIntegrationValueException("IntegrationExchangeMessage received or sent at must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrationValueException("IntegrationExchangeMessage status must not be null.");
        }

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
