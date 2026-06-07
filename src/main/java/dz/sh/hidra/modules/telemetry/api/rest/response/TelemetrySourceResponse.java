/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST response DTO for telemetry acquisition sources.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for telemetry acquisition sources.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.
 */
public record TelemetrySourceResponse(
        @Schema(description = "Telemetry source identifier", example = "source-001")
        String id,
        @Schema(description = "Telemetry source business code", example = "SCADA_TRAPSA")
        String code,
        @Schema(description = "Multilingual telemetry source name")
        TelemetryLocalizedNameResponse name,
        @Schema(description = "Telemetry source type catalog reference")
        TelemetryTypeReferenceResponse sourceType,
        @Schema(description = "Telemetry protocol catalog reference")
        TelemetryTypeReferenceResponse protocol,
        @Schema(description = "Telemetry source endpoint URI", example = "opc.tcp://scada.example.local:4840")
        String endpointUri,
        @Schema(description = "External source system reference", example = "SCADA-TRC-01")
        String externalReference,
        @Schema(description = "Technical source lifecycle status", example = "ACTIVE")
        String status,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
}
