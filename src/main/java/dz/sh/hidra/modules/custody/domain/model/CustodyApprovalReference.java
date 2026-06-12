/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyApprovalReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Workflow approval reference.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.value.*;
import java.time.Instant;

    /**
     * Workflow approval reference.
     *
         * @param id id
     * @param targetType targetType
     * @param targetId targetId
     * @param workflowInstanceId workflowInstanceId
     * @param workflowTaskId workflowTaskId
     * @param approvedByActorId approvedByActorId
     * @param approvalStatus approvalStatus
     * @param approvalComment approvalComment
     * @param approvedAt approvedAt
     * @param createdAt createdAt
     */
    public record CustodyApprovalReference(
            String id,
        String targetType,
        String targetId,
        String workflowInstanceId,
        String workflowTaskId,
        String approvedByActorId,
        CustodyApprovalStatus approvalStatus,
        String approvalComment,
        Instant approvedAt,
        Instant createdAt
    ) {

        public CustodyApprovalReference {
        id = normalize(id);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        workflowInstanceId = normalize(workflowInstanceId);
        workflowTaskId = normalize(workflowTaskId);
        approvedByActorId = normalize(approvedByActorId);
        approvalComment = normalize(approvalComment);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
