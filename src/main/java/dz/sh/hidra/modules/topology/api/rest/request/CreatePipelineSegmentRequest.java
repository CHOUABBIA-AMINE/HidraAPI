/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePipelineSegmentRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request body for creating a pipeline segment.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request body used to create a pipeline segment.
 *
 * <p>Business role:
 * Creates a physical pipe section between two topology nodes.
 *
 * <p>Architecture role:
 * This is a REST input contract. Controllers map it to CreatePipelineSegmentCommand through
 * TopologyRestMapper.
 *
 * <p>Validation:
 * Pipeline id, code, name, from-node id, to-node id, length, and diameter are required.
 *
 * @param pipelineId parent pipeline identifier
 * @param code pipeline segment business code
 * @param name pipeline segment display name
 * @param fromNodeId from-node identifier
 * @param toNodeId to-node identifier
 * @param lengthKm segment length in kilometers
 * @param diameterInches segment diameter in inches
 */
@Schema(name = "CreatePipelineSegmentRequest", description = "Request body for creating a topology pipeline segment.")
public record CreatePipelineSegmentRequest(
        @Schema(description = "Parent pipeline identifier.", example = "pipe_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String pipelineId,

        @Schema(description = "Pipeline segment business code.", example = "GZ1-SEG-001", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String code,

        @Schema(description = "Pipeline segment display name.", example = "GZ1 Segment KP000-KP025", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 160)
        String name,

        @Schema(description = "From-node identifier.", example = "node_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String fromNodeId,

        @Schema(description = "To-node identifier.", example = "node_660e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String toNodeId,

        @Schema(description = "Segment length in kilometers.", example = "25.000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal lengthKm,

        @Schema(description = "Segment diameter in inches.", example = "42.000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal diameterInches) {
}
