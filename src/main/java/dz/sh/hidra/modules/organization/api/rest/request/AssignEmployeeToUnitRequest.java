/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignEmployeeToUnitRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : REST request body for assigning an employee to an organization unit.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request body used to assign an employee to an organization unit and position.
 *
 * <p>Business role:
 * Assigns a real operational employee to a unit and position, optionally with a neutral
 * operational scope reference.
 *
 * <p>Architecture role:
 * This REST input contract maps to AssignEmployeeToUnitCommand and does not import topology
 * domain objects.
 *
 * <p>Validation:
 * Organization unit id, position id, and effective start date are required.
 *
 * @param organizationUnitId target organization unit identifier
 * @param positionId target position identifier
 * @param operationalScopeType optional neutral operational scope type
 * @param operationalScopeId optional neutral operational scope identifier
 * @param operationalScopeCode optional neutral operational scope code
 * @param operationalScopeName optional neutral operational scope display name
 * @param effectiveFrom assignment start date
 */
@Schema(name = "AssignEmployeeToUnitRequest", description = "Request body for assigning an employee to an organization unit and position.")
public record AssignEmployeeToUnitRequest(
        @Schema(description = "Target organization unit identifier.", example = "ou_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(max = 80)
        String organizationUnitId,

        @Schema(description = "Target position identifier.", example = "pos_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(max = 80)
        String positionId,

        @Schema(description = "Optional neutral operational scope type.", example = "TOPOLOGY_COMPRESSION_STATION")
        @Size(max = 80)
        String operationalScopeType,

        @Schema(description = "Optional neutral operational scope identifier.", example = "station_001")
        @Size(max = 120)
        String operationalScopeId,

        @Schema(description = "Optional neutral operational scope code.", example = "CS-EAST-01")
        @Size(max = 120)
        String operationalScopeCode,

        @Schema(description = "Optional neutral operational scope display name.", example = "Compression Station East 01")
        @Size(max = 160)
        String operationalScopeName,

        @Schema(description = "Assignment effective start date.", example = "2026-06-01", type = "string", format = "date", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        LocalDate effectiveFrom) {
}
