/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyComplianceStatusJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyComplianceStatus.
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
     * Database-backed JPA entity for PartyComplianceStatus.
     */
    @Entity
    @Table(name = "hidra_party_compliance_status")
    public class PartyComplianceStatusJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "compliance_status", nullable = false, length = 80)
    private ComplianceStatus complianceStatus;

    @Column(name = "screening_source", nullable = true, length = 160)
    private String screeningSource;

    @Column(name = "screening_reference", nullable = true, length = 160)
    private String screeningReference;

    @Column(name = "checked_at", nullable = false)
    private Instant checkedAt;

    @Column(name = "valid_until", nullable = true)
    private Instant validUntil;

    @Column(name = "notes", nullable = true, columnDefinition = "text")
    private String notes;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyComplianceStatusJpaEntity() {
            // Required by JPA.
        }

        public PartyComplianceStatusJpaEntity(
                String id,
            String partyId,
            ComplianceStatus complianceStatus,
            String screeningSource,
            String screeningReference,
            Instant checkedAt,
            Instant validUntil,
            String notes,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.complianceStatus = complianceStatus;
        this.screeningSource = screeningSource;
        this.screeningReference = screeningReference;
        this.checkedAt = checkedAt;
        this.validUntil = validUntil;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String partyId() {
        return partyId;
    }


    public ComplianceStatus complianceStatus() {
        return complianceStatus;
    }


    public String screeningSource() {
        return screeningSource;
    }


    public String screeningReference() {
        return screeningReference;
    }


    public Instant checkedAt() {
        return checkedAt;
    }


    public Instant validUntil() {
        return validUntil;
    }


    public String notes() {
        return notes;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
