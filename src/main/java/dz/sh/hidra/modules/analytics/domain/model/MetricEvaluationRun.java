/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricEvaluationRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Metric calculation execution.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;

    /**
     * Metric calculation execution.
     *
         * @param id id
     * @param metricDefinitionVersionId metricDefinitionVersionId
     * @param runStatus runStatus
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param recordsRead recordsRead
     * @param recordsProduced recordsProduced
     * @param errorCode errorCode
     * @param errorMessage errorMessage
     * @param correlationId correlationId
     * @param createdAt createdAt
     */
    public record MetricEvaluationRun(
            String id,
        String metricDefinitionVersionId,
        AnalyticsRunStatus runStatus,
        Instant periodStart,
        Instant periodEnd,
        String scopeType,
        String scopeId,
        Instant startedAt,
        Instant completedAt,
        Long recordsRead,
        Long recordsProduced,
        String errorCode,
        String errorMessage,
        String correlationId,
        Instant createdAt
    ) {

        public MetricEvaluationRun {
        id = normalize(id);
        metricDefinitionVersionId = normalize(metricDefinitionVersionId);
        scopeType = normalize(scopeType);
        scopeId = normalize(scopeId);
        errorCode = normalize(errorCode);
        errorMessage = normalize(errorMessage);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
