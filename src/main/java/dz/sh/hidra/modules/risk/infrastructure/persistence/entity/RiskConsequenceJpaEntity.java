/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskConsequenceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskConsequence.
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
     * Database-backed JPA entity for RiskConsequence.
     */
    @Entity
    @Table(name = "hidra_risk_consequence")
    public class RiskConsequenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_assessment_id", nullable = false, length = 80)
    private String riskAssessmentId;

    @Column(name = "category_id", nullable = false, length = 80)
    private String categoryId;

    @Column(name = "consequence_level_id", nullable = false, length = 80)
    private String consequenceLevelId;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "people_impact_level_id", nullable = true, length = 80)
    private String peopleImpactLevelId;

    @Column(name = "environment_impact_level_id", nullable = true, length = 80)
    private String environmentImpactLevelId;

    @Column(name = "production_impact_level_id", nullable = true, length = 80)
    private String productionImpactLevelId;

    @Column(name = "asset_impact_level_id", nullable = true, length = 80)
    private String assetImpactLevelId;

    @Column(name = "financial_impact_level_id", nullable = true, length = 80)
    private String financialImpactLevelId;

    @Column(name = "reputation_impact_level_id", nullable = true, length = 80)
    private String reputationImpactLevelId;

    @Column(name = "compliance_impact_level_id", nullable = true, length = 80)
    private String complianceImpactLevelId;

    @Column(name = "estimated_cost", nullable = true, precision = 18, scale = 6)
    private BigDecimal estimatedCost;

    @Column(name = "currency_code", nullable = true, length = 3)
    private String currencyCode;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskConsequenceJpaEntity() {
            // Required by JPA.
        }

        public RiskConsequenceJpaEntity(
                String id,
            String riskAssessmentId,
            String categoryId,
            String consequenceLevelId,
            String description,
            String peopleImpactLevelId,
            String environmentImpactLevelId,
            String productionImpactLevelId,
            String assetImpactLevelId,
            String financialImpactLevelId,
            String reputationImpactLevelId,
            String complianceImpactLevelId,
            BigDecimal estimatedCost,
            String currencyCode,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.riskAssessmentId = riskAssessmentId;
        this.categoryId = categoryId;
        this.consequenceLevelId = consequenceLevelId;
        this.description = description;
        this.peopleImpactLevelId = peopleImpactLevelId;
        this.environmentImpactLevelId = environmentImpactLevelId;
        this.productionImpactLevelId = productionImpactLevelId;
        this.assetImpactLevelId = assetImpactLevelId;
        this.financialImpactLevelId = financialImpactLevelId;
        this.reputationImpactLevelId = reputationImpactLevelId;
        this.complianceImpactLevelId = complianceImpactLevelId;
        this.estimatedCost = estimatedCost;
        this.currencyCode = currencyCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String riskAssessmentId() {
        return riskAssessmentId;
    }


    public String categoryId() {
        return categoryId;
    }


    public String consequenceLevelId() {
        return consequenceLevelId;
    }


    public String description() {
        return description;
    }


    public String peopleImpactLevelId() {
        return peopleImpactLevelId;
    }


    public String environmentImpactLevelId() {
        return environmentImpactLevelId;
    }


    public String productionImpactLevelId() {
        return productionImpactLevelId;
    }


    public String assetImpactLevelId() {
        return assetImpactLevelId;
    }


    public String financialImpactLevelId() {
        return financialImpactLevelId;
    }


    public String reputationImpactLevelId() {
        return reputationImpactLevelId;
    }


    public String complianceImpactLevelId() {
        return complianceImpactLevelId;
    }


    public BigDecimal estimatedCost() {
        return estimatedCost;
    }


    public String currencyCode() {
        return currencyCode;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
