/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Calculated metric value.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Calculated metric value.
     *
         * @param id id
     * @param metricEvaluationRunId metricEvaluationRunId
     * @param metricDefinitionId metricDefinitionId
     * @param metricDefinitionVersionId metricDefinitionVersionId
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param valueNumeric valueNumeric
     * @param valueText valueText
     * @param unitId unitId
     * @param qualityStatus qualityStatus
     * @param calculatedAt calculatedAt
     */
    public record MetricValue(
            String id,
        String metricEvaluationRunId,
        String metricDefinitionId,
        String metricDefinitionVersionId,
        String scopeType,
        String scopeId,
        Instant periodStart,
        Instant periodEnd,
        BigDecimal valueNumeric,
        String valueText,
        String unitId,
        AnalyticsQualityStatus qualityStatus,
        Instant calculatedAt
    ) {

        public MetricValue {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("MetricValue id must not be blank.");
        }
        // HRA-051 required: metricEvaluationRunId
        if (metricEvaluationRunId == null || metricEvaluationRunId.isBlank()) {
            throw new InvalidAnalyticsValueException("MetricValue metric evaluation run id must not be blank.");
        }
        // HRA-051 required: metricDefinitionId
        if (metricDefinitionId == null || metricDefinitionId.isBlank()) {
            throw new InvalidAnalyticsValueException("MetricValue metric definition id must not be blank.");
        }
        // HRA-051 required: metricDefinitionVersionId
        if (metricDefinitionVersionId == null || metricDefinitionVersionId.isBlank()) {
            throw new InvalidAnalyticsValueException("MetricValue metric definition version id must not be blank.");
        }
        // HMR-038 required: scopeType
        if (scopeType == null || scopeType.isBlank()) {
            throw new InvalidAnalyticsValueException("MetricValue scope type must not be blank.");
        }
        // HRA-051 required: periodStart
        if (periodStart == null) {
            throw new InvalidAnalyticsValueException("MetricValue period start must not be null.");
        }
        // HRA-051 required: periodEnd
        if (periodEnd == null) {
            throw new InvalidAnalyticsValueException("MetricValue period end must not be null.");
        }
        // HRA-051 required: qualityStatus
        if (qualityStatus == null) {
            throw new InvalidAnalyticsValueException("MetricValue quality status must not be null.");
        }
        // HRA-051 required: calculatedAt
        if (calculatedAt == null) {
            throw new InvalidAnalyticsValueException("MetricValue calculated at must not be null.");
        }
        // HRA-051 order: periodStart <= periodEnd
        if (periodStart != null && periodEnd != null && periodEnd.isBefore(periodStart)) {
            throw new InvalidAnalyticsValueException("MetricValue period end must not be before period start.");
        }

        id = normalize(id);
        metricEvaluationRunId = normalize(metricEvaluationRunId);
        metricDefinitionId = normalize(metricDefinitionId);
        metricDefinitionVersionId = normalize(metricDefinitionVersionId);
        scopeType = normalize(scopeType);
        scopeId = normalize(scopeId);
        valueText = normalize(valueText);
        unitId = normalize(unitId);
        }
        public boolean derivedAndRebuildable() {
            return true;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
