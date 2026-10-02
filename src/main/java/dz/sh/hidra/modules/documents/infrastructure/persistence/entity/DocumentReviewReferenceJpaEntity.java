/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentReviewReferenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for DocumentReviewReference.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.entity;

import dz.sh.hidra.modules.documents.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for DocumentReviewReference.
     */
    @Entity
    @Table(name = "hidra_documents_review_reference")
    public class DocumentReviewReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "document_id", nullable = false, length = 80)
    private String documentId;

    @Column(name = "document_version_id", nullable = true, length = 80)
    private String documentVersionId;

    @Column(name = "workflow_instance_id", nullable = false, length = 80)
    private String workflowInstanceId;

    @Column(name = "review_type_id", nullable = false, length = 80)
    private String reviewTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_status", nullable = false, length = 40)
    private DocumentReviewStatus reviewStatus;

    @Column(name = "requested_by_actor_id", nullable = false, length = 80)
    private String requestedByActorId;

    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "decision_reason_id", nullable = true, length = 80)
    private String decisionReasonId;

    @Column(name = "decision_comment", nullable = true, length = 2000)
    private String decisionComment;

        protected DocumentReviewReferenceJpaEntity() {
            // Required by JPA.
        }

        public DocumentReviewReferenceJpaEntity(
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
            this.id = id;
        this.documentId = documentId;
        this.documentVersionId = documentVersionId;
        this.workflowInstanceId = workflowInstanceId;
        this.reviewTypeId = reviewTypeId;
        this.reviewStatus = reviewStatus;
        this.requestedByActorId = requestedByActorId;
        this.requestedAt = requestedAt;
        this.completedAt = completedAt;
        this.decisionReasonId = decisionReasonId;
        this.decisionComment = decisionComment;
        }


    public String id() {
        return id;
    }


    public String documentId() {
        return documentId;
    }


    public String documentVersionId() {
        return documentVersionId;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public String reviewTypeId() {
        return reviewTypeId;
    }


    public DocumentReviewStatus reviewStatus() {
        return reviewStatus;
    }


    public String requestedByActorId() {
        return requestedByActorId;
    }


    public Instant requestedAt() {
        return requestedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String decisionReasonId() {
        return decisionReasonId;
    }


    public String decisionComment() {
        return decisionComment;
    }

    }
