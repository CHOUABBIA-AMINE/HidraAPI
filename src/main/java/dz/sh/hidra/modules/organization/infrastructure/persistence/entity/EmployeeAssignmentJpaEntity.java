/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : JPA representation of an employee assignment.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * JPA representation of an employee assignment.
 *
 * <p>Business role:
 * This entity stores assignment of an employee to an organization unit and position, optionally
 * linked to a neutral operational scope reference.
 *
 * <p>Architecture role:
 * This class belongs to organization persistence infrastructure. It is owned by the employee
 * persistence aggregate and must not be exposed as an API or application contract.
 *
 * <p>Validation:
 * Domain assignment rules are enforced before persistence. Database constraints protect required
 * identifiers and effective dates.
 *
 * <p>Usage:
 * Use only through EmployeeJpaEntity and persistence mappers.
 */
@Entity
@Table(name = "hidra_org_employee_assignment")
public class EmployeeAssignmentJpaEntity {

    /** Stable assignment identifier. */
    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    /** Owning employee persistence entity. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeJpaEntity employee;

    /** Copied employee id for simple query and mapping support. */
    @Column(name = "employee_id", nullable = false, insertable = false, updatable = false, length = 80)
    private String employeeId;

    /** Target organization unit identifier. */
    @Column(name = "organization_unit_id", nullable = false, length = 80)
    private String organizationUnitId;

    /** Target position identifier. */
    @Column(name = "position_id", nullable = false, length = 80)
    private String positionId;

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

    /** Assignment effective start date. */
    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    /** Optional assignment effective end date. */
    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    public EmployeeAssignmentJpaEntity() {
        // Required by JPA.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmployeeJpaEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeJpaEntity employee) {
        this.employee = employee;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getOrganizationUnitId() {
        return organizationUnitId;
    }

    public void setOrganizationUnitId(String organizationUnitId) {
        this.organizationUnitId = organizationUnitId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
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

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }
}
