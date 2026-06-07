/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDeviceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST response DTO for telemetry devices.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for telemetry devices.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.
 */
public record TelemetryDeviceResponse(
        @Schema(description = "Telemetry device identifier", example = "device-001")
        String id,
        @Schema(description = "Telemetry source identifier", example = "source-001")
        String sourceId,
        @Schema(description = "Telemetry device business code", example = "RTU-SP1")
        String code,
        @Schema(description = "Multilingual telemetry device name")
        TelemetryLocalizedNameResponse name,
        @Schema(description = "Telemetry device type catalog reference")
        TelemetryTypeReferenceResponse deviceType,
        @Schema(description = "External device reference", example = "RTU-001")
        String externalReference,
        @Schema(description = "Technical device lifecycle status", example = "ACTIVE")
        String status,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
}
