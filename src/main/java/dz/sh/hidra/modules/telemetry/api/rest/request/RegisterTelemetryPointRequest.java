/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterTelemetryPointRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : REST request DTO for registering telemetry points.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * REST request DTO for registering telemetry points.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.

 */
public record RegisterTelemetryPointRequest(
        @Schema(description = "Telemetry device identifier", example = "device-001")
        @NotBlank
        @Size(max = 80)
        String deviceId,
        @Schema(description = "Telemetry point business code", example = "PT-001")
        @NotBlank
        @Size(max = 120)
        String code,
        @Valid
        @NotNull
        @Schema(description = "Multilingual telemetry point name")
        TelemetryLocalizedNameRequest name,
        @Valid
        @NotNull
        @Schema(description = "Telemetry point type catalog reference")
        TelemetryTypeReferenceRequest pointType,
        @Valid
        @NotNull
        @Schema(description = "Telemetry signal type catalog reference")
        TelemetryTypeReferenceRequest signalType,
        @Valid
        @Schema(description = "Telemetry unit catalog reference")
        TelemetryTypeReferenceRequest unit,
        @Valid
        @Schema(description = "Default aggregation method catalog reference")
        TelemetryTypeReferenceRequest defaultAggregationMethod,
        @Schema(description = "Sampling period in seconds", example = "60")
        @Min(1)
        @Max(86400)
        Integer samplingPeriodSeconds,
        @Schema(description = "External telemetry tag reference", example = "SCADA.PT001.PV")
        @Size(max = 200)
        String externalReference) {
}
