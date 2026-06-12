/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskScoreJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskScore.
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
     * Database-backed JPA entity for RiskScore.
     */
    @Entity
    @Table(name = "hidra_risk_score")
    public class RiskScoreJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_assessment_id", nullable = false, length = 80)
    private String riskAssessmentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "score_type", nullable = false, length = 40)
    private RiskScoreType scoreType;

    @Column(name = "risk_matrix_id", nullable = false, length = 80)
    private String riskMatrixId;

    @Column(name = "likelihood_level_id", nullable = false, length = 80)
    private String likelihoodLevelId;

    @Column(name = "consequence_level_id", nullable = false, length = 80)
    private String consequenceLevelId;

    @Column(name = "score_value", nullable = false, precision = 18, scale = 6)
    private BigDecimal scoreValue;

    @Column(name = "rating_id", nullable = false, length = 80)
    private String ratingId;

    @Column(name = "rating_label_snapshot", nullable = true, length = 160)
    private String ratingLabelSnapshot;

    @Column(name = "calculated_at", nullable = false)
    private Instant calculatedAt;

    @Column(name = "calculation_method", nullable = true, length = 160)
    private String calculationMethod;

    @Column(name = "explanation", nullable = true, columnDefinition = "text")
    private String explanation;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected RiskScoreJpaEntity() {
            // Required by JPA.
        }

        public RiskScoreJpaEntity(
                String id,
            String riskAssessmentId,
            RiskScoreType scoreType,
            String riskMatrixId,
            String likelihoodLevelId,
            String consequenceLevelId,
            BigDecimal scoreValue,
            String ratingId,
            String ratingLabelSnapshot,
            Instant calculatedAt,
            String calculationMethod,
            String explanation,
            Instant createdAt
        ) {
            this.id = id;
        this.riskAssessmentId = riskAssessmentId;
        this.scoreType = scoreType;
        this.riskMatrixId = riskMatrixId;
        this.likelihoodLevelId = likelihoodLevelId;
        this.consequenceLevelId = consequenceLevelId;
        this.scoreValue = scoreValue;
        this.ratingId = ratingId;
        this.ratingLabelSnapshot = ratingLabelSnapshot;
        this.calculatedAt = calculatedAt;
        this.calculationMethod = calculationMethod;
        this.explanation = explanation;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String riskAssessmentId() {
        return riskAssessmentId;
    }


    public RiskScoreType scoreType() {
        return scoreType;
    }


    public String riskMatrixId() {
        return riskMatrixId;
    }


    public String likelihoodLevelId() {
        return likelihoodLevelId;
    }


    public String consequenceLevelId() {
        return consequenceLevelId;
    }


    public BigDecimal scoreValue() {
        return scoreValue;
    }


    public String ratingId() {
        return ratingId;
    }


    public String ratingLabelSnapshot() {
        return ratingLabelSnapshot;
    }


    public Instant calculatedAt() {
        return calculatedAt;
    }


    public String calculationMethod() {
        return calculationMethod;
    }


    public String explanation() {
        return explanation;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
