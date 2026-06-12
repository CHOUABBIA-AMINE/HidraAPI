/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDocumentReferenceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyDocumentReference.
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
     * Database-backed JPA entity for CustodyDocumentReference.
     */
    @Entity
    @Table(name = "hidra_custody_document_reference")
    public class CustodyDocumentReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "target_type", nullable = false, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = false, length = 80)
    private String targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false, length = 80)
    private CustodyDocumentType documentType;

    @Column(name = "document_reference_id", nullable = false, length = 80)
    private String documentReferenceId;

    @Column(name = "document_code_snapshot", nullable = true, length = 160)
    private String documentCodeSnapshot;

    @Column(name = "document_title_snapshot", nullable = true, length = 255)
    private String documentTitleSnapshot;

    @Column(name = "attached_at", nullable = false)
    private Instant attachedAt;

    @Column(name = "attached_by_actor_id", nullable = true, length = 80)
    private String attachedByActorId;

        protected CustodyDocumentReferenceJpaEntity() {
            // Required by JPA.
        }

        public CustodyDocumentReferenceJpaEntity(
                String id,
            String targetType,
            String targetId,
            CustodyDocumentType documentType,
            String documentReferenceId,
            String documentCodeSnapshot,
            String documentTitleSnapshot,
            Instant attachedAt,
            String attachedByActorId
        ) {
            this.id = id;
        this.targetType = targetType;
        this.targetId = targetId;
        this.documentType = documentType;
        this.documentReferenceId = documentReferenceId;
        this.documentCodeSnapshot = documentCodeSnapshot;
        this.documentTitleSnapshot = documentTitleSnapshot;
        this.attachedAt = attachedAt;
        this.attachedByActorId = attachedByActorId;
        }


    public String id() {
        return id;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public CustodyDocumentType documentType() {
        return documentType;
    }


    public String documentReferenceId() {
        return documentReferenceId;
    }


    public String documentCodeSnapshot() {
        return documentCodeSnapshot;
    }


    public String documentTitleSnapshot() {
        return documentTitleSnapshot;
    }


    public Instant attachedAt() {
        return attachedAt;
    }


    public String attachedByActorId() {
        return attachedByActorId;
    }

    }
