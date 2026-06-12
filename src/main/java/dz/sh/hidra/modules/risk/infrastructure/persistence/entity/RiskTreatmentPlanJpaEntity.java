/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskTreatmentPlanJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskTreatmentPlan.
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
     * Database-backed JPA entity for RiskTreatmentPlan.
     */
    @Entity
    @Table(name = "hidra_risk_treatment_plan")
    public class RiskTreatmentPlanJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_assessment_id", nullable = false, length = 80)
    private String riskAssessmentId;

    @Column(name = "treatment_strategy_id", nullable = false, length = 80)
    private String treatmentStrategyId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "owner_organization_unit_id", nullable = true, length = 80)
    private String ownerOrganizationUnitId;

    @Column(name = "owner_organization_unit_name_snapshot", nullable = true, length = 500)
    private String ownerOrganizationUnitNameSnapshot;

    @Column(name = "owner_actor_id", nullable = true, length = 80)
    private String ownerActorId;

    @Column(name = "owner_display_name_snapshot", nullable = true, length = 255)
    private String ownerDisplayNameSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private RiskTreatmentStatus status;

    @Column(name = "target_residual_rating_id", nullable = true, length = 80)
    private String targetResidualRatingId;

    @Column(name = "target_completion_date", nullable = true)
    private Instant targetCompletionDate;

    @Column(name = "workflow_reference_id", nullable = true, length = 80)
    private String workflowReferenceId;

    @Column(name = "audit_reference_id", nullable = true, length = 80)
    private String auditReferenceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskTreatmentPlanJpaEntity() {
            // Required by JPA.
        }

        public RiskTreatmentPlanJpaEntity(
                String id,
            String riskAssessmentId,
            String treatmentStrategyId,
            String title,
            String description,
            String ownerOrganizationUnitId,
            String ownerOrganizationUnitNameSnapshot,
            String ownerActorId,
            String ownerDisplayNameSnapshot,
            RiskTreatmentStatus status,
            String targetResidualRatingId,
            Instant targetCompletionDate,
            String workflowReferenceId,
            String auditReferenceId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.riskAssessmentId = riskAssessmentId;
        this.treatmentStrategyId = treatmentStrategyId;
        this.title = title;
        this.description = description;
        this.ownerOrganizationUnitId = ownerOrganizationUnitId;
        this.ownerOrganizationUnitNameSnapshot = ownerOrganizationUnitNameSnapshot;
        this.ownerActorId = ownerActorId;
        this.ownerDisplayNameSnapshot = ownerDisplayNameSnapshot;
        this.status = status;
        this.targetResidualRatingId = targetResidualRatingId;
        this.targetCompletionDate = targetCompletionDate;
        this.workflowReferenceId = workflowReferenceId;
        this.auditReferenceId = auditReferenceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String riskAssessmentId() {
        return riskAssessmentId;
    }


    public String treatmentStrategyId() {
        return treatmentStrategyId;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String ownerOrganizationUnitId() {
        return ownerOrganizationUnitId;
    }


    public String ownerOrganizationUnitNameSnapshot() {
        return ownerOrganizationUnitNameSnapshot;
    }


    public String ownerActorId() {
        return ownerActorId;
    }


    public String ownerDisplayNameSnapshot() {
        return ownerDisplayNameSnapshot;
    }


    public RiskTreatmentStatus status() {
        return status;
    }


    public String targetResidualRatingId() {
        return targetResidualRatingId;
    }


    public Instant targetCompletionDate() {
        return targetCompletionDate;
    }


    public String workflowReferenceId() {
        return workflowReferenceId;
    }


    public String auditReferenceId() {
        return auditReferenceId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
