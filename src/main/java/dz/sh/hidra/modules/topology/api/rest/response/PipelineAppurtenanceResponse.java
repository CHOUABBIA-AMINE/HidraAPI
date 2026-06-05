/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing a pipeline appurtenance.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.math.BigDecimal;
import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a pipeline appurtenance.
 *
 * <p>Business role:
 * Returns a point asset installed along a pipeline, including valves, injection points, extraction
 * points, purge points, vents, drains, scraper launchers, scraper receivers, hot tap points, bypass
 * points, metering points, sampling points, and connection points.
 *
 * <p>Architecture role:
 * This is a REST output contract. It does not expose operations, permits, telemetry, hydraulic
 * calculations, maintenance records, risk scores, or workflow tasks.
 *
 * <p>Validation:
 * Appurtenance validity is enforced by topology domain models, value objects, policies, and services
 * before this response is produced.
 *
 * @param pipelineAppurtenanceId appurtenance identifier
 * @param pipelineId parent pipeline identifier
 * @param nodeId topology node identifier
 * @param code appurtenance business code
 * @param name appurtenance display name
 * @param appurtenanceType appurtenance type
 * @param valveType optional valve type
 * @param pipelineKilometerPoint KP/PK/chainage value in kilometers
 * @param status lifecycle status
 * @param coordinate optional geographical coordinate
 * @param description optional description
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
@Schema(name = "PipelineAppurtenanceResponse", description = "REST response representing a topology pipeline appurtenance.")
public record PipelineAppurtenanceResponse(
        @Schema(description = "Pipeline appurtenance identifier.", example = "app_550e8400-e29b-41d4-a716-446655440000")
        String pipelineAppurtenanceId,

        @Schema(description = "Parent pipeline identifier.", example = "pipe_550e8400-e29b-41d4-a716-446655440000")
        String pipelineId,

        @Schema(description = "Topology node identifier representing this appurtenance.", example = "node_550e8400-e29b-41d4-a716-446655440000")
        String nodeId,

        @Schema(description = "Pipeline appurtenance business code.", example = "GZ1-BV-001")
        String code,

        @Schema(description = "Pipeline appurtenance display name.", example = "Block Valve KP 25")
        String name,

        @Schema(description = "Pipeline appurtenance type.", example = "VALVE", allowableValues = {"VALVE", "INJECTION_POINT", "EXTRACTION_POINT", "PURGE_POINT", "VENT_POINT", "DRAIN_POINT", "SAMPLING_POINT", "METERING_POINT", "SCRAPER_LAUNCHER", "SCRAPER_RECEIVER", "HOT_TAP_POINT", "BYPASS_POINT", "CONNECTION_POINT", "OTHER"})
        String appurtenanceType,

        @Schema(description = "Optional valve type. Present only when appurtenanceType is VALVE.", example = "BLOCK_VALVE", allowableValues = {"BLOCK_VALVE", "SECTIONALIZING_VALVE", "ISOLATION_VALVE", "SHUTDOWN_VALVE", "CONTROL_VALVE", "CHECK_VALVE", "RELIEF_VALVE", "PRESSURE_REGULATING_VALVE", "BYPASS_VALVE", "DRAIN_VALVE", "VENT_VALVE", "ESD_VALVE", "MANUAL_VALVE", "MOTORIZED_VALVE", "OTHER"})
        String valveType,

        @Schema(description = "KP/PK/chainage value in kilometers.", example = "25.000")
        BigDecimal pipelineKilometerPoint,

        @Schema(description = "Pipeline appurtenance lifecycle status.", example = "ACTIVE", allowableValues = {"PLANNED", "ACTIVE", "INACTIVE", "UNDER_MAINTENANCE", "RETIRED", "DECOMMISSIONED"})
        String status,

        @Schema(description = "Optional geographical coordinate.")
        GeoCoordinateResponse coordinate,

        @Schema(description = "Optional pipeline appurtenance description.", example = "Main line block valve.")
        String description,

        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,

        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {
}
