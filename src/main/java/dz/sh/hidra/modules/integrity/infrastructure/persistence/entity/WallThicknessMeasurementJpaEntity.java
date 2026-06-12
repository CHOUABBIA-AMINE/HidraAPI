/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WallThicknessMeasurementJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WallThicknessMeasurement.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for WallThicknessMeasurement.
     */
    @Entity
    @Table(name = "hidra_integrity_wall_thickness_measurement")
    public class WallThicknessMeasurementJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "inspection_run_id", nullable = true, length = 80)
    private String inspectionRunId;

    @Column(name = "topology_asset_type_code", nullable = false, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code_snapshot", nullable = true, length = 160)
    private String topologyAssetCodeSnapshot;

    @Column(name = "kilometer_point", nullable = true, precision = 14, scale = 4)
    private BigDecimal kilometerPoint;

    @Column(name = "nominal_thickness", nullable = true, precision = 18, scale = 6)
    private BigDecimal nominalThickness;

    @Column(name = "measured_thickness", nullable = false, precision = 18, scale = 6)
    private BigDecimal measuredThickness;

    @Column(name = "thickness_unit_id", nullable = false, length = 80)
    private String thicknessUnitId;

    @Column(name = "metal_loss_percent", nullable = true, precision = 10, scale = 4)
    private BigDecimal metalLossPercent;

    @Column(name = "measured_at", nullable = false)
    private Instant measuredAt;

    @Column(name = "measurement_method_id", nullable = true, length = 80)
    private String measurementMethodId;

        protected WallThicknessMeasurementJpaEntity() {
            // Required by JPA.
        }

        public WallThicknessMeasurementJpaEntity(
                String id,
            String inspectionRunId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCodeSnapshot,
            BigDecimal kilometerPoint,
            BigDecimal nominalThickness,
            BigDecimal measuredThickness,
            String thicknessUnitId,
            BigDecimal metalLossPercent,
            Instant measuredAt,
            String measurementMethodId
        ) {
            this.id = id;
        this.inspectionRunId = inspectionRunId;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCodeSnapshot = topologyAssetCodeSnapshot;
        this.kilometerPoint = kilometerPoint;
        this.nominalThickness = nominalThickness;
        this.measuredThickness = measuredThickness;
        this.thicknessUnitId = thicknessUnitId;
        this.metalLossPercent = metalLossPercent;
        this.measuredAt = measuredAt;
        this.measurementMethodId = measurementMethodId;
        }


    public String id() {
        return id;
    }


    public String inspectionRunId() {
        return inspectionRunId;
    }


    public String topologyAssetTypeCode() {
        return topologyAssetTypeCode;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCodeSnapshot() {
        return topologyAssetCodeSnapshot;
    }


    public BigDecimal kilometerPoint() {
        return kilometerPoint;
    }


    public BigDecimal nominalThickness() {
        return nominalThickness;
    }


    public BigDecimal measuredThickness() {
        return measuredThickness;
    }


    public String thicknessUnitId() {
        return thicknessUnitId;
    }


    public BigDecimal metalLossPercent() {
        return metalLossPercent;
    }


    public Instant measuredAt() {
        return measuredAt;
    }


    public String measurementMethodId() {
        return measurementMethodId;
    }

    }
