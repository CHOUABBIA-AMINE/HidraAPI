/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OwnerProfileJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for OwnerProfile.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.entity;

import dz.sh.hidra.modules.party.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for OwnerProfile.
     */
    @Entity
    @Table(name = "hidra_party_owner_profile")
    public class OwnerProfileJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_profile_type", nullable = false, length = 80)
    private OwnerProfileType ownerProfileType;

    @Column(name = "ownership_context_code", nullable = true, length = 120)
    private String ownershipContextCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level_snapshot", nullable = true, length = 80)
    private RiskLevel riskLevelSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PartyCatalogStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected OwnerProfileJpaEntity() {
            // Required by JPA.
        }

        public OwnerProfileJpaEntity(
                String id,
            String partyId,
            OwnerProfileType ownerProfileType,
            String ownershipContextCode,
            RiskLevel riskLevelSnapshot,
            PartyCatalogStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.ownerProfileType = ownerProfileType;
        this.ownershipContextCode = ownershipContextCode;
        this.riskLevelSnapshot = riskLevelSnapshot;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String partyId() {
        return partyId;
    }


    public OwnerProfileType ownerProfileType() {
        return ownerProfileType;
    }


    public String ownershipContextCode() {
        return ownershipContextCode;
    }


    public RiskLevel riskLevelSnapshot() {
        return riskLevelSnapshot;
    }


    public PartyCatalogStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
