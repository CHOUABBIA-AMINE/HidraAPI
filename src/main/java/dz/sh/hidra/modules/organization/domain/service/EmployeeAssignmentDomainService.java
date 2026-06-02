/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.service
 *
 * @Description : Domain service coordinating employee assignment rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.service;

import java.util.Objects;

import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.policy.EmployeeAssignmentPolicy;

/**
 * Coordinates employee assignment validation rules.
 *
 * <p>Business role:
 * This service validates assignment of employees to organization units and positions, including
 * station team assignments and optional operational scope references.
 *
 * <p>Architecture role:
 * This is a pure organization domain service. It coordinates domain models and policies without
 * depending on Spring, JPA, REST DTOs, identity, topology, platform, or infrastructure code.
 *
 * <p>Validation:
 * It delegates employee, organization unit, position, and assignment invariant checks to
 * <code>EmployeeAssignmentPolicy</code>.
 *
 * <p>Usage:
 * Use this service when an assignment operation needs validation across employee, unit, and
 * position domain objects.
 */
public final class EmployeeAssignmentDomainService {

    /**
     * Policy protecting employee assignment invariants.
     */
    private final EmployeeAssignmentPolicy employeeAssignmentPolicy;

    /**
     * Creates the employee assignment domain service.
     *
     * @param employeeAssignmentPolicy employee assignment policy
     */
    public EmployeeAssignmentDomainService(EmployeeAssignmentPolicy employeeAssignmentPolicy) {
        this.employeeAssignmentPolicy = Objects.requireNonNull(
                employeeAssignmentPolicy,
                "Employee assignment policy must not be null.");
    }

    /**
     * Assigns an employee after validating all assignment rules.
     *
     * @param employee employee aggregate
     * @param organizationUnit target organization unit
     * @param position target position
     * @param assignment assignment to add
     * @return updated employee aggregate
     */
    public Employee assignEmployeeToUnit(
            Employee employee,
            OrganizationUnit organizationUnit,
            Position position,
            EmployeeAssignment assignment) {

        employeeAssignmentPolicy.ensureAssignmentAllowed(employee, organizationUnit, position, assignment);
        return employee.addAssignment(assignment);
    }
}
