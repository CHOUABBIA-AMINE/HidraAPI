/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentRetentionRecordJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for DocumentRetentionRecord.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.entity;

import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for DocumentRetentionRecord.
     */
    @Entity
    @Table(name = "hidra_documents_retention_record")
    public class DocumentRetentionRecordJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "document_id", nullable = false, length = 80)
    private String documentId;

    @Column(name = "retention_policy_id", nullable = false, length = 80)
    private String retentionPolicyId;

    @Column(name = "retention_class_id", nullable = false, length = 80)
    private String retentionClassId;

    @Column(name = "retain_until", nullable = true)
    private LocalDate retainUntil;

    @Column(name = "legal_hold", nullable = false)
    private boolean legalHold;

    @Column(name = "legal_hold_reason", nullable = true, length = 500)
    private String legalHoldReason;

    @Column(name = "archived_at", nullable = true)
    private Instant archivedAt;

    @Column(name = "archive_storage_object_id", nullable = true, length = 80)
    private String archiveStorageObjectId;

    @Column(name = "disposal_allowed_from", nullable = true)
    private LocalDate disposalAllowedFrom;

    @Column(name = "disposed_at", nullable = true)
    private Instant disposedAt;

    @Column(name = "disposed_by_actor_id", nullable = true, length = 80)
    private String disposedByActorId;

        protected DocumentRetentionRecordJpaEntity() {
            // Required by JPA.
        }

        public DocumentRetentionRecordJpaEntity(
                String id,
            String documentId,
            String retentionPolicyId,
            String retentionClassId,
            LocalDate retainUntil,
            boolean legalHold,
            String legalHoldReason,
            Instant archivedAt,
            String archiveStorageObjectId,
            LocalDate disposalAllowedFrom,
            Instant disposedAt,
            String disposedByActorId
        ) {
            this.id = id;
        this.documentId = documentId;
        this.retentionPolicyId = retentionPolicyId;
        this.retentionClassId = retentionClassId;
        this.retainUntil = retainUntil;
        this.legalHold = legalHold;
        this.legalHoldReason = legalHoldReason;
        this.archivedAt = archivedAt;
        this.archiveStorageObjectId = archiveStorageObjectId;
        this.disposalAllowedFrom = disposalAllowedFrom;
        this.disposedAt = disposedAt;
        this.disposedByActorId = disposedByActorId;
        }


    public String id() {
        return id;
    }


    public String documentId() {
        return documentId;
    }


    public String retentionPolicyId() {
        return retentionPolicyId;
    }


    public String retentionClassId() {
        return retentionClassId;
    }


    public LocalDate retainUntil() {
        return retainUntil;
    }


    public boolean legalHold() {
        return legalHold;
    }


    public String legalHoldReason() {
        return legalHoldReason;
    }


    public Instant archivedAt() {
        return archivedAt;
    }


    public String archiveStorageObjectId() {
        return archiveStorageObjectId;
    }


    public LocalDate disposalAllowedFrom() {
        return disposalAllowedFrom;
    }


    public Instant disposedAt() {
        return disposedAt;
    }


    public String disposedByActorId() {
        return disposedByActorId;
    }

    }
