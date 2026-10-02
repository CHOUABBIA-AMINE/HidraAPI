/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Assessment over topology scope.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;

    /**
     * Assessment over topology scope.
     *
         * @param id id
     * @param programId programId
     * @param assessmentNumber assessmentNumber
     * @param title title
     * @param description description
     * @param assessmentTypeId assessmentTypeId
     * @param methodologyId methodologyId
     * @param status status
     * @param assessmentDate assessmentDate
     * @param assessedByActorId assessedByActorId
     * @param reviewedByActorId reviewedByActorId
     * @param approvedByActorId approvedByActorId
     * @param approvedAt approvedAt
     * @param workflowInstanceId workflowInstanceId
     * @param auditReferenceId auditReferenceId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IntegrityAssessment(
            String id,
        String programId,
        String assessmentNumber,
        String title,
        String description,
        String assessmentTypeId,
        String methodologyId,
        IntegrityAssessmentStatus status,
        Instant assessmentDate,
        String assessedByActorId,
        String reviewedByActorId,
        String approvedByActorId,
        Instant approvedAt,
        String workflowInstanceId,
        String auditReferenceId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IntegrityAssessment {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityAssessment id must not be blank.");
        }
        // HRA-051 required: assessmentNumber
        if (assessmentNumber == null || assessmentNumber.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityAssessment assessment number must not be blank.");
        }
        // HRA-051 required: assessmentTypeId
        if (assessmentTypeId == null || assessmentTypeId.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityAssessment assessment type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrityValueException("IntegrityAssessment status must not be null.");
        }
        // HRA-051 required: assessmentDate
        if (assessmentDate == null) {
            throw new InvalidIntegrityValueException("IntegrityAssessment assessment date must not be null.");
        }

        id = normalize(id);
        programId = normalize(programId);
        assessmentNumber = normalize(assessmentNumber);
        title = normalize(title);
        description = normalize(description);
        assessmentTypeId = normalize(assessmentTypeId);
        methodologyId = normalize(methodologyId);
        assessedByActorId = normalize(assessedByActorId);
        reviewedByActorId = normalize(reviewedByActorId);
        approvedByActorId = normalize(approvedByActorId);
        workflowInstanceId = normalize(workflowInstanceId);
        auditReferenceId = normalize(auditReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
