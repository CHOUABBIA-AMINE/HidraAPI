/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DefectAssessment
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
