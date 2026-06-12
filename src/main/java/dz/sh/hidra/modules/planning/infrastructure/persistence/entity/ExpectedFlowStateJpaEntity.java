/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExpectedFlowStateJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ExpectedFlowState.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for ExpectedFlowState.
     */
    @Entity
    @Table(name = "hidra_planning_expected_flow_state")
    public class ExpectedFlowStateJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "revision_id", nullable = false, length = 80)
    private String revisionId;

    @Column(name = "scenario_id", nullable = true, length = 80)
    private String scenarioId;

    @Column(name = "plan_target_id", nullable = true, length = 80)
    private String planTargetId;

    @Column(name = "topology_asset_type", nullable = false, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = false, length = 160)
    private String topologyAssetCode;

    @Column(name = "expected_at", nullable = false)
    private Instant expectedAt;

    @Column(name = "expected_flow_rate", nullable = true, precision = 18, scale = 6)
    private BigDecimal expectedFlowRate;

    @Column(name = "flow_rate_unit_id", nullable = true, length = 80)
    private String flowRateUnitId;

    @Column(name = "expected_pressure_in", nullable = true, precision = 18, scale = 6)
    private BigDecimal expectedPressureIn;

    @Column(name = "expected_pressure_out", nullable = true, precision = 18, scale = 6)
    private BigDecimal expectedPressureOut;

    @Column(name = "pressure_unit_id", nullable = true, length = 80)
    private String pressureUnitId;

    @Column(name = "expected_temperature", nullable = true, precision = 18, scale = 6)
    private BigDecimal expectedTemperature;

    @Column(name = "temperature_unit_id", nullable = true, length = 80)
    private String temperatureUnitId;

    @Column(name = "expected_volume", nullable = true, precision = 18, scale = 6)
    private BigDecimal expectedVolume;

    @Column(name = "volume_unit_id", nullable = true, length = 80)
    private String volumeUnitId;

    @Column(name = "expected_operating_mode", nullable = true, length = 160)
    private String expectedOperatingMode;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected ExpectedFlowStateJpaEntity() {
            // Required by JPA.
        }

        public ExpectedFlowStateJpaEntity(
                String id,
            String revisionId,
            String scenarioId,
            String planTargetId,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            Instant expectedAt,
            BigDecimal expectedFlowRate,
            String flowRateUnitId,
            BigDecimal expectedPressureIn,
            BigDecimal expectedPressureOut,
            String pressureUnitId,
            BigDecimal expectedTemperature,
            String temperatureUnitId,
            BigDecimal expectedVolume,
            String volumeUnitId,
            String expectedOperatingMode,
            Instant validFrom,
            Instant validTo,
            Instant createdAt
        ) {
            this.id = id;
        this.revisionId = revisionId;
        this.scenarioId = scenarioId;
        this.planTargetId = planTargetId;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.expectedAt = expectedAt;
        this.expectedFlowRate = expectedFlowRate;
        this.flowRateUnitId = flowRateUnitId;
        this.expectedPressureIn = expectedPressureIn;
        this.expectedPressureOut = expectedPressureOut;
        this.pressureUnitId = pressureUnitId;
        this.expectedTemperature = expectedTemperature;
        this.temperatureUnitId = temperatureUnitId;
        this.expectedVolume = expectedVolume;
        this.volumeUnitId = volumeUnitId;
        this.expectedOperatingMode = expectedOperatingMode;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
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


    public String planTargetId() {
        return planTargetId;
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


    public Instant expectedAt() {
        return expectedAt;
    }


    public BigDecimal expectedFlowRate() {
        return expectedFlowRate;
    }


    public String flowRateUnitId() {
        return flowRateUnitId;
    }


    public BigDecimal expectedPressureIn() {
        return expectedPressureIn;
    }


    public BigDecimal expectedPressureOut() {
        return expectedPressureOut;
    }


    public String pressureUnitId() {
        return pressureUnitId;
    }


    public BigDecimal expectedTemperature() {
        return expectedTemperature;
    }


    public String temperatureUnitId() {
        return temperatureUnitId;
    }


    public BigDecimal expectedVolume() {
        return expectedVolume;
    }


    public String volumeUnitId() {
        return volumeUnitId;
    }


    public String expectedOperatingMode() {
        return expectedOperatingMode;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
