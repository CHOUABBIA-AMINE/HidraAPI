/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for Party.
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
     * Database-backed JPA entity for Party.
     */
    @Entity
    @Table(name = "hidra_party_party")
    public class PartyJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "party_type_id", nullable = false, length = 80)
    private String partyTypeId;

    @Column(name = "legal_name", nullable = false, length = 255)
    private String legalName;

    @Column(name = "trade_name", nullable = true, length = 255)
    private String tradeName;

    @Column(name = "short_name", nullable = true, length = 160)
    private String shortName;

    @Column(name = "country_code", nullable = false, length = 3)
    private String countryCode;

    @Column(name = "jurisdiction_code", nullable = true, length = 120)
    private String jurisdictionCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PartyStatus status;

    @Column(name = "primary_role_code_snapshot", nullable = true, length = 120)
    private String primaryRoleCodeSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyJpaEntity() {
            // Required by JPA.
        }

        public PartyJpaEntity(
                String id,
            String code,
            String partyTypeId,
            String legalName,
            String tradeName,
            String shortName,
            String countryCode,
            String jurisdictionCode,
            PartyStatus status,
            String primaryRoleCodeSnapshot,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.partyTypeId = partyTypeId;
        this.legalName = legalName;
        this.tradeName = tradeName;
        this.shortName = shortName;
        this.countryCode = countryCode;
        this.jurisdictionCode = jurisdictionCode;
        this.status = status;
        this.primaryRoleCodeSnapshot = primaryRoleCodeSnapshot;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String partyTypeId() {
        return partyTypeId;
    }


    public String legalName() {
        return legalName;
    }


    public String tradeName() {
        return tradeName;
    }


    public String shortName() {
        return shortName;
    }


    public String countryCode() {
        return countryCode;
    }


    public String jurisdictionCode() {
        return jurisdictionCode;
    }


    public PartyStatus status() {
        return status;
    }


    public String primaryRoleCodeSnapshot() {
        return primaryRoleCodeSnapshot;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
