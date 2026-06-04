/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UpdateOrganizationUnitRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : REST request body for updating an organization unit.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request body used to update an organization unit.
 *
 * <p>Business role:
 * Updates organization unit descriptive, hierarchy, or neutral operational scope information.
 * It does not update topology assets.
 *
 * <p>Architecture role:
 * This is a REST input contract mapped to an application command.
 *
 * <p>Validation:
 * Name is required. Parent and operational scope fields are optional.
 *
 * @param name organization unit display name
 * @param parentId optional parent organization unit identifier
 * @param operationalScopeType optional neutral operational scope type
 * @param operationalScopeId optional neutral operational scope identifier
 * @param operationalScopeCode optional neutral operational scope code
 * @param operationalScopeName optional neutral operational scope display name
 */
@Schema(name = "UpdateOrganizationUnitRequest", description = "Request body for updating an organization unit.")
public record UpdateOrganizationUnitRequest(
        @Schema(description = "Organization unit display name.", example = "Compression Station East 01", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 160)
        String name,

        @Schema(description = "Optional parent organization unit identifier.", example = "ou_550e8400-e29b-41d4-a716-446655440000")
        @Size(max = 80)
        String parentId,

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
        String operationalScopeName) {
}
