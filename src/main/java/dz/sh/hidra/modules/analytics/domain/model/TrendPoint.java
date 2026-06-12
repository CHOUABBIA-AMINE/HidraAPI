/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrendPoint
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
