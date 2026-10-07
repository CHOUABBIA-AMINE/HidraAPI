/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanRevision
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Version of an operational plan.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.value.*;
import java.time.Instant;

    /**
     * Version of an operational plan.
     *
         * @param id id
     * @param planId planId
     * @param revisionNumber revisionNumber
     * @param revisionCode revisionCode
     * @param status status
     * @param changeReasonCodeId changeReasonCodeId
     * @param changeReasonText changeReasonText
     * @param baseRevisionId baseRevisionId
     * @param submittedByActorId submittedByActorId
     * @param submittedAt submittedAt
     * @param approvedByActorId approvedByActorId
     * @param approvedAt approvedAt
     * @param workflowInstanceId workflowInstanceId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PlanRevision(
            String id,
        String planId,
        int revisionNumber,
        String revisionCode,
        PlanRevisionStatus status,
        String changeReasonCodeId,
        String changeReasonText,
        String baseRevisionId,
        String submittedByActorId,
        Instant submittedAt,
        String approvedByActorId,
        Instant approvedAt,
        String workflowInstanceId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PlanRevision {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("PlanRevision id must not be blank.");
        }
        // HRA-051 required: planId
        if (planId == null || planId.isBlank()) {
            throw new InvalidPlanningValueException("PlanRevision plan id must not be blank.");
        }
        // HRA-051 required: revisionCode
        if (revisionCode == null || revisionCode.isBlank()) {
            throw new InvalidPlanningValueException("PlanRevision revision code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPlanningValueException("PlanRevision status must not be null.");
        }
        // HRA-051 self-reference: baseRevisionId != id
        if (id != null && baseRevisionId != null && baseRevisionId.equals(id)) {
            throw new InvalidPlanningValueException("PlanRevision base revision id must not reference itself.");
        }

        if (revisionNumber <= 0) {
            throw new InvalidPlanningValueException("Revision number must be positive.");
        }
        id = normalize(id);
        planId = normalize(planId);
        revisionCode = normalize(revisionCode);
        changeReasonCodeId = normalize(changeReasonCodeId);
        changeReasonText = normalize(changeReasonText);
        baseRevisionId = normalize(baseRevisionId);
        if (id.equals(baseRevisionId)) {
            throw new InvalidPlanningValueException("Revision cannot be its own base.");
        }
        submittedByActorId = normalize(submittedByActorId);
        approvedByActorId = normalize(approvedByActorId);
        workflowInstanceId = normalize(workflowInstanceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
