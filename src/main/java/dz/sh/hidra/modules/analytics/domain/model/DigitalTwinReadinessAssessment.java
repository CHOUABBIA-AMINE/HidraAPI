/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DigitalTwinReadinessAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Assessment of data and model readiness for future digital twin use.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Assessment of data and model readiness for future digital twin use.
     *
         * @param id id
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param topologySnapshotId topologySnapshotId
     * @param assessmentPeriodStart assessmentPeriodStart
     * @param assessmentPeriodEnd assessmentPeriodEnd
     * @param telemetryCompletenessScore telemetryCompletenessScore
     * @param telemetryQualityScore telemetryQualityScore
     * @param topologyCompletenessScore topologyCompletenessScore
     * @param modelAvailabilityScore modelAvailabilityScore
     * @param lineageCompletenessScore lineageCompletenessScore
     * @param overallReadinessScore overallReadinessScore
     * @param readinessStatus readinessStatus
     * @param assessedAt assessedAt
     * @param createdAt createdAt
     */
    public record DigitalTwinReadinessAssessment(
            String id,
        String scopeType,
        String scopeId,
        String topologySnapshotId,
        Instant assessmentPeriodStart,
        Instant assessmentPeriodEnd,
        BigDecimal telemetryCompletenessScore,
        BigDecimal telemetryQualityScore,
        BigDecimal topologyCompletenessScore,
        BigDecimal modelAvailabilityScore,
        BigDecimal lineageCompletenessScore,
        BigDecimal overallReadinessScore,
        DigitalTwinReadinessStatus readinessStatus,
        Instant assessedAt,
        Instant createdAt
    ) {

        public DigitalTwinReadinessAssessment {
        id = normalize(id);
        scopeType = normalize(scopeType);
        scopeId = normalize(scopeId);
        topologySnapshotId = normalize(topologySnapshotId);
        }
        public boolean runtimeDigitalTwin() {
            return false;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
