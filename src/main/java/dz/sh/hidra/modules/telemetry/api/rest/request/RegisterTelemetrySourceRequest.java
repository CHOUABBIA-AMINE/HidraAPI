/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterTelemetrySourceRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : REST request DTO for registering telemetry acquisition sources.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * REST request DTO for registering telemetry acquisition sources.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.

 */
public record RegisterTelemetrySourceRequest(
        @Schema(description = "Telemetry source business code", example = "SCADA_TRAPSA")
        @NotBlank
        @Size(max = 120)
        String code,
        @Valid
        @NotNull
        @Schema(description = "Multilingual telemetry source name")
        TelemetryLocalizedNameRequest name,
        @Valid
        @NotNull
        @Schema(description = "Telemetry source type catalog reference")
        TelemetryTypeReferenceRequest sourceType,
        @Valid
        @NotNull
        @Schema(description = "Telemetry protocol catalog reference")
        TelemetryTypeReferenceRequest protocol,
        @Schema(description = "Telemetry source endpoint URI", example = "opc.tcp://scada.example.local:4840")
        @Size(max = 500)
        String endpointUri,
        @Schema(description = "External source system reference", example = "SCADA-TRC-01")
        @Size(max = 200)
        String externalReference) {
}
