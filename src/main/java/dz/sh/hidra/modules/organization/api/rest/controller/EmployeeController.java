/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : REST controller for organization employees.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.organization.api.rest.mapper.OrganizationRestMapper;
import dz.sh.hidra.modules.organization.api.rest.request.AssignEmployeeToUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.request.CreateEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.request.UpdateEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeAssignmentResponse;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeResponse;
import dz.sh.hidra.modules.organization.application.port.in.AssignEmployeeToUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.in.CreateEmployeeUseCase;
import dz.sh.hidra.modules.organization.application.port.in.GetEmployeeUseCase;
import dz.sh.hidra.modules.organization.application.port.in.ListEmployeesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * REST controller exposing employee endpoints.
 *
 * <p>Business role:
 * Exposes employee creation, retrieval, listing, and assignment endpoints for real operational
 * employees. Identity users, roles, permissions, and topology assets are not managed here.
 *
 * <p>Architecture role:
 * This controller depends only on application inbound ports and the REST mapper. It does not
 * access repositories, persistence entities, identity implementation, or topology implementation.
 *
 * <p>Validation:
 * Request bodies use Bean Validation with @Valid. Query pagination parameters are bounded.
 *
 * <p>Usage:
 * Use endpoints under /api/v1/organization/employees.
 */
@RestController
@RequestMapping("/api/v1/organization/employees")
@Tag(name = "Organization Employees", description = "Employee endpoints for the organization module.")
public class EmployeeController {

    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final GetEmployeeUseCase getEmployeeUseCase;
    private final ListEmployeesUseCase listEmployeesUseCase;
    private final AssignEmployeeToUnitUseCase assignEmployeeToUnitUseCase;
    private final OrganizationRestMapper mapper;

    public EmployeeController(
            CreateEmployeeUseCase createEmployeeUseCase,
            GetEmployeeUseCase getEmployeeUseCase,
            ListEmployeesUseCase listEmployeesUseCase,
            AssignEmployeeToUnitUseCase assignEmployeeToUnitUseCase,
            OrganizationRestMapper mapper) {

        this.createEmployeeUseCase = createEmployeeUseCase;
        this.getEmployeeUseCase = getEmployeeUseCase;
        this.listEmployeesUseCase = listEmployeesUseCase;
        this.assignEmployeeToUnitUseCase = assignEmployeeToUnitUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create employee", description = "Creates a real operational employee in the organization module.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee created."),
            @ApiResponse(responseCode = "400", description = "Invalid employee creation request."),
            @ApiResponse(responseCode = "409", description = "Employee number or identity reference conflicts with an existing employee."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(createEmployeeUseCase.createEmployee(mapper.toCommand(request))));
    }

    @GetMapping("/{employeeId}")
    @Operation(summary = "Get employee", description = "Retrieves one employee by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee found."),
            @ApiResponse(responseCode = "400", description = "Invalid employee identifier."),
            @ApiResponse(responseCode = "404", description = "Employee not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<EmployeeResponse> getEmployee(
            @Parameter(description = "Employee identifier.", required = true)
            @PathVariable String employeeId) {

        return getEmployeeUseCase.getEmployee(mapper.toGetEmployeeByIdQuery(employeeId))
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "List employees", description = "Lists employees with optional search, status, and organization unit filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employees listed."),
            @ApiResponse(responseCode = "400", description = "Invalid list employees query parameters."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public PageResult<EmployeeResponse> listEmployees(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String organizationUnitId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size) {

        return mapper.toEmployeeResponsePage(listEmployeesUseCase.listEmployees(
                mapper.toListEmployeesQuery(searchText, status, organizationUnitId, page, size)));
    }

    @PutMapping("/{employeeId}")
    @Operation(summary = "Update employee", description = "Endpoint contract for updating employee information. The update use case is not available in the current application ports.")
    @ApiResponses({
            @ApiResponse(responseCode = "501", description = "Update employee use case is not implemented in current application ports."),
            @ApiResponse(responseCode = "400", description = "Invalid employee update request."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public EmployeeResponse updateEmployee(
            @Parameter(description = "Employee identifier.", required = true)
            @PathVariable String employeeId,
            @Valid @RequestBody UpdateEmployeeRequest request) {

        mapper.toCommand(employeeId, request);
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Update employee use case is not available in current organization application ports.");
    }

    @PostMapping("/{employeeId}/assignments")
    @Operation(summary = "Assign employee to unit", description = "Assigns an employee to an organization unit and position.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee assigned to organization unit."),
            @ApiResponse(responseCode = "400", description = "Invalid assignment request."),
            @ApiResponse(responseCode = "404", description = "Employee, organization unit, or position not found."),
            @ApiResponse(responseCode = "409", description = "Assignment conflicts with organization rules."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<EmployeeAssignmentResponse> assignEmployeeToUnit(
            @Parameter(description = "Employee identifier.", required = true)
            @PathVariable String employeeId,
            @Valid @RequestBody AssignEmployeeToUnitRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(assignEmployeeToUnitUseCase.assignEmployeeToUnit(mapper.toCommand(employeeId, request))));
    }
}
