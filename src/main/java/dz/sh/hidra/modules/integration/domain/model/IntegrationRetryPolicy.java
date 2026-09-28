/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationRetryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Retry behavior policy.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Retry behavior policy.
     *
         * @param id id
     * @param code code
     * @param maxAttempts maxAttempts
     * @param initialDelaySeconds initialDelaySeconds
     * @param maxDelaySeconds maxDelaySeconds
     * @param backoffStrategy backoffStrategy
     * @param retryableErrorCodes retryableErrorCodes
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IntegrationRetryPolicy(
            String id,
        String code,
        int maxAttempts,
        int initialDelaySeconds,
        int maxDelaySeconds,
        RetryBackoffStrategy backoffStrategy,
        String retryableErrorCodes,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IntegrationRetryPolicy {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationRetryPolicy id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationRetryPolicy code must not be blank.");
        }
        // HRA-051 required: backoffStrategy
        if (backoffStrategy == null) {
            throw new InvalidIntegrationValueException("IntegrationRetryPolicy backoff strategy must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        retryableErrorCodes = normalize(retryableErrorCodes);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
