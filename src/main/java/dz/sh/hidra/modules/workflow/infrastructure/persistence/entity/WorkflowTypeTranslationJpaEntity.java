/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTypeTranslationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : JPA entity for workflow controlled vocabulary translations.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for workflow controlled vocabulary translations.
 *
 * <p>Architecture role:
 * Persistence-only workflow representation. It must not be exposed to domain, application, or REST
 * layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_workflow_type_translation")
public class WorkflowTypeTranslationJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "type_id", nullable = false)
    private String typeId;

    @Column(name = "locale", nullable = false)
    private String locale;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected WorkflowTypeTranslationJpaEntity() {
        // Required by JPA.
    }

    public WorkflowTypeTranslationJpaEntity(
            String id,
            String typeId,
            String locale,
            String name,
            String description,
            Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.typeId = typeId;
        this.locale = locale;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
