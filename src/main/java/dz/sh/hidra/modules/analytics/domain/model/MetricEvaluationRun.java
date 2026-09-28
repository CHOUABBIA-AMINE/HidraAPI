/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricEvaluationRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("MetricEvaluationRun id must not be blank.");
        }
        // HRA-051 required: metricDefinitionVersionId
        if (metricDefinitionVersionId == null || metricDefinitionVersionId.isBlank()) {
            throw new InvalidAnalyticsValueException("MetricEvaluationRun metric definition version id must not be blank.");
        }
        // HRA-051 required: runStatus
        if (runStatus == null) {
            throw new InvalidAnalyticsValueException("MetricEvaluationRun run status must not be null.");
        }
        // HRA-051 required: periodStart
        if (periodStart == null) {
            throw new InvalidAnalyticsValueException("MetricEvaluationRun period start must not be null.");
        }
        // HRA-051 required: periodEnd
        if (periodEnd == null) {
            throw new InvalidAnalyticsValueException("MetricEvaluationRun period end must not be null.");
        }
        // HRA-051 required: startedAt
        if (startedAt == null) {
            throw new InvalidAnalyticsValueException("MetricEvaluationRun started at must not be null.");
        }
        // HRA-051 order: periodStart <= periodEnd
        if (periodStart != null && periodEnd != null && periodEnd.isBefore(periodStart)) {
            throw new InvalidAnalyticsValueException("MetricEvaluationRun period end must not be before period start.");
        }

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
