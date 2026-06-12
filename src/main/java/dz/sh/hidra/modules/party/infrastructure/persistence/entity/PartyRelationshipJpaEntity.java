/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRelationshipJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyRelationship.
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
     * Database-backed JPA entity for PartyRelationship.
     */
    @Entity
    @Table(name = "hidra_party_relationship")
    public class PartyRelationshipJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "source_party_id", nullable = false, length = 80)
    private String sourcePartyId;

    @Column(name = "target_party_id", nullable = false, length = 80)
    private String targetPartyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship_type", nullable = false, length = 80)
    private PartyRelationshipType relationshipType;

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

        protected PartyRelationshipJpaEntity() {
            // Required by JPA.
        }

        public PartyRelationshipJpaEntity(
                String id,
            String sourcePartyId,
            String targetPartyId,
            PartyRelationshipType relationshipType,
            Instant validFrom,
            Instant validTo,
            PartyCatalogStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.sourcePartyId = sourcePartyId;
        this.targetPartyId = targetPartyId;
        this.relationshipType = relationshipType;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String sourcePartyId() {
        return sourcePartyId;
    }


    public String targetPartyId() {
        return targetPartyId;
    }


    public PartyRelationshipType relationshipType() {
        return relationshipType;
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
