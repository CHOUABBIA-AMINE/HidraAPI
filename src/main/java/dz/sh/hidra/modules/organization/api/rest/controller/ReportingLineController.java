/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : REST controller for employee reporting lines.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dz.sh.hidra.modules.organization.api.rest.mapper.OrganizationRestMapper;
import dz.sh.hidra.modules.organization.api.rest.request.SetEmployeeReportingLineRequest;
import dz.sh.hidra.modules.organization.api.rest.response.ReportingLineResponse;
import dz.sh.hidra.modules.organization.application.port.in.SetEmployeeReportingLineUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * REST controller exposing employee reporting-line endpoints.
 *
 * <p>Business role:
 * Exposes matrix reporting line operations for LINE, OPERATIONAL, FUNCTIONAL, ADMINISTRATIVE,
 * TECHNICAL, and DOTTED_LINE reporting relationships.
 *
 * <p>Architecture role:
 * This controller depends only on the reporting-line application inbound port and REST mapper.
 *
 * <p>Validation:
 * Request bodies use Bean Validation with @Valid.
 *
 * <p>Usage:
 * Use endpoints under /api/v1/organization/employees/{employeeId}/reporting-lines.
 */
@RestController
@RequestMapping("/api/v1/organization/employees/{employeeId}/reporting-lines")
@Tag(name = "Organization Reporting Lines", description = "Matrix reporting-line endpoints for the organization module.")
public class ReportingLineController {

    private final SetEmployeeReportingLineUseCase setEmployeeReportingLineUseCase;
    private final OrganizationRestMapper mapper;

    public ReportingLineController(
            SetEmployeeReportingLineUseCase setEmployeeReportingLineUseCase,
            OrganizationRestMapper mapper) {

        this.setEmployeeReportingLineUseCase = setEmployeeReportingLineUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Set employee reporting line", description = "Creates a matrix-capable employee reporting line.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reporting line created."),
            @ApiResponse(responseCode = "400", description = "Invalid reporting line request."),
            @ApiResponse(responseCode = "404", description = "Employee or manager not found."),
            @ApiResponse(responseCode = "409", description = "Reporting line conflicts with organization reporting rules."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<ReportingLineResponse> setEmployeeReportingLine(
            @Parameter(description = "Employee identifier.", required = true)
            @PathVariable String employeeId,
            @Valid @RequestBody SetEmployeeReportingLineRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(setEmployeeReportingLineUseCase.setEmployeeReportingLine(mapper.toCommand(employeeId, request))));
    }
}
