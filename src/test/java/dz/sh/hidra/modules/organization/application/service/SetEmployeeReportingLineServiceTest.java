/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SetEmployeeReportingLineServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Unit tests for SetEmployeeReportingLineService.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.organization.application.command.SetEmployeeReportingLineCommand;
import dz.sh.hidra.modules.organization.application.dto.ReportingLineDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepository;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.policy.ReportingLinePolicy;
import dz.sh.hidra.modules.organization.domain.service.ReportingLineDomainService;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.IdentityUserReference;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;

/**
 * Tests the employee reporting-line application service.
 *
 * <p>Business role:
 * Verifies matrix-capable reporting line creation and domain event publication.
 *
 * <p>Architecture role:
 * This is an application-layer unit test using in-memory fake outbound ports. It does not load
 * Spring, JPA, REST API, identity, topology, platform, or persistence infrastructure.
 */
class SetEmployeeReportingLineServiceTest {

    @Test
    void shouldSetEmployeeReportingLineAndPublishEvent() {
        InMemoryEmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
        CapturingDomainEventPublisher eventPublisher = new CapturingDomainEventPublisher();

        Employee employee = activeEmployee("EMP-RPT-SVC-001");
        Employee manager = activeEmployee("EMP-RPT-SVC-002");

        employeeRepository.save(employee);
        employeeRepository.save(manager);

        SetEmployeeReportingLineService service = new SetEmployeeReportingLineService(
                employeeRepository,
                new ReportingLineDomainService(new ReportingLinePolicy()),
                eventPublisher,
                new OrganizationApplicationMapper());

        ReportingLineDto reportingLine = service.setEmployeeReportingLine(new SetEmployeeReportingLineCommand(
                employee.id(),
                manager.id(),
                ReportingLineType.LINE,
                true,
                LocalDate.now(),
                "Primary line reporting relationship"));

        assertEquals(employee.id().value(), reportingLine.employeeId());
        assertEquals(manager.id().value(), reportingLine.managerEmployeeId());
        assertEquals("LINE", reportingLine.reportingLineType());
        assertEquals(1, employeeRepository.lastSavedEmployee.reportingLines().size());
        assertEquals(1, eventPublisher.events.size());
        assertEquals("organization.employee.reporting-line-changed", eventPublisher.events.get(0).eventType());
    }

    @Test
    void shouldRejectReportingLineWhenManagerIsMissing() {
        InMemoryEmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
        Employee employee = activeEmployee("EMP-RPT-SVC-003");
        employeeRepository.save(employee);

        SetEmployeeReportingLineService service = new SetEmployeeReportingLineService(
                employeeRepository,
                new ReportingLineDomainService(new ReportingLinePolicy()),
                new CapturingDomainEventPublisher(),
                new OrganizationApplicationMapper());

        SetEmployeeReportingLineCommand command = new SetEmployeeReportingLineCommand(
                employee.id(),
                EmployeeId.newId(),
                ReportingLineType.FUNCTIONAL,
                false,
                LocalDate.now(),
                "Functional reporting relationship");

        assertThrows(BusinessRuleViolationException.class, () -> service.setEmployeeReportingLine(command));
    }

    private static Employee activeEmployee(String employeeNumber) {
        return Employee.register(
                EmployeeNumber.of(employeeNumber),
                EmployeeFullName.of("Abir MEDJERAB"),
                EmployeeEmail.of("abir.medjerab@example.com"),
                null)
                .activate();
    }

    private static final class InMemoryEmployeeRepository implements EmployeeRepository {

        private final List<Employee> employees = new ArrayList<>();
        private Employee lastSavedEmployee;

        @Override
        public Employee save(Employee employee) {
            employees.removeIf(existing -> existing.id().equals(employee.id()));
            employees.add(employee);
            lastSavedEmployee = employee;
            return employee;
        }

        @Override
        public Optional<Employee> findById(EmployeeId id) {
            return employees.stream().filter(employee -> employee.id().equals(id)).findFirst();
        }

        @Override
        public Optional<Employee> findByEmployeeNumber(EmployeeNumber employeeNumber) {
            return employees.stream().filter(employee -> employee.employeeNumber().equals(employeeNumber)).findFirst();
        }

        @Override
        public Optional<Employee> findByIdentityUserReference(IdentityUserReference identityUserReference) {
            return employees.stream()
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
