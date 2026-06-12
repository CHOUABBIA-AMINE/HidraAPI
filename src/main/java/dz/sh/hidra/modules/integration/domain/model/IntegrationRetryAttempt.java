/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationRetryAttempt
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Append-only retry attempt.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Append-only retry attempt.
     *
         * @param id id
     * @param retryPolicyId retryPolicyId
     * @param targetRecordType targetRecordType
     * @param targetRecordId targetRecordId
     * @param attemptNumber attemptNumber
     * @param status status
     * @param scheduledAt scheduledAt
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param errorCode errorCode
     * @param errorMessage errorMessage
     * @param createdAt createdAt
     */
    public record IntegrationRetryAttempt(
            String id,
        String retryPolicyId,
        String targetRecordType,
        String targetRecordId,
        int attemptNumber,
        RetryAttemptStatus status,
        Instant scheduledAt,
        Instant startedAt,
        Instant completedAt,
        String errorCode,
        String errorMessage,
        Instant createdAt
    ) {

        public IntegrationRetryAttempt {
        id = normalize(id);
        retryPolicyId = normalize(retryPolicyId);
        targetRecordType = normalize(targetRecordType);
        targetRecordId = normalize(targetRecordId);
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
