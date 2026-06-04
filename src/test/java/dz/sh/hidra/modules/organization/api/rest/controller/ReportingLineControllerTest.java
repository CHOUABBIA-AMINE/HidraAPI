/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : Unit tests for ReportingLineController API behavior.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dz.sh.hidra.modules.organization.api.rest.mapper.OrganizationRestMapper;
import dz.sh.hidra.modules.organization.api.rest.request.SetEmployeeReportingLineRequest;
import dz.sh.hidra.modules.organization.api.rest.response.ReportingLineResponse;
import dz.sh.hidra.modules.organization.application.command.SetEmployeeReportingLineCommand;
import dz.sh.hidra.modules.organization.application.dto.ReportingLineDto;
import dz.sh.hidra.modules.organization.application.port.in.SetEmployeeReportingLineUseCase;

/**
 * Tests the reporting line REST controller.
 *
 * <p>Business role:
 * Verifies API behavior for matrix-capable employee reporting lines, including LINE and functional
 * reporting relationships.
 *
 * <p>Architecture role:
 * This API-layer unit test uses a fake application inbound port and does not access persistence,
 * identity, topology, platform, repositories, or Spring Boot test context.
 */
class ReportingLineControllerTest {

    @Test
    void shouldSetEmployeeReportingLineThroughApplicationPort() {
        ReportingLineController controller = new ReportingLineController(
                new FakeSetEmployeeReportingLineUseCase(),
                new OrganizationRestMapper());

        ResponseEntity<ReportingLineResponse> response = controller.setEmployeeReportingLine(
                "emp_010",
                new SetEmployeeReportingLineRequest(
                        "emp_manager_010",
                        "LINE",
                        true,
                        LocalDate.of(2026, 6, 1),
                        "Primary line reporting relationship"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("emp_010", response.getBody().employeeId());
        assertEquals("emp_manager_010", response.getBody().managerEmployeeId());
        assertEquals("LINE", response.getBody().reportingLineType());
        assertEquals(true, response.getBody().primaryLine());
    }

    private static final class FakeSetEmployeeReportingLineUseCase implements SetEmployeeReportingLineUseCase {

        @Override
        public ReportingLineDto setEmployeeReportingLine(SetEmployeeReportingLineCommand command) {
            return new ReportingLineDto(
                    "rpl_010",
                    command.employeeId().value(),
                    command.managerEmployeeId().value(),
                    command.reportingLineType().name(),
                    command.primaryLine(),
                    command.effectiveFrom(),
                    null,
                    command.description());
        }
    }
}
