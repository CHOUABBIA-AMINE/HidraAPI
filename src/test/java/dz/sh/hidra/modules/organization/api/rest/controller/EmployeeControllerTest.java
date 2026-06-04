/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : Unit tests for EmployeeController API behavior.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.organization.api.rest.mapper.OrganizationRestMapper;
import dz.sh.hidra.modules.organization.api.rest.request.AssignEmployeeToUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.request.CreateEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeAssignmentResponse;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeResponse;
import dz.sh.hidra.modules.organization.application.command.AssignEmployeeToUnitCommand;
import dz.sh.hidra.modules.organization.application.command.CreateEmployeeCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAssignmentDto;
import dz.sh.hidra.modules.organization.application.dto.EmployeeDto;
import dz.sh.hidra.modules.organization.application.port.in.AssignEmployeeToUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.in.CreateEmployeeUseCase;
import dz.sh.hidra.modules.organization.application.port.in.GetEmployeeUseCase;
import dz.sh.hidra.modules.organization.application.port.in.ListEmployeesUseCase;
import dz.sh.hidra.modules.organization.application.query.GetEmployeeByIdQuery;
import dz.sh.hidra.modules.organization.application.query.ListEmployeesQuery;

/**
 * Tests the employee REST controller.
 *
 * <p>Business role:
 * Verifies employee API behavior for create, get, list, and assignment operations.
 *
 * <p>Architecture role:
 * This API-layer unit test uses fake application inbound ports. It does not access repositories,
 * JPA entities, persistence adapters, identity, topology, platform, or Spring Boot test context.
 */
class EmployeeControllerTest {

    @Test
    void shouldCreateEmployeeThroughApplicationPort() {
        EmployeeController controller = controller();

        ResponseEntity<EmployeeResponse> response = controller.createEmployee(new CreateEmployeeRequest(
                "EMP-API-010",
                "Abir MEDJERAB",
                "abir.medjerab@example.com",
                "usr_010"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("EMP-API-010", response.getBody().employeeNumber());
        assertEquals("Abir MEDJERAB", response.getBody().fullName());
    }

    @Test
    void shouldReturnEmployeeWhenFound() {
        EmployeeController controller = controller();

        ResponseEntity<EmployeeResponse> response = controller.getEmployee("emp_010");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("emp_010", response.getBody().employeeId());
    }

    @Test
    void shouldReturnNotFoundWhenEmployeeDoesNotExist() {
        EmployeeController controller = controller();

        ResponseEntity<EmployeeResponse> response = controller.getEmployee("missing");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldListEmployeesThroughApplicationPort() {
        EmployeeController controller = controller();

        PageResult<EmployeeResponse> response = controller.listEmployees("abir", "ACTIVE", "ou_010", 0, 20);

        assertEquals(1, response.items().size());
        assertEquals("EMP-API-010", response.items().get(0).employeeNumber());
        assertEquals(1, response.totalElements());
    }

    @Test
    void shouldAssignEmployeeToUnitThroughApplicationPort() {
        EmployeeController controller = controller();

        ResponseEntity<EmployeeAssignmentResponse> response = controller.assignEmployeeToUnit(
                "emp_010",
                new AssignEmployeeToUnitRequest(
                        "ou_010",
                        "pos_010",
                        "TOPOLOGY_COMPRESSION_STATION",
                        "station-010",
                        "CS-EAST-10",
                        "Compression Station East 10",
                        LocalDate.of(2026, 6, 1)));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("emp_010", response.getBody().employeeId());
        assertEquals("ou_010", response.getBody().organizationUnitId());
        assertEquals("TOPOLOGY_COMPRESSION_STATION", response.getBody().operationalScopeType());
    }

    private static EmployeeController controller() {
        OrganizationRestMapper mapper = new OrganizationRestMapper();
        return new EmployeeController(
                new FakeCreateEmployeeUseCase(),
                new FakeGetEmployeeUseCase(),
                new FakeListEmployeesUseCase(),
                new FakeAssignEmployeeToUnitUseCase(),
                mapper);
    }

    private static EmployeeDto employeeDto(String employeeId) {
        Instant now = Instant.parse("2026-06-01T10:15:30Z");
        return new EmployeeDto(
                employeeId,
                "EMP-API-010",
                "Abir MEDJERAB",
                "abir.medjerab@example.com",
                "ACTIVE",
                "usr_010",
                List.of(),
                List.of(),
                now,
                now,
                null,
                null,
                now);
    }

    private static final class FakeCreateEmployeeUseCase implements CreateEmployeeUseCase {

        @Override
        public EmployeeDto createEmployee(CreateEmployeeCommand command) {
            return employeeDto("emp_010");
        }
    }

    private static final class FakeGetEmployeeUseCase implements GetEmployeeUseCase {

        @Override
        public Optional<EmployeeDto> getEmployee(GetEmployeeByIdQuery query) {
            if ("missing".equals(query.employeeId().value())) {
                return Optional.empty();
            }
            return Optional.of(employeeDto(query.employeeId().value()));
        }
    }

    private static final class FakeListEmployeesUseCase implements ListEmployeesUseCase {

        @Override
        public PageResult<EmployeeDto> listEmployees(ListEmployeesQuery query) {
            return PageResult.of(List.of(employeeDto("emp_010")), query.pageRequest().page(), query.pageRequest().size(), 1);
        }
    }

    private static final class FakeAssignEmployeeToUnitUseCase implements AssignEmployeeToUnitUseCase {

        @Override
        public EmployeeAssignmentDto assignEmployeeToUnit(AssignEmployeeToUnitCommand command) {
            return new EmployeeAssignmentDto(
                    "asg_010",
                    command.employeeId().value(),
                    command.organizationUnitId().value(),
                    command.positionId().value(),
                    command.operationalScopeType() == null ? null : command.operationalScopeType().name(),
                    command.operationalScopeId(),
                    command.operationalScopeCode(),
                    command.operationalScopeName(),
                    command.effectiveFrom(),
                    null);
        }
    }
}
