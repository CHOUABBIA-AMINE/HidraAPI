/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationInboundRecord
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Mapped inbound record extracted from an exchange message.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Mapped inbound record extracted from an exchange message.
     *
         * @param id id
     * @param exchangeMessageId exchangeMessageId
     * @param jobRunId jobRunId
     * @param recordSequence recordSequence
     * @param mappingProfileId mappingProfileId
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param targetId targetId
     * @param targetCodeSnapshot targetCodeSnapshot
     * @param mappedPayload mappedPayload
     * @param validationStatus validationStatus
     * @param submissionStatus submissionStatus
     * @param targetResponseCode targetResponseCode
     * @param targetResponseMessage targetResponseMessage
     * @param errorCode errorCode
     * @param errorMessage errorMessage
     * @param createdAt createdAt
     * @param submittedAt submittedAt
     * @param completedAt completedAt
     */
    public record IntegrationInboundRecord(
            String id,
        String exchangeMessageId,
        String jobRunId,
        long recordSequence,
        String mappingProfileId,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String targetCodeSnapshot,
        String mappedPayload,
        ValidationStatus validationStatus,
        SubmissionStatus submissionStatus,
        String targetResponseCode,
        String targetResponseMessage,
        String errorCode,
        String errorMessage,
        Instant createdAt,
        Instant submittedAt,
        Instant completedAt
    ) {

        public IntegrationInboundRecord {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationInboundRecord id must not be blank.");
        }
        // HRA-051 required: exchangeMessageId
        if (exchangeMessageId == null || exchangeMessageId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationInboundRecord exchange message id must not be blank.");
        }
        // HRA-051 required: targetTypeCode
        if (targetTypeCode == null || targetTypeCode.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationInboundRecord target type code must not be blank.");
        }
        // HRA-051 required: validationStatus
        if (validationStatus == null) {
            throw new InvalidIntegrationValueException("IntegrationInboundRecord validation status must not be null.");
        }
        // HRA-051 required: submissionStatus
        if (submissionStatus == null) {
            throw new InvalidIntegrationValueException("IntegrationInboundRecord submission status must not be null.");
        }

        id = normalize(id);
        exchangeMessageId = normalize(exchangeMessageId);
        jobRunId = normalize(jobRunId);
        mappingProfileId = normalize(mappingProfileId);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        targetId = normalize(targetId);
        targetCodeSnapshot = normalize(targetCodeSnapshot);
        mappedPayload = normalize(mappedPayload);
        targetResponseCode = normalize(targetResponseCode);
        targetResponseMessage = normalize(targetResponseMessage);
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
