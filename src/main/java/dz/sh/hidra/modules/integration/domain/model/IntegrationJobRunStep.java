/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobRunStep
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Execution stage inside a job run.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Execution stage inside a job run.
     *
         * @param id id
     * @param jobRunId jobRunId
     * @param stepName stepName
     * @param status status
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param processedCount processedCount
     * @param errorCount errorCount
     * @param detailsJson detailsJson
     */
    public record IntegrationJobRunStep(
            String id,
        String jobRunId,
        String stepName,
        JobRunStepStatus status,
        Instant startedAt,
        Instant completedAt,
        long processedCount,
        long errorCount,
        String detailsJson
    ) {

        public IntegrationJobRunStep {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationJobRunStep id must not be blank.");
        }
        // HRA-051 required: jobRunId
        if (jobRunId == null || jobRunId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationJobRunStep job run id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrationValueException("IntegrationJobRunStep status must not be null.");
        }
        // HRA-051 required: startedAt
        if (startedAt == null) {
            throw new InvalidIntegrationValueException("IntegrationJobRunStep started at must not be null.");
        }

        id = normalize(id);
        jobRunId = normalize(jobRunId);
        stepName = normalize(stepName);
        detailsJson = normalize(detailsJson);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
