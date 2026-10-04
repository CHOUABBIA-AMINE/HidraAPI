/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Single governed execution of an integration job.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.value.JobRunStatus;
import dz.sh.hidra.modules.integration.domain.value.JobTriggerType;
import java.time.Instant;

/**
 * Single execution of an integration job.
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
        if (runNumber < 0) {
            throw new InvalidIntegrationValueException(
                    "IntegrationJobRun run number must be zero (unallocated) or positive."
            );
        }
        // HRA-051 required: triggerType
        if (triggerType == null) {
            throw new InvalidIntegrationValueException("IntegrationJobRun trigger type must not be null.");
        }
        if (triggerType == JobTriggerType.MANUAL
                && (triggeredByActorId == null || triggeredByActorId.isBlank())) {
            throw new InvalidIntegrationValueException(
                    "MANUAL IntegrationJobRun requires triggeredByActorId."
            );
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrationValueException("IntegrationJobRun status must not be null.");
        }
        // HRA-051 required: startedAt
        if (startedAt == null) {
            throw new InvalidIntegrationValueException("IntegrationJobRun started at must not be null.");
        }
        if (completedAt != null && completedAt.isBefore(startedAt)) {
            throw new InvalidIntegrationValueException(
                    "IntegrationJobRun completedAt must not precede startedAt."
            );
        }
        if (receivedCount < 0
                || mappedCount < 0
                || acceptedCount < 0
                || rejectedCount < 0
                || deadLetterCount < 0
                || retryCount < 0) {
            throw new InvalidIntegrationValueException(
                    "IntegrationJobRun counters must not be negative."
            );
        }
        long accounted;
        try {
            accounted = Math.addExact(
                    Math.addExact(acceptedCount, rejectedCount),
                    deadLetterCount
            );
        } catch (ArithmeticException overflow) {
            throw new InvalidIntegrationValueException(
                    "IntegrationJobRun outcome counters exceed supported range."
            );
        }
        if (accounted > receivedCount) {
            throw new InvalidIntegrationValueException(
                    "IntegrationJobRun accepted + rejected + dead-letter counts must not exceed received count."
            );
        }

        id = normalize(id);
        jobDefinitionId = normalize(jobDefinitionId);
        triggeredByActorId = normalize(triggeredByActorId);
        correlationId = normalize(correlationId);
        failureReason = normalize(failureReason);
    }

    public boolean terminalStatus() {
        return terminal(status);
    }

    public void validateTransitionFrom(JobRunStatus previousStatus) {
        if (previousStatus == null) {
            throw new InvalidIntegrationValueException(
                    "IntegrationJobRun previous status must not be null."
            );
        }
        if (terminal(previousStatus) && status != previousStatus) {
            throw new InvalidIntegrationValueException(
                    "Terminal IntegrationJobRun status is immutable."
            );
        }
        if (previousStatus == JobRunStatus.RUNNING && status == JobRunStatus.PENDING) {
            throw new InvalidIntegrationValueException(
                    "IntegrationJobRun status must not move from RUNNING back to PENDING."
            );
        }
    }

    public IntegrationJobRun withRunNumber(long allocatedRunNumber) {
        return new IntegrationJobRun(
                id,
                jobDefinitionId,
                allocatedRunNumber,
                triggerType,
                triggeredByActorId,
                status,
                correlationId,
                startedAt,
                completedAt,
                receivedCount,
                mappedCount,
                acceptedCount,
                rejectedCount,
                deadLetterCount,
                retryCount,
                failureReason,
                createdAt,
                updatedAt
        );
    }

    private static boolean terminal(JobRunStatus value) {
        return value == JobRunStatus.COMPLETED
                || value == JobRunStatus.COMPLETED_WITH_ERRORS
                || value == JobRunStatus.FAILED
                || value == JobRunStatus.CANCELLED;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
