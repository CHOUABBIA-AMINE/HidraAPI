/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed entity for a scoped responsibility assignment.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

/**
 * Database-backed entity for ResponsibilityAssignment.
 *
 * <p>{@code scopeId} is the new canonical registry FK. The four legacy columns
 * remain mapped temporarily so existing rows can be inspected/reconciled without
 * deleting data. Canonical application mapping ignores those legacy values.</p>
 */
@Entity
@Table(name = "hidra_org_responsibility_assignment")
public class ResponsibilityAssignmentJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "responsibility_type", nullable = false, length = 80)
    private ResponsibilityType responsibilityType;

    @Column(name = "assignee_type", nullable = false, length = 80)
    private String assigneeType;

    @Column(name = "assignee_id", nullable = false, length = 80)
    private String assigneeId;

    @Column(name = "scope_id")
    private Long scopeId;

    /**
     * Legacy migration-only columns. Do not use these as canonical identity.
     */
    @Column(name = "operational_scope_type", insertable = false, updatable = false, length = 80)
    private String operationalScopeType;

    @Column(name = "operational_scope_id", insertable = false, updatable = false, length = 120)
    private String operationalScopeId;

    @Column(name = "operational_scope_code", insertable = false, updatable = false, length = 120)
    private String operationalScopeCode;

    @Column(name = "operational_scope_name", insertable = false, updatable = false, length = 255)
    private String operationalScopeName;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private AssignmentStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected ResponsibilityAssignmentJpaEntity() {
        // Required by JPA.
    }

    public ResponsibilityAssignmentJpaEntity(
            String id,
            ResponsibilityType responsibilityType,
            String assigneeType,
            String assigneeId,
            Long scopeId,
            String description,
            Instant validFrom,
            Instant validTo,
            AssignmentStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.responsibilityType = responsibilityType;
        this.assigneeType = assigneeType;
        this.assigneeId = assigneeId;
        this.scopeId = scopeId;
        this.description = description;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Transitional constructor retained only so the older generated organization
     * mapper still compiles until it is removed. It never treats a non-numeric
     * legacy owner target ID as a registry ID.
     */
    @Deprecated(forRemoval = true)
    public ResponsibilityAssignmentJpaEntity(
            String id,
            ResponsibilityType responsibilityType,
            String assigneeType,
            String assigneeId,
            String operationalScopeType,
            String operationalScopeId,
            String operationalScopeCode,
            String operationalScopeName,
            String description,
            Instant validFrom,
            Instant validTo,
            AssignmentStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this(
                id,
                responsibilityType,
                assigneeType,
                assigneeId,
                parseRegistryId(operationalScopeId),
                description,
                validFrom,
                validTo,
                status,
                createdAt,
                updatedAt
        );
    }

    public String id() {
        return id;
    }

    public ResponsibilityType responsibilityType() {
        return responsibilityType;
    }

    public String assigneeType() {
        return assigneeType;
    }

    public String assigneeId() {
        return assigneeId;
    }

    public Long scopeId() {
        return scopeId;
    }

    @Deprecated(forRemoval = true)
    public String operationalScopeType() {
        return operationalScopeType;
    }

    @Deprecated(forRemoval = true)
    public String operationalScopeId() {
        return scopeId == null ? operationalScopeId : scopeId.toString();
    }

    @Deprecated(forRemoval = true)
    public String operationalScopeCode() {
        return operationalScopeCode;
    }

    @Deprecated(forRemoval = true)
    public String operationalScopeName() {
        return operationalScopeName;
    }

    public String description() {
        return description;
    }

    public Instant validFrom() {
        return validFrom;
    }

    public Instant validTo() {
        return validTo;
    }

    public AssignmentStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static Long parseRegistryId(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            long parsed = Long.parseLong(value.trim());
            return parsed > 0 ? parsed : null;
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
