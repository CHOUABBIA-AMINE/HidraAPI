/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Persistence adapter implementing the employee repository port.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepository;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.IdentityUserReference;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;

/**
 * Persistence adapter implementing the employee repository port.
 *
 * <p>Business role:
 * Persists and retrieves employee aggregates for organization use cases.
 *
 * <p>Architecture role:
 * This class adapts the application outbound EmployeeRepository port to Spring Data JPA without
 * exposing JPA entities to application or domain code.
 *
 * <p>Validation:
 * Domain validation happens before persistence. This adapter delegates database constraint
 * enforcement to the database and Spring Data repository.
 *
 * <p>Usage:
 * Wire this adapter later in organization configuration.
 */
public final class EmployeeRepositoryAdapter implements EmployeeRepository {

    private final EmployeeJpaRepository employeeJpaRepository;
    private final OrganizationPersistenceMapper mapper;

    public EmployeeRepositoryAdapter(
            EmployeeJpaRepository employeeJpaRepository,
            OrganizationPersistenceMapper mapper) {

        this.employeeJpaRepository = Objects.requireNonNull(employeeJpaRepository, "Employee JPA repository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Organization persistence mapper must not be null.");
    }

    @Override
    public Employee save(Employee employee) {
        return mapper.toDomain(employeeJpaRepository.save(mapper.toEntity(employee)));
    }

    @Override
    public Optional<Employee> findById(EmployeeId id) {
        Objects.requireNonNull(id, "Employee id must not be null.");
        return employeeJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<Employee> findByEmployeeNumber(EmployeeNumber employeeNumber) {
        Objects.requireNonNull(employeeNumber, "Employee number must not be null.");
        return employeeJpaRepository.findByEmployeeNumber(employeeNumber.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<Employee> findByIdentityUserReference(IdentityUserReference identityUserReference) {
        Objects.requireNonNull(identityUserReference, "Identity user reference must not be null.");
        return employeeJpaRepository.findByIdentityUserReference(identityUserReference.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmployeeNumber(EmployeeNumber employeeNumber) {
        Objects.requireNonNull(employeeNumber, "Employee number must not be null.");
        return employeeJpaRepository.existsByEmployeeNumber(employeeNumber.value());
    }

    @Override
    public List<Employee> findAssignedToOrganizationUnit(OrganizationUnitId organizationUnitId) {
        Objects.requireNonNull(organizationUnitId, "Organization unit id must not be null.");
        return employeeJpaRepository.findAssignedToOrganizationUnit(organizationUnitId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
