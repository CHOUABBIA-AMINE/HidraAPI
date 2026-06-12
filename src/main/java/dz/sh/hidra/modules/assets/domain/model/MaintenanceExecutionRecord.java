/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceExecutionRecord
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
