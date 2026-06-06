/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : REST response representing an organization unit.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing an organization unit.
 *
 * @param organizationUnitId organization unit identifier
 * @param code organization unit code
 * @param name organization unit name
 * @param status organization unit status
 * @param type localized organization unit type catalog reference
 * @param parentId optional parent organization unit identifier
 * @param operationalScopeType optional neutral operational scope type
 * @param operationalScopeId optional neutral operational scope identifier
 * @param operationalScopeCode optional neutral operational scope code
 * @param operationalScopeName optional neutral operational scope name
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
@Schema(name = "OrganizationUnitResponse", description = "REST response representing an organization unit.")
public record OrganizationUnitResponse(
        @Schema(description = "Organization unit identifier.", example = "ou_550e8400-e29b-41d4-a716-446655440000")
        String organizationUnitId,
        @Schema(description = "Organization unit code.", example = "CS_EAST_01")
        String code,
        @Schema(description = "Organization unit name.", example = "Compression Station East 01")
        String name,
        @Schema(description = "Organization unit status.", example = "ACTIVE")
        String status,
        @Schema(description = "Localized organization unit type catalog reference.")
        OrganizationUnitTypeResponse type,
        @Schema(description = "Optional parent organization unit identifier.")
        String parentId,
        @Schema(description = "Optional neutral operational scope type.", example = "TOPOLOGY_COMPRESSION_STATION")
        String operationalScopeType,
        @Schema(description = "Optional neutral operational scope identifier.", example = "station_001")
        String operationalScopeId,
        @Schema(description = "Optional neutral operational scope code.", example = "CS-EAST-01")
        String operationalScopeCode,
        @Schema(description = "Optional neutral operational scope name.", example = "Compression Station East 01")
        String operationalScopeName,
        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,
        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {
}
