/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyContactPersonJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyContactPerson.
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
     * Database-backed JPA entity for PartyContactPerson.
     */
    @Entity
    @Table(name = "hidra_party_contact_person")
    public class PartyContactPersonJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(name = "job_title", nullable = true, length = 255)
    private String jobTitle;

    @Column(name = "email_address", nullable = true, length = 254)
    private String emailAddress;

    @Column(name = "phone_number", nullable = true, length = 80)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PartyContactPersonStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyContactPersonJpaEntity() {
            // Required by JPA.
        }

        public PartyContactPersonJpaEntity(
                String id,
            String partyId,
            String fullName,
            String jobTitle,
            String emailAddress,
            String phoneNumber,
            PartyContactPersonStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
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


    public String fullName() {
        return fullName;
    }


    public String jobTitle() {
        return jobTitle;
    }


    public String emailAddress() {
        return emailAddress;
    }


    public String phoneNumber() {
        return phoneNumber;
    }


    public PartyContactPersonStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
