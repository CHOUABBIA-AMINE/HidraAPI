/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogTranslationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for WorkflowCatalogTranslation.
     */
    @Entity
    @Table(name = "hidra_workflow_type_translation")
    public class WorkflowCatalogTranslationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "type_id", nullable = false, length = 80)
    private String typeId;

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

        protected WorkflowCatalogTranslationJpaEntity() {
            // Required by JPA.
        }

        public WorkflowCatalogTranslationJpaEntity(
                String id,
            String typeId,
            String locale,
            String name,
            String description,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.typeId = typeId;
        this.locale = locale;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String typeId() {
        return typeId;
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
