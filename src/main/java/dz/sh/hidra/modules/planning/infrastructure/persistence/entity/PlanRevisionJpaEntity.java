/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanRevisionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PlanRevision.
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
     * Database-backed JPA entity for PlanRevision.
     */
    @Entity
    @Table(name = "hidra_planning_plan_revision")
    public class PlanRevisionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "plan_id", nullable = false, length = 80)
    private String planId;

    @Column(name = "revision_number", nullable = false)
    private int revisionNumber;

    @Column(name = "revision_code", nullable = false, length = 80)
    private String revisionCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PlanRevisionStatus status;

    @Column(name = "change_reason_code_id", nullable = true, length = 80)
    private String changeReasonCodeId;

    @Column(name = "change_reason_text", nullable = true, length = 500)
    private String changeReasonText;

    @Column(name = "base_revision_id", nullable = true, length = 80)
    private String baseRevisionId;

    @Column(name = "submitted_by_actor_id", nullable = true, length = 80)
    private String submittedByActorId;

    @Column(name = "submitted_at", nullable = true)
    private Instant submittedAt;

    @Column(name = "approved_by_actor_id", nullable = true, length = 80)
    private String approvedByActorId;

    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PlanRevisionJpaEntity() {
            // Required by JPA.
        }

        public PlanRevisionJpaEntity(
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
            this.id = id;
        this.planId = planId;
        this.revisionNumber = revisionNumber;
        this.revisionCode = revisionCode;
        this.status = status;
        this.changeReasonCodeId = changeReasonCodeId;
        this.changeReasonText = changeReasonText;
        this.baseRevisionId = baseRevisionId;
        this.submittedByActorId = submittedByActorId;
        this.submittedAt = submittedAt;
        this.approvedByActorId = approvedByActorId;
        this.approvedAt = approvedAt;
        this.workflowInstanceId = workflowInstanceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String planId() {
        return planId;
    }


    public int revisionNumber() {
        return revisionNumber;
    }


    public String revisionCode() {
        return revisionCode;
    }


    public PlanRevisionStatus status() {
        return status;
    }


    public String changeReasonCodeId() {
        return changeReasonCodeId;
    }


    public String changeReasonText() {
        return changeReasonText;
    }


    public String baseRevisionId() {
        return baseRevisionId;
    }


    public String submittedByActorId() {
        return submittedByActorId;
    }


    public Instant submittedAt() {
        return submittedAt;
    }


    public String approvedByActorId() {
        return approvedByActorId;
    }


    public Instant approvedAt() {
        return approvedAt;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
