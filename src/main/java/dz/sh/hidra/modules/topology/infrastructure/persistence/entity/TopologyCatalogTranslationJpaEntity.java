/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCatalogTranslationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Persistence row representation of a localized topology catalog translation.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import java.time.Instant;

/**
 * Persistence row representation of a localized topology catalog translation.
 *
 * <p>Business role:
 * Carries one localized label and optional description for a persisted topology catalog entry.
 *
 * <p>Architecture role:
 * This infrastructure class is used by topology catalog repository adapters as a neutral row model.
 * V003 intentionally uses one translation table per catalog family, so this class is not mapped to one
 * concrete table directly.
 *
 * <p>Validation:
 * SQL constraints protect required values. Domain validation is re-applied when this row is mapped
 * back to TopologyTypeTranslation.
 *
 * <p>Usage:
 * Use only inside topology persistence infrastructure.
 */
public class TopologyCatalogTranslationJpaEntity {

    private String id;
    private String typeId;
    private String locale;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;

    public TopologyCatalogTranslationJpaEntity() {
        // Required by persistence mappers and repository projections.
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
