/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentExternalReferenceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for DocumentExternalReference.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.entity;

import dz.sh.hidra.modules.documents.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for DocumentExternalReference.
     */
    @Entity
    @Table(name = "hidra_documents_external_reference")
    public class DocumentExternalReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "document_id", nullable = false, length = 80)
    private String documentId;

    @Column(name = "document_version_id", nullable = true, length = 80)
    private String documentVersionId;

    @Column(name = "external_system_id", nullable = false, length = 80)
    private String externalSystemId;

    @Column(name = "external_object_type", nullable = false, length = 80)
    private String externalObjectType;

    @Column(name = "external_object_id", nullable = false, length = 160)
    private String externalObjectId;

    @Column(name = "external_object_code", nullable = true, length = 160)
    private String externalObjectCode;

    @Column(name = "external_url_reference", nullable = true, length = 1000)
    private String externalUrlReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status", nullable = false, length = 40)
    private DocumentExternalSyncStatus syncStatus;

    @Column(name = "last_synced_at", nullable = true)
    private Instant lastSyncedAt;

        protected DocumentExternalReferenceJpaEntity() {
            // Required by JPA.
        }

        public DocumentExternalReferenceJpaEntity(
                String id,
            String documentId,
            String documentVersionId,
            String externalSystemId,
            String externalObjectType,
            String externalObjectId,
            String externalObjectCode,
            String externalUrlReference,
            DocumentExternalSyncStatus syncStatus,
            Instant lastSyncedAt
        ) {
            this.id = id;
        this.documentId = documentId;
        this.documentVersionId = documentVersionId;
        this.externalSystemId = externalSystemId;
        this.externalObjectType = externalObjectType;
        this.externalObjectId = externalObjectId;
        this.externalObjectCode = externalObjectCode;
        this.externalUrlReference = externalUrlReference;
        this.syncStatus = syncStatus;
        this.lastSyncedAt = lastSyncedAt;
        }


    public String id() {
        return id;
    }


    public String documentId() {
        return documentId;
    }


    public String documentVersionId() {
        return documentVersionId;
    }


    public String externalSystemId() {
        return externalSystemId;
    }


    public String externalObjectType() {
        return externalObjectType;
    }


    public String externalObjectId() {
        return externalObjectId;
    }


    public String externalObjectCode() {
        return externalObjectCode;
    }


    public String externalUrlReference() {
        return externalUrlReference;
    }


    public DocumentExternalSyncStatus syncStatus() {
        return syncStatus;
    }


    public Instant lastSyncedAt() {
        return lastSyncedAt;
    }

    }
