/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeRepositoryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Unit tests for EmployeeRepositoryAdapter persistence mapping and delegation.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.EmployeeJpaRepository;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.EmployeeRepositoryAdapter;

/**
 * Tests the employee persistence adapter.
 *
 * <p>Business role:
 * Verifies that employee aggregates are translated to persistence entities and retrieved back
 * through the EmployeeRepository application outbound port adapter.
 *
 * <p>Architecture role:
 * This is an infrastructure persistence unit test. It uses a mocked Spring Data repository to keep
 * the test independent from database configuration while still exercising the adapter and mapper.
 *
 * <p>Validation:
 * The test verifies save delegation, employee number lookup, identity-independent employee
 * persistence, and active assignment lookup delegation.
 */
class EmployeeRepositoryAdapterTest {

    private final OrganizationPersistenceMapper mapper = new OrganizationPersistenceMapper();

    @Test
    void shouldSaveEmployeeAggregateThroughJpaRepository() {
        EmployeeJpaRepository jpaRepository = mock(EmployeeJpaRepository.class);
        EmployeeRepositoryAdapter adapter = new EmployeeRepositoryAdapter(jpaRepository, mapper);
        Employee employee = employee("EMP-PER-001");

        when(jpaRepository.save(any(EmployeeJpaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Employee savedEmployee = adapter.save(employee);

        assertEquals(employee.id(), savedEmployee.id());
        assertEquals("EMP-PER-001", savedEmployee.employeeNumber().value());
        assertEquals("Abir MEDJERAB", savedEmployee.fullName().value());
        assertEquals("abir.medjerab@example.com", savedEmployee.email().orElseThrow().value());
        verify(jpaRepository).save(any(EmployeeJpaEntity.class));
    }

    @Test
    void shouldFindEmployeeByEmployeeNumber() {
        EmployeeJpaRepository jpaRepository = mock(EmployeeJpaRepository.class);
        EmployeeRepositoryAdapter adapter = new EmployeeRepositoryAdapter(jpaRepository, mapper);
        Employee employee = employee("EMP-PER-002");

        when(jpaRepository.findByEmployeeNumber("EMP-PER-002")).thenReturn(Optional.of(mapper.toEntity(employee)));

        Optional<Employee> foundEmployee = adapter.findByEmployeeNumber(EmployeeNumber.of("EMP-PER-002"));

        assertTrue(foundEmployee.isPresent());
        assertEquals(employee.id(), foundEmployee.orElseThrow().id());
        assertEquals("EMP-PER-002", foundEmployee.orElseThrow().employeeNumber().value());
        verify(jpaRepository).findByEmployeeNumber("EMP-PER-002");
    }

    @Test
    void shouldDelegateExistsByEmployeeNumber() {
        EmployeeJpaRepository jpaRepository = mock(EmployeeJpaRepository.class);
        EmployeeRepositoryAdapter adapter = new EmployeeRepositoryAdapter(jpaRepository, mapper);

        when(jpaRepository.existsByEmployeeNumber("EMP-PER-003")).thenReturn(true);

        assertTrue(adapter.existsByEmployeeNumber(EmployeeNumber.of("EMP-PER-003")));
        verify(jpaRepository).existsByEmployeeNumber("EMP-PER-003");
    }

    @Test
    void shouldDelegateAssignedEmployeeLookupToJpaRepository() {
        EmployeeJpaRepository jpaRepository = mock(EmployeeJpaRepository.class);
        EmployeeRepositoryAdapter adapter = new EmployeeRepositoryAdapter(jpaRepository, mapper);
        Employee employee = employee("EMP-PER-004");

        when(jpaRepository.findAssignedToOrganizationUnit("ou-test-001"))
                .thenReturn(List.of(mapper.toEntity(employee)));

        List<Employee> assignedEmployees = adapter.findAssignedToOrganizationUnit(
                dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId.of("ou-test-001"));

        assertEquals(1, assignedEmployees.size());
        assertEquals("EMP-PER-004", assignedEmployees.get(0).employeeNumber().value());
        verify(jpaRepository).findAssignedToOrganizationUnit("ou-test-001");
    }

    private static Employee employee(String employeeNumber) {
        return Employee.register(
                EmployeeNumber.of(employeeNumber),
                EmployeeFullName.of("Abir MEDJERAB"),
                EmployeeEmail.of("abir.medjerab@example.com"),
                null);
    }
}
