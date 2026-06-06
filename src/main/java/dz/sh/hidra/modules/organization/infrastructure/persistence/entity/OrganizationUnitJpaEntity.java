/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : JPA representation of an organization unit.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA representation of an organization unit.
 */
@Entity
@Table(name = "hidra_org_unit")
public class OrganizationUnitJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, unique = true, length = 80)
    private String code;

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Column(name = "status", nullable = false, length = 40)
    private String status;

    @Column(name = "unit_type_id", nullable = false, length = 80)
    private String typeId;

    @Column(name = "parent_id", length = 80)
    private String parentId;

    @Column(name = "operational_scope_type", length = 80)
    private String operationalScopeType;

    @Column(name = "operational_scope_id", length = 120)
    private String operationalScopeId;

    @Column(name = "operational_scope_code", length = 120)
    private String operationalScopeCode;

    @Column(name = "operational_scope_name", length = 160)
    private String operationalScopeName;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public OrganizationUnitJpaEntity() {
        // Required by JPA.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOperationalScopeType() {
        return operationalScopeType;
    }

    public void setOperationalScopeType(String operationalScopeType) {
        this.operationalScopeType = operationalScopeType;
    }

    public String getOperationalScopeId() {
        return operationalScopeId;
    }

    public void setOperationalScopeId(String operationalScopeId) {
        this.operationalScopeId = operationalScopeId;
    }

    public String getOperationalScopeCode() {
        return operationalScopeCode;
    }

    public void setOperationalScopeCode(String operationalScopeCode) {
        this.operationalScopeCode = operationalScopeCode;
    }

    public String getOperationalScopeName() {
        return operationalScopeName;
    }

    public void setOperationalScopeName(String operationalScopeName) {
        this.operationalScopeName = operationalScopeName;
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
