/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePositionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : REST request body for creating an organization position.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request body used to create an organization position.
 *
 * <p>Business role:
 * Creates an operational position/function such as Station Team Leader or Region Director. It is
 * not an identity role.
 *
 * <p>Architecture role:
 * This is a REST input contract mapped to CreatePositionCommand.
 *
 * <p>Validation:
 * Code and title are required. Description is optional.
 *
 * @param code position business code
 * @param title position display title
 * @param description optional business description
 */
@Schema(name = "CreatePositionRequest", description = "Request body for creating an organization position.")
public record CreatePositionRequest(
        @Schema(description = "Position business code.", example = "STATION_TEAM_LEADER", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String code,

        @Schema(description = "Position display title.", example = "Station Team Leader", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 120)
        String title,

        @Schema(description = "Optional position description.", example = "Leads operational station team activities.")
        @Size(max = 500)
        String description) {
}
