/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ComplianceAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.hse.domain.exception.InvalidHseValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidHseValueException("ComplianceAssessment id must not be blank.");
        }
        // HRA-051 required: obligationId
        if (obligationId == null || obligationId.isBlank()) {
            throw new InvalidHseValueException("ComplianceAssessment obligation id must not be blank.");
        }
        // HRA-051 required: assessmentNumber
        if (assessmentNumber == null || assessmentNumber.isBlank()) {
            throw new InvalidHseValueException("ComplianceAssessment assessment number must not be blank.");
        }
        // HRA-051 required: complianceStatus
        if (complianceStatus == null) {
            throw new InvalidHseValueException("ComplianceAssessment compliance status must not be null.");
        }
        // HRA-051 required: assessedAt
        if (assessedAt == null) {
            throw new InvalidHseValueException("ComplianceAssessment assessed at must not be null.");
        }

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
