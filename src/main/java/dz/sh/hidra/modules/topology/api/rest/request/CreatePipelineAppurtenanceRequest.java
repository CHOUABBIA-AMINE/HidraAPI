/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePipelineAppurtenanceRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request body for creating a pipeline appurtenance.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request body used to create a pipeline appurtenance.
 *
 * <p>Business role:
 * Creates a point asset installed along a pipeline, including valves, injection points, extraction
 * points, purge points, vents, drains, scraper launchers, scraper receivers, hot tap points, bypass
 * points, metering points, sampling points, and connection points.
 *
 * <p>Architecture role:
 * This REST input contract accepts stable appurtenance and valve type catalog codes.
 *
 * <p>Validation:
 * Pipeline id, node id, code, name, appurtenance type code, and KP are required. Valve type code is
 * validated later by the mapper/domain layer as required only for valve appurtenances.
 *
 * @param pipelineId parent pipeline identifier
 * @param nodeId topology node identifier
 * @param code appurtenance business code
 * @param name appurtenance display name
 * @param appurtenanceTypeCode language-neutral appurtenance type catalog code
 * @param valveTypeCode optional language-neutral valve type catalog code
 * @param pipelineKilometerPoint KP/PK/chainage value in kilometers
 * @param coordinate optional geographical coordinate
 * @param description optional description
 */
@Schema(name = "CreatePipelineAppurtenanceRequest", description = "Request body for creating a topology pipeline appurtenance.")
public record CreatePipelineAppurtenanceRequest(
        @Schema(description = "Parent pipeline identifier.", example = "pipe_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String pipelineId,

        @Schema(description = "Topology node identifier representing this appurtenance.", example = "node_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String nodeId,

        @Schema(description = "Pipeline appurtenance business code.", example = "GZ1-BV-001", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String code,

        @Schema(description = "Pipeline appurtenance display name.", example = "Block Valve KP 25", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 160)
        String name,

        @Schema(description = "Language-neutral appurtenance type catalog code.", example = "VALVE", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        @Pattern(regexp = "[A-Za-z0-9][A-Za-z0-9_.-]{1,79}")
        String appurtenanceTypeCode,

        @Schema(description = "Optional language-neutral valve type catalog code. Required when the appurtenance type code is VALVE.", example = "BLOCK_VALVE")
        @Size(min = 2, max = 80)
        @Pattern(regexp = "[A-Za-z0-9][A-Za-z0-9_.-]{1,79}")
        String valveTypeCode,

        @Schema(description = "KP/PK/chainage value in kilometers.", example = "25.000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @DecimalMin("0.0")
        BigDecimal pipelineKilometerPoint,

        @Schema(description = "Optional geographical coordinate.")
        @Valid
        GeoCoordinateRequest coordinate,

        @Schema(description = "Optional pipeline appurtenance description.", example = "Main line block valve.")
        @Size(max = 500)
        String description) {
}
