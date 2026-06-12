/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanActualReviewSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Optional post-operation planned-versus-actual review projection.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Optional post-operation planned-versus-actual review projection.
     *
         * @param id id
     * @param planTargetId planTargetId
     * @param monitoringDeviationId monitoringDeviationId
     * @param trustedTelemetryReadingId trustedTelemetryReadingId
     * @param actualValue actualValue
     * @param plannedValue plannedValue
     * @param differenceValue differenceValue
     * @param differencePercent differencePercent
     * @param unitId unitId
     * @param reviewedAt reviewedAt
     * @param reviewSource reviewSource
     * @param notes notes
     */
    public record PlanActualReviewSnapshot(
            String id,
        String planTargetId,
        String monitoringDeviationId,
        String trustedTelemetryReadingId,
        BigDecimal actualValue,
        BigDecimal plannedValue,
        BigDecimal differenceValue,
        BigDecimal differencePercent,
        String unitId,
        Instant reviewedAt,
        ReviewSource reviewSource,
        String notes
    ) {

        public PlanActualReviewSnapshot {
        id = normalize(id);
        planTargetId = normalize(planTargetId);
        monitoringDeviationId = normalize(monitoringDeviationId);
        trustedTelemetryReadingId = normalize(trustedTelemetryReadingId);
        unitId = normalize(unitId);
        notes = normalize(notes);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
