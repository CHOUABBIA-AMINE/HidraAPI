/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : KpiEvaluation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Evaluated KPI value.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Evaluated KPI value.
     *
         * @param id id
     * @param kpiDefinitionId kpiDefinitionId
     * @param metricValueId metricValueId
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param value value
     * @param unitId unitId
     * @param bandId bandId
     * @param trendDirection trendDirection
     * @param evaluatedAt evaluatedAt
     */
    public record KpiEvaluation(
            String id,
        String kpiDefinitionId,
        String metricValueId,
        String scopeType,
        String scopeId,
        Instant periodStart,
        Instant periodEnd,
        BigDecimal value,
        String unitId,
        String bandId,
        AnalyticsTrendDirection trendDirection,
        Instant evaluatedAt
    ) {

        public KpiEvaluation {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("KpiEvaluation id must not be blank.");
        }
        // HRA-051 required: kpiDefinitionId
        if (kpiDefinitionId == null || kpiDefinitionId.isBlank()) {
            throw new InvalidAnalyticsValueException("KpiEvaluation kpi definition id must not be blank.");
        }
        // HRA-051 required: periodStart
        if (periodStart == null) {
            throw new InvalidAnalyticsValueException("KpiEvaluation period start must not be null.");
        }
        // HRA-051 required: periodEnd
        if (periodEnd == null) {
            throw new InvalidAnalyticsValueException("KpiEvaluation period end must not be null.");
        }
        // HRA-051 required: value
        if (value == null) {
            throw new InvalidAnalyticsValueException("KpiEvaluation value must not be null.");
        }
        // HRA-051 required: evaluatedAt
        if (evaluatedAt == null) {
            throw new InvalidAnalyticsValueException("KpiEvaluation evaluated at must not be null.");
        }
        // HRA-051 order: periodStart <= periodEnd
        if (periodStart != null && periodEnd != null && periodEnd.isBefore(periodStart)) {
            throw new InvalidAnalyticsValueException("KpiEvaluation period end must not be before period start.");
        }

        id = normalize(id);
        kpiDefinitionId = normalize(kpiDefinitionId);
        metricValueId = normalize(metricValueId);
        scopeType = normalize(scopeType);
        scopeId = normalize(scopeId);
        unitId = normalize(unitId);
        bandId = normalize(bandId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
