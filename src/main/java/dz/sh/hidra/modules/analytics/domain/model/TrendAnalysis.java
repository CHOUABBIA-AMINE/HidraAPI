/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrendAnalysis
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Analytical trend detected over time.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Analytical trend detected over time.
     *
         * @param id id
     * @param subjectAreaId subjectAreaId
     * @param trendType trendType
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param metricDefinitionId metricDefinitionId
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param trendDirection trendDirection
     * @param confidenceScore confidenceScore
     * @param strengthScore strengthScore
     * @param detectedAt detectedAt
     * @param createdAt createdAt
     */
    public record TrendAnalysis(
            String id,
        String subjectAreaId,
        AnalyticsTrendDirection trendType,
        String scopeType,
        String scopeId,
        String metricDefinitionId,
        Instant periodStart,
        Instant periodEnd,
        AnalyticsTrendDirection trendDirection,
        BigDecimal confidenceScore,
        BigDecimal strengthScore,
        Instant detectedAt,
        Instant createdAt
    ) {

        public TrendAnalysis {
        id = normalize(id);
        subjectAreaId = normalize(subjectAreaId);
        scopeType = normalize(scopeType);
        scopeId = normalize(scopeId);
        metricDefinitionId = normalize(metricDefinitionId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
