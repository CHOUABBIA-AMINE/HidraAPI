/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyApprovalReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyApprovalReference id must not be blank.");
        }
        // HRA-051 required: targetId
        if (targetId == null || targetId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyApprovalReference target id must not be blank.");
        }
        // HRA-051 required: approvalStatus
        if (approvalStatus == null) {
            throw new InvalidCustodyValueException("CustodyApprovalReference approval status must not be null.");
        }

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
