/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyAddressJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyAddress.
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
     * Database-backed JPA entity for PartyAddress.
     */
    @Entity
    @Table(name = "hidra_party_address")
    public class PartyAddressJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false, length = 80)
    private PartyAddressType addressType;

    @Column(name = "country_code", nullable = false, length = 3)
    private String countryCode;

    @Column(name = "state_or_region", nullable = true, length = 160)
    private String stateOrRegion;

    @Column(name = "city", nullable = true, length = 160)
    private String city;

    @Column(name = "postal_code", nullable = true, length = 40)
    private String postalCode;

    @Column(name = "address_line1", nullable = true, length = 255)
    private String addressLine1;

    @Column(name = "address_line2", nullable = true, length = 255)
    private String addressLine2;

    @Column(name = "primary_address", nullable = false)
    private boolean primaryAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PartyCatalogStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyAddressJpaEntity() {
            // Required by JPA.
        }

        public PartyAddressJpaEntity(
                String id,
            String partyId,
            PartyAddressType addressType,
            String countryCode,
            String stateOrRegion,
            String city,
            String postalCode,
            String addressLine1,
            String addressLine2,
            boolean primaryAddress,
            PartyCatalogStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.addressType = addressType;
        this.countryCode = countryCode;
        this.stateOrRegion = stateOrRegion;
        this.city = city;
        this.postalCode = postalCode;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.primaryAddress = primaryAddress;
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


    public PartyAddressType addressType() {
        return addressType;
    }


    public String countryCode() {
        return countryCode;
    }


    public String stateOrRegion() {
        return stateOrRegion;
    }


    public String city() {
        return city;
    }


    public String postalCode() {
        return postalCode;
    }


    public String addressLine1() {
        return addressLine1;
    }


    public String addressLine2() {
        return addressLine2;
    }


    public boolean primaryAddress() {
        return primaryAddress;
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
