/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentReviewReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.model
 *
 * @Description : Workflow review and approval reference.
 *
 */
package dz.sh.hidra.modules.documents.domain.model;

import dz.sh.hidra.modules.documents.domain.value.*;
import java.time.Instant;

    /**
     * Workflow review and approval reference.
     *
         * @param id id
     * @param documentId documentId
     * @param documentVersionId documentVersionId
     * @param workflowInstanceId workflowInstanceId
     * @param reviewTypeId reviewTypeId
     * @param reviewStatus reviewStatus
     * @param requestedByActorId requestedByActorId
     * @param requestedAt requestedAt
     * @param completedAt completedAt
     * @param decisionReasonId decisionReasonId
     * @param decisionComment decisionComment
     */
    public record DocumentReviewReference(
            String id,
        String documentId,
        String documentVersionId,
        String workflowInstanceId,
        String reviewTypeId,
        DocumentReviewStatus reviewStatus,
        String requestedByActorId,
        Instant requestedAt,
        Instant completedAt,
        String decisionReasonId,
        String decisionComment
    ) {

        public DocumentReviewReference {
        id = normalize(id);
        documentId = normalize(documentId);
        documentVersionId = normalize(documentVersionId);
        workflowInstanceId = normalize(workflowInstanceId);
        reviewTypeId = normalize(reviewTypeId);
        requestedByActorId = normalize(requestedByActorId);
        decisionReasonId = normalize(decisionReasonId);
        decisionComment = normalize(decisionComment);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
