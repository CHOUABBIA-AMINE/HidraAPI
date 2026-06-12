/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
