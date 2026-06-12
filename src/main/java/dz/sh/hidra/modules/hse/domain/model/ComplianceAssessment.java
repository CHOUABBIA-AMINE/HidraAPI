/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ComplianceAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : Compliance assessment.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;

    /**
     * Compliance assessment.
     *
         * @param id id
     * @param obligationId obligationId
     * @param assessmentNumber assessmentNumber
     * @param complianceStatus complianceStatus
     * @param assessmentSummary assessmentSummary
     * @param assessedByActorId assessedByActorId
     * @param assessedAt assessedAt
     * @param evidenceReferenceId evidenceReferenceId
     * @param linkedHseCaseId linkedHseCaseId
     * @param nextAssessmentDueAt nextAssessmentDueAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ComplianceAssessment(
            String id,
        String obligationId,
        String assessmentNumber,
        ComplianceStatus complianceStatus,
        String assessmentSummary,
        String assessedByActorId,
        Instant assessedAt,
        String evidenceReferenceId,
        String linkedHseCaseId,
        Instant nextAssessmentDueAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ComplianceAssessment {
        id = normalize(id);
        obligationId = normalize(obligationId);
        assessmentNumber = normalize(assessmentNumber);
        assessmentSummary = normalize(assessmentSummary);
        assessedByActorId = normalize(assessedByActorId);
        evidenceReferenceId = normalize(evidenceReferenceId);
        linkedHseCaseId = normalize(linkedHseCaseId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
