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
 * This is a REST input contract. It does not create operations, permits, telemetry, hydraulic
 * calculations, maintenance records, risk scores, or workflow tasks.
 *
 * <p>Validation:
 * Pipeline id, node id, code, name, appurtenance type, and KP are required. Valve type is validated
 * later by the REST mapper/domain layer as required only for VALVE and forbidden otherwise.
 *
 * @param pipelineId parent pipeline identifier
 * @param nodeId topology node identifier
 * @param code appurtenance business code
 * @param name appurtenance display name
 * @param appurtenanceType appurtenance type
 * @param valveType optional valve type
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

        @Schema(description = "Pipeline appurtenance type.", example = "VALVE", allowableValues = {"VALVE", "INJECTION_POINT", "EXTRACTION_POINT", "PURGE_POINT", "VENT_POINT", "DRAIN_POINT", "SAMPLING_POINT", "METERING_POINT", "SCRAPER_LAUNCHER", "SCRAPER_RECEIVER", "HOT_TAP_POINT", "BYPASS_POINT", "CONNECTION_POINT", "OTHER"}, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "VALVE|INJECTION_POINT|EXTRACTION_POINT|PURGE_POINT|VENT_POINT|DRAIN_POINT|SAMPLING_POINT|METERING_POINT|SCRAPER_LAUNCHER|SCRAPER_RECEIVER|HOT_TAP_POINT|BYPASS_POINT|CONNECTION_POINT|OTHER")
        String appurtenanceType,

        @Schema(description = "Optional valve type. Required when appurtenanceType is VALVE and must be absent otherwise.", example = "BLOCK_VALVE", allowableValues = {"BLOCK_VALVE", "SECTIONALIZING_VALVE", "ISOLATION_VALVE", "SHUTDOWN_VALVE", "CONTROL_VALVE", "CHECK_VALVE", "RELIEF_VALVE", "PRESSURE_REGULATING_VALVE", "BYPASS_VALVE", "DRAIN_VALVE", "VENT_VALVE", "ESD_VALVE", "MANUAL_VALVE", "MOTORIZED_VALVE", "OTHER"})
        @Pattern(regexp = "BLOCK_VALVE|SECTIONALIZING_VALVE|ISOLATION_VALVE|SHUTDOWN_VALVE|CONTROL_VALVE|CHECK_VALVE|RELIEF_VALVE|PRESSURE_REGULATING_VALVE|BYPASS_VALVE|DRAIN_VALVE|VENT_VALVE|ESD_VALVE|MANUAL_VALVE|MOTORIZED_VALVE|OTHER")
        String valveType,

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
