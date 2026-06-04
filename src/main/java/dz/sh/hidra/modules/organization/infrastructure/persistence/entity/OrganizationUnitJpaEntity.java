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
 *
 * <p>Business role:
 * This entity stores an organization unit such as a division, department, region, station as an
 * organization unit, or team. The optional operational scope fields are neutral references and do
 * not make organization own topology assets.
 *
 * <p>Architecture role:
 * This class belongs to the organization infrastructure persistence layer and is used only by
 * repositories, adapters, and persistence mappers.
 *
 * <p>Validation:
 * Domain validation is performed before mapping. Database constraints protect required fields and
 * uniqueness of organization unit code.
 *
 * <p>Usage:
 * Use only inside persistence infrastructure. Do not expose this class through application or API.
 */
@Entity
@Table(name = "hidra_org_unit")
public class OrganizationUnitJpaEntity {

    /** Stable organization unit identifier. */
    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    /** Unique organization unit business code. */
    @Column(name = "code", nullable = false, unique = true, length = 80)
    private String code;

    /** Organization unit display name. */
    @Column(name = "name", nullable = false, length = 160)
    private String name;

    /** Organization unit lifecycle status. */
    @Column(name = "status", nullable = false, length = 40)
    private String status;

    /** Organization unit type, including STATION for station-as-organization-unit. */
    @Column(name = "unit_type", nullable = false, length = 60)
    private String type;

    /** Optional parent organization unit identifier. */
    @Column(name = "parent_id", length = 80)
    private String parentId;

    /** Optional neutral operational scope type. */
    @Column(name = "operational_scope_type", length = 80)
    private String operationalScopeType;

    /** Optional neutral operational scope identifier. */
    @Column(name = "operational_scope_id", length = 120)
    private String operationalScopeId;

    /** Optional neutral operational scope business code. */
    @Column(name = "operational_scope_code", length = 120)
    private String operationalScopeCode;

    /** Optional neutral operational scope display name. */
    @Column(name = "operational_scope_name", length = 160)
    private String operationalScopeName;

    /** Creation instant. */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** Last update instant. */
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
