/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterEquipmentRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request body for registering topology equipment.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request body used to register topology equipment.
 *
 * <p>Business role:
 * Registers optional physical equipment/component references attached to topology assets.
 *
 * <p>Architecture role:
 * This REST input contract accepts a stable equipment type catalog code. Parent asset type remains an
 * internal topology asset discriminator.
 *
 * <p>Validation:
 * Code, name, equipment type code, parent asset type, and parent asset id are required. Parent asset
 * type cannot be EQUIPMENT.
 *
 * @param code equipment business code
 * @param name equipment display name
 * @param equipmentTypeCode language-neutral equipment type catalog code
 * @param parentAssetType parent topology asset type
 * @param parentAssetId parent topology asset identifier
 */
@Schema(name = "RegisterEquipmentRequest", description = "Request body for registering topology equipment.")
public record RegisterEquipmentRequest(
        @Schema(description = "Equipment business code.", example = "CMP-CS-EAST-01-A", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String code,

        @Schema(description = "Equipment display name.", example = "Compressor A", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 160)
        String name,

        @Schema(description = "Language-neutral equipment type catalog code.", example = "COMPRESSOR", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        @Pattern(regexp = "[A-Za-z0-9][A-Za-z0-9_.-]{1,79}")
        String equipmentTypeCode,

        @Schema(description = "Parent topology asset type. EQUIPMENT is not allowed as a parent.", example = "FACILITY", allowableValues = {"PIPELINE_SYSTEM", "PIPELINE", "FACILITY", "NODE", "SEGMENT", "APPURTENANCE", "CONNECTION"}, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "PIPELINE_SYSTEM|PIPELINE|FACILITY|NODE|SEGMENT|APPURTENANCE|CONNECTION")
        String parentAssetType,

        @Schema(description = "Parent topology asset identifier.", example = "fac_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 120)
        String parentAssetId) {
}
