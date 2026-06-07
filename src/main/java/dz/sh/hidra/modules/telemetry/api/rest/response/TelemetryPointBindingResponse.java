/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointBindingResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST response DTO for telemetry point topology bindings.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for telemetry point topology bindings.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.
 */
public record TelemetryPointBindingResponse(
        @Schema(description = "Telemetry point binding identifier", example = "binding-001")
        String id,
        @Schema(description = "Telemetry point identifier", example = "point-001")
        String pointId,
        @Schema(description = "Neutral topology asset reference")
        TopologyAssetReferenceResponse topologyAssetReference,
        @Schema(description = "Telemetry binding role catalog reference")
        TelemetryTypeReferenceResponse bindingRole,
        @Schema(description = "Whether the binding is active", example = "true")
        Boolean active,
        @Schema(description = "Binding validity start instant")
        Instant validFrom,
        @Schema(description = "Binding validity end instant")
        Instant validTo,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
}
