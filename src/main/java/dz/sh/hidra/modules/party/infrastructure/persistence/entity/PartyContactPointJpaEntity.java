/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyContactPointJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyContactPoint.
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
     * Database-backed JPA entity for PartyContactPoint.
     */
    @Entity
    @Table(name = "hidra_party_contact_point")
    public class PartyContactPointJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_point_type", nullable = false, length = 80)
    private PartyContactPointType contactPointType;

    @Column(name = "label", nullable = true, length = 255)
    private String label;

    @Column(name = "value", nullable = false, length = 255)
    private String value;

    @Column(name = "primary_contact", nullable = false)
    private boolean primaryContact;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PartyCatalogStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyContactPointJpaEntity() {
            // Required by JPA.
        }

        public PartyContactPointJpaEntity(
                String id,
            String partyId,
            PartyContactPointType contactPointType,
            String label,
            String value,
            boolean primaryContact,
            PartyCatalogStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.contactPointType = contactPointType;
        this.label = label;
        this.value = value;
        this.primaryContact = primaryContact;
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


    public PartyContactPointType contactPointType() {
        return contactPointType;
    }


    public String label() {
        return label;
    }


    public String value() {
        return value;
    }


    public boolean primaryContact() {
        return primaryContact;
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
