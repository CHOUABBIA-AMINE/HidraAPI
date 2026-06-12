/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanActualDeviationJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PlanActualDeviation.
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
     * Database-backed JPA entity for PlanActualDeviation.
     */
    @Entity
    @Table(name = "hidra_monitoring_plan_actual_deviation")
    public class PlanActualDeviationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "evaluation_id", nullable = true, length = 80)
    private String evaluationId;

    @Column(name = "plan_target_id", nullable = false, length = 80)
    private String planTargetId;

    @Column(name = "expected_flow_state_id", nullable = true, length = 80)
    private String expectedFlowStateId;

    @Column(name = "trusted_telemetry_reading_id", nullable = true, length = 80)
    private String trustedTelemetryReadingId;

    @Column(name = "telemetry_point_id", nullable = true, length = 80)
    private String telemetryPointId;

    @Column(name = "topology_asset_type", nullable = false, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = true, length = 160)
    private String topologyAssetCode;

    @Column(name = "actual_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal actualValue;

    @Column(name = "expected_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal expectedValue;

    @Column(name = "difference_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal differenceValue;

    @Column(name = "difference_percent", nullable = true, precision = 10, scale = 4)
    private BigDecimal differencePercent;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 40)
    private DeviationSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private DeviationStatus status;

    @Column(name = "detected_at", nullable = false)
    private Instant detectedAt;

    @Column(name = "resolved_at", nullable = true)
    private Instant resolvedAt;

    @Column(name = "reason_code", nullable = true, length = 80)
    private String reasonCode;

    @Column(name = "reason_message", nullable = true, columnDefinition = "text")
    private String reasonMessage;

        protected PlanActualDeviationJpaEntity() {
            // Required by JPA.
        }

        public PlanActualDeviationJpaEntity(
                String id,
            String evaluationId,
            String planTargetId,
            String expectedFlowStateId,
            String trustedTelemetryReadingId,
            String telemetryPointId,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            BigDecimal actualValue,
            BigDecimal expectedValue,
            BigDecimal differenceValue,
            BigDecimal differencePercent,
            String unitId,
            DeviationSeverity severity,
            DeviationStatus status,
            Instant detectedAt,
            Instant resolvedAt,
            String reasonCode,
            String reasonMessage
        ) {
            this.id = id;
        this.evaluationId = evaluationId;
        this.planTargetId = planTargetId;
        this.expectedFlowStateId = expectedFlowStateId;
        this.trustedTelemetryReadingId = trustedTelemetryReadingId;
        this.telemetryPointId = telemetryPointId;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.actualValue = actualValue;
        this.expectedValue = expectedValue;
        this.differenceValue = differenceValue;
        this.differencePercent = differencePercent;
        this.unitId = unitId;
        this.severity = severity;
        this.status = status;
        this.detectedAt = detectedAt;
        this.resolvedAt = resolvedAt;
        this.reasonCode = reasonCode;
        this.reasonMessage = reasonMessage;
        }


    public String id() {
        return id;
    }


    public String evaluationId() {
        return evaluationId;
    }


    public String planTargetId() {
        return planTargetId;
    }


    public String expectedFlowStateId() {
        return expectedFlowStateId;
    }


    public String trustedTelemetryReadingId() {
        return trustedTelemetryReadingId;
    }


    public String telemetryPointId() {
        return telemetryPointId;
    }


    public String topologyAssetType() {
        return topologyAssetType;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCode() {
        return topologyAssetCode;
    }


    public BigDecimal actualValue() {
        return actualValue;
    }


    public BigDecimal expectedValue() {
        return expectedValue;
    }


    public BigDecimal differenceValue() {
        return differenceValue;
    }


    public BigDecimal differencePercent() {
        return differencePercent;
    }


    public String unitId() {
        return unitId;
    }


    public DeviationSeverity severity() {
        return severity;
    }


    public DeviationStatus status() {
        return status;
    }


    public Instant detectedAt() {
        return detectedAt;
    }


    public Instant resolvedAt() {
        return resolvedAt;
    }


    public String reasonCode() {
        return reasonCode;
    }


    public String reasonMessage() {
        return reasonMessage;
    }

    }
