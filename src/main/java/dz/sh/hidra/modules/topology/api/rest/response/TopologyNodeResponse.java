/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNodeResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing a topology node.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.math.BigDecimal;
import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a topology node.
 *
 * @param topologyNodeId topology node identifier
 * @param code topology node business code
 * @param name topology node display name
 * @param nodeType localized topology node type catalog reference
 * @param facilityId optional facility identifier
 * @param pipelineAppurtenanceId optional pipeline appurtenance identifier
 * @param coordinate optional geographical coordinate
 * @param elevationMeters optional elevation in meters
 * @param status lifecycle status
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
@Schema(name = "TopologyNodeResponse", description = "REST response representing a topology node.")
public record TopologyNodeResponse(
        @Schema(description = "Topology node identifier.", example = "node_550e8400-e29b-41d4-a716-446655440000")
        String topologyNodeId,

        @Schema(description = "Topology node business code.", example = "NODE-CS-EAST-01-IN")
        String code,

        @Schema(description = "Topology node display name.", example = "Compression Station East 01 Inlet")
        String name,

        @Schema(description = "Localized topology node type catalog reference.")
        TopologyTypeReferenceResponse nodeType,

        @Schema(description = "Optional facility identifier.", example = "fac_550e8400-e29b-41d4-a716-446655440000")
        String facilityId,

        @Schema(description = "Optional pipeline appurtenance identifier.", example = "app_550e8400-e29b-41d4-a716-446655440000")
        String pipelineAppurtenanceId,

        @Schema(description = "Optional geographical coordinate.")
        GeoCoordinateResponse coordinate,

        @Schema(description = "Optional elevation in meters.", example = "725.300")
        BigDecimal elevationMeters,

        @Schema(description = "Topology node lifecycle status.", example = "ACTIVE", allowableValues = {"PLANNED", "ACTIVE", "INACTIVE", "UNDER_MAINTENANCE", "RETIRED", "DECOMMISSIONED"})
        String status,

        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,

        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {
}
