/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentCatalogTranslationJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for DocumentCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for DocumentCatalogTranslation.
     */
    @Entity
    @Table(name = "hidra_documents_catalog_translation")
    public class DocumentCatalogTranslationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "catalog_entry_id", nullable = false, length = 80)
    private String catalogEntryId;

    @Column(name = "locale", nullable = false, length = 10)
    private String locale;

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected DocumentCatalogTranslationJpaEntity() {
            // Required by JPA.
        }

        public DocumentCatalogTranslationJpaEntity(
                String id,
            String catalogEntryId,
            String locale,
            String name,
            String description,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.catalogEntryId = catalogEntryId;
        this.locale = locale;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String catalogEntryId() {
        return catalogEntryId;
    }


    public String locale() {
        return locale;
    }


    public String name() {
        return name;
    }


    public String description() {
        return description;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
