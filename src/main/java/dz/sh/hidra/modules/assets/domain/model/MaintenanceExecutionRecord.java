/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceExecutionRecord
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Execution record.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;

    /**
     * Execution record.
     *
         * @param id id
     * @param workOrderId workOrderId
     * @param taskId taskId
     * @param executionResult executionResult
     * @param performedByActorId performedByActorId
     * @param executedAt executedAt
     * @param durationMinutes durationMinutes
     * @param resultSummary resultSummary
     * @param measurementJson measurementJson
     * @param followUpRecommendationId followUpRecommendationId
     * @param createdAt createdAt
     */
    public record MaintenanceExecutionRecord(
            String id,
        String workOrderId,
        String taskId,
        ExecutionResultStatus executionResult,
        String performedByActorId,
        Instant executedAt,
        Integer durationMinutes,
        String resultSummary,
        String measurementJson,
        String followUpRecommendationId,
        Instant createdAt
    ) {

        public MaintenanceExecutionRecord {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceExecutionRecord id must not be blank.");
        }
        // HRA-051 required: workOrderId
        if (workOrderId == null || workOrderId.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceExecutionRecord work order id must not be blank.");
        }
        // HRA-051 required: executionResult
        if (executionResult == null) {
            throw new InvalidAssetsValueException("MaintenanceExecutionRecord execution result must not be null.");
        }
        // HRA-051 required: executedAt
        if (executedAt == null) {
            throw new InvalidAssetsValueException("MaintenanceExecutionRecord executed at must not be null.");
        }

        id = normalize(id);
        workOrderId = normalize(workOrderId);
        taskId = normalize(taskId);
        performedByActorId = normalize(performedByActorId);
        resultSummary = normalize(resultSummary);
        measurementJson = normalize(measurementJson);
        followUpRecommendationId = normalize(followUpRecommendationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
