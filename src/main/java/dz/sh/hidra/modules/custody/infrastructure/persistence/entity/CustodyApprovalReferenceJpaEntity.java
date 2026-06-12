/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyApprovalReferenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyApprovalReference.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.entity;

import dz.sh.hidra.modules.custody.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for CustodyApprovalReference.
     */
    @Entity
    @Table(name = "hidra_custody_approval_reference")
    public class CustodyApprovalReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "target_type", nullable = false, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = false, length = 80)
    private String targetId;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "workflow_task_id", nullable = true, length = 80)
    private String workflowTaskId;

    @Column(name = "approved_by_actor_id", nullable = true, length = 80)
    private String approvedByActorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false, length = 40)
    private CustodyApprovalStatus approvalStatus;

    @Column(name = "approval_comment", nullable = true, columnDefinition = "text")
    private String approvalComment;

    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected CustodyApprovalReferenceJpaEntity() {
            // Required by JPA.
        }

        public CustodyApprovalReferenceJpaEntity(
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
            this.id = id;
        this.targetType = targetType;
        this.targetId = targetId;
        this.workflowInstanceId = workflowInstanceId;
        this.workflowTaskId = workflowTaskId;
        this.approvedByActorId = approvedByActorId;
        this.approvalStatus = approvalStatus;
        this.approvalComment = approvalComment;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public String workflowTaskId() {
        return workflowTaskId;
    }


    public String approvedByActorId() {
        return approvedByActorId;
    }


    public CustodyApprovalStatus approvalStatus() {
        return approvalStatus;
    }


    public String approvalComment() {
        return approvalComment;
    }


    public Instant approvedAt() {
        return approvedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
