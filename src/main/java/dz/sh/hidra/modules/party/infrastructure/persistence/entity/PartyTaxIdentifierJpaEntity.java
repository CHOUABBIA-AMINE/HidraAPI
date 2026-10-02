/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyTaxIdentifierJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyTaxIdentifier.
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
     * Database-backed JPA entity for PartyTaxIdentifier.
     */
    @Entity
    @Table(name = "hidra_party_tax_identifier")
    public class PartyTaxIdentifierJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_identifier_type", nullable = false, length = 80)
    private TaxIdentifierType taxIdentifierType;

    @Column(name = "identifier_value", nullable = false, length = 160)
    private String identifierValue;

    @Column(name = "country_code", nullable = true, length = 3)
    private String countryCode;

    @Column(name = "primary_identifier", nullable = false)
    private boolean primaryIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PartyCatalogStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyTaxIdentifierJpaEntity() {
            // Required by JPA.
        }

        public PartyTaxIdentifierJpaEntity(
                String id,
            String partyId,
            TaxIdentifierType taxIdentifierType,
            String identifierValue,
            String countryCode,
            boolean primaryIdentifier,
            PartyCatalogStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.taxIdentifierType = taxIdentifierType;
        this.identifierValue = identifierValue;
        this.countryCode = countryCode;
        this.primaryIdentifier = primaryIdentifier;
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


    public TaxIdentifierType taxIdentifierType() {
        return taxIdentifierType;
    }


    public String identifierValue() {
        return identifierValue;
    }


    public String countryCode() {
        return countryCode;
    }


    public boolean primaryIdentifier() {
        return primaryIdentifier;
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
