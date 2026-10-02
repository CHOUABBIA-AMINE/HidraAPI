/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskAssessment.
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
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for RiskAssessment.
     */
    @Entity
    @Table(name = "hidra_risk_assessment")
    public class RiskAssessmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_register_id", nullable = false, length = 80)
    private String riskRegisterId;

    @Column(name = "assessment_number", nullable = false, length = 80)
    private String assessmentNumber;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "assessment_type_id", nullable = false, length = 80)
    private String assessmentTypeId;

    @Column(name = "methodology_id", nullable = false, length = 80)
    private String methodologyId;

    @Column(name = "scope_id", nullable = true, length = 80)
    private String scopeId;

    @Column(name = "risk_scenario_id", nullable = false, length = 80)
    private String riskScenarioId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private RiskAssessmentStatus status;

    @Column(name = "assessment_date", nullable = false)
    private Instant assessmentDate;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "assessed_by_actor_id", nullable = true, length = 80)
    private String assessedByActorId;

    @Column(name = "assessed_by_display_name_snapshot", nullable = true, length = 255)
    private String assessedByDisplayNameSnapshot;

    @Column(name = "reviewed_by_actor_id", nullable = true, length = 80)
    private String reviewedByActorId;

    @Column(name = "reviewed_by_display_name_snapshot", nullable = true, length = 255)
    private String reviewedByDisplayNameSnapshot;

    @Column(name = "approved_by_actor_id", nullable = true, length = 80)
    private String approvedByActorId;

    @Column(name = "approved_by_display_name_snapshot", nullable = true, length = 255)
    private String approvedByDisplayNameSnapshot;

    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;

    @Column(name = "inherent_likelihood_id", nullable = true, length = 80)
    private String inherentLikelihoodId;

    @Column(name = "inherent_consequence_id", nullable = true, length = 80)
    private String inherentConsequenceId;

    @Column(name = "inherent_score", nullable = true, precision = 18, scale = 6)
    private BigDecimal inherentScore;

    @Column(name = "inherent_rating_id", nullable = true, length = 80)
    private String inherentRatingId;

    @Column(name = "residual_likelihood_id", nullable = true, length = 80)
    private String residualLikelihoodId;

    @Column(name = "residual_consequence_id", nullable = true, length = 80)
    private String residualConsequenceId;

    @Column(name = "residual_score", nullable = true, precision = 18, scale = 6)
    private BigDecimal residualScore;

    @Column(name = "residual_rating_id", nullable = true, length = 80)
    private String residualRatingId;

    @Column(name = "confidence_level_id", nullable = true, length = 80)
    private String confidenceLevelId;

    @Column(name = "uncertainty_note", nullable = true, columnDefinition = "text")
    private String uncertaintyNote;

    @Column(name = "workflow_reference_id", nullable = true, length = 80)
    private String workflowReferenceId;

    @Column(name = "audit_reference_id", nullable = true, length = 80)
    private String auditReferenceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskAssessmentJpaEntity() {
            // Required by JPA.
        }

        public RiskAssessmentJpaEntity(
                String id,
            String riskRegisterId,
            String assessmentNumber,
            String title,
            String description,
            String assessmentTypeId,
            String methodologyId,
            String scopeId,
            String riskScenarioId,
            RiskAssessmentStatus status,
            Instant assessmentDate,
            Instant validFrom,
            Instant validTo,
            String assessedByActorId,
            String assessedByDisplayNameSnapshot,
            String reviewedByActorId,
            String reviewedByDisplayNameSnapshot,
            String approvedByActorId,
            String approvedByDisplayNameSnapshot,
            Instant approvedAt,
            String inherentLikelihoodId,
            String inherentConsequenceId,
            BigDecimal inherentScore,
            String inherentRatingId,
            String residualLikelihoodId,
            String residualConsequenceId,
            BigDecimal residualScore,
            String residualRatingId,
            String confidenceLevelId,
            String uncertaintyNote,
            String workflowReferenceId,
            String auditReferenceId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.riskRegisterId = riskRegisterId;
        this.assessmentNumber = assessmentNumber;
        this.title = title;
        this.description = description;
        this.assessmentTypeId = assessmentTypeId;
        this.methodologyId = methodologyId;
        this.scopeId = scopeId;
        this.riskScenarioId = riskScenarioId;
        this.status = status;
        this.assessmentDate = assessmentDate;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.assessedByActorId = assessedByActorId;
        this.assessedByDisplayNameSnapshot = assessedByDisplayNameSnapshot;
        this.reviewedByActorId = reviewedByActorId;
        this.reviewedByDisplayNameSnapshot = reviewedByDisplayNameSnapshot;
        this.approvedByActorId = approvedByActorId;
        this.approvedByDisplayNameSnapshot = approvedByDisplayNameSnapshot;
        this.approvedAt = approvedAt;
        this.inherentLikelihoodId = inherentLikelihoodId;
        this.inherentConsequenceId = inherentConsequenceId;
        this.inherentScore = inherentScore;
        this.inherentRatingId = inherentRatingId;
        this.residualLikelihoodId = residualLikelihoodId;
        this.residualConsequenceId = residualConsequenceId;
        this.residualScore = residualScore;
        this.residualRatingId = residualRatingId;
        this.confidenceLevelId = confidenceLevelId;
        this.uncertaintyNote = uncertaintyNote;
        this.workflowReferenceId = workflowReferenceId;
        this.auditReferenceId = auditReferenceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String riskRegisterId() {
        return riskRegisterId;
    }


    public String assessmentNumber() {
        return assessmentNumber;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String assessmentTypeId() {
        return assessmentTypeId;
    }


    public String methodologyId() {
        return methodologyId;
    }


    public String scopeId() {
        return scopeId;
    }


    public String riskScenarioId() {
        return riskScenarioId;
    }


    public RiskAssessmentStatus status() {
        return status;
    }


    public Instant assessmentDate() {
        return assessmentDate;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public String assessedByActorId() {
        return assessedByActorId;
    }


    public String assessedByDisplayNameSnapshot() {
        return assessedByDisplayNameSnapshot;
    }


    public String reviewedByActorId() {
        return reviewedByActorId;
    }


    public String reviewedByDisplayNameSnapshot() {
        return reviewedByDisplayNameSnapshot;
    }


    public String approvedByActorId() {
        return approvedByActorId;
    }


    public String approvedByDisplayNameSnapshot() {
        return approvedByDisplayNameSnapshot;
    }


    public Instant approvedAt() {
        return approvedAt;
    }


    public String inherentLikelihoodId() {
        return inherentLikelihoodId;
    }


    public String inherentConsequenceId() {
        return inherentConsequenceId;
    }


    public BigDecimal inherentScore() {
        return inherentScore;
    }


    public String inherentRatingId() {
        return inherentRatingId;
    }


    public String residualLikelihoodId() {
        return residualLikelihoodId;
    }


    public String residualConsequenceId() {
        return residualConsequenceId;
    }


    public BigDecimal residualScore() {
        return residualScore;
    }


    public String residualRatingId() {
        return residualRatingId;
    }


    public String confidenceLevelId() {
        return confidenceLevelId;
    }


    public String uncertaintyNote() {
        return uncertaintyNote;
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
