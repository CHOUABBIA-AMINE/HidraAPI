/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResidualRiskAssessmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ResidualRiskAssessment.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for ResidualRiskAssessment.
     */
    @Entity
    @Table(name = "hidra_residual_risk_assessment")
    public class ResidualRiskAssessmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_assessment_id", nullable = false, length = 80)
    private String riskAssessmentId;

    @Column(name = "treatment_plan_id", nullable = true, length = 80)
    private String treatmentPlanId;

    @Column(name = "reassessment_date", nullable = false)
    private Instant reassessmentDate;

    @Column(name = "residual_likelihood_id", nullable = false, length = 80)
    private String residualLikelihoodId;

    @Column(name = "residual_consequence_id", nullable = false, length = 80)
    private String residualConsequenceId;

    @Column(name = "residual_score", nullable = false, precision = 18, scale = 6)
    private BigDecimal residualScore;

    @Column(name = "residual_rating_id", nullable = false, length = 80)
    private String residualRatingId;

    @Column(name = "residual_confidence_level_id", nullable = true, length = 80)
    private String residualConfidenceLevelId;

    @Column(name = "residual_justification", nullable = true, columnDefinition = "text")
    private String residualJustification;

    @Column(name = "assessed_by_actor_id", nullable = false, length = 80)
    private String assessedByActorId;

    @Column(name = "approved_by_actor_id", nullable = true, length = 80)
    private String approvedByActorId;

    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ResidualRiskAssessmentJpaEntity() {
            // Required by JPA.
        }

        public ResidualRiskAssessmentJpaEntity(
                String id,
            String riskAssessmentId,
            String treatmentPlanId,
            Instant reassessmentDate,
            String residualLikelihoodId,
            String residualConsequenceId,
            BigDecimal residualScore,
            String residualRatingId,
            String residualConfidenceLevelId,
            String residualJustification,
            String assessedByActorId,
            String approvedByActorId,
            Instant approvedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.riskAssessmentId = riskAssessmentId;
        this.treatmentPlanId = treatmentPlanId;
        this.reassessmentDate = reassessmentDate;
        this.residualLikelihoodId = residualLikelihoodId;
        this.residualConsequenceId = residualConsequenceId;
        this.residualScore = residualScore;
        this.residualRatingId = residualRatingId;
        this.residualConfidenceLevelId = residualConfidenceLevelId;
        this.residualJustification = residualJustification;
        this.assessedByActorId = assessedByActorId;
        this.approvedByActorId = approvedByActorId;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String riskAssessmentId() {
        return riskAssessmentId;
    }


    public String treatmentPlanId() {
        return treatmentPlanId;
    }


    public Instant reassessmentDate() {
        return reassessmentDate;
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


    public String residualConfidenceLevelId() {
        return residualConfidenceLevelId;
    }


    public String residualJustification() {
        return residualJustification;
    }


    public String assessedByActorId() {
        return assessedByActorId;
    }


    public String approvedByActorId() {
        return approvedByActorId;
    }


    public Instant approvedAt() {
        return approvedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
