/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskSignalJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskSignal.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for RiskSignal.
     */
    @Entity
    @Table(name = "hidra_monitoring_risk_signal")
    public class RiskSignalJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "source_deviation_id", nullable = true, length = 80)
    private String sourceDeviationId;

    @Column(name = "source_evaluation_id", nullable = true, length = 80)
    private String sourceEvaluationId;

    @Column(name = "topology_asset_type", nullable = true, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = true, length = 80)
    private String topologyAssetId;

    @Column(name = "risk_type_id", nullable = false, length = 80)
    private String riskTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false, length = 40)
    private RiskSignalLevel riskLevel;

    @Column(name = "risk_score", nullable = true, precision = 10, scale = 4)
    private BigDecimal riskScore;

    @Column(name = "signal_payload", nullable = true, columnDefinition = "jsonb")
    private String signalPayload;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private RiskSignalStatus status;

    @Column(name = "raised_at", nullable = false)
    private Instant raisedAt;

    @Column(name = "expires_at", nullable = true)
    private Instant expiresAt;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected RiskSignalJpaEntity() {
            // Required by JPA.
        }

        public RiskSignalJpaEntity(
                String id,
            String sourceDeviationId,
            String sourceEvaluationId,
            String topologyAssetType,
            String topologyAssetId,
            String riskTypeId,
            RiskSignalLevel riskLevel,
            BigDecimal riskScore,
            String signalPayload,
            RiskSignalStatus status,
            Instant raisedAt,
            Instant expiresAt,
            String correlationId
        ) {
            this.id = id;
        this.sourceDeviationId = sourceDeviationId;
        this.sourceEvaluationId = sourceEvaluationId;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.riskTypeId = riskTypeId;
        this.riskLevel = riskLevel;
        this.riskScore = riskScore;
        this.signalPayload = signalPayload;
        this.status = status;
        this.raisedAt = raisedAt;
        this.expiresAt = expiresAt;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String sourceDeviationId() {
        return sourceDeviationId;
    }


    public String sourceEvaluationId() {
        return sourceEvaluationId;
    }


    public String topologyAssetType() {
        return topologyAssetType;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String riskTypeId() {
        return riskTypeId;
    }


    public RiskSignalLevel riskLevel() {
        return riskLevel;
    }


    public BigDecimal riskScore() {
        return riskScore;
    }


    public String signalPayload() {
        return signalPayload;
    }


    public RiskSignalStatus status() {
        return status;
    }


    public Instant raisedAt() {
        return raisedAt;
    }


    public Instant expiresAt() {
        return expiresAt;
    }


    public String correlationId() {
        return correlationId;
    }

    }
