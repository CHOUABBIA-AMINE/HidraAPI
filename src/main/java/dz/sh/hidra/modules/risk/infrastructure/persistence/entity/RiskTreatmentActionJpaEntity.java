/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskTreatmentActionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskTreatmentAction.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import dz.sh.hidra.modules.risk.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for RiskTreatmentAction.
     */
    @Entity
    @Table(name = "hidra_risk_treatment_action")
    public class RiskTreatmentActionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_treatment_plan_id", nullable = false, length = 80)
    private String riskTreatmentPlanId;

    @Column(name = "action_code", nullable = false, length = 80)
    private String actionCode;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "action_type_id", nullable = false, length = 80)
    private String actionTypeId;

    @Column(name = "owner_actor_id", nullable = true, length = 80)
    private String ownerActorId;

    @Column(name = "owner_display_name_snapshot", nullable = true, length = 255)
    private String ownerDisplayNameSnapshot;

    @Column(name = "owner_organization_unit_id", nullable = true, length = 80)
    private String ownerOrganizationUnitId;

    @Column(name = "owner_organization_unit_name_snapshot", nullable = true, length = 500)
    private String ownerOrganizationUnitNameSnapshot;

    @Column(name = "target_date", nullable = true)
    private Instant targetDate;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "verification_required", nullable = false)
    private boolean verificationRequired;

    @Column(name = "verified_by_actor_id", nullable = true, length = 80)
    private String verifiedByActorId;

    @Column(name = "verified_at", nullable = true)
    private Instant verifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private RiskTreatmentStatus status;

    @Column(name = "linked_work_order_id", nullable = true, length = 80)
    private String linkedWorkOrderId;

    @Column(name = "linked_workflow_task_id", nullable = true, length = 80)
    private String linkedWorkflowTaskId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskTreatmentActionJpaEntity() {
            // Required by JPA.
        }

        public RiskTreatmentActionJpaEntity(
                String id,
            String riskTreatmentPlanId,
            String actionCode,
            String title,
            String description,
            String actionTypeId,
            String ownerActorId,
            String ownerDisplayNameSnapshot,
            String ownerOrganizationUnitId,
            String ownerOrganizationUnitNameSnapshot,
            Instant targetDate,
            Instant completedAt,
            boolean verificationRequired,
            String verifiedByActorId,
            Instant verifiedAt,
            RiskTreatmentStatus status,
            String linkedWorkOrderId,
            String linkedWorkflowTaskId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.riskTreatmentPlanId = riskTreatmentPlanId;
        this.actionCode = actionCode;
        this.title = title;
        this.description = description;
        this.actionTypeId = actionTypeId;
        this.ownerActorId = ownerActorId;
        this.ownerDisplayNameSnapshot = ownerDisplayNameSnapshot;
        this.ownerOrganizationUnitId = ownerOrganizationUnitId;
        this.ownerOrganizationUnitNameSnapshot = ownerOrganizationUnitNameSnapshot;
        this.targetDate = targetDate;
        this.completedAt = completedAt;
        this.verificationRequired = verificationRequired;
        this.verifiedByActorId = verifiedByActorId;
        this.verifiedAt = verifiedAt;
        this.status = status;
        this.linkedWorkOrderId = linkedWorkOrderId;
        this.linkedWorkflowTaskId = linkedWorkflowTaskId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String riskTreatmentPlanId() {
        return riskTreatmentPlanId;
    }


    public String actionCode() {
        return actionCode;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String actionTypeId() {
        return actionTypeId;
    }


    public String ownerActorId() {
        return ownerActorId;
    }


    public String ownerDisplayNameSnapshot() {
        return ownerDisplayNameSnapshot;
    }


    public String ownerOrganizationUnitId() {
        return ownerOrganizationUnitId;
    }


    public String ownerOrganizationUnitNameSnapshot() {
        return ownerOrganizationUnitNameSnapshot;
    }


    public Instant targetDate() {
        return targetDate;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public boolean verificationRequired() {
        return verificationRequired;
    }


    public String verifiedByActorId() {
        return verifiedByActorId;
    }


    public Instant verifiedAt() {
        return verifiedAt;
    }


    public RiskTreatmentStatus status() {
        return status;
    }


    public String linkedWorkOrderId() {
        return linkedWorkOrderId;
    }


    public String linkedWorkflowTaskId() {
        return linkedWorkflowTaskId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
