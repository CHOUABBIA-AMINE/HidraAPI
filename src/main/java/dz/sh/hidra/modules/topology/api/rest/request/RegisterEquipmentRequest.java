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
 * This is a REST input contract. It does not create maintenance records, inspections, reliability
 * calculations, telemetry, workflow tasks, or risk scores.
 *
 * <p>Validation:
 * Code, name, equipment type, parent asset type, and parent asset id are required. Parent asset type
 * cannot be EQUIPMENT.
 *
 * @param code equipment business code
 * @param name equipment display name
 * @param equipmentType equipment type
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

        @Schema(description = "Equipment type.", example = "COMPRESSOR", allowableValues = {"COMPRESSOR", "PUMP", "VALVE", "METER", "SEPARATOR", "SCRAPER_LAUNCHER", "SCRAPER_RECEIVER", "ACTUATOR", "CONTROL_PANEL", "INSTRUMENTATION", "OTHER"}, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "COMPRESSOR|PUMP|VALVE|METER|SEPARATOR|SCRAPER_LAUNCHER|SCRAPER_RECEIVER|ACTUATOR|CONTROL_PANEL|INSTRUMENTATION|OTHER")
        String equipmentType,

        @Schema(description = "Parent topology asset type. EQUIPMENT is not allowed as a parent.", example = "FACILITY", allowableValues = {"PIPELINE_SYSTEM", "PIPELINE", "FACILITY", "NODE", "SEGMENT", "APPURTENANCE", "CONNECTION"}, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "PIPELINE_SYSTEM|PIPELINE|FACILITY|NODE|SEGMENT|APPURTENANCE|CONNECTION")
        String parentAssetType,

        @Schema(description = "Parent topology asset identifier.", example = "fac_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 120)
        String parentAssetId) {
}
