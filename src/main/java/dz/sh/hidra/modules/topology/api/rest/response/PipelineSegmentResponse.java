/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSegmentResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing a pipeline segment.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.math.BigDecimal;
import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a pipeline segment.
 *
 * <p>Business role:
 * Returns a physical pipe section between two topology nodes.
 *
 * <p>Architecture role:
 * This is a REST output contract mapped from PipelineSegmentDto through TopologyRestMapper.
 *
 * <p>Validation:
 * Segment validity is enforced by topology domain models, value objects, policies, and services
 * before this response is produced.
 *
 * @param pipelineSegmentId pipeline segment identifier
 * @param pipelineId parent pipeline identifier
 * @param code pipeline segment business code
 * @param name pipeline segment display name
 * @param fromNodeId from-node identifier
 * @param toNodeId to-node identifier
 * @param lengthKm segment length in kilometers
 * @param diameterInches segment diameter in inches
 * @param status lifecycle status
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
@Schema(name = "PipelineSegmentResponse", description = "REST response representing a topology pipeline segment.")
public record PipelineSegmentResponse(
        @Schema(description = "Pipeline segment identifier.", example = "seg_550e8400-e29b-41d4-a716-446655440000")
        String pipelineSegmentId,

        @Schema(description = "Parent pipeline identifier.", example = "pipe_550e8400-e29b-41d4-a716-446655440000")
        String pipelineId,

        @Schema(description = "Pipeline segment business code.", example = "GZ1-SEG-001")
        String code,

        @Schema(description = "Pipeline segment display name.", example = "GZ1 Segment KP000-KP025")
        String name,

        @Schema(description = "From-node identifier.", example = "node_550e8400-e29b-41d4-a716-446655440000")
        String fromNodeId,

        @Schema(description = "To-node identifier.", example = "node_660e8400-e29b-41d4-a716-446655440000")
        String toNodeId,

        @Schema(description = "Segment length in kilometers.", example = "25.000")
        BigDecimal lengthKm,

        @Schema(description = "Segment diameter in inches.", example = "42.000")
        BigDecimal diameterInches,

        @Schema(description = "Pipeline segment lifecycle status.", example = "ACTIVE", allowableValues = {"PLANNED", "ACTIVE", "INACTIVE", "UNDER_MAINTENANCE", "RETIRED", "DECOMMISSIONED"})
        String status,

        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,

        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {
}
