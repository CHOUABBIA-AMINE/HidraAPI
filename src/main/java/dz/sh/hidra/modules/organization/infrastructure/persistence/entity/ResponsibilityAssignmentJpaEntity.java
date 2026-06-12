/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignmentJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ResponsibilityAssignment.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ResponsibilityAssignment.
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

    @Column(name = "operational_scope_type", nullable = false, length = 80)
    private String operationalScopeType;

    @Column(name = "operational_scope_id", nullable = false, length = 120)
    private String operationalScopeId;

    @Column(name = "operational_scope_code", nullable = true, length = 120)
    private String operationalScopeCode;

    @Column(name = "operational_scope_name", nullable = true, length = 255)
    private String operationalScopeName;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
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
            this.id = id;
        this.responsibilityType = responsibilityType;
        this.assigneeType = assigneeType;
        this.assigneeId = assigneeId;
        this.operationalScopeType = operationalScopeType;
        this.operationalScopeId = operationalScopeId;
        this.operationalScopeCode = operationalScopeCode;
        this.operationalScopeName = operationalScopeName;
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


    public String assigneeType() {
        return assigneeType;
    }


    public String assigneeId() {
        return assigneeId;
    }


    public String operationalScopeType() {
        return operationalScopeType;
    }


    public String operationalScopeId() {
        return operationalScopeId;
    }


    public String operationalScopeCode() {
        return operationalScopeCode;
    }


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

    }
