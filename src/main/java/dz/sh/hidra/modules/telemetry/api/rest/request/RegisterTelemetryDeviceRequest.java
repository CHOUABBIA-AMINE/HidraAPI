/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterTelemetryDeviceRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : REST request DTO for registering telemetry devices.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * REST request DTO for registering telemetry devices.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.

 */
public record RegisterTelemetryDeviceRequest(
        @Schema(description = "Telemetry source identifier", example = "source-001")
        @NotBlank
        @Size(max = 80)
        String sourceId,
        @Schema(description = "Telemetry device business code", example = "RTU-SP1")
        @NotBlank
        @Size(max = 120)
        String code,
        @Valid
        @NotNull
        @Schema(description = "Multilingual telemetry device name")
        TelemetryLocalizedNameRequest name,
        @Valid
        @NotNull
        @Schema(description = "Telemetry device type catalog reference")
        TelemetryTypeReferenceRequest deviceType,
        @Schema(description = "External device reference", example = "RTU-001")
        @Size(max = 200)
        String externalReference) {
}
