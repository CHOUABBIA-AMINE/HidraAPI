/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricValue
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
