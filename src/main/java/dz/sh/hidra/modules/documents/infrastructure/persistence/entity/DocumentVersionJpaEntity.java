/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentVersionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for DocumentVersion.
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
import java.time.LocalDate;

    /**
     * Database-backed JPA entity for DocumentVersion.
     */
    @Entity
    @Table(name = "hidra_documents_document_version")
    public class DocumentVersionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "document_id", nullable = false, length = 80)
    private String documentId;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Column(name = "version_label", nullable = true, length = 80)
    private String versionLabel;

    @Column(name = "title_ar", nullable = true, length = 240)
    private String titleAr;

    @Column(name = "title_fr", nullable = true, length = 240)
    private String titleFr;

    @Column(name = "title_en", nullable = true, length = 240)
    private String titleEn;

    @Column(name = "description", nullable = true, length = 1000)
    private String description;

    @Column(name = "storage_object_id", nullable = false, length = 80)
    private String storageObjectId;

    @Column(name = "mime_type", nullable = false, length = 120)
    private String mimeType;

    @Column(name = "original_filename", nullable = false, length = 255)
    private String originalFilename;

    @Column(name = "file_extension", nullable = true, length = 20)
    private String fileExtension;

    @Column(name = "file_size_bytes", nullable = false)
    private long fileSizeBytes;

    @Column(name = "checksum_algorithm", nullable = false, length = 40)
    private String checksumAlgorithm;

    @Column(name = "checksum_value", nullable = false, length = 160)
    private String checksumValue;

    @Column(name = "language_code", nullable = true, length = 10)
    private String languageCode;

    @Column(name = "document_date", nullable = true)
    private LocalDate documentDate;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "version_status", nullable = false, length = 40)
    private DocumentVersionStatus versionStatus;

    @Column(name = "uploaded_by_actor_id", nullable = false, length = 80)
    private String uploadedByActorId;

    @Column(name = "uploaded_by_display_name_snapshot", nullable = false, length = 160)
    private String uploadedByDisplayNameSnapshot;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

    @Column(name = "approved_by_workflow_instance_id", nullable = true, length = 80)
    private String approvedByWorkflowInstanceId;

    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;

    @Column(name = "superseded_by_version_id", nullable = true, length = 80)
    private String supersededByVersionId;

        protected DocumentVersionJpaEntity() {
            // Required by JPA.
        }

        public DocumentVersionJpaEntity(
                String id,
            String documentId,
            int versionNumber,
            String versionLabel,
            String titleAr,
            String titleFr,
            String titleEn,
            String description,
            String storageObjectId,
            String mimeType,
            String originalFilename,
            String fileExtension,
            long fileSizeBytes,
            String checksumAlgorithm,
            String checksumValue,
            String languageCode,
            LocalDate documentDate,
            LocalDate effectiveFrom,
            LocalDate effectiveTo,
            DocumentVersionStatus versionStatus,
            String uploadedByActorId,
            String uploadedByDisplayNameSnapshot,
            Instant uploadedAt,
            String approvedByWorkflowInstanceId,
            Instant approvedAt,
            String supersededByVersionId
        ) {
            this.id = id;
        this.documentId = documentId;
        this.versionNumber = versionNumber;
        this.versionLabel = versionLabel;
        this.titleAr = titleAr;
        this.titleFr = titleFr;
        this.titleEn = titleEn;
        this.description = description;
        this.storageObjectId = storageObjectId;
        this.mimeType = mimeType;
        this.originalFilename = originalFilename;
        this.fileExtension = fileExtension;
        this.fileSizeBytes = fileSizeBytes;
        this.checksumAlgorithm = checksumAlgorithm;
        this.checksumValue = checksumValue;
        this.languageCode = languageCode;
        this.documentDate = documentDate;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.versionStatus = versionStatus;
        this.uploadedByActorId = uploadedByActorId;
        this.uploadedByDisplayNameSnapshot = uploadedByDisplayNameSnapshot;
        this.uploadedAt = uploadedAt;
        this.approvedByWorkflowInstanceId = approvedByWorkflowInstanceId;
        this.approvedAt = approvedAt;
        this.supersededByVersionId = supersededByVersionId;
        }


    public String id() {
        return id;
    }


    public String documentId() {
        return documentId;
    }


    public int versionNumber() {
        return versionNumber;
    }


    public String versionLabel() {
        return versionLabel;
    }


    public String titleAr() {
        return titleAr;
    }


    public String titleFr() {
        return titleFr;
    }


    public String titleEn() {
        return titleEn;
    }


    public String description() {
        return description;
    }


    public String storageObjectId() {
        return storageObjectId;
    }


    public String mimeType() {
        return mimeType;
    }


    public String originalFilename() {
        return originalFilename;
    }


    public String fileExtension() {
        return fileExtension;
    }


    public long fileSizeBytes() {
        return fileSizeBytes;
    }


    public String checksumAlgorithm() {
        return checksumAlgorithm;
    }


    public String checksumValue() {
        return checksumValue;
    }


    public String languageCode() {
        return languageCode;
    }


    public LocalDate documentDate() {
        return documentDate;
    }


    public LocalDate effectiveFrom() {
        return effectiveFrom;
    }


    public LocalDate effectiveTo() {
        return effectiveTo;
    }


    public DocumentVersionStatus versionStatus() {
        return versionStatus;
    }


    public String uploadedByActorId() {
        return uploadedByActorId;
    }


    public String uploadedByDisplayNameSnapshot() {
        return uploadedByDisplayNameSnapshot;
    }


    public Instant uploadedAt() {
        return uploadedAt;
    }


    public String approvedByWorkflowInstanceId() {
        return approvedByWorkflowInstanceId;
    }


    public Instant approvedAt() {
        return approvedAt;
    }


    public String supersededByVersionId() {
        return supersededByVersionId;
    }

    }
