/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing topology equipment.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing topology equipment.
 *
 * @param equipmentId equipment identifier
 * @param code equipment business code
 * @param name equipment display name
 * @param equipmentType localized equipment type catalog reference
 * @param parentAssetType parent topology asset type
 * @param parentAssetId parent topology asset identifier
 * @param status lifecycle status
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
@Schema(name = "EquipmentResponse", description = "REST response representing topology equipment.")
public record EquipmentResponse(
        @Schema(description = "Equipment identifier.", example = "eqp_550e8400-e29b-41d4-a716-446655440000")
        String equipmentId,

        @Schema(description = "Equipment business code.", example = "CMP-CS-EAST-01-A")
        String code,

        @Schema(description = "Equipment display name.", example = "Compressor A")
        String name,

        @Schema(description = "Localized equipment type catalog reference.")
        TopologyTypeReferenceResponse equipmentType,

        @Schema(description = "Parent topology asset type.", example = "FACILITY", allowableValues = {"PIPELINE_SYSTEM", "PIPELINE", "FACILITY", "NODE", "SEGMENT", "APPURTENANCE", "CONNECTION"})
        String parentAssetType,

        @Schema(description = "Parent topology asset identifier.", example = "fac_550e8400-e29b-41d4-a716-446655440000")
        String parentAssetId,

        @Schema(description = "Equipment lifecycle status.", example = "ACTIVE", allowableValues = {"PLANNED", "ACTIVE", "INACTIVE", "UNDER_MAINTENANCE", "RETIRED", "DECOMMISSIONED"})
        String status,

        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,

        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {
}
