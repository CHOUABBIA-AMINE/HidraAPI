/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyAgreementJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyAgreement.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.entity;

import dz.sh.hidra.modules.custody.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for CustodyAgreement.
     */
    @Entity
    @Table(name = "hidra_custody_agreement")
    public class CustodyAgreementJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "agreement_number", nullable = false, length = 80)
    private String agreementNumber;

    @Column(name = "agreement_type_id", nullable = false, length = 80)
    private String agreementTypeId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "transfer_point_id", nullable = false, length = 80)
    private String transferPointId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private CustodyAgreementStatus status;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "terms_snapshot_json", nullable = true, columnDefinition = "jsonb")
    private String termsSnapshotJson;

    @Column(name = "document_reference_id", nullable = true, length = 80)
    private String documentReferenceId;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected CustodyAgreementJpaEntity() {
            // Required by JPA.
        }

        public CustodyAgreementJpaEntity(
                String id,
            String agreementNumber,
            String agreementTypeId,
            String title,
            String description,
            String transferPointId,
            CustodyAgreementStatus status,
            Instant validFrom,
            Instant validTo,
            String termsSnapshotJson,
            String documentReferenceId,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.agreementNumber = agreementNumber;
        this.agreementTypeId = agreementTypeId;
        this.title = title;
        this.description = description;
        this.transferPointId = transferPointId;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.termsSnapshotJson = termsSnapshotJson;
        this.documentReferenceId = documentReferenceId;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String agreementNumber() {
        return agreementNumber;
    }


    public String agreementTypeId() {
        return agreementTypeId;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String transferPointId() {
        return transferPointId;
    }


    public CustodyAgreementStatus status() {
        return status;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public String termsSnapshotJson() {
        return termsSnapshotJson;
    }


    public String documentReferenceId() {
        return documentReferenceId;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
