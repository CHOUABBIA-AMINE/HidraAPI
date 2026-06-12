/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskMatrixCellJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskMatrixCell.
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
     * Database-backed JPA entity for RiskMatrixCell.
     */
    @Entity
    @Table(name = "hidra_risk_matrix_cell")
    public class RiskMatrixCellJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

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

    @Column(name = "color_code", nullable = true, length = 40)
    private String colorCode;

    @Column(name = "requires_treatment", nullable = false)
    private boolean requiresTreatment;

    @Column(name = "requires_approval", nullable = false)
    private boolean requiresApproval;

    @Column(name = "requires_executive_acceptance", nullable = false)
    private boolean requiresExecutiveAcceptance;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskMatrixCellJpaEntity() {
            // Required by JPA.
        }

        public RiskMatrixCellJpaEntity(
                String id,
            String riskMatrixId,
            String likelihoodLevelId,
            String consequenceLevelId,
            BigDecimal scoreValue,
            String ratingId,
            String colorCode,
            boolean requiresTreatment,
            boolean requiresApproval,
            boolean requiresExecutiveAcceptance,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.riskMatrixId = riskMatrixId;
        this.likelihoodLevelId = likelihoodLevelId;
        this.consequenceLevelId = consequenceLevelId;
        this.scoreValue = scoreValue;
        this.ratingId = ratingId;
        this.colorCode = colorCode;
        this.requiresTreatment = requiresTreatment;
        this.requiresApproval = requiresApproval;
        this.requiresExecutiveAcceptance = requiresExecutiveAcceptance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
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


    public String colorCode() {
        return colorCode;
    }


    public boolean requiresTreatment() {
        return requiresTreatment;
    }


    public boolean requiresApproval() {
        return requiresApproval;
    }


    public boolean requiresExecutiveAcceptance() {
        return requiresExecutiveAcceptance;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
