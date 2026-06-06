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
 * This REST input contract accepts stable catalog type codes instead of Java enum-shaped values.
 *
 * <p>Validation:
 * Code, name, facility type code, and product type code are required. Coordinate and organization
 * unit reference are optional.
 *
 * @param code facility business code
 * @param name facility display name
 * @param facilityTypeCode language-neutral facility type catalog code
 * @param productTypeCode language-neutral product type catalog code
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

        @Schema(description = "Language-neutral facility type catalog code.", example = "COMPRESSION_STATION", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        @Pattern(regexp = "[A-Za-z0-9][A-Za-z0-9_.-]{1,79}")
        String facilityTypeCode,

        @Schema(description = "Language-neutral product type catalog code.", example = "GAS", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        @Pattern(regexp = "[A-Za-z0-9][A-Za-z0-9_.-]{1,79}")
        String productTypeCode,

        @Schema(description = "Optional geographical coordinate.")
        @Valid
        GeoCoordinateRequest coordinate,

        @Schema(description = "Optional neutral organization unit reference.")
        @Valid
        OrganizationUnitReferenceRequest organizationUnitReference) {
}
