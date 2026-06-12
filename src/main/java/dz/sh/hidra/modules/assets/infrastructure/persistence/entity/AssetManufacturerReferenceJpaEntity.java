/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetManufacturerReferenceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetManufacturerReference.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AssetManufacturerReference.
     */
    @Entity
    @Table(name = "hidra_asset_manufacturer_reference")
    public class AssetManufacturerReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "maintainable_asset_id", nullable = true, length = 80)
    private String maintainableAssetId;

    @Column(name = "manufacturer_party_id", nullable = false, length = 80)
    private String manufacturerPartyId;

    @Column(name = "manufacturer_code_snapshot", nullable = true, length = 160)
    private String manufacturerCodeSnapshot;

    @Column(name = "manufacturer_name_snapshot", nullable = true, length = 255)
    private String manufacturerNameSnapshot;

    @Column(name = "manufacturer_role_code_snapshot", nullable = true, length = 80)
    private String manufacturerRoleCodeSnapshot;

    @Column(name = "manufacturer_reference_number", nullable = true, length = 160)
    private String manufacturerReferenceNumber;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AssetManufacturerReferenceJpaEntity() {
            // Required by JPA.
        }

        public AssetManufacturerReferenceJpaEntity(
                String id,
            String maintainableAssetId,
            String manufacturerPartyId,
            String manufacturerCodeSnapshot,
            String manufacturerNameSnapshot,
            String manufacturerRoleCodeSnapshot,
            String manufacturerReferenceNumber,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.maintainableAssetId = maintainableAssetId;
        this.manufacturerPartyId = manufacturerPartyId;
        this.manufacturerCodeSnapshot = manufacturerCodeSnapshot;
        this.manufacturerNameSnapshot = manufacturerNameSnapshot;
        this.manufacturerRoleCodeSnapshot = manufacturerRoleCodeSnapshot;
        this.manufacturerReferenceNumber = manufacturerReferenceNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public String manufacturerPartyId() {
        return manufacturerPartyId;
    }


    public String manufacturerCodeSnapshot() {
        return manufacturerCodeSnapshot;
    }


    public String manufacturerNameSnapshot() {
        return manufacturerNameSnapshot;
    }


    public String manufacturerRoleCodeSnapshot() {
        return manufacturerRoleCodeSnapshot;
    }


    public String manufacturerReferenceNumber() {
        return manufacturerReferenceNumber;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
