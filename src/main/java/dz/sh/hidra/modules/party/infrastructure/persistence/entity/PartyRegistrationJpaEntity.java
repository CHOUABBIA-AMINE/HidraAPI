/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRegistrationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyRegistration.
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
     * Database-backed JPA entity for PartyRegistration.
     */
    @Entity
    @Table(name = "hidra_party_registration")
    public class PartyRegistrationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_type", nullable = false, length = 80)
    private PartyRegistrationType registrationType;

    @Column(name = "registration_number", nullable = false, length = 160)
    private String registrationNumber;

    @Column(name = "issuing_authority", nullable = true, length = 255)
    private String issuingAuthority;

    @Column(name = "country_code", nullable = true, length = 3)
    private String countryCode;

    @Column(name = "issued_at", nullable = true)
    private Instant issuedAt;

    @Column(name = "expires_at", nullable = true)
    private Instant expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PartyCatalogStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyRegistrationJpaEntity() {
            // Required by JPA.
        }

        public PartyRegistrationJpaEntity(
                String id,
            String partyId,
            PartyRegistrationType registrationType,
            String registrationNumber,
            String issuingAuthority,
            String countryCode,
            Instant issuedAt,
            Instant expiresAt,
            PartyCatalogStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.registrationType = registrationType;
        this.registrationNumber = registrationNumber;
        this.issuingAuthority = issuingAuthority;
        this.countryCode = countryCode;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
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


    public PartyRegistrationType registrationType() {
        return registrationType;
    }


    public String registrationNumber() {
        return registrationNumber;
    }


    public String issuingAuthority() {
        return issuingAuthority;
    }


    public String countryCode() {
        return countryCode;
    }


    public Instant issuedAt() {
        return issuedAt;
    }


    public Instant expiresAt() {
        return expiresAt;
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
