/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentReviewReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.documents.domain.exception.InvalidDocumentValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidDocumentValueException("DocumentReviewReference id must not be blank.");
        }
        // HRA-051 required: documentId
        if (documentId == null || documentId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentReviewReference document id must not be blank.");
        }
        // HRA-051 required: workflowInstanceId
        if (workflowInstanceId == null || workflowInstanceId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentReviewReference workflow instance id must not be blank.");
        }
        // HRA-051 required: reviewTypeId
        if (reviewTypeId == null || reviewTypeId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentReviewReference review type id must not be blank.");
        }
        // HRA-051 required: reviewStatus
        if (reviewStatus == null) {
            throw new InvalidDocumentValueException("DocumentReviewReference review status must not be null.");
        }
        // HRA-051 required: requestedByActorId
        if (requestedByActorId == null || requestedByActorId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentReviewReference requested by actor id must not be blank.");
        }
        // HRA-051 required: requestedAt
        if (requestedAt == null) {
            throw new InvalidDocumentValueException("DocumentReviewReference requested at must not be null.");
        }

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
