/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskLikelihoodJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskLikelihood.
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
     * Database-backed JPA entity for RiskLikelihood.
     */
    @Entity
    @Table(name = "hidra_risk_likelihood")
    public class RiskLikelihoodJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_assessment_id", nullable = false, length = 80)
    private String riskAssessmentId;

    @Column(name = "likelihood_level_id", nullable = false, length = 80)
    private String likelihoodLevelId;

    @Column(name = "probability_value", nullable = true, precision = 10, scale = 6)
    private BigDecimal probabilityValue;

    @Column(name = "frequency_estimate", nullable = true, precision = 18, scale = 6)
    private BigDecimal frequencyEstimate;

    @Column(name = "frequency_unit_id", nullable = true, length = 80)
    private String frequencyUnitId;

    @Column(name = "likelihood_basis_id", nullable = true, length = 80)
    private String likelihoodBasisId;

    @Column(name = "confidence_level_id", nullable = true, length = 80)
    private String confidenceLevelId;

    @Column(name = "evidence_summary", nullable = true, columnDefinition = "text")
    private String evidenceSummary;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskLikelihoodJpaEntity() {
            // Required by JPA.
        }

        public RiskLikelihoodJpaEntity(
                String id,
            String riskAssessmentId,
            String likelihoodLevelId,
            BigDecimal probabilityValue,
            BigDecimal frequencyEstimate,
            String frequencyUnitId,
            String likelihoodBasisId,
            String confidenceLevelId,
            String evidenceSummary,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.riskAssessmentId = riskAssessmentId;
        this.likelihoodLevelId = likelihoodLevelId;
        this.probabilityValue = probabilityValue;
        this.frequencyEstimate = frequencyEstimate;
        this.frequencyUnitId = frequencyUnitId;
        this.likelihoodBasisId = likelihoodBasisId;
        this.confidenceLevelId = confidenceLevelId;
        this.evidenceSummary = evidenceSummary;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String riskAssessmentId() {
        return riskAssessmentId;
    }


    public String likelihoodLevelId() {
        return likelihoodLevelId;
    }


    public BigDecimal probabilityValue() {
        return probabilityValue;
    }


    public BigDecimal frequencyEstimate() {
        return frequencyEstimate;
    }


    public String frequencyUnitId() {
        return frequencyUnitId;
    }


    public String likelihoodBasisId() {
        return likelihoodBasisId;
    }


    public String confidenceLevelId() {
        return confidenceLevelId;
    }


    public String evidenceSummary() {
        return evidenceSummary;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
