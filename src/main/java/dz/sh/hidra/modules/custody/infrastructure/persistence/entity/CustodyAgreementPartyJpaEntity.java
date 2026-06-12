/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyAgreementPartyJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyAgreementParty.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for CustodyAgreementParty.
     */
    @Entity
    @Table(name = "hidra_custody_agreement_party")
    public class CustodyAgreementPartyJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "agreement_id", nullable = false, length = 80)
    private String agreementId;

    @Column(name = "party_role_id", nullable = false, length = 80)
    private String partyRoleId;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Column(name = "party_code_snapshot", nullable = true, length = 160)
    private String partyCodeSnapshot;

    @Column(name = "party_name_snapshot", nullable = false, length = 255)
    private String partyNameSnapshot;

    @Column(name = "party_role_code_snapshot", nullable = true, length = 80)
    private String partyRoleCodeSnapshot;

    @Column(name = "ownership_share_percent", nullable = true, precision = 10, scale = 6)
    private BigDecimal ownershipSharePercent;

    @Column(name = "effective_from", nullable = true)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected CustodyAgreementPartyJpaEntity() {
            // Required by JPA.
        }

        public CustodyAgreementPartyJpaEntity(
                String id,
            String agreementId,
            String partyRoleId,
            String partyId,
            String partyCodeSnapshot,
            String partyNameSnapshot,
            String partyRoleCodeSnapshot,
            BigDecimal ownershipSharePercent,
            Instant effectiveFrom,
            Instant effectiveTo,
            Instant createdAt
        ) {
            this.id = id;
        this.agreementId = agreementId;
        this.partyRoleId = partyRoleId;
        this.partyId = partyId;
        this.partyCodeSnapshot = partyCodeSnapshot;
        this.partyNameSnapshot = partyNameSnapshot;
        this.partyRoleCodeSnapshot = partyRoleCodeSnapshot;
        this.ownershipSharePercent = ownershipSharePercent;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String agreementId() {
        return agreementId;
    }


    public String partyRoleId() {
        return partyRoleId;
    }


    public String partyId() {
        return partyId;
    }


    public String partyCodeSnapshot() {
        return partyCodeSnapshot;
    }


    public String partyNameSnapshot() {
        return partyNameSnapshot;
    }


    public String partyRoleCodeSnapshot() {
        return partyRoleCodeSnapshot;
    }


    public BigDecimal ownershipSharePercent() {
        return ownershipSharePercent;
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

    }
