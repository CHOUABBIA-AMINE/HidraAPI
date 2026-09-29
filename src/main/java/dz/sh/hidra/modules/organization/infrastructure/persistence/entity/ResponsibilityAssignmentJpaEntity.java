/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed entity for a canonical scoped responsibility assignment.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
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
 * <p>{@code scopeId} is the sole mapped operational-scope identity. Historical
 * {@code operational_scope_*} database columns remain a schema concern for ORG-032
 * and are intentionally not mapped by this entity.</p>
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

    @Enumerated(EnumType.STRING)
    @Column(name = "assignee_type", nullable = false, length = 80)
    private ResponsibilityAssigneeType assigneeType;

    @Column(name = "assignee_id", nullable = false, length = 80)
    private String assigneeId;

    @Column(name = "scope_id")
    private Long scopeId;

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
            ResponsibilityAssigneeType assigneeType,
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

    public String id() {
        return id;
    }

    public ResponsibilityType responsibilityType() {
        return responsibilityType;
    }

    public ResponsibilityAssigneeType assigneeType() {
        return assigneeType;
    }

    public String assigneeId() {
        return assigneeId;
    }

    public Long scopeId() {
        return scopeId;
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

}
