/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateTopologyNodeRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request body for creating a topology node.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request body used to create a topology node.
 *
 * <p>Business role:
 * Creates a physical graph vertex such as a facility inlet/outlet, internal facility node, pipeline
 * junction, valve point, injection point, extraction point, purge point, vent point, drain point,
 * metering point, sampling point, scraper point, receipt point, delivery point, or connection point.
 *
 * <p>Architecture role:
 * This REST input contract accepts a stable node type catalog code instead of an enum-shaped value.
 *
 * <p>Validation:
 * Code, name, and node type code are required. Facility id, pipeline appurtenance id, coordinate,
 * and elevation are optional.
 *
 * @param code topology node business code
 * @param name topology node display name
 * @param nodeTypeCode language-neutral node type catalog code
 * @param facilityId optional facility identifier
 * @param pipelineAppurtenanceId optional pipeline appurtenance identifier
 * @param coordinate optional geographical coordinate
 * @param elevationMeters optional elevation in meters
 */
@Schema(name = "CreateTopologyNodeRequest", description = "Request body for creating a topology node.")
public record CreateTopologyNodeRequest(
        @Schema(description = "Topology node business code.", example = "NODE-CS-EAST-01-IN", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String code,

        @Schema(description = "Topology node display name.", example = "Compression Station East 01 Inlet", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 160)
        String name,

        @Schema(description = "Language-neutral node type catalog code.", example = "FACILITY_INLET", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        @Pattern(regexp = "[A-Za-z0-9][A-Za-z0-9_.-]{1,79}")
        String nodeTypeCode,

        @Schema(description = "Optional facility identifier.", example = "fac_550e8400-e29b-41d4-a716-446655440000")
        @Size(max = 80)
        String facilityId,

        @Schema(description = "Optional pipeline appurtenance identifier.", example = "app_550e8400-e29b-41d4-a716-446655440000")
        @Size(max = 80)
        String pipelineAppurtenanceId,

        @Schema(description = "Optional geographical coordinate.")
        @Valid
        GeoCoordinateRequest coordinate,

        @Schema(description = "Optional elevation in meters.", example = "725.300")
        BigDecimal elevationMeters) {
}
