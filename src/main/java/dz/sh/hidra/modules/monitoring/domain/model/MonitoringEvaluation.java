/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringEvaluation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.model
 *
 * @Description : Evaluation execution.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.model;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import java.time.Instant;

    /**
     * Evaluation execution.
     *
         * @param id id
     * @param ruleId ruleId
     * @param periodId periodId
     * @param planRevisionId planRevisionId
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param telemetryPointId telemetryPointId
     * @param status status
     * @param result result
     * @param evaluationStart evaluationStart
     * @param evaluationEnd evaluationEnd
     * @param actualReadingCount actualReadingCount
     * @param deviationCount deviationCount
     * @param failureReason failureReason
     * @param correlationId correlationId
     * @param createdAt createdAt
     */
    public record MonitoringEvaluation(
            String id,
        String ruleId,
        String periodId,
        String planRevisionId,
        String topologyAssetType,
        String topologyAssetId,
        String telemetryPointId,
        EvaluationStatus status,
        EvaluationResult result,
        Instant evaluationStart,
        Instant evaluationEnd,
        int actualReadingCount,
        int deviationCount,
        String failureReason,
        String correlationId,
        Instant createdAt
    ) {

        public MonitoringEvaluation {
        id = normalize(id);
        ruleId = normalize(ruleId);
        periodId = normalize(periodId);
        planRevisionId = normalize(planRevisionId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        telemetryPointId = normalize(telemetryPointId);
        failureReason = normalize(failureReason);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
