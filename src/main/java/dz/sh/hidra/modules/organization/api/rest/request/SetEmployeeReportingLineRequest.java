/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SetEmployeeReportingLineRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : REST request body for setting an employee reporting line.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request body used to create a matrix-capable employee reporting line.
 *
 * <p>Business role:
 * Creates a reporting relationship between an employee and a manager, including LINE,
 * OPERATIONAL, FUNCTIONAL, ADMINISTRATIVE, TECHNICAL, or DOTTED_LINE reporting.
 *
 * <p>Architecture role:
 * This REST input contract maps to SetEmployeeReportingLineCommand. It does not represent identity
 * roles or permissions.
 *
 * <p>Validation:
 * Manager id, reporting line type, and effective start date are required. Description is optional.
 *
 * @param managerEmployeeId manager employee identifier
 * @param reportingLineType reporting line type
 * @param primaryLine whether this is the primary reporting line
 * @param effectiveFrom reporting line start date
 * @param description optional business description
 */
@Schema(name = "SetEmployeeReportingLineRequest", description = "Request body for setting an employee reporting line.")
public record SetEmployeeReportingLineRequest(
        @Schema(description = "Manager employee identifier.", example = "emp_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(max = 80)
        String managerEmployeeId,

        @Schema(description = "Reporting line type.", example = "LINE", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String reportingLineType,

        @Schema(description = "Whether this is the primary reporting line.", example = "true")
        boolean primaryLine,

        @Schema(description = "Reporting line effective start date.", example = "2026-06-01", type = "string", format = "date", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        LocalDate effectiveFrom,

        @Schema(description = "Optional reporting line description.", example = "Primary line reporting relationship.")
        @Size(max = 500)
        String description) {
}
