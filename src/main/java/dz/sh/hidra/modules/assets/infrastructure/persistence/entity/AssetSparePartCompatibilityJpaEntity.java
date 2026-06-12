/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetSparePartCompatibilityJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetSparePartCompatibility.
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
import java.time.Instant;

    /**
     * Database-backed JPA entity for AssetSparePartCompatibility.
     */
    @Entity
    @Table(name = "hidra_asset_spare_part_compatibility")
    public class AssetSparePartCompatibilityJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "maintainable_asset_id", nullable = true, length = 80)
    private String maintainableAssetId;

    @Column(name = "asset_type_id", nullable = true, length = 80)
    private String assetTypeId;

    @Column(name = "asset_model_id", nullable = true, length = 80)
    private String assetModelId;

    @Column(name = "spare_part_id", nullable = false, length = 80)
    private String sparePartId;

    @Column(name = "compatibility_rule", nullable = true, columnDefinition = "text")
    private String compatibilityRule;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private CompatibilityStatus status;

    @Column(name = "effective_from", nullable = true)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AssetSparePartCompatibilityJpaEntity() {
            // Required by JPA.
        }

        public AssetSparePartCompatibilityJpaEntity(
                String id,
            String maintainableAssetId,
            String assetTypeId,
            String assetModelId,
            String sparePartId,
            String compatibilityRule,
            CompatibilityStatus status,
            Instant effectiveFrom,
            Instant effectiveTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.maintainableAssetId = maintainableAssetId;
        this.assetTypeId = assetTypeId;
        this.assetModelId = assetModelId;
        this.sparePartId = sparePartId;
        this.compatibilityRule = compatibilityRule;
        this.status = status;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public String assetTypeId() {
        return assetTypeId;
    }


    public String assetModelId() {
        return assetModelId;
    }


    public String sparePartId() {
        return sparePartId;
    }


    public String compatibilityRule() {
        return compatibilityRule;
    }


    public CompatibilityStatus status() {
        return status;
    }


    public Instant effectiveFrom() {
        return effectiveFrom;
    }


    public Instant effectiveTo() {
        return effectiveTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
