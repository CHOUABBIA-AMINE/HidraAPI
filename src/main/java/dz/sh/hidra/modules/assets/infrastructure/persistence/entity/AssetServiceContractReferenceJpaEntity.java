/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetServiceContractReferenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetServiceContractReference.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AssetServiceContractReference.
     */
    @Entity
    @Table(name = "hidra_asset_service_contract_reference")
    public class AssetServiceContractReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "maintainable_asset_id", nullable = false, length = 80)
    private String maintainableAssetId;

    @Column(name = "contract_reference_id", nullable = false, length = 80)
    private String contractReferenceId;

    @Column(name = "contract_code_snapshot", nullable = true, length = 160)
    private String contractCodeSnapshot;

    @Column(name = "service_provider_party_id", nullable = true, length = 80)
    private String serviceProviderPartyId;

    @Column(name = "service_provider_name_snapshot", nullable = true, length = 255)
    private String serviceProviderNameSnapshot;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AssetServiceContractReferenceJpaEntity() {
            // Required by JPA.
        }

        public AssetServiceContractReferenceJpaEntity(
                String id,
            String maintainableAssetId,
            String contractReferenceId,
            String contractCodeSnapshot,
            String serviceProviderPartyId,
            String serviceProviderNameSnapshot,
            Instant validFrom,
            Instant validTo,
            boolean active,
            Instant createdAt
        ) {
            this.id = id;
        this.maintainableAssetId = maintainableAssetId;
        this.contractReferenceId = contractReferenceId;
        this.contractCodeSnapshot = contractCodeSnapshot;
        this.serviceProviderPartyId = serviceProviderPartyId;
        this.serviceProviderNameSnapshot = serviceProviderNameSnapshot;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.active = active;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public String contractReferenceId() {
        return contractReferenceId;
    }


    public String contractCodeSnapshot() {
        return contractCodeSnapshot;
    }


    public String serviceProviderPartyId() {
        return serviceProviderPartyId;
    }


    public String serviceProviderNameSnapshot() {
        return serviceProviderNameSnapshot;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
