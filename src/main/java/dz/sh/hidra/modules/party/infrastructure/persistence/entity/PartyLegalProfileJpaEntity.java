/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyLegalProfileJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyLegalProfile.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PartyLegalProfile.
     */
    @Entity
    @Table(name = "hidra_party_legal_profile")
    public class PartyLegalProfileJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Column(name = "legal_name", nullable = false, length = 255)
    private String legalName;

    @Column(name = "trade_name", nullable = true, length = 255)
    private String tradeName;

    @Column(name = "legal_form_code", nullable = true, length = 120)
    private String legalFormCode;

    @Column(name = "registration_summary", nullable = true, columnDefinition = "text")
    private String registrationSummary;

    @Column(name = "jurisdiction_code", nullable = true, length = 120)
    private String jurisdictionCode;

    @Column(name = "country_code", nullable = false, length = 3)
    private String countryCode;

    @Column(name = "effective_from", nullable = true)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyLegalProfileJpaEntity() {
            // Required by JPA.
        }

        public PartyLegalProfileJpaEntity(
                String id,
            String partyId,
            String legalName,
            String tradeName,
            String legalFormCode,
            String registrationSummary,
            String jurisdictionCode,
            String countryCode,
            Instant effectiveFrom,
            Instant effectiveTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.legalName = legalName;
        this.tradeName = tradeName;
        this.legalFormCode = legalFormCode;
        this.registrationSummary = registrationSummary;
        this.jurisdictionCode = jurisdictionCode;
        this.countryCode = countryCode;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String partyId() {
        return partyId;
    }


    public String legalName() {
        return legalName;
    }


    public String tradeName() {
        return tradeName;
    }


    public String legalFormCode() {
        return legalFormCode;
    }


    public String registrationSummary() {
        return registrationSummary;
    }


    public String jurisdictionCode() {
        return jurisdictionCode;
    }


    public String countryCode() {
        return countryCode;
    }


    public Instant effectiveFrom() {
        return effectiveFrom;
    }


    public Instant effectiveTo() {
        return effectiveTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
