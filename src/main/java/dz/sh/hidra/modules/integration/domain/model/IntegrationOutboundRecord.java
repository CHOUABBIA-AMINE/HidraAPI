/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationOutboundRecord
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Outbound record prepared for an external system.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Outbound record prepared for an external system.
     *
         * @param id id
     * @param exchangeMessageId exchangeMessageId
     * @param jobRunId jobRunId
     * @param sourceModule sourceModule
     * @param sourceTypeCode sourceTypeCode
     * @param sourceId sourceId
     * @param sourceCodeSnapshot sourceCodeSnapshot
     * @param sourceLabelSnapshot sourceLabelSnapshot
     * @param mappingProfileId mappingProfileId
     * @param outboundPayload outboundPayload
     * @param externalSystemId externalSystemId
     * @param externalObjectId externalObjectId
     * @param status status
     * @param errorCode errorCode
     * @param errorMessage errorMessage
     * @param createdAt createdAt
     * @param sentAt sentAt
     * @param acknowledgedAt acknowledgedAt
     */
    public record IntegrationOutboundRecord(
            String id,
        String exchangeMessageId,
        String jobRunId,
        String sourceModule,
        String sourceTypeCode,
        String sourceId,
        String sourceCodeSnapshot,
        String sourceLabelSnapshot,
        String mappingProfileId,
        String outboundPayload,
        String externalSystemId,
        String externalObjectId,
        OutboundRecordStatus status,
        String errorCode,
        String errorMessage,
        Instant createdAt,
        Instant sentAt,
        Instant acknowledgedAt
    ) {

        public IntegrationOutboundRecord {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationOutboundRecord id must not be blank.");
        }
        // HRA-051 required: sourceTypeCode
        if (sourceTypeCode == null || sourceTypeCode.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationOutboundRecord source type code must not be blank.");
        }
        // HRA-051 required: sourceId
        if (sourceId == null || sourceId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationOutboundRecord source id must not be blank.");
        }
        // HRA-051 required: externalSystemId
        if (externalSystemId == null || externalSystemId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationOutboundRecord external system id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrationValueException("IntegrationOutboundRecord status must not be null.");
        }

        id = normalize(id);
        exchangeMessageId = normalize(exchangeMessageId);
        jobRunId = normalize(jobRunId);
        sourceModule = normalize(sourceModule);
        sourceTypeCode = normalize(sourceTypeCode);
        sourceId = normalize(sourceId);
        sourceCodeSnapshot = normalize(sourceCodeSnapshot);
        sourceLabelSnapshot = normalize(sourceLabelSnapshot);
        mappingProfileId = normalize(mappingProfileId);
        outboundPayload = normalize(outboundPayload);
        externalSystemId = normalize(externalSystemId);
        externalObjectId = normalize(externalObjectId);
        errorCode = normalize(errorCode);
        errorMessage = normalize(errorMessage);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
