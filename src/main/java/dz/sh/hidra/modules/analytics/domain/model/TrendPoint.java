/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrendPoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Point used in trend analysis.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Point used in trend analysis.
     *
         * @param id id
     * @param trendAnalysisId trendAnalysisId
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param value value
     * @param unitId unitId
     * @param sourceMetricValueId sourceMetricValueId
     * @param createdAt createdAt
     */
    public record TrendPoint(
            String id,
        String trendAnalysisId,
        Instant periodStart,
        Instant periodEnd,
        BigDecimal value,
        String unitId,
        String sourceMetricValueId,
        Instant createdAt
    ) {

        public TrendPoint {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("TrendPoint id must not be blank.");
        }
        // HRA-051 required: trendAnalysisId
        if (trendAnalysisId == null || trendAnalysisId.isBlank()) {
            throw new InvalidAnalyticsValueException("TrendPoint trend analysis id must not be blank.");
        }
        // HRA-051 required: periodStart
        if (periodStart == null) {
            throw new InvalidAnalyticsValueException("TrendPoint period start must not be null.");
        }
        // HRA-051 required: periodEnd
        if (periodEnd == null) {
            throw new InvalidAnalyticsValueException("TrendPoint period end must not be null.");
        }
        // HRA-051 required: value
        if (value == null) {
            throw new InvalidAnalyticsValueException("TrendPoint value must not be null.");
        }
        // HRA-051 order: periodStart <= periodEnd
        if (periodStart != null && periodEnd != null && periodEnd.isBefore(periodStart)) {
            throw new InvalidAnalyticsValueException("TrendPoint period end must not be before period start.");
        }

        id = normalize(id);
        trendAnalysisId = normalize(trendAnalysisId);
        unitId = normalize(unitId);
        sourceMetricValueId = normalize(sourceMetricValueId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
