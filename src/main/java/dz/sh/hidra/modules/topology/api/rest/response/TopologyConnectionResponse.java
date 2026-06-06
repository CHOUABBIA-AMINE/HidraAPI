/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing a topology connection.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a topology connection.
 *
 * @param topologyConnectionId topology connection identifier
 * @param code connection business code
 * @param name connection display name
 * @param fromNodeId from-node identifier
 * @param toNodeId to-node identifier
 * @param connectionType localized connection type catalog reference
 * @param linkedAssetType linked topology asset type
 * @param linkedAssetId linked topology asset identifier
 * @param status lifecycle status
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
@Schema(name = "TopologyConnectionResponse", description = "REST response representing a topology connection.")
public record TopologyConnectionResponse(
        @Schema(description = "Topology connection identifier.", example = "conn_550e8400-e29b-41d4-a716-446655440000")
        String topologyConnectionId,

        @Schema(description = "Topology connection business code.", example = "CONN-GZ1-001")
        String code,

        @Schema(description = "Topology connection display name.", example = "GZ1 Segment Connection 001")
        String name,

        @Schema(description = "From-node identifier.", example = "node_550e8400-e29b-41d4-a716-446655440000")
        String fromNodeId,

        @Schema(description = "To-node identifier.", example = "node_660e8400-e29b-41d4-a716-446655440000")
        String toNodeId,

        @Schema(description = "Localized connection type catalog reference.")
        TopologyTypeReferenceResponse connectionType,

        @Schema(description = "Linked topology asset type.", example = "SEGMENT", allowableValues = {"PIPELINE_SYSTEM", "PIPELINE", "FACILITY", "NODE", "SEGMENT", "APPURTENANCE", "CONNECTION", "EQUIPMENT"})
        String linkedAssetType,

        @Schema(description = "Linked topology asset identifier.", example = "seg_550e8400-e29b-41d4-a716-446655440000")
        String linkedAssetId,

        @Schema(description = "Topology connection lifecycle status.", example = "ACTIVE", allowableValues = {"PLANNED", "ACTIVE", "INACTIVE", "UNDER_MAINTENANCE", "RETIRED", "DECOMMISSIONED"})
        String status,

        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,

        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {
}
