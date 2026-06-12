/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAggregationSnapshotJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskAggregationSnapshot.
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
     * Database-backed JPA entity for RiskAggregationSnapshot.
     */
    @Entity
    @Table(name = "hidra_risk_aggregation_snapshot")
    public class RiskAggregationSnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "scope_type", nullable = false, length = 160)
    private String scopeType;

    @Column(name = "scope_id", nullable = false, length = 80)
    private String scopeId;

    @Column(name = "scope_code_snapshot", nullable = true, length = 160)
    private String scopeCodeSnapshot;

    @Column(name = "scope_label_snapshot", nullable = true, length = 500)
    private String scopeLabelSnapshot;

    @Column(name = "snapshot_date", nullable = false)
    private Instant snapshotDate;

    @Column(name = "risk_matrix_id", nullable = true, length = 80)
    private String riskMatrixId;

    @Column(name = "total_risk_count", nullable = false)
    private int totalRiskCount;

    @Column(name = "critical_risk_count", nullable = false)
    private int criticalRiskCount;

    @Column(name = "high_risk_count", nullable = false)
    private int highRiskCount;

    @Column(name = "medium_risk_count", nullable = false)
    private int mediumRiskCount;

    @Column(name = "low_risk_count", nullable = false)
    private int lowRiskCount;

    @Column(name = "average_risk_score", nullable = true, precision = 18, scale = 6)
    private BigDecimal averageRiskScore;

    @Column(name = "maximum_risk_score", nullable = true, precision = 18, scale = 6)
    private BigDecimal maximumRiskScore;

    @Column(name = "open_treatment_count", nullable = false)
    private int openTreatmentCount;

    @Column(name = "overdue_treatment_count", nullable = false)
    private int overdueTreatmentCount;

    @Column(name = "accepted_risk_count", nullable = false)
    private int acceptedRiskCount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected RiskAggregationSnapshotJpaEntity() {
            // Required by JPA.
        }

        public RiskAggregationSnapshotJpaEntity(
                String id,
            String scopeType,
            String scopeId,
            String scopeCodeSnapshot,
            String scopeLabelSnapshot,
            Instant snapshotDate,
            String riskMatrixId,
            int totalRiskCount,
            int criticalRiskCount,
            int highRiskCount,
            int mediumRiskCount,
            int lowRiskCount,
            BigDecimal averageRiskScore,
            BigDecimal maximumRiskScore,
            int openTreatmentCount,
            int overdueTreatmentCount,
            int acceptedRiskCount,
            Instant createdAt
        ) {
            this.id = id;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.scopeCodeSnapshot = scopeCodeSnapshot;
        this.scopeLabelSnapshot = scopeLabelSnapshot;
        this.snapshotDate = snapshotDate;
        this.riskMatrixId = riskMatrixId;
        this.totalRiskCount = totalRiskCount;
        this.criticalRiskCount = criticalRiskCount;
        this.highRiskCount = highRiskCount;
        this.mediumRiskCount = mediumRiskCount;
        this.lowRiskCount = lowRiskCount;
        this.averageRiskScore = averageRiskScore;
        this.maximumRiskScore = maximumRiskScore;
        this.openTreatmentCount = openTreatmentCount;
        this.overdueTreatmentCount = overdueTreatmentCount;
        this.acceptedRiskCount = acceptedRiskCount;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String scopeType() {
        return scopeType;
    }


    public String scopeId() {
        return scopeId;
    }


    public String scopeCodeSnapshot() {
        return scopeCodeSnapshot;
    }


    public String scopeLabelSnapshot() {
        return scopeLabelSnapshot;
    }


    public Instant snapshotDate() {
        return snapshotDate;
    }


    public String riskMatrixId() {
        return riskMatrixId;
    }


    public int totalRiskCount() {
        return totalRiskCount;
    }


    public int criticalRiskCount() {
        return criticalRiskCount;
    }


    public int highRiskCount() {
        return highRiskCount;
    }


    public int mediumRiskCount() {
        return mediumRiskCount;
    }


    public int lowRiskCount() {
        return lowRiskCount;
    }


    public BigDecimal averageRiskScore() {
        return averageRiskScore;
    }


    public BigDecimal maximumRiskScore() {
        return maximumRiskScore;
    }


    public int openTreatmentCount() {
        return openTreatmentCount;
    }


    public int overdueTreatmentCount() {
        return overdueTreatmentCount;
    }


    public int acceptedRiskCount() {
        return acceptedRiskCount;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
