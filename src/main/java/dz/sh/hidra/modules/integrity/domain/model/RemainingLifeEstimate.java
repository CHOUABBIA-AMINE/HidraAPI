/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RemainingLifeEstimate
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Remaining-life estimate.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Remaining-life estimate.
     *
         * @param id id
     * @param defectId defectId
     * @param assessmentId assessmentId
     * @param methodId methodId
     * @param remainingLifeValue remainingLifeValue
     * @param remainingLifeUnitId remainingLifeUnitId
     * @param corrosionRate corrosionRate
     * @param corrosionRateUnitId corrosionRateUnitId
     * @param estimatedAt estimatedAt
     * @param estimatedByActorId estimatedByActorId
     * @param confidenceLevelId confidenceLevelId
     * @param notes notes
     */
    public record RemainingLifeEstimate(
            String id,
        String defectId,
        String assessmentId,
        String methodId,
        BigDecimal remainingLifeValue,
        String remainingLifeUnitId,
        BigDecimal corrosionRate,
        String corrosionRateUnitId,
        Instant estimatedAt,
        String estimatedByActorId,
        String confidenceLevelId,
        String notes
    ) {

        public RemainingLifeEstimate {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrityValueException("RemainingLifeEstimate id must not be blank.");
        }
        // HRA-051 required: methodId
        if (methodId == null || methodId.isBlank()) {
            throw new InvalidIntegrityValueException("RemainingLifeEstimate method id must not be blank.");
        }
        // HRA-051 required: remainingLifeValue
        if (remainingLifeValue == null) {
            throw new InvalidIntegrityValueException("RemainingLifeEstimate remaining life value must not be null.");
        }
        // HRA-051 required: remainingLifeUnitId
        if (remainingLifeUnitId == null || remainingLifeUnitId.isBlank()) {
            throw new InvalidIntegrityValueException("RemainingLifeEstimate remaining life unit id must not be blank.");
        }
        // HRA-051 required: estimatedAt
        if (estimatedAt == null) {
            throw new InvalidIntegrityValueException("RemainingLifeEstimate estimated at must not be null.");
        }

        id = normalize(id);
        defectId = normalize(defectId);
        assessmentId = normalize(assessmentId);
        methodId = normalize(methodId);
        remainingLifeUnitId = normalize(remainingLifeUnitId);
        corrosionRateUnitId = normalize(corrosionRateUnitId);
        estimatedByActorId = normalize(estimatedByActorId);
        confidenceLevelId = normalize(confidenceLevelId);
        notes = normalize(notes);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
