/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanApprovalReferenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PlanApprovalReference.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import dz.sh.hidra.modules.planning.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PlanApprovalReference.
     */
    @Entity
    @Table(name = "hidra_planning_plan_approval_reference")
    public class PlanApprovalReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "revision_id", nullable = false, length = 80)
    private String revisionId;

    @Column(name = "workflow_instance_id", nullable = false, length = 80)
    private String workflowInstanceId;

    @Column(name = "workflow_definition_code_snapshot", nullable = true, length = 160)
    private String workflowDefinitionCodeSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status_snapshot", nullable = false, length = 40)
    private ApprovalStatusSnapshot approvalStatusSnapshot;

    @Column(name = "submitted_by_actor_id", nullable = true, length = 80)
    private String submittedByActorId;

    @Column(name = "submitted_at", nullable = true)
    private Instant submittedAt;

    @Column(name = "decided_by_actor_id", nullable = true, length = 80)
    private String decidedByActorId;

    @Column(name = "decided_at", nullable = true)
    private Instant decidedAt;

    @Column(name = "decision_reason_snapshot", nullable = true, length = 500)
    private String decisionReasonSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PlanApprovalReferenceJpaEntity() {
            // Required by JPA.
        }

        public PlanApprovalReferenceJpaEntity(
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
            this.id = id;
        this.revisionId = revisionId;
        this.workflowInstanceId = workflowInstanceId;
        this.workflowDefinitionCodeSnapshot = workflowDefinitionCodeSnapshot;
        this.approvalStatusSnapshot = approvalStatusSnapshot;
        this.submittedByActorId = submittedByActorId;
        this.submittedAt = submittedAt;
        this.decidedByActorId = decidedByActorId;
        this.decidedAt = decidedAt;
        this.decisionReasonSnapshot = decisionReasonSnapshot;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String revisionId() {
        return revisionId;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public String workflowDefinitionCodeSnapshot() {
        return workflowDefinitionCodeSnapshot;
    }


    public ApprovalStatusSnapshot approvalStatusSnapshot() {
        return approvalStatusSnapshot;
    }


    public String submittedByActorId() {
        return submittedByActorId;
    }


    public Instant submittedAt() {
        return submittedAt;
    }


    public String decidedByActorId() {
        return decidedByActorId;
    }


    public Instant decidedAt() {
        return decidedAt;
    }


    public String decisionReasonSnapshot() {
        return decisionReasonSnapshot;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
