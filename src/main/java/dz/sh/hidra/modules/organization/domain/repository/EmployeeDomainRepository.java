/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeDomainRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.repository
 *
 * @Description : Domain repository contract for employee lookup.
 *
 */
package dz.sh.hidra.modules.organization.domain.repository;

import java.util.List;
import java.util.Optional;

import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.IdentityUserReference;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;

/**
 * Defines the domain lookup contract for employees.
 *
 * <p>Business role:
 * This contract allows organization domain/application logic to look up employees, including
 * employees assigned to operational organization units, station teams, regions, or departments.
 *
 * <p>Architecture role:
 * This is a domain repository contract. It is not a Spring Data repository and must not import
 * JPA, Spring, REST DTOs, identity, topology, platform, or infrastructure adapter code.
 *
 * <p>Validation:
 * Implementations must preserve employee-number uniqueness and must not return persistence
 * entities to domain callers. Assignment and reporting-line rules remain domain policy or
 * aggregate responsibilities.
 *
 * <p>Usage:
 * Implement this contract later through infrastructure adapters or bridge it through application
 * outbound ports when persistence is introduced.
 */
public interface EmployeeDomainRepository {

    /**
     * Finds an employee by stable identifier.
     *
     * @param id employee identifier
     * @return matching employee when present
     */
    Optional<Employee> findById(EmployeeId id);

    /**
     * Finds an employee by unique employee number.
     *
     * @param employeeNumber employee number
     * @return matching employee when present
     */
    Optional<Employee> findByEmployeeNumber(EmployeeNumber employeeNumber);

    /**
     * Finds an employee by neutral identity user reference.
     *
     * @param identityUserReference neutral identity user reference
     * @return matching employee when present
     */
    Optional<Employee> findByIdentityUserReference(IdentityUserReference identityUserReference);

    /**
     * Checks whether an employee exists with the supplied employee number.
     *
     * @param employeeNumber employee number
     * @return true when an employee exists
     */
    boolean existsByEmployeeNumber(EmployeeNumber employeeNumber);

    /**
     * Lists employees currently assigned to the supplied organization unit.
     *
     * @param organizationUnitId organization unit identifier
     * @return employees assigned to the organization unit
     */
    List<Employee> findAssignedToOrganizationUnit(OrganizationUnitId organizationUnitId);
}
