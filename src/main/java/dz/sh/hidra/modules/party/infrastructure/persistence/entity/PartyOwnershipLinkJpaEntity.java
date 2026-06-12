/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyOwnershipLinkJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyOwnershipLink.
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
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PartyOwnershipLink.
     */
    @Entity
    @Table(name = "hidra_party_ownership_link")
    public class PartyOwnershipLinkJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "owner_party_id", nullable = false, length = 80)
    private String ownerPartyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 80)
    private OwnershipTargetType targetType;

    @Column(name = "target_id", nullable = false, length = 120)
    private String targetId;

    @Column(name = "target_code_snapshot", nullable = true, length = 120)
    private String targetCodeSnapshot;

    @Column(name = "target_name_snapshot", nullable = true, length = 255)
    private String targetNameSnapshot;

    @Column(name = "ownership_percentage", nullable = true, precision = 10, scale = 4)
    private BigDecimal ownershipPercentage;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PartyCatalogStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyOwnershipLinkJpaEntity() {
            // Required by JPA.
        }

        public PartyOwnershipLinkJpaEntity(
                String id,
            String ownerPartyId,
            OwnershipTargetType targetType,
            String targetId,
            String targetCodeSnapshot,
            String targetNameSnapshot,
            BigDecimal ownershipPercentage,
            Instant validFrom,
            Instant validTo,
            PartyCatalogStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.ownerPartyId = ownerPartyId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.targetNameSnapshot = targetNameSnapshot;
        this.ownershipPercentage = ownershipPercentage;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String ownerPartyId() {
        return ownerPartyId;
    }


    public OwnershipTargetType targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public String targetCodeSnapshot() {
        return targetCodeSnapshot;
    }


    public String targetNameSnapshot() {
        return targetNameSnapshot;
    }


    public BigDecimal ownershipPercentage() {
        return ownershipPercentage;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
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
