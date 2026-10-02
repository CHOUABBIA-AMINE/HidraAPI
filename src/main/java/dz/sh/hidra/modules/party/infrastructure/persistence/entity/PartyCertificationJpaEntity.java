/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyCertificationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyCertification.
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
     * Database-backed JPA entity for PartyCertification.
     */
    @Entity
    @Table(name = "hidra_party_certification")
    public class PartyCertificationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Column(name = "certification_code", nullable = false, length = 120)
    private String certificationCode;

    @Column(name = "certification_body_party_id", nullable = true, length = 80)
    private String certificationBodyPartyId;

    @Column(name = "certificate_number", nullable = true, length = 160)
    private String certificateNumber;

    @Column(name = "issued_at", nullable = true)
    private Instant issuedAt;

    @Column(name = "expires_at", nullable = true)
    private Instant expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PartyCertificationStatus status;

    @Column(name = "document_reference_id", nullable = true, length = 120)
    private String documentReferenceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyCertificationJpaEntity() {
            // Required by JPA.
        }

        public PartyCertificationJpaEntity(
                String id,
            String partyId,
            String certificationCode,
            String certificationBodyPartyId,
            String certificateNumber,
            Instant issuedAt,
            Instant expiresAt,
            PartyCertificationStatus status,
            String documentReferenceId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.certificationCode = certificationCode;
        this.certificationBodyPartyId = certificationBodyPartyId;
        this.certificateNumber = certificateNumber;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.status = status;
        this.documentReferenceId = documentReferenceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String partyId() {
        return partyId;
    }


    public String certificationCode() {
        return certificationCode;
    }


    public String certificationBodyPartyId() {
        return certificationBodyPartyId;
    }


    public String certificateNumber() {
        return certificateNumber;
    }


    public Instant issuedAt() {
        return issuedAt;
    }


    public Instant expiresAt() {
        return expiresAt;
    }


    public PartyCertificationStatus status() {
        return status;
    }


    public String documentReferenceId() {
        return documentReferenceId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
