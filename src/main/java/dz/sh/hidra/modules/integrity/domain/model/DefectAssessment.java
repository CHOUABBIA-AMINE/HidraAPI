/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DefectAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Engineering assessment of defect.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Engineering assessment of defect.
     *
         * @param id id
     * @param defectId defectId
     * @param assessmentMethodId assessmentMethodId
     * @param assessmentNumber assessmentNumber
     * @param assessedSeverity assessedSeverity
     * @param failurePressure failurePressure
     * @param pressureUnitId pressureUnitId
     * @param safetyFactor safetyFactor
     * @param fitForService fitForService
     * @param assessmentSummary assessmentSummary
     * @param assessedByActorId assessedByActorId
     * @param assessedAt assessedAt
     * @param approvedByActorId approvedByActorId
     * @param approvedAt approvedAt
     */
    public record DefectAssessment(
            String id,
        String defectId,
        String assessmentMethodId,
        String assessmentNumber,
        FindingSeverity assessedSeverity,
        BigDecimal failurePressure,
        String pressureUnitId,
        BigDecimal safetyFactor,
        boolean fitForService,
        String assessmentSummary,
        String assessedByActorId,
        Instant assessedAt,
        String approvedByActorId,
        Instant approvedAt
    ) {

        public DefectAssessment {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrityValueException("DefectAssessment id must not be blank.");
        }
        // HRA-051 required: defectId
        if (defectId == null || defectId.isBlank()) {
            throw new InvalidIntegrityValueException("DefectAssessment defect id must not be blank.");
        }
        // HRA-051 required: assessmentMethodId
        if (assessmentMethodId == null || assessmentMethodId.isBlank()) {
            throw new InvalidIntegrityValueException("DefectAssessment assessment method id must not be blank.");
        }
        // HRA-051 required: assessmentNumber
        if (assessmentNumber == null || assessmentNumber.isBlank()) {
            throw new InvalidIntegrityValueException("DefectAssessment assessment number must not be blank.");
        }
        // HRA-051 required: assessedAt
        if (assessedAt == null) {
            throw new InvalidIntegrityValueException("DefectAssessment assessed at must not be null.");
        }

        id = normalize(id);
        defectId = normalize(defectId);
        assessmentMethodId = normalize(assessmentMethodId);
        assessmentNumber = normalize(assessmentNumber);
        pressureUnitId = normalize(pressureUnitId);
        assessmentSummary = normalize(assessmentSummary);
        assessedByActorId = normalize(assessedByActorId);
        approvedByActorId = normalize(approvedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
