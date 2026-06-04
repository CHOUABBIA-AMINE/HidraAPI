/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : REST response representing a reporting line.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a matrix-capable employee reporting line.
 *
 * @param reportingLineId reporting line identifier
 * @param employeeId employee identifier
 * @param managerEmployeeId manager employee identifier
 * @param reportingLineType reporting line type
 * @param primaryLine whether this is the primary line
 * @param effectiveFrom reporting line start date
 * @param effectiveTo optional reporting line end date
 * @param description optional description
 */
@Schema(name = "ReportingLineResponse", description = "REST response representing an employee reporting line.")
public record ReportingLineResponse(
        @Schema(description = "Reporting line identifier.", example = "rpl_550e8400-e29b-41d4-a716-446655440000")
        String reportingLineId,
        @Schema(description = "Employee identifier.", example = "emp_550e8400-e29b-41d4-a716-446655440000")
        String employeeId,
        @Schema(description = "Manager employee identifier.", example = "emp_650e8400-e29b-41d4-a716-446655440000")
        String managerEmployeeId,
        @Schema(description = "Reporting line type.", example = "LINE")
        String reportingLineType,
        @Schema(description = "Whether this is the primary reporting line.", example = "true")
        boolean primaryLine,
        @Schema(description = "Reporting line start date.", type = "string", format = "date")
        LocalDate effectiveFrom,
        @Schema(description = "Reporting line end date.", type = "string", format = "date")
        LocalDate effectiveTo,
        @Schema(description = "Optional business description.")
        String description) {
}
