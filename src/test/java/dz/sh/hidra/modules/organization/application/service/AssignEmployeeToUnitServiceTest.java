/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignEmployeeToUnitServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Unit tests for AssignEmployeeToUnitService.
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
import dz.sh.hidra.modules.organization.application.command.AssignEmployeeToUnitCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAssignmentDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepository;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepository;
import dz.sh.hidra.modules.organization.application.port.out.PositionRepository;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.policy.EmployeeAssignmentPolicy;
import dz.sh.hidra.modules.organization.domain.service.EmployeeAssignmentDomainService;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.IdentityUserReference;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitName;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitType;
import dz.sh.hidra.modules.organization.domain.value.PositionCode;
import dz.sh.hidra.modules.organization.domain.value.PositionId;
import dz.sh.hidra.modules.organization.domain.value.PositionTitle;

/**
 * Tests the employee assignment application service.
 *
 * <p>Business role:
 * Verifies that active employees can be assigned to active organization units and active positions,
 * including station-as-organization-unit scenarios.
 *
 * <p>Architecture role:
 * This is an application-layer unit test using in-memory fake outbound ports. It does not load
 * Spring, JPA, REST API, identity, topology, platform, or persistence infrastructure.
 */
class AssignEmployeeToUnitServiceTest {

    @Test
    void shouldAssignEmployeeToStationUnitAndPublishEvent() {
        InMemoryEmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
        InMemoryOrganizationUnitRepository organizationUnitRepository = new InMemoryOrganizationUnitRepository();
        InMemoryPositionRepository positionRepository = new InMemoryPositionRepository();
        CapturingDomainEventPublisher eventPublisher = new CapturingDomainEventPublisher();

        Employee employee = activeEmployee("EMP-ASG-001");
        OrganizationUnit stationUnit = stationOrganizationUnit();
        Position position = Position.create(
                PositionCode.of("STATION_TEAM_LEADER"),
                PositionTitle.of("Station Team Leader"),
                "Leads station team");

        employeeRepository.save(employee);
        organizationUnitRepository.save(stationUnit);
        positionRepository.save(position);

        AssignEmployeeToUnitService service = new AssignEmployeeToUnitService(
                employeeRepository,
                organizationUnitRepository,
                positionRepository,
                new EmployeeAssignmentDomainService(new EmployeeAssignmentPolicy()),
                eventPublisher,
                new OrganizationApplicationMapper());

        EmployeeAssignmentDto assignment = service.assignEmployeeToUnit(new AssignEmployeeToUnitCommand(
                employee.id(),
                stationUnit.id(),
                position.id(),
                OperationalScopeType.TOPOLOGY_COMPRESSION_STATION,
                "station-001",
                "CS-EAST-01",
                "Compression Station East 01",
                LocalDate.now()));

        assertEquals(employee.id().value(), assignment.employeeId());
        assertEquals(stationUnit.id().value(), assignment.organizationUnitId());
        assertEquals(position.id().value(), assignment.positionId());
        assertEquals("TOPOLOGY_COMPRESSION_STATION", assignment.operationalScopeType());
        assertEquals(1, employeeRepository.lastSavedEmployee.assignments().size());
        assertEquals(1, eventPublisher.events.size());
        assertEquals("organization.employee.assigned-to-unit", eventPublisher.events.get(0).eventType());
    }

    @Test
    void shouldRejectAssignmentWhenEmployeeIsMissing() {
        InMemoryOrganizationUnitRepository organizationUnitRepository = new InMemoryOrganizationUnitRepository();
        InMemoryPositionRepository positionRepository = new InMemoryPositionRepository();

        OrganizationUnit organizationUnit = organizationUnit();
        Position position = Position.create(PositionCode.of("TEAM_LEADER"), PositionTitle.of("Team Leader"), null);

        organizationUnitRepository.save(organizationUnit);
        positionRepository.save(position);

        AssignEmployeeToUnitService service = new AssignEmployeeToUnitService(
                new InMemoryEmployeeRepository(),
                organizationUnitRepository,
                positionRepository,
                new EmployeeAssignmentDomainService(new EmployeeAssignmentPolicy()),
                new CapturingDomainEventPublisher(),
                new OrganizationApplicationMapper());

        AssignEmployeeToUnitCommand command = new AssignEmployeeToUnitCommand(
                EmployeeId.newId(),
                organizationUnit.id(),
                position.id(),
                null,
                null,
                null,
                null,
                LocalDate.now());

        assertThrows(BusinessRuleViolationException.class, () -> service.assignEmployeeToUnit(command));
    }

