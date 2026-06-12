/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for Document.
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
     * Database-backed JPA entity for Document.
     */
    @Entity
    @Table(name = "hidra_documents_document")
    public class DocumentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "title_ar", nullable = true, length = 240)
    private String titleAr;

    @Column(name = "title_fr", nullable = false, length = 240)
    private String titleFr;

    @Column(name = "title_en", nullable = true, length = 240)
    private String titleEn;

    @Column(name = "document_type_id", nullable = false, length = 80)
    private String documentTypeId;

    @Column(name = "document_category_id", nullable = true, length = 80)
    private String documentCategoryId;

    @Column(name = "classification_id", nullable = false, length = 80)
    private String classificationId;

    @Column(name = "confidentiality_level", nullable = false)
    private int confidentialityLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private DocumentStatus status;

    @Column(name = "current_version_id", nullable = true, length = 80)
    private String currentVersionId;

    @Column(name = "owner_module", nullable = true, length = 80)
    private String ownerModule;

    @Column(name = "owner_target_type_code", nullable = true, length = 80)
    private String ownerTargetTypeCode;

    @Column(name = "owner_target_id", nullable = true, length = 120)
    private String ownerTargetId;

    @Column(name = "owner_target_code_snapshot", nullable = true, length = 120)
    private String ownerTargetCodeSnapshot;

    @Column(name = "owner_target_label_snapshot", nullable = true, length = 240)
    private String ownerTargetLabelSnapshot;

    @Column(name = "created_by_actor_id", nullable = false, length = 80)
    private String createdByActorId;

    @Column(name = "created_by_display_name_snapshot", nullable = false, length = 160)
    private String createdByDisplayNameSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "archived_at", nullable = true)
    private Instant archivedAt;

        protected DocumentJpaEntity() {
            // Required by JPA.
        }

        public DocumentJpaEntity(
                String id,
            String code,
            String titleAr,
            String titleFr,
            String titleEn,
            String documentTypeId,
            String documentCategoryId,
            String classificationId,
            int confidentialityLevel,
            DocumentStatus status,
            String currentVersionId,
            String ownerModule,
            String ownerTargetTypeCode,
            String ownerTargetId,
            String ownerTargetCodeSnapshot,
            String ownerTargetLabelSnapshot,
            String createdByActorId,
            String createdByDisplayNameSnapshot,
            Instant createdAt,
            Instant updatedAt,
            Instant archivedAt
        ) {
            this.id = id;
        this.code = code;
        this.titleAr = titleAr;
        this.titleFr = titleFr;
        this.titleEn = titleEn;
        this.documentTypeId = documentTypeId;
        this.documentCategoryId = documentCategoryId;
        this.classificationId = classificationId;
        this.confidentialityLevel = confidentialityLevel;
        this.status = status;
        this.currentVersionId = currentVersionId;
        this.ownerModule = ownerModule;
        this.ownerTargetTypeCode = ownerTargetTypeCode;
        this.ownerTargetId = ownerTargetId;
        this.ownerTargetCodeSnapshot = ownerTargetCodeSnapshot;
        this.ownerTargetLabelSnapshot = ownerTargetLabelSnapshot;
        this.createdByActorId = createdByActorId;
        this.createdByDisplayNameSnapshot = createdByDisplayNameSnapshot;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.archivedAt = archivedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
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


    public String documentTypeId() {
        return documentTypeId;
    }


    public String documentCategoryId() {
        return documentCategoryId;
    }


    public String classificationId() {
        return classificationId;
    }


    public int confidentialityLevel() {
        return confidentialityLevel;
    }


    public DocumentStatus status() {
        return status;
    }


    public String currentVersionId() {
        return currentVersionId;
    }


    public String ownerModule() {
        return ownerModule;
    }


    public String ownerTargetTypeCode() {
        return ownerTargetTypeCode;
    }


    public String ownerTargetId() {
        return ownerTargetId;
    }


    public String ownerTargetCodeSnapshot() {
        return ownerTargetCodeSnapshot;
    }


    public String ownerTargetLabelSnapshot() {
        return ownerTargetLabelSnapshot;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public String createdByDisplayNameSnapshot() {
        return createdByDisplayNameSnapshot;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }


    public Instant archivedAt() {
        return archivedAt;
    }

    }
