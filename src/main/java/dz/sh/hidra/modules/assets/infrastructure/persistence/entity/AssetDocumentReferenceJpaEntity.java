/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetDocumentReferenceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetDocumentReference.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import dz.sh.hidra.modules.assets.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AssetDocumentReference.
     */
    @Entity
    @Table(name = "hidra_asset_document_reference")
    public class AssetDocumentReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "maintainable_asset_id", nullable = false, length = 80)
    private String maintainableAssetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false, length = 80)
    private DocumentReferenceType documentType;

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

        protected AssetDocumentReferenceJpaEntity() {
            // Required by JPA.
        }

        public AssetDocumentReferenceJpaEntity(
                String id,
            String maintainableAssetId,
            DocumentReferenceType documentType,
            String documentReferenceId,
            String documentCodeSnapshot,
            String documentTitleSnapshot,
            Instant attachedAt,
            String attachedByActorId
        ) {
            this.id = id;
        this.maintainableAssetId = maintainableAssetId;
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


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public DocumentReferenceType documentType() {
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
