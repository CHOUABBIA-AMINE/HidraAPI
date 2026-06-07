/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST response DTO for telemetry points.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for telemetry points.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.
 */
public record TelemetryPointResponse(
        @Schema(description = "Telemetry point identifier", example = "point-001")
        String id,
        @Schema(description = "Telemetry device identifier", example = "device-001")
        String deviceId,
        @Schema(description = "Telemetry point business code", example = "PT-001")
        String code,
        @Schema(description = "Multilingual telemetry point name")
        TelemetryLocalizedNameResponse name,
        @Schema(description = "Telemetry point type catalog reference")
        TelemetryTypeReferenceResponse pointType,
        @Schema(description = "Telemetry signal type catalog reference")
        TelemetryTypeReferenceResponse signalType,
        @Schema(description = "Telemetry unit catalog reference")
        TelemetryTypeReferenceResponse unit,
        @Schema(description = "Default aggregation method catalog reference")
        TelemetryTypeReferenceResponse defaultAggregationMethod,
        @Schema(description = "Sampling period in seconds", example = "60")
        Integer samplingPeriodSeconds,
        @Schema(description = "External telemetry tag reference", example = "SCADA.PT001.PV")
        String externalReference,
        @Schema(description = "Technical point lifecycle status", example = "ACTIVE")
        String status,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
}
