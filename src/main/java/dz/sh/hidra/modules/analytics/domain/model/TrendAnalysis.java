/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrendAnalysis
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("TrendAnalysis id must not be blank.");
        }
        // HRA-051 required: subjectAreaId
        if (subjectAreaId == null || subjectAreaId.isBlank()) {
            throw new InvalidAnalyticsValueException("TrendAnalysis subject area id must not be blank.");
        }
        // HRA-051 required: trendType
        if (trendType == null) {
            throw new InvalidAnalyticsValueException("TrendAnalysis trend type must not be null.");
        }
        // HRA-051 required: periodStart
        if (periodStart == null) {
            throw new InvalidAnalyticsValueException("TrendAnalysis period start must not be null.");
        }
        // HRA-051 required: periodEnd
        if (periodEnd == null) {
            throw new InvalidAnalyticsValueException("TrendAnalysis period end must not be null.");
        }
        // HRA-051 required: trendDirection
        if (trendDirection == null) {
            throw new InvalidAnalyticsValueException("TrendAnalysis trend direction must not be null.");
        }
        // HRA-051 required: detectedAt
        if (detectedAt == null) {
            throw new InvalidAnalyticsValueException("TrendAnalysis detected at must not be null.");
        }
        // HRA-051 order: periodStart <= periodEnd
        if (periodStart != null && periodEnd != null && periodEnd.isBefore(periodStart)) {
            throw new InvalidAnalyticsValueException("TrendAnalysis period end must not be before period start.");
        }

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
