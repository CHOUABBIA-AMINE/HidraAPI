/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetWarrantyJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetWarranty.
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
     * Database-backed JPA entity for AssetWarranty.
     */
    @Entity
    @Table(name = "hidra_asset_warranty")
    public class AssetWarrantyJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "maintainable_asset_id", nullable = false, length = 80)
    private String maintainableAssetId;

    @Column(name = "warranty_number", nullable = false, length = 80)
    private String warrantyNumber;

    @Column(name = "provider_party_id", nullable = true, length = 80)
    private String providerPartyId;

    @Column(name = "provider_name_snapshot", nullable = true, length = 255)
    private String providerNameSnapshot;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private WarrantyStatus status;

    @Column(name = "terms_summary", nullable = true, columnDefinition = "text")
    private String termsSummary;

    @Column(name = "document_reference_id", nullable = true, length = 80)
    private String documentReferenceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AssetWarrantyJpaEntity() {
            // Required by JPA.
        }

        public AssetWarrantyJpaEntity(
                String id,
            String maintainableAssetId,
            String warrantyNumber,
            String providerPartyId,
            String providerNameSnapshot,
            Instant validFrom,
            Instant validTo,
            WarrantyStatus status,
            String termsSummary,
            String documentReferenceId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.maintainableAssetId = maintainableAssetId;
        this.warrantyNumber = warrantyNumber;
        this.providerPartyId = providerPartyId;
        this.providerNameSnapshot = providerNameSnapshot;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.termsSummary = termsSummary;
        this.documentReferenceId = documentReferenceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public String warrantyNumber() {
        return warrantyNumber;
    }


    public String providerPartyId() {
        return providerPartyId;
    }


    public String providerNameSnapshot() {
        return providerNameSnapshot;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public WarrantyStatus status() {
        return status;
    }


    public String termsSummary() {
        return termsSummary;
    }


    public String documentReferenceId() {
        return documentReferenceId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
