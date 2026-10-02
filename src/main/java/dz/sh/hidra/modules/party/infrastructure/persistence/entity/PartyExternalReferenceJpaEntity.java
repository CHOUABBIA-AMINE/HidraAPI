/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyExternalReferenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyExternalReference.
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
     * Database-backed JPA entity for PartyExternalReference.
     */
    @Entity
    @Table(name = "hidra_party_external_reference")
    public class PartyExternalReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "external_system_type", nullable = false, length = 80)
    private ExternalSystemType externalSystemType;

    @Column(name = "external_system_code", nullable = false, length = 120)
    private String externalSystemCode;

    @Column(name = "external_reference", nullable = false, length = 255)
    private String externalReference;

    @Column(name = "external_label_snapshot", nullable = true, length = 255)
    private String externalLabelSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PartyCatalogStatus status;

    @Column(name = "last_synchronized_at", nullable = true)
    private Instant lastSynchronizedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyExternalReferenceJpaEntity() {
            // Required by JPA.
        }

        public PartyExternalReferenceJpaEntity(
                String id,
            String partyId,
            ExternalSystemType externalSystemType,
            String externalSystemCode,
            String externalReference,
            String externalLabelSnapshot,
            PartyCatalogStatus status,
            Instant lastSynchronizedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.externalSystemType = externalSystemType;
        this.externalSystemCode = externalSystemCode;
        this.externalReference = externalReference;
        this.externalLabelSnapshot = externalLabelSnapshot;
        this.status = status;
        this.lastSynchronizedAt = lastSynchronizedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String partyId() {
        return partyId;
    }


    public ExternalSystemType externalSystemType() {
        return externalSystemType;
    }


    public String externalSystemCode() {
        return externalSystemCode;
    }


    public String externalReference() {
        return externalReference;
    }


    public String externalLabelSnapshot() {
        return externalLabelSnapshot;
    }


    public PartyCatalogStatus status() {
        return status;
    }


    public Instant lastSynchronizedAt() {
        return lastSynchronizedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
