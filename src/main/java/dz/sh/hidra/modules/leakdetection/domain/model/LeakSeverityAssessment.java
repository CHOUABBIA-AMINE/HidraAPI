/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakSeverityAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakSeverityAssessment id must not be blank.");
        }
        // HRA-051 required: candidateId
        if (candidateId == null || candidateId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakSeverityAssessment candidate id must not be blank.");
        }
        // HRA-051 required: severityLevel
        if (severityLevel == null) {
            throw new InvalidLeakDetectionValueException("LeakSeverityAssessment severity level must not be null.");
        }
        // HRA-051 required: confidenceScore
        if (confidenceScore == null) {
            throw new InvalidLeakDetectionValueException("LeakSeverityAssessment confidence score must not be null.");
        }
        // HRA-051 required: assessedAt
        if (assessedAt == null) {
            throw new InvalidLeakDetectionValueException("LeakSeverityAssessment assessed at must not be null.");
        }

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
