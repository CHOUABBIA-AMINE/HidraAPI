/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOrganizationUnitRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : REST request body for creating an organization unit.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request body used to create an organization unit.
 *
 * <p>Business role:
 * Creates organization units such as divisions, departments, regions, station-as-organization-unit
 * structures, and teams. Physical station assets remain owned by topology.
 *
 * <p>Architecture role:
 * This is a REST input contract. Operational scope fields are neutral strings and do not import
 * topology domain classes.
 *
 * <p>Validation:
 * Code, name, and type are required. Parent and operational scope fields are optional.
 *
 * @param code organization unit business code
 * @param name organization unit display name
 * @param type organization unit type
 * @param parentId optional parent organization unit identifier
 * @param operationalScopeType optional neutral operational scope type
 * @param operationalScopeId optional neutral operational scope identifier
 * @param operationalScopeCode optional neutral operational scope code
 * @param operationalScopeName optional neutral operational scope display name
 */
@Schema(name = "CreateOrganizationUnitRequest", description = "Request body for creating an organization unit.")
public record CreateOrganizationUnitRequest(
        @Schema(description = "Organization unit business code.", example = "CS_EAST_01", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String code,

        @Schema(description = "Organization unit display name.", example = "Compression Station East 01", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 160)
        String name,

        @Schema(description = "Organization unit type.", example = "STATION", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String type,

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
