/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsInsight
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Derived analytical finding or observation.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Derived analytical finding or observation.
     *
         * @param id id
     * @param insightType insightType
     * @param subjectAreaId subjectAreaId
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param title title
     * @param summary summary
     * @param severityId severityId
     * @param confidenceScore confidenceScore
     * @param sourceProjectionSnapshotId sourceProjectionSnapshotId
     * @param sourceTrendAnalysisId sourceTrendAnalysisId
     * @param sourceModelRunId sourceModelRunId
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AnalyticsInsight(
            String id,
        String insightType,
        String subjectAreaId,
        String scopeType,
        String scopeId,
        String title,
        String summary,
        String severityId,
        BigDecimal confidenceScore,
        String sourceProjectionSnapshotId,
        String sourceTrendAnalysisId,
        String sourceModelRunId,
        AnalyticsInsightStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AnalyticsInsight {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsInsight id must not be blank.");
        }
        // HRA-051 required: subjectAreaId
        if (subjectAreaId == null || subjectAreaId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsInsight subject area id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidAnalyticsValueException("AnalyticsInsight status must not be null.");
        }

        id = normalize(id);
        insightType = normalize(insightType);
        subjectAreaId = normalize(subjectAreaId);
        scopeType = normalize(scopeType);
        scopeId = normalize(scopeId);
        title = normalize(title);
        summary = normalize(summary);
        severityId = normalize(severityId);
        sourceProjectionSnapshotId = normalize(sourceProjectionSnapshotId);
        sourceTrendAnalysisId = normalize(sourceTrendAnalysisId);
        sourceModelRunId = normalize(sourceModelRunId);
        }
        public boolean advisoryOnly() {
            return true;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
