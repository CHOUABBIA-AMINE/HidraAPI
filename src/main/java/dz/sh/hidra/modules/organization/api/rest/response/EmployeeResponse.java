/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : REST response representing an organization employee.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;

import java.time.Instant;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a real operational employee.
 *
 * @param employeeId employee identifier
 * @param employeeNumber employee business number
 * @param fullName employee full name
 * @param email optional professional email
 * @param status employment status
 * @param identityUserReference optional neutral identity user reference
 * @param assignments employee assignments
 * @param reportingLines employee reporting lines
 * @param createdAt creation instant
 * @param activatedAt optional activation instant
 * @param suspendedAt optional suspension instant
 * @param disabledAt optional disabled instant
 * @param updatedAt last update instant
 */
@Schema(name = "EmployeeResponse", description = "REST response representing an organization employee.")
public record EmployeeResponse(
        @Schema(description = "Employee identifier.", example = "emp_550e8400-e29b-41d4-a716-446655440000")
        String employeeId,
        @Schema(description = "Employee business number.", example = "EMP-000123")
        String employeeNumber,
        @Schema(description = "Employee full name.", example = "Abir MEDJERAB")
        String fullName,
        @Schema(description = "Optional professional email.", example = "abir.medjerab@sh.dz")
        String email,
        @Schema(description = "Employment status.", example = "ACTIVE")
        String status,
        @Schema(description = "Optional neutral identity user reference.", example = "usr_550e8400-e29b-41d4-a716-446655440000")
        String identityUserReference,
        @Schema(description = "Employee assignments.")
        List<EmployeeAssignmentResponse> assignments,
        @Schema(description = "Employee reporting lines.")
        List<ReportingLineResponse> reportingLines,
        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,
        @Schema(description = "Activation instant.", type = "string", format = "date-time")
        Instant activatedAt,
        @Schema(description = "Suspension instant.", type = "string", format = "date-time")
        Instant suspendedAt,
        @Schema(description = "Disabled instant.", type = "string", format = "date-time")
        Instant disabledAt,
        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {

    public EmployeeResponse {
        assignments = assignments == null ? List.of() : List.copyOf(assignments);
        reportingLines = reportingLines == null ? List.of() : List.copyOf(reportingLines);
    }
}
