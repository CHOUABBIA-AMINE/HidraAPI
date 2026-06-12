/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintainableAssetJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MaintainableAsset.
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
     * Database-backed JPA entity for MaintainableAsset.
     */
    @Entity
    @Table(name = "hidra_asset_maintainable_asset")
    public class MaintainableAssetJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "asset_number", nullable = false, length = 80)
    private String assetNumber;

    @Column(name = "asset_code", nullable = false, length = 160)
    private String assetCode;

    @Column(name = "asset_name", nullable = false, length = 255)
    private String assetName;

    @Column(name = "asset_type_id", nullable = false, length = 80)
    private String assetTypeId;

    @Column(name = "topology_asset_type_code", nullable = false, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code_snapshot", nullable = true, length = 160)
    private String topologyAssetCodeSnapshot;

    @Column(name = "topology_asset_name_snapshot", nullable = true, length = 500)
    private String topologyAssetNameSnapshot;

    @Column(name = "parent_asset_id", nullable = true, length = 80)
    private String parentAssetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private AssetLifecycleStatus status;

    @Column(name = "criticality_id", nullable = true, length = 80)
    private String criticalityId;

    @Column(name = "owner_organization_unit_id", nullable = true, length = 80)
    private String ownerOrganizationUnitId;

    @Column(name = "owner_organization_unit_name_snapshot", nullable = true, length = 500)
    private String ownerOrganizationUnitNameSnapshot;

    @Column(name = "manufacturer_party_id", nullable = true, length = 80)
    private String manufacturerPartyId;

    @Column(name = "manufacturer_name_snapshot", nullable = true, length = 255)
    private String manufacturerNameSnapshot;

    @Column(name = "model_id", nullable = true, length = 80)
    private String modelId;

    @Column(name = "serial_identity_id", nullable = true, length = 80)
    private String serialIdentityId;

    @Column(name = "registered_at", nullable = false)
    private Instant registeredAt;

    @Column(name = "installed_at", nullable = true)
    private Instant installedAt;

    @Column(name = "commissioned_at", nullable = true)
    private Instant commissionedAt;

    @Column(name = "retired_at", nullable = true)
    private Instant retiredAt;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected MaintainableAssetJpaEntity() {
            // Required by JPA.
        }

        public MaintainableAssetJpaEntity(
                String id,
            String assetNumber,
            String assetCode,
            String assetName,
            String assetTypeId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCodeSnapshot,
            String topologyAssetNameSnapshot,
            String parentAssetId,
            AssetLifecycleStatus status,
            String criticalityId,
            String ownerOrganizationUnitId,
            String ownerOrganizationUnitNameSnapshot,
            String manufacturerPartyId,
            String manufacturerNameSnapshot,
            String modelId,
            String serialIdentityId,
            Instant registeredAt,
            Instant installedAt,
            Instant commissionedAt,
            Instant retiredAt,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.assetNumber = assetNumber;
        this.assetCode = assetCode;
        this.assetName = assetName;
        this.assetTypeId = assetTypeId;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCodeSnapshot = topologyAssetCodeSnapshot;
        this.topologyAssetNameSnapshot = topologyAssetNameSnapshot;
        this.parentAssetId = parentAssetId;
        this.status = status;
        this.criticalityId = criticalityId;
        this.ownerOrganizationUnitId = ownerOrganizationUnitId;
        this.ownerOrganizationUnitNameSnapshot = ownerOrganizationUnitNameSnapshot;
        this.manufacturerPartyId = manufacturerPartyId;
        this.manufacturerNameSnapshot = manufacturerNameSnapshot;
        this.modelId = modelId;
        this.serialIdentityId = serialIdentityId;
        this.registeredAt = registeredAt;
        this.installedAt = installedAt;
        this.commissionedAt = commissionedAt;
        this.retiredAt = retiredAt;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String assetNumber() {
        return assetNumber;
    }


    public String assetCode() {
        return assetCode;
    }


    public String assetName() {
        return assetName;
    }


    public String assetTypeId() {
        return assetTypeId;
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


    public String topologyAssetNameSnapshot() {
        return topologyAssetNameSnapshot;
    }


    public String parentAssetId() {
        return parentAssetId;
    }


    public AssetLifecycleStatus status() {
        return status;
    }


    public String criticalityId() {
        return criticalityId;
    }


    public String ownerOrganizationUnitId() {
        return ownerOrganizationUnitId;
    }


    public String ownerOrganizationUnitNameSnapshot() {
        return ownerOrganizationUnitNameSnapshot;
    }


    public String manufacturerPartyId() {
        return manufacturerPartyId;
    }


    public String manufacturerNameSnapshot() {
        return manufacturerNameSnapshot;
    }


    public String modelId() {
        return modelId;
    }


    public String serialIdentityId() {
        return serialIdentityId;
    }


    public Instant registeredAt() {
        return registeredAt;
    }


    public Instant installedAt() {
        return installedAt;
    }


    public Instant commissionedAt() {
        return commissionedAt;
    }


    public Instant retiredAt() {
        return retiredAt;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
