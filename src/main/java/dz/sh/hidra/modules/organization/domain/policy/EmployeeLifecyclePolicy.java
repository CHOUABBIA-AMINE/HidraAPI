/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeLifecyclePolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Domain policy validating employee lifecycle transitions.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

import java.util.Objects;

import dz.sh.hidra.modules.organization.domain.exception.EmployeeLifecycleException;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.value.EmploymentStatus;

/**
 * Validates employee lifecycle transitions.
 *
 * <p>Business role:
 * This policy protects the lifecycle of real operational people represented by the organization
 * module. It does not manage login accounts, identity roles, permissions, or authentication state.
 *
 * <p>Architecture role:
 * This is a pure domain policy. It depends only on organization domain objects and value objects.
 *
 * <p>Validation:
 * It enforces allowed transitions between REGISTERED, ACTIVE, SUSPENDED, and DISABLED states.
 *
 * <p>Usage:
 * Use this policy before executing employee lifecycle commands when validation must be explicit
 * outside the aggregate method.
 */
public final class EmployeeLifecyclePolicy {

    /**
     * Ensures the employee can be activated.
     *
     * @param employee employee to validate
     */
    public void ensureCanActivate(Employee employee) {
        Objects.requireNonNull(employee, "Employee must not be null.");

        if (employee.status() == EmploymentStatus.DISABLED) {
            throw new EmployeeLifecycleException("Disabled employee cannot be activated.");
        }
    }

    /**
     * Ensures the employee can be suspended.
     *
     * @param employee employee to validate
     */
    public void ensureCanSuspend(Employee employee) {
        Objects.requireNonNull(employee, "Employee must not be null.");

        if (employee.status() != EmploymentStatus.ACTIVE) {
            throw new EmployeeLifecycleException("Only active employee can be suspended.");
        }
    }

    /**
     * Ensures the employee can be disabled.
     *
     * @param employee employee to validate
     */
    public void ensureCanDisable(Employee employee) {
        Objects.requireNonNull(employee, "Employee must not be null.");

        if (employee.status() == EmploymentStatus.DISABLED) {
            throw new EmployeeLifecycleException("Employee is already disabled.");
        }
    }

    /**
     * Ensures the employee can receive new operational responsibility.
     *
     * @param employee employee to validate
     */
    public void ensureCanReceiveOperationalResponsibility(Employee employee) {
        Objects.requireNonNull(employee, "Employee must not be null.");

        if (!employee.status().allowsOperationalAssignment()) {
            throw new EmployeeLifecycleException(
                    "Employee cannot receive new operational responsibility in status " + employee.status() + ".");
        }
    }
}
