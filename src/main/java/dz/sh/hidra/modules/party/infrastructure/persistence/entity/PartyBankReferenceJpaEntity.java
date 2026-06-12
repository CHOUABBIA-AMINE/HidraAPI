/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyBankReferenceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyBankReference.
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
     * Database-backed JPA entity for PartyBankReference.
     */
    @Entity
    @Table(name = "hidra_party_bank_reference")
    public class PartyBankReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Column(name = "bank_name", nullable = true, length = 255)
    private String bankName;

    @Column(name = "account_reference_masked", nullable = true, length = 160)
    private String accountReferenceMasked;

    @Column(name = "iban_masked", nullable = true, length = 160)
    private String ibanMasked;

    @Column(name = "swift_code", nullable = true, length = 80)
    private String swiftCode;

    @Column(name = "country_code", nullable = true, length = 3)
    private String countryCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private BankReferenceStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyBankReferenceJpaEntity() {
            // Required by JPA.
        }

        public PartyBankReferenceJpaEntity(
                String id,
            String partyId,
            String bankName,
            String accountReferenceMasked,
            String ibanMasked,
            String swiftCode,
            String countryCode,
            BankReferenceStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.bankName = bankName;
        this.accountReferenceMasked = accountReferenceMasked;
        this.ibanMasked = ibanMasked;
        this.swiftCode = swiftCode;
        this.countryCode = countryCode;
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


    public String bankName() {
        return bankName;
    }


    public String accountReferenceMasked() {
        return accountReferenceMasked;
    }


    public String ibanMasked() {
        return ibanMasked;
    }


    public String swiftCode() {
        return swiftCode;
    }


    public String countryCode() {
        return countryCode;
    }


    public BankReferenceStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
