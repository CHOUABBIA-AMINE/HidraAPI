/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanRevision
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
        id = normalize(id);
        planId = normalize(planId);
        revisionCode = normalize(revisionCode);
        changeReasonCodeId = normalize(changeReasonCodeId);
        changeReasonText = normalize(changeReasonText);
        baseRevisionId = normalize(baseRevisionId);
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
