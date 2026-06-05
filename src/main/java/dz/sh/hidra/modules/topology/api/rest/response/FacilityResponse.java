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
 * @Description : REST response representing a topology facility.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a physical topology facility.
 *
 * <p>Business role:
 * Returns a physical facility such as a compression station, pumping station, terminal, processing
 * plant, production field interface, gathering center, storage facility, receipt facility, or
 * delivery facility.
 *
 * <p>Architecture role:
 * This is a REST output contract. It returns physical topology facility data only; station as an
 * organization unit remains owned by organization.
 *
 * <p>Validation:
 * Facility validity is enforced by topology domain models, value objects, policies, and services
 * before this response is produced.
 *
 * @param facilityId facility identifier
 * @param code facility business code
 * @param name facility display name
 * @param facilityType physical facility type
 * @param productType hydrocarbon product type
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

        @Schema(description = "Physical facility type.", example = "COMPRESSION_STATION", allowableValues = {"COMPRESSION_STATION", "PUMPING_STATION", "METERING_STATION", "VALVE_STATION", "TERMINAL", "PROCESSING_PLANT", "PRODUCTION_FIELD", "GATHERING_CENTER", "STORAGE_FACILITY", "DELIVERY_FACILITY", "RECEIPT_FACILITY", "DISPATCHING_CENTER", "OTHER"})
        String facilityType,

        @Schema(description = "Hydrocarbon product type.", example = "GAS", allowableValues = {"GAS", "CRUDE_OIL", "CONDENSATE", "LPG", "REFINED_PRODUCT", "MULTIPHASE", "UNKNOWN"})
        String productType,

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
