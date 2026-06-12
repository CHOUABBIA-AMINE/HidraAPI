/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : KpiEvaluation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
