/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetMeterReadingReferenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetMeterReadingReference.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import dz.sh.hidra.modules.assets.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AssetMeterReadingReference.
     */
    @Entity
    @Table(name = "hidra_asset_meter_reading_reference")
    public class AssetMeterReadingReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "maintainable_asset_id", nullable = false, length = 80)
    private String maintainableAssetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reading_type", nullable = false, length = 80)
    private MeterReadingReferenceType readingType;

    @Column(name = "reading_reference_id", nullable = false, length = 80)
    private String readingReferenceId;

    @Column(name = "reading_code_snapshot", nullable = true, length = 160)
    private String readingCodeSnapshot;

    @Column(name = "reading_value_snapshot", nullable = true, precision = 18, scale = 6)
    private BigDecimal readingValueSnapshot;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Column(name = "reading_at", nullable = true)
    private Instant readingAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AssetMeterReadingReferenceJpaEntity() {
            // Required by JPA.
        }

        public AssetMeterReadingReferenceJpaEntity(
                String id,
            String maintainableAssetId,
            MeterReadingReferenceType readingType,
            String readingReferenceId,
            String readingCodeSnapshot,
            BigDecimal readingValueSnapshot,
            String unitId,
            Instant readingAt,
            Instant createdAt
        ) {
            this.id = id;
        this.maintainableAssetId = maintainableAssetId;
        this.readingType = readingType;
        this.readingReferenceId = readingReferenceId;
        this.readingCodeSnapshot = readingCodeSnapshot;
        this.readingValueSnapshot = readingValueSnapshot;
        this.unitId = unitId;
        this.readingAt = readingAt;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public MeterReadingReferenceType readingType() {
        return readingType;
    }


    public String readingReferenceId() {
        return readingReferenceId;
    }


    public String readingCodeSnapshot() {
        return readingCodeSnapshot;
    }


    public BigDecimal readingValueSnapshot() {
        return readingValueSnapshot;
    }


    public String unitId() {
        return unitId;
    }


    public Instant readingAt() {
        return readingAt;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
