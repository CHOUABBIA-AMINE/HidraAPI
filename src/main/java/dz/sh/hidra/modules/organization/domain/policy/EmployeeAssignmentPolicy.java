/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Domain policy validating employee assignment rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

import java.util.Objects;

import dz.sh.hidra.modules.organization.domain.exception.EmployeeAssignmentNotAllowedException;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.Position;

/**
 * Validates employee assignment rules.
 *
 * <p>Business role:
 * This policy protects the rule that only valid employees can be assigned to valid organization
 * units and positions, including station organization units and station teams.
 *
 * <p>Architecture role:
 * This is a pure domain policy. It coordinates already-loaded domain objects and does not depend on
 * persistence, API, identity, topology, Spring, JPA, or platform code.
 *
 * <p>Validation:
 * It validates employee activity, organization unit assignability, position assignability,
 * assignment ownership, and station operational scope consistency when relevant.
 *
 * <p>Usage:
 * Use this policy before adding an assignment to an employee aggregate.
 */
public final class EmployeeAssignmentPolicy {

    /**
     * Ensures an assignment is allowed.
     *
     * @param employee assigned employee aggregate
     * @param organizationUnit target organization unit
     * @param position target position
     * @param assignment assignment to validate
     */
    public void ensureAssignmentAllowed(
            Employee employee,
            OrganizationUnit organizationUnit,
            Position position,
            EmployeeAssignment assignment) {

        Objects.requireNonNull(employee, "Employee must not be null.");
        Objects.requireNonNull(organizationUnit, "Organization unit must not be null.");
        Objects.requireNonNull(position, "Position must not be null.");
        Objects.requireNonNull(assignment, "Employee assignment must not be null.");

        ensureAssignmentBelongsToEmployee(employee, assignment);
        ensureAssignmentTargetsUnit(organizationUnit, assignment);
        ensureAssignmentTargetsPosition(position, assignment);
        ensureEmployeeCanReceiveAssignment(employee);
        ensureOrganizationUnitCanReceiveAssignment(organizationUnit);
        ensurePositionCanBeAssigned(position);
        ensureStationAssignmentScopeIsConsistent(organizationUnit, assignment);
    }

    /**
     * Ensures the assignment belongs to the employee aggregate.
     *
     * @param employee employee aggregate
     * @param assignment assignment to validate
     */
    public void ensureAssignmentBelongsToEmployee(Employee employee, EmployeeAssignment assignment) {
        Objects.requireNonNull(employee, "Employee must not be null.");
        Objects.requireNonNull(assignment, "Employee assignment must not be null.");

        if (!employee.id().equals(assignment.employeeId())) {
            throw new EmployeeAssignmentNotAllowedException("Assignment employee id must match aggregate employee id.");
        }
    }

    private void ensureAssignmentTargetsUnit(OrganizationUnit organizationUnit, EmployeeAssignment assignment) {
        if (!organizationUnit.id().equals(assignment.organizationUnitId())) {
            throw new EmployeeAssignmentNotAllowedException("Assignment organization unit id must match target organization unit.");
        }
    }

    private void ensureAssignmentTargetsPosition(Position position, EmployeeAssignment assignment) {
        if (!position.id().equals(assignment.positionId())) {
            throw new EmployeeAssignmentNotAllowedException("Assignment position id must match target position.");
        }
    }

    private void ensureEmployeeCanReceiveAssignment(Employee employee) {
        if (!employee.status().allowsOperationalAssignment()) {
            throw new EmployeeAssignmentNotAllowedException(
                    "Employee cannot receive new assignment in status " + employee.status() + ".");
        }
    }

    private void ensureOrganizationUnitCanReceiveAssignment(OrganizationUnit organizationUnit) {
        if (!organizationUnit.status().allowsEmployeeAssignment()) {
            throw new EmployeeAssignmentNotAllowedException(
                    "Organization unit cannot receive assignments in status " + organizationUnit.status() + ".");
        }
    }

    private void ensurePositionCanBeAssigned(Position position) {
        if (!position.active()) {
            throw new EmployeeAssignmentNotAllowedException("Inactive position cannot be assigned to an employee.");
        }
    }

    private void ensureStationAssignmentScopeIsConsistent(OrganizationUnit organizationUnit, EmployeeAssignment assignment) {
        if (!organizationUnit.isStationOrganizationUnit()) {
            return;
        }

        assignment.optionalOperationalScopeReference().ifPresent(scope -> {
            if (!scope.isStationScope()) {
                throw new EmployeeAssignmentNotAllowedException(
                        "Assignment to a station organization unit must reference a station-like operational scope.");
            }
        });
    }
}
