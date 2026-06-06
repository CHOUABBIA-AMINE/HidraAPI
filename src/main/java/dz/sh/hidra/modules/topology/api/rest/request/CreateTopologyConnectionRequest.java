/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateTopologyConnectionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request body for creating a topology connection.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request body used to create an explicit topology connection.
 *
 * <p>Business role:
 * Creates a physical graph edge between two topology nodes.
 *
 * <p>Architecture role:
 * This REST input contract accepts a stable connection type catalog code. Linked asset type remains
 * an internal topology asset discriminator.
 *
 * <p>Validation:
 * Code, name, endpoint nodes, connection type code, linked asset type, and linked asset id are
 * required.
 *
 * @param code connection business code
 * @param name connection display name
 * @param fromNodeId from-node identifier
 * @param toNodeId to-node identifier
 * @param connectionTypeCode language-neutral connection type catalog code
 * @param linkedAssetType linked topology asset type
 * @param linkedAssetId linked topology asset identifier
 */
@Schema(name = "CreateTopologyConnectionRequest", description = "Request body for creating a topology connection.")
public record CreateTopologyConnectionRequest(
        @Schema(description = "Topology connection business code.", example = "CONN-GZ1-001", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String code,

        @Schema(description = "Topology connection display name.", example = "GZ1 Segment Connection 001", requiredMode = Schema.RequiredMode.REQUIRED)
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

        @Schema(description = "Language-neutral connection type catalog code.", example = "PIPELINE_SEGMENT", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        @Pattern(regexp = "[A-Za-z0-9][A-Za-z0-9_.-]{1,79}")
        String connectionTypeCode,

        @Schema(description = "Linked topology asset type.", example = "SEGMENT", allowableValues = {"PIPELINE_SYSTEM", "PIPELINE", "FACILITY", "NODE", "SEGMENT", "APPURTENANCE", "CONNECTION", "EQUIPMENT"}, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "PIPELINE_SYSTEM|PIPELINE|FACILITY|NODE|SEGMENT|APPURTENANCE|CONNECTION|EQUIPMENT")
        String linkedAssetType,

        @Schema(description = "Linked topology asset identifier.", example = "seg_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 120)
        String linkedAssetId) {
}
