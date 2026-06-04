/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateEmployeeServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Unit tests for CreateEmployeeService.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.organization.application.command.CreateEmployeeCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepository;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.IdentityUserReference;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;

/**
 * Tests the employee creation application service.
 *
 * <p>Business role:
 * Verifies that employee creation creates an organization employee, enforces employee-number
 * uniqueness, and publishes a domain event.
 *
 * <p>Architecture role:
 * This is an application-layer unit test using in-memory fake outbound ports. It does not load
 * Spring, JPA, REST API, identity, topology, platform, or persistence infrastructure.
 */
class CreateEmployeeServiceTest {

    @Test
    void shouldCreateEmployeeAndPublishEvent() {
        InMemoryEmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
        CapturingDomainEventPublisher eventPublisher = new CapturingDomainEventPublisher();
        CreateEmployeeService service = new CreateEmployeeService(
                employeeRepository,
                eventPublisher,
                new OrganizationApplicationMapper());

        EmployeeDto createdEmployee = service.createEmployee(new CreateEmployeeCommand(
                EmployeeNumber.of("EMP-APP-001"),
                EmployeeFullName.of("Abir MEDJERAB"),
                EmployeeEmail.of("abir.medjerab@example.com"),
                IdentityUserReference.of("usr_001")));

        assertEquals("EMP-APP-001", createdEmployee.employeeNumber());
        assertEquals("Abir MEDJERAB", createdEmployee.fullName());
        assertEquals("REGISTERED", createdEmployee.status());
        assertEquals(1, employeeRepository.savedEmployees.size());
        assertEquals(1, eventPublisher.events.size());
        assertEquals("organization.employee.created", eventPublisher.events.get(0).eventType());
    }

    @Test
    void shouldRejectDuplicateEmployeeNumber() {
        InMemoryEmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
        employeeRepository.save(Employee.register(
                EmployeeNumber.of("EMP-APP-002"),
                EmployeeFullName.of("Existing Employee"),
                null,
                null));

        CreateEmployeeService service = new CreateEmployeeService(
                employeeRepository,
                new CapturingDomainEventPublisher(),
                new OrganizationApplicationMapper());

        CreateEmployeeCommand command = new CreateEmployeeCommand(
                EmployeeNumber.of("EMP-APP-002"),
                EmployeeFullName.of("Duplicate Employee"),
                null,
                null);

        assertThrows(BusinessRuleViolationException.class, () -> service.createEmployee(command));
    }

    private static final class InMemoryEmployeeRepository implements EmployeeRepository {

        private final List<Employee> savedEmployees = new ArrayList<>();

        @Override
        public Employee save(Employee employee) {
            savedEmployees.removeIf(existing -> existing.id().equals(employee.id()));
            savedEmployees.add(employee);
            return employee;
        }

        @Override
        public Optional<Employee> findById(EmployeeId id) {
            return savedEmployees.stream()
                    .filter(employee -> employee.id().equals(id))
                    .findFirst();
        }

        @Override
        public Optional<Employee> findByEmployeeNumber(EmployeeNumber employeeNumber) {
            return savedEmployees.stream()
                    .filter(employee -> employee.employeeNumber().equals(employeeNumber))
                    .findFirst();
        }

        @Override
        public Optional<Employee> findByIdentityUserReference(IdentityUserReference identityUserReference) {
            return savedEmployees.stream()
                    .filter(employee -> employee.identityUserReference()
                            .map(identityUserReference::equals)
                            .orElse(false))
                    .findFirst();
        }

        @Override
        public boolean existsByEmployeeNumber(EmployeeNumber employeeNumber) {
            return findByEmployeeNumber(employeeNumber).isPresent();
        }

        @Override
        public List<Employee> findAssignedToOrganizationUnit(OrganizationUnitId organizationUnitId) {
            return List.of();
        }
    }

    private static final class CapturingDomainEventPublisher implements DomainEventPublisherPort {

        private final List<DomainEvent> events = new ArrayList<>();

        @Override
        public void publish(DomainEvent domainEvent) {
            events.add(domainEvent);
        }
    }
}
