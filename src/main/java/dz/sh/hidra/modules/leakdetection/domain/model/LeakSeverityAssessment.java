/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakSeverityAssessment
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Severity and confidence assessment.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import dz.sh.hidra.modules.leakdetection.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Severity and confidence assessment.
     *
         * @param id id
     * @param candidateId candidateId
     * @param severityLevel severityLevel
     * @param confidenceScore confidenceScore
     * @param estimatedLeakRate estimatedLeakRate
     * @param leakRateUnitId leakRateUnitId
     * @param estimatedVolumeLoss estimatedVolumeLoss
     * @param volumeUnitId volumeUnitId
     * @param assessmentReason assessmentReason
     * @param assessedByActorId assessedByActorId
     * @param assessedAt assessedAt
     */
    public record LeakSeverityAssessment(
            String id,
        String candidateId,
        LeakSeverityLevel severityLevel,
        BigDecimal confidenceScore,
        BigDecimal estimatedLeakRate,
        String leakRateUnitId,
        BigDecimal estimatedVolumeLoss,
        String volumeUnitId,
        String assessmentReason,
        String assessedByActorId,
        Instant assessedAt
    ) {

        public LeakSeverityAssessment {
        id = normalize(id);
        candidateId = normalize(candidateId);
        leakRateUnitId = normalize(leakRateUnitId);
        volumeUnitId = normalize(volumeUnitId);
        assessmentReason = normalize(assessmentReason);
        assessedByActorId = normalize(assessedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
