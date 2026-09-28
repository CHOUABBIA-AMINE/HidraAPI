/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Single execution of an integration job.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Single execution of an integration job.
     *
         * @param id id
     * @param jobDefinitionId jobDefinitionId
     * @param runNumber runNumber
     * @param triggerType triggerType
     * @param triggeredByActorId triggeredByActorId
     * @param status status
     * @param correlationId correlationId
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param receivedCount receivedCount
     * @param mappedCount mappedCount
     * @param acceptedCount acceptedCount
     * @param rejectedCount rejectedCount
     * @param deadLetterCount deadLetterCount
     * @param retryCount retryCount
     * @param failureReason failureReason
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IntegrationJobRun(
            String id,
        String jobDefinitionId,
        long runNumber,
        JobTriggerType triggerType,
        String triggeredByActorId,
        JobRunStatus status,
        String correlationId,
        Instant startedAt,
        Instant completedAt,
        long receivedCount,
        long mappedCount,
        long acceptedCount,
        long rejectedCount,
        long deadLetterCount,
        long retryCount,
        String failureReason,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IntegrationJobRun {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationJobRun id must not be blank.");
        }
        // HRA-051 required: jobDefinitionId
        if (jobDefinitionId == null || jobDefinitionId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationJobRun job definition id must not be blank.");
        }
        // HRA-051 required: triggerType
        if (triggerType == null) {
            throw new InvalidIntegrationValueException("IntegrationJobRun trigger type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrationValueException("IntegrationJobRun status must not be null.");
        }
        // HRA-051 required: startedAt
        if (startedAt == null) {
            throw new InvalidIntegrationValueException("IntegrationJobRun started at must not be null.");
        }

        id = normalize(id);
        jobDefinitionId = normalize(jobDefinitionId);
        triggeredByActorId = normalize(triggeredByActorId);
        correlationId = normalize(correlationId);
        failureReason = normalize(failureReason);
        }
        public boolean terminalStatus() {
            return status == JobRunStatus.COMPLETED
                    || status == JobRunStatus.COMPLETED_WITH_ERRORS
                    || status == JobRunStatus.FAILED
                    || status == JobRunStatus.CANCELLED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
