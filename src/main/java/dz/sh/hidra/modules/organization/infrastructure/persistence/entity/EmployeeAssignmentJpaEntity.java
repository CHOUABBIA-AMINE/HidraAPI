/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for EmployeeAssignment.
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
     * Database-backed JPA entity for EmployeeAssignment.
     */
    @Entity
    @Table(name = "hidra_org_employee_assignment")
    public class EmployeeAssignmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "employee_id", nullable = false, length = 80)
    private String employeeId;

    @Column(name = "organization_unit_id", nullable = false, length = 80)
    private String organizationUnitId;

    @Column(name = "position_id", nullable = false, length = 80)
    private String positionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_type", nullable = false, length = 80)
    private AssignmentType assignmentType;

    @Column(name = "operational_scope_type", nullable = true, length = 80)
    private String operationalScopeType;

    @Column(name = "operational_scope_id", nullable = true, length = 120)
    private String operationalScopeId;

    @Column(name = "operational_scope_code", nullable = true, length = 120)
    private String operationalScopeCode;

    @Column(name = "operational_scope_name", nullable = true, length = 255)
    private String operationalScopeName;

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

        protected EmployeeAssignmentJpaEntity() {
            // Required by JPA.
        }

        public EmployeeAssignmentJpaEntity(
                String id,
            String employeeId,
            String organizationUnitId,
            String positionId,
            AssignmentType assignmentType,
            String operationalScopeType,
            String operationalScopeId,
            String operationalScopeCode,
            String operationalScopeName,
            Instant validFrom,
            Instant validTo,
            AssignmentStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.employeeId = employeeId;
        this.organizationUnitId = organizationUnitId;
        this.positionId = positionId;
        this.assignmentType = assignmentType;
        this.operationalScopeType = operationalScopeType;
        this.operationalScopeId = operationalScopeId;
        this.operationalScopeCode = operationalScopeCode;
        this.operationalScopeName = operationalScopeName;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String employeeId() {
        return employeeId;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String positionId() {
        return positionId;
    }


    public AssignmentType assignmentType() {
        return assignmentType;
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
