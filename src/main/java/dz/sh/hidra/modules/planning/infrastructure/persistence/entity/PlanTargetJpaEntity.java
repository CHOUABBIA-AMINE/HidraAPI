/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanTargetJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PlanTarget.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import dz.sh.hidra.modules.planning.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PlanTarget.
     */
    @Entity
    @Table(name = "hidra_planning_plan_target")
    public class PlanTargetJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "revision_id", nullable = false, length = 80)
    private String revisionId;

    @Column(name = "scenario_id", nullable = true, length = 80)
    private String scenarioId;

    @Column(name = "nomination_id", nullable = true, length = 80)
    private String nominationId;

    @Column(name = "target_type_id", nullable = false, length = 80)
    private String targetTypeId;

    @Column(name = "topology_asset_type", nullable = false, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = false, length = 160)
    private String topologyAssetCode;

    @Column(name = "topology_asset_name_snapshot", nullable = true, length = 500)
    private String topologyAssetNameSnapshot;

    @Column(name = "telemetry_point_id", nullable = true, length = 80)
    private String telemetryPointId;

    @Column(name = "telemetry_point_code_snapshot", nullable = true, length = 160)
    private String telemetryPointCodeSnapshot;

    @Column(name = "target_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal targetValue;

    @Column(name = "target_text_value", nullable = true, length = 160)
    private String targetTextValue;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Column(name = "tolerance_low", nullable = true, precision = 18, scale = 6)
    private BigDecimal toleranceLow;

    @Column(name = "tolerance_high", nullable = true, precision = 18, scale = 6)
    private BigDecimal toleranceHigh;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @Column(name = "priority", nullable = true)
    private Integer priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PlanTargetStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PlanTargetJpaEntity() {
            // Required by JPA.
        }

        public PlanTargetJpaEntity(
                String id,
            String revisionId,
            String scenarioId,
            String nominationId,
            String targetTypeId,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            String topologyAssetNameSnapshot,
            String telemetryPointId,
            String telemetryPointCodeSnapshot,
            BigDecimal targetValue,
            String targetTextValue,
            String unitId,
            BigDecimal toleranceLow,
            BigDecimal toleranceHigh,
            Instant validFrom,
            Instant validTo,
            Integer priority,
            PlanTargetStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.revisionId = revisionId;
        this.scenarioId = scenarioId;
        this.nominationId = nominationId;
        this.targetTypeId = targetTypeId;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.topologyAssetNameSnapshot = topologyAssetNameSnapshot;
        this.telemetryPointId = telemetryPointId;
        this.telemetryPointCodeSnapshot = telemetryPointCodeSnapshot;
        this.targetValue = targetValue;
        this.targetTextValue = targetTextValue;
        this.unitId = unitId;
        this.toleranceLow = toleranceLow;
        this.toleranceHigh = toleranceHigh;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String revisionId() {
        return revisionId;
    }


    public String scenarioId() {
        return scenarioId;
    }


    public String nominationId() {
        return nominationId;
    }


    public String targetTypeId() {
        return targetTypeId;
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


    public String topologyAssetNameSnapshot() {
        return topologyAssetNameSnapshot;
    }


    public String telemetryPointId() {
        return telemetryPointId;
    }


    public String telemetryPointCodeSnapshot() {
        return telemetryPointCodeSnapshot;
    }


    public BigDecimal targetValue() {
        return targetValue;
    }


    public String targetTextValue() {
        return targetTextValue;
    }


    public String unitId() {
        return unitId;
    }


    public BigDecimal toleranceLow() {
        return toleranceLow;
    }


    public BigDecimal toleranceHigh() {
        return toleranceHigh;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public Integer priority() {
        return priority;
    }


    public PlanTargetStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
