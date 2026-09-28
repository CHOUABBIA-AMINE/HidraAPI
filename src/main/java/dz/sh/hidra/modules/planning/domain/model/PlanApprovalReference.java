/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanApprovalReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Planning-side reference to workflow approval state.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.value.*;
import java.time.Instant;

    /**
     * Planning-side reference to workflow approval state.
     *
         * @param id id
     * @param revisionId revisionId
     * @param workflowInstanceId workflowInstanceId
     * @param workflowDefinitionCodeSnapshot workflowDefinitionCodeSnapshot
     * @param approvalStatusSnapshot approvalStatusSnapshot
     * @param submittedByActorId submittedByActorId
     * @param submittedAt submittedAt
     * @param decidedByActorId decidedByActorId
     * @param decidedAt decidedAt
     * @param decisionReasonSnapshot decisionReasonSnapshot
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PlanApprovalReference(
            String id,
        String revisionId,
        String workflowInstanceId,
        String workflowDefinitionCodeSnapshot,
        ApprovalStatusSnapshot approvalStatusSnapshot,
        String submittedByActorId,
        Instant submittedAt,
        String decidedByActorId,
        Instant decidedAt,
        String decisionReasonSnapshot,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PlanApprovalReference {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("PlanApprovalReference id must not be blank.");
        }
        // HRA-051 required: revisionId
        if (revisionId == null || revisionId.isBlank()) {
            throw new InvalidPlanningValueException("PlanApprovalReference revision id must not be blank.");
        }
        // HRA-051 required: workflowInstanceId
        if (workflowInstanceId == null || workflowInstanceId.isBlank()) {
            throw new InvalidPlanningValueException("PlanApprovalReference workflow instance id must not be blank.");
        }
        // HRA-051 required: approvalStatusSnapshot
        if (approvalStatusSnapshot == null) {
            throw new InvalidPlanningValueException("PlanApprovalReference approval status snapshot must not be null.");
        }

        id = normalize(id);
        revisionId = normalize(revisionId);
        workflowInstanceId = normalize(workflowInstanceId);
        workflowDefinitionCodeSnapshot = normalize(workflowDefinitionCodeSnapshot);
        submittedByActorId = normalize(submittedByActorId);
        decidedByActorId = normalize(decidedByActorId);
        decisionReasonSnapshot = normalize(decisionReasonSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
