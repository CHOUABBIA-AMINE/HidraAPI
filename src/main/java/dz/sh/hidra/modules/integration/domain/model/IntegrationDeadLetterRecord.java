/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationDeadLetterRecord
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Dead-letter record after processing failure.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Dead-letter record after processing failure.
     *
         * @param id id
     * @param externalSystemId externalSystemId
     * @param jobRunId jobRunId
     * @param exchangeMessageId exchangeMessageId
     * @param inboundRecordId inboundRecordId
     * @param outboundRecordId outboundRecordId
     * @param targetModule targetModule
     * @param failureStage failureStage
     * @param reasonCode reasonCode
     * @param reasonMessage reasonMessage
     * @param payloadHash payloadHash
     * @param sanitizedPayload sanitizedPayload
     * @param status status
     * @param resolvedByActorId resolvedByActorId
     * @param resolvedAt resolvedAt
     * @param resolutionComment resolutionComment
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IntegrationDeadLetterRecord(
            String id,
        String externalSystemId,
        String jobRunId,
        String exchangeMessageId,
        String inboundRecordId,
        String outboundRecordId,
        String targetModule,
        String failureStage,
        String reasonCode,
        String reasonMessage,
        String payloadHash,
        String sanitizedPayload,
        DeadLetterStatus status,
        String resolvedByActorId,
        Instant resolvedAt,
        String resolutionComment,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IntegrationDeadLetterRecord {
        id = normalize(id);
        externalSystemId = normalize(externalSystemId);
        jobRunId = normalize(jobRunId);
        exchangeMessageId = normalize(exchangeMessageId);
        inboundRecordId = normalize(inboundRecordId);
        outboundRecordId = normalize(outboundRecordId);
        targetModule = normalize(targetModule);
        failureStage = normalize(failureStage);
        reasonCode = normalize(reasonCode);
        reasonMessage = normalize(reasonMessage);
        payloadHash = normalize(payloadHash);
        sanitizedPayload = normalize(sanitizedPayload);
        resolvedByActorId = normalize(resolvedByActorId);
        resolutionComment = normalize(resolutionComment);
        }
        public boolean replayable() {
            return status == DeadLetterStatus.OPEN
                    || status == DeadLetterStatus.UNDER_REVIEW;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
