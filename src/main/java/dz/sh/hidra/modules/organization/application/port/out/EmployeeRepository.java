/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Outbound employee persistence port.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import java.util.List;
import java.util.Optional;

import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.IdentityUserReference;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;

/**
 * Defines the outbound persistence contract for employees.
 *
 * <p>Business role:
 * This port persists and retrieves real operational employees and their organization assignments
 * without exposing storage technology to the application layer.
 *
 * <p>Architecture role:
 * This is an application outbound port. Infrastructure adapters implement it later. It must not
 * depend on Spring Data, JPA entities, REST DTOs, identity domain objects, or topology domain objects.
 *
 * <p>Validation:
 * Implementations must preserve employee-number uniqueness and return domain aggregates, not
 * persistence entities.
 *
 * <p>Usage:
 * Application services depend on this interface when loading or saving employee aggregates.
 */
public interface EmployeeRepository {

    /**
     * Saves an employee aggregate.
     *
     * @param employee employee to save
     * @return saved employee
     */
    Employee save(Employee employee);

    /**
     * Finds an employee by identifier.
     *
     * @param id employee identifier
     * @return employee when found
     */
    Optional<Employee> findById(EmployeeId id);

    /**
     * Finds an employee by business employee number.
     *
     * @param employeeNumber employee number
     * @return employee when found
     */
    Optional<Employee> findByEmployeeNumber(EmployeeNumber employeeNumber);

    /**
     * Finds an employee by neutral identity user reference.
     *
     * @param identityUserReference neutral identity user reference
     * @return employee when found
     */
    Optional<Employee> findByIdentityUserReference(IdentityUserReference identityUserReference);

    /**
     * Checks whether an employee number already exists.
     *
     * @param employeeNumber employee number
     * @return true when the employee number exists
     */
    boolean existsByEmployeeNumber(EmployeeNumber employeeNumber);

    /**
     * Lists employees assigned to an organization unit.
     *
     * @param organizationUnitId organization unit identifier
     * @return assigned employees
     */
    List<Employee> findAssignedToOrganizationUnit(OrganizationUnitId organizationUnitId);
}
