/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationRestMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.mapper
 *
 * @Description : Unit tests for OrganizationRestMapper request, query, and response mapping.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.organization.api.rest.request.AssignEmployeeToUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.request.CreateEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.request.CreateOrganizationUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.request.SetEmployeeReportingLineRequest;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeResponse;
import dz.sh.hidra.modules.organization.api.rest.response.OrganizationUnitResponse;
import dz.sh.hidra.modules.organization.application.command.AssignEmployeeToUnitCommand;
import dz.sh.hidra.modules.organization.application.command.CreateEmployeeCommand;
import dz.sh.hidra.modules.organization.application.command.CreateOrganizationUnitCommand;
import dz.sh.hidra.modules.organization.application.command.SetEmployeeReportingLineCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeDto;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitDto;
import dz.sh.hidra.modules.organization.application.query.ListEmployeesQuery;
import dz.sh.hidra.modules.organization.application.query.ListOrganizationUnitsQuery;

/**
 * Tests the organization REST mapper.
 */
class OrganizationRestMapperTest {

    private final OrganizationRestMapper mapper = new OrganizationRestMapper();

    @Test
    void shouldMapCreateEmployeeRequestToCommand() {
        CreateEmployeeRequest request = new CreateEmployeeRequest(
                "EMP-API-001",
                "Abir MEDJERAB",
                "abir.medjerab@example.com",
                "usr_001");

        CreateEmployeeCommand command = mapper.toCommand(request);

        assertEquals("EMP-API-001", command.employeeNumber().value());
        assertEquals("Abir MEDJERAB", command.fullName().value());
        assertEquals("abir.medjerab@example.com", command.email().value());
        assertEquals("usr_001", command.identityUserReference().value());
    }

    @Test
    void shouldMapCreateOrganizationUnitRequestToCommandWithStationScope() {
        CreateOrganizationUnitRequest request = new CreateOrganizationUnitRequest(
                "CS_EAST_01",
                "Compression Station East 01",
                "station",
                null,
                "topology_compression_station",
                "station-001",
                "CS-EAST-01",
                "Compression Station East 01");

        CreateOrganizationUnitCommand command = mapper.toCommand(request);

        assertEquals("CS_EAST_01", command.code().value());
        assertEquals("Compression Station East 01", command.name().value());
        assertEquals("STATION", command.type().name());
        assertEquals("organization-out-station", command.type().id());
        assertEquals("TOPOLOGY_COMPRESSION_STATION", command.operationalScopeType().name());
        assertEquals("station-001", command.operationalScopeId());
        assertEquals("CS-EAST-01", command.operationalScopeCode());
    }

    @Test
    void shouldMapAssignEmployeeRequestToCommand() {
        AssignEmployeeToUnitRequest request = new AssignEmployeeToUnitRequest(
                "ou_001",
                "pos_001",
                "TOPOLOGY_COMPRESSION_STATION",
                "station-001",
                "CS-EAST-01",
                "Compression Station East 01",
                LocalDate.of(2026, 6, 1));

        AssignEmployeeToUnitCommand command = mapper.toCommand("emp_001", request);

        assertEquals("emp_001", command.employeeId().value());
        assertEquals("ou_001", command.organizationUnitId().value());
        assertEquals("pos_001", command.positionId().value());
        assertEquals("TOPOLOGY_COMPRESSION_STATION", command.operationalScopeType().name());
        assertEquals(LocalDate.of(2026, 6, 1), command.effectiveFrom());
    }

    @Test
    void shouldMapReportingLineRequestToCommand() {
        SetEmployeeReportingLineRequest request = new SetEmployeeReportingLineRequest(
                "emp_manager_001",
                "functional",
                false,
                LocalDate.of(2026, 6, 1),
                "Functional reporting relationship");

        SetEmployeeReportingLineCommand command = mapper.toCommand("emp_001", request);

        assertEquals("emp_001", command.employeeId().value());
        assertEquals("emp_manager_001", command.managerEmployeeId().value());
        assertEquals("FUNCTIONAL", command.reportingLineType().name());
        assertEquals(false, command.primaryLine());
    }

    @Test
    void shouldMapListQueriesWithOptionalFilters() {
        ListEmployeesQuery employeesQuery = mapper.toListEmployeesQuery("abir", "active", "ou_001", 1, 25);

        assertEquals("abir", employeesQuery.searchText());
        assertEquals("ACTIVE", employeesQuery.status().name());
        assertEquals("ou_001", employeesQuery.organizationUnitId().value());
        assertEquals(1, employeesQuery.pageRequest().page());
        assertEquals(25, employeesQuery.pageRequest().size());

        ListOrganizationUnitsQuery unitsQuery = mapper.toListOrganizationUnitsQuery("station", "station", "active", null, 0, 20);

        assertEquals("station", unitsQuery.searchText());
        assertEquals("STATION", unitsQuery.type().name());
        assertEquals("organization-out-station", unitsQuery.type().id());
        assertEquals("ACTIVE", unitsQuery.status().name());
        assertNull(unitsQuery.parentId());
    }

    @Test
    void shouldMapApplicationDtosToRestResponsesAndPages() {
        Instant now = Instant.parse("2026-06-01T10:15:30Z");
        EmployeeDto employeeDto = new EmployeeDto(
                "emp_001",
                "EMP-API-002",
                "Abir MEDJERAB",
                "abir.medjerab@example.com",
                "ACTIVE",
                "usr_001",
                List.of(),
                List.of(),
                now,
                now,
                null,
                null,
                now);

        EmployeeResponse employeeResponse = mapper.toResponse(employeeDto);

        assertEquals("emp_001", employeeResponse.employeeId());
        assertEquals("EMP-API-002", employeeResponse.employeeNumber());
        assertEquals("ACTIVE", employeeResponse.status());

        OrganizationUnitDto unitDto = new OrganizationUnitDto(
                "ou_001",
                "CS_EAST_01",
                "Compression Station East 01",
                "ACTIVE",
                "organization-out-station",
                "STATION",
                null,
                "TOPOLOGY_COMPRESSION_STATION",
                "station-001",
                "CS-EAST-01",
                "Compression Station East 01",
                now,
                now);

        PageResult<OrganizationUnitResponse> responsePage = mapper.toOrganizationUnitResponsePage(
                PageResult.of(List.of(unitDto), 0, 20, 1),
                "fr");

        assertEquals(1, responsePage.items().size());
        assertEquals("CS_EAST_01", responsePage.items().get(0).code());
        assertEquals("STATION", responsePage.items().get(0).type().code());
        assertEquals("Station", responsePage.items().get(0).type().label());
        assertEquals("fr", responsePage.items().get(0).type().locale());
        assertEquals(1, responsePage.totalElements());
    }
}
