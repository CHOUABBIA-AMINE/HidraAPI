/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyDocumentReferenceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyDocumentReference.
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
     * Database-backed JPA entity for PartyDocumentReference.
     */
    @Entity
    @Table(name = "hidra_party_document_reference")
    public class PartyDocumentReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_reference_type", nullable = false, length = 80)
    private DocumentReferenceType documentReferenceType;

    @Column(name = "document_id", nullable = false, length = 120)
    private String documentId;

    @Column(name = "document_code_snapshot", nullable = true, length = 120)
    private String documentCodeSnapshot;

    @Column(name = "document_title_snapshot", nullable = true, length = 255)
    private String documentTitleSnapshot;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PartyCatalogStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyDocumentReferenceJpaEntity() {
            // Required by JPA.
        }

        public PartyDocumentReferenceJpaEntity(
                String id,
            String partyId,
            DocumentReferenceType documentReferenceType,
            String documentId,
            String documentCodeSnapshot,
            String documentTitleSnapshot,
            Instant validFrom,
            Instant validTo,
            PartyCatalogStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.documentReferenceType = documentReferenceType;
        this.documentId = documentId;
        this.documentCodeSnapshot = documentCodeSnapshot;
        this.documentTitleSnapshot = documentTitleSnapshot;
        this.validFrom = validFrom;
        this.validTo = validTo;
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


    public DocumentReferenceType documentReferenceType() {
        return documentReferenceType;
    }


    public String documentId() {
        return documentId;
    }


    public String documentCodeSnapshot() {
        return documentCodeSnapshot;
    }


    public String documentTitleSnapshot() {
        return documentTitleSnapshot;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
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
