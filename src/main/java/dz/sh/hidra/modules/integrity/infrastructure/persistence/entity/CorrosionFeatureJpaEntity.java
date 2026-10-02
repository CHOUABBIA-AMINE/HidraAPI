/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CorrosionFeatureJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CorrosionFeature.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integrity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for CorrosionFeature.
     */
    @Entity
    @Table(name = "hidra_integrity_corrosion_feature")
    public class CorrosionFeatureJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "defect_id", nullable = true, length = 80)
    private String defectId;

    @Column(name = "corrosion_type_id", nullable = false, length = 80)
    private String corrosionTypeId;

    @Column(name = "topology_asset_type_code", nullable = false, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "kilometer_point", nullable = true, precision = 14, scale = 4)
    private BigDecimal kilometerPoint;

    @Column(name = "length", nullable = true, precision = 18, scale = 6)
    private BigDecimal length;

    @Column(name = "width", nullable = true, precision = 18, scale = 6)
    private BigDecimal width;

    @Column(name = "depth", nullable = true, precision = 18, scale = 6)
    private BigDecimal depth;

    @Column(name = "dimension_unit_id", nullable = true, length = 80)
    private String dimensionUnitId;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = true, length = 40)
    private FindingSeverity severity;

    @Column(name = "observed_at", nullable = false)
    private Instant observedAt;

    @Column(name = "notes", nullable = true, columnDefinition = "text")
    private String notes;

        protected CorrosionFeatureJpaEntity() {
            // Required by JPA.
        }

        public CorrosionFeatureJpaEntity(
                String id,
            String defectId,
            String corrosionTypeId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            BigDecimal kilometerPoint,
            BigDecimal length,
            BigDecimal width,
            BigDecimal depth,
            String dimensionUnitId,
            FindingSeverity severity,
            Instant observedAt,
            String notes
        ) {
            this.id = id;
        this.defectId = defectId;
        this.corrosionTypeId = corrosionTypeId;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.kilometerPoint = kilometerPoint;
        this.length = length;
        this.width = width;
        this.depth = depth;
        this.dimensionUnitId = dimensionUnitId;
        this.severity = severity;
        this.observedAt = observedAt;
        this.notes = notes;
        }


    public String id() {
        return id;
    }


    public String defectId() {
        return defectId;
    }


    public String corrosionTypeId() {
        return corrosionTypeId;
    }


    public String topologyAssetTypeCode() {
        return topologyAssetTypeCode;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public BigDecimal kilometerPoint() {
        return kilometerPoint;
    }


    public BigDecimal length() {
        return length;
    }


    public BigDecimal width() {
        return width;
    }


    public BigDecimal depth() {
        return depth;
    }


    public String dimensionUnitId() {
        return dimensionUnitId;
    }


    public FindingSeverity severity() {
        return severity;
    }


    public Instant observedAt() {
        return observedAt;
    }


    public String notes() {
        return notes;
    }

    }
