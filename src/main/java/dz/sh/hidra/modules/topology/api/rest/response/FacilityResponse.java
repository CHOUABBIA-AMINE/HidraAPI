/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing a physical topology facility.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a physical topology facility.
 *
 * @param facilityId facility identifier
 * @param code facility business code
 * @param name facility display name
 * @param facilityType localized facility type catalog reference
 * @param productType localized product type catalog reference
 * @param status lifecycle status
 * @param coordinate optional geographical coordinate
 * @param organizationUnitReference optional neutral organization unit reference
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
@Schema(name = "FacilityResponse", description = "REST response representing a physical topology facility.")
public record FacilityResponse(
        @Schema(description = "Facility identifier.", example = "fac_550e8400-e29b-41d4-a716-446655440000")
        String facilityId,

        @Schema(description = "Facility business code.", example = "CS-EAST-01")
        String code,

        @Schema(description = "Facility display name.", example = "Compression Station East 01")
        String name,

        @Schema(description = "Localized physical facility type catalog reference.")
        TopologyTypeReferenceResponse facilityType,

        @Schema(description = "Localized hydrocarbon product type catalog reference.")
        TopologyTypeReferenceResponse productType,

        @Schema(description = "Facility lifecycle status.", example = "ACTIVE", allowableValues = {"PLANNED", "ACTIVE", "INACTIVE", "UNDER_MAINTENANCE", "RETIRED", "DECOMMISSIONED"})
        String status,

        @Schema(description = "Optional geographical coordinate.")
        GeoCoordinateResponse coordinate,

        @Schema(description = "Optional neutral organization unit reference.")
        OrganizationUnitReferenceResponse organizationUnitReference,

        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,

        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {
}
