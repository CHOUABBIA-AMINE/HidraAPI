/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetSerialIdentityJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetSerialIdentity.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AssetSerialIdentity.
     */
    @Entity
    @Table(name = "hidra_asset_serial_identity")
    public class AssetSerialIdentityJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "maintainable_asset_id", nullable = false, length = 80)
    private String maintainableAssetId;

    @Column(name = "serial_number", nullable = false, length = 160)
    private String serialNumber;

    @Column(name = "batch_number", nullable = true, length = 160)
    private String batchNumber;

    @Column(name = "manufacturer_part_number", nullable = true, length = 160)
    private String manufacturerPartNumber;

    @Column(name = "nameplate_data_json", nullable = true, columnDefinition = "jsonb")
    private String nameplateDataJson;

    @Column(name = "manufactured_at", nullable = true)
    private Instant manufacturedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AssetSerialIdentityJpaEntity() {
            // Required by JPA.
        }

        public AssetSerialIdentityJpaEntity(
                String id,
            String maintainableAssetId,
            String serialNumber,
            String batchNumber,
            String manufacturerPartNumber,
            String nameplateDataJson,
            Instant manufacturedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.maintainableAssetId = maintainableAssetId;
        this.serialNumber = serialNumber;
        this.batchNumber = batchNumber;
        this.manufacturerPartNumber = manufacturerPartNumber;
        this.nameplateDataJson = nameplateDataJson;
        this.manufacturedAt = manufacturedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public String serialNumber() {
        return serialNumber;
    }


    public String batchNumber() {
        return batchNumber;
    }


    public String manufacturerPartNumber() {
        return manufacturerPartNumber;
    }


    public String nameplateDataJson() {
        return nameplateDataJson;
    }


    public Instant manufacturedAt() {
        return manufacturedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
