/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetModelJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetModel.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AssetModel.
     */
    @Entity
    @Table(name = "hidra_asset_model")
    public class AssetModelJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "model_code", nullable = false, length = 80)
    private String modelCode;

    @Column(name = "model_name", nullable = false, length = 160)
    private String modelName;

    @Column(name = "asset_type_id", nullable = false, length = 80)
    private String assetTypeId;

    @Column(name = "manufacturer_party_id", nullable = true, length = 80)
    private String manufacturerPartyId;

    @Column(name = "manufacturer_name_snapshot", nullable = true, length = 255)
    private String manufacturerNameSnapshot;

    @Column(name = "technical_description", nullable = true, columnDefinition = "text")
    private String technicalDescription;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AssetModelJpaEntity() {
            // Required by JPA.
        }

        public AssetModelJpaEntity(
                String id,
            String modelCode,
            String modelName,
            String assetTypeId,
            String manufacturerPartyId,
            String manufacturerNameSnapshot,
            String technicalDescription,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.modelCode = modelCode;
        this.modelName = modelName;
        this.assetTypeId = assetTypeId;
        this.manufacturerPartyId = manufacturerPartyId;
        this.manufacturerNameSnapshot = manufacturerNameSnapshot;
        this.technicalDescription = technicalDescription;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String modelCode() {
        return modelCode;
    }


    public String modelName() {
        return modelName;
    }


    public String assetTypeId() {
        return assetTypeId;
    }


    public String manufacturerPartyId() {
        return manufacturerPartyId;
    }


    public String manufacturerNameSnapshot() {
        return manufacturerNameSnapshot;
    }


    public String technicalDescription() {
        return technicalDescription;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
