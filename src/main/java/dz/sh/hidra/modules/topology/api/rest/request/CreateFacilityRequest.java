/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateFacilityRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request body for creating a topology facility.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request body used to create a physical topology facility.
 *
 * <p>Business role:
 * Creates a physical facility such as a compression station, pumping station, terminal, processing
 * plant, production field interface, gathering center, storage facility, receipt facility, or
 * delivery facility.
 *
 * <p>Architecture role:
 * This is a REST input contract. It creates topology physical facility data only; station as an
 * organization unit remains owned by organization.
 *
 * <p>Validation:
 * Code, name, facility type, and product type are required. Coordinate and organization unit
 * reference are optional.
 *
 * @param code facility business code
 * @param name facility display name
 * @param facilityType physical facility type
 * @param productType hydrocarbon product type
 * @param coordinate optional geographical coordinate
 * @param organizationUnitReference optional neutral organization unit reference
 */
@Schema(name = "CreateFacilityRequest", description = "Request body for creating a physical topology facility.")
public record CreateFacilityRequest(
        @Schema(description = "Facility business code.", example = "CS-EAST-01", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String code,

        @Schema(description = "Facility display name.", example = "Compression Station East 01", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 160)
        String name,

        @Schema(description = "Physical facility type.", example = "COMPRESSION_STATION", allowableValues = {"COMPRESSION_STATION", "PUMPING_STATION", "METERING_STATION", "VALVE_STATION", "TERMINAL", "PROCESSING_PLANT", "PRODUCTION_FIELD", "GATHERING_CENTER", "STORAGE_FACILITY", "DELIVERY_FACILITY", "RECEIPT_FACILITY", "DISPATCHING_CENTER", "OTHER"}, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "COMPRESSION_STATION|PUMPING_STATION|METERING_STATION|VALVE_STATION|TERMINAL|PROCESSING_PLANT|PRODUCTION_FIELD|GATHERING_CENTER|STORAGE_FACILITY|DELIVERY_FACILITY|RECEIPT_FACILITY|DISPATCHING_CENTER|OTHER")
        String facilityType,

        @Schema(description = "Hydrocarbon product type.", example = "GAS", allowableValues = {"GAS", "CRUDE_OIL", "CONDENSATE", "LPG", "REFINED_PRODUCT", "MULTIPHASE", "UNKNOWN"}, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "GAS|CRUDE_OIL|CONDENSATE|LPG|REFINED_PRODUCT|MULTIPHASE|UNKNOWN")
        String productType,

        @Schema(description = "Optional geographical coordinate.")
        @Valid
        GeoCoordinateRequest coordinate,

        @Schema(description = "Optional neutral organization unit reference.")
        @Valid
        OrganizationUnitReferenceRequest organizationUnitReference) {
}
