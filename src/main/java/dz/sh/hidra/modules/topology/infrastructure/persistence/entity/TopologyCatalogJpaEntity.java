/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCatalogJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Persistence row representation of a topology controlled vocabulary catalog entry.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import java.time.Instant;

/**
 * Persistence row representation of a topology controlled vocabulary catalog entry.
 *
 * <p>Business role:
 * Carries one persisted topology catalog entry from one of the multilingual catalog tables introduced
 * by V003, such as product type, facility type, node type, valve type, equipment type, or connection
 * type.
 *
 * <p>Architecture role:
 * This infrastructure class is used by topology catalog repository adapters as a neutral row model.
 * V003 intentionally uses one physical table per catalog family, so this class is not mapped to one
 * concrete table directly.
 *
 * <p>Validation:
 * SQL table and column constraints protect persisted values. Domain validation is re-applied when
 * this row is mapped back to TopologyTypeCatalog.
 *
 * <p>Usage:
 * Use only inside topology persistence infrastructure.
 */
public class TopologyCatalogJpaEntity {

    private String id;
    private String catalogName;
    private String code;
    private String status;
    private Integer sortOrder;
    private Boolean systemDefined;
    private Instant createdAt;
    private Instant updatedAt;

    public TopologyCatalogJpaEntity() {
        // Required by persistence mappers and repository projections.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getSystemDefined() {
        return systemDefined;
    }

    public void setSystemDefined(Boolean systemDefined) {
        this.systemDefined = systemDefined;
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
