/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetInstallationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetInstallation.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AssetInstallation.
     */
    @Entity
    @Table(name = "hidra_asset_installation")
    public class AssetInstallationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "maintainable_asset_id", nullable = false, length = 80)
    private String maintainableAssetId;

    @Column(name = "installation_number", nullable = false, length = 80)
    private String installationNumber;

    @Column(name = "topology_asset_type_code", nullable = false, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code_snapshot", nullable = true, length = 160)
    private String topologyAssetCodeSnapshot;

    @Column(name = "installed_at", nullable = false)
    private Instant installedAt;

    @Column(name = "commissioned_at", nullable = true)
    private Instant commissionedAt;

    @Column(name = "installed_by_party_id", nullable = true, length = 80)
    private String installedByPartyId;

    @Column(name = "installed_by_name_snapshot", nullable = true, length = 255)
    private String installedByNameSnapshot;

    @Column(name = "commissioning_document_id", nullable = true, length = 80)
    private String commissioningDocumentId;

    @Column(name = "notes", nullable = true, columnDefinition = "text")
    private String notes;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AssetInstallationJpaEntity() {
            // Required by JPA.
        }

        public AssetInstallationJpaEntity(
                String id,
            String maintainableAssetId,
            String installationNumber,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCodeSnapshot,
            Instant installedAt,
            Instant commissionedAt,
            String installedByPartyId,
            String installedByNameSnapshot,
            String commissioningDocumentId,
            String notes,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.maintainableAssetId = maintainableAssetId;
        this.installationNumber = installationNumber;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCodeSnapshot = topologyAssetCodeSnapshot;
        this.installedAt = installedAt;
        this.commissionedAt = commissionedAt;
        this.installedByPartyId = installedByPartyId;
        this.installedByNameSnapshot = installedByNameSnapshot;
        this.commissioningDocumentId = commissioningDocumentId;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public String installationNumber() {
        return installationNumber;
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


    public Instant installedAt() {
        return installedAt;
    }


    public Instant commissionedAt() {
        return commissionedAt;
    }


    public String installedByPartyId() {
        return installedByPartyId;
    }


    public String installedByNameSnapshot() {
        return installedByNameSnapshot;
    }


    public String commissioningDocumentId() {
        return commissioningDocumentId;
    }


    public String notes() {
        return notes;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