    private static Employee activeEmployee(String employeeNumber) {
        return Employee.register(
                EmployeeNumber.of(employeeNumber),
                EmployeeFullName.of("Abir MEDJERAB"),
                EmployeeEmail.of("abir.medjerab@example.com"),
                null)
                .activate();
    }

    private static OrganizationUnit organizationUnit() {
        return OrganizationUnit.create(
                OrganizationUnitCode.of("TEAM_APP_ASSIGN"),
                OrganizationUnitName.of("Application Assignment Team"),
                OrganizationUnitType.TEAM,
                null,
                null);
    }

    private static OrganizationUnit stationOrganizationUnit() {
        return OrganizationUnit.createStation(
                OrganizationUnitCode.of("CS_EAST_01"),
                OrganizationUnitName.of("Compression Station East 01"),
                null,
                new OperationalScopeReference(
                        OperationalScopeType.TOPOLOGY_COMPRESSION_STATION,
                        "station-001",
                        "CS-EAST-01",
                        "Compression Station East 01"));
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
            return Optional.empty();
        }

        @Override
        public boolean existsByEmployeeNumber(EmployeeNumber employeeNumber) {
            return findByEmployeeNumber(employeeNumber).isPresent();
        }

        @Override
        public List<Employee> findAssignedToOrganizationUnit(OrganizationUnitId organizationUnitId) {
            return employees.stream()
                    .filter(employee -> employee.assignments().stream()
                            .anyMatch(assignment -> assignment.organizationUnitId().equals(organizationUnitId)))
                    .toList();
        }
    }

    private static final class InMemoryOrganizationUnitRepository implements OrganizationUnitRepository {

        private final List<OrganizationUnit> organizationUnits = new ArrayList<>();

        @Override
        public OrganizationUnit save(OrganizationUnit organizationUnit) {
            organizationUnits.removeIf(existing -> existing.id().equals(organizationUnit.id()));
            organizationUnits.add(organizationUnit);
            return organizationUnit;
        }

        @Override
        public Optional<OrganizationUnit> findById(OrganizationUnitId id) {
            return organizationUnits.stream().filter(unit -> unit.id().equals(id)).findFirst();
        }

        @Override
        public Optional<OrganizationUnit> findByCode(OrganizationUnitCode code) {
            return organizationUnits.stream().filter(unit -> unit.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(OrganizationUnitCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public List<OrganizationUnit> findChildrenOf(OrganizationUnitId parentId) {
            return List.of();
        }

        @Override
        public List<OrganizationUnit> findByType(OrganizationUnitType type) {
            return organizationUnits.stream().filter(unit -> unit.type() == type).toList();
        }

        @Override
        public List<OrganizationUnit> findByOperationalScope(OperationalScopeReference operationalScopeReference) {
            return List.of();
        }
    }

    private static final class InMemoryPositionRepository implements PositionRepository {

        private final List<Position> positions = new ArrayList<>();

        @Override
        public Position save(Position position) {
            positions.removeIf(existing -> existing.id().equals(position.id()));
            positions.add(position);
            return position;
        }

        @Override
        public Optional<Position> findById(PositionId id) {
            return positions.stream().filter(position -> position.id().equals(id)).findFirst();
        }

        @Override
        public Optional<Position> findByCode(PositionCode code) {
            return positions.stream().filter(position -> position.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(PositionCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public List<Position> findActivePositions() {
            return positions.stream().filter(Position::active).toList();
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
