/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : BindTelemetryPointRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : REST request DTO for binding telemetry points to topology assets.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;

/**
 * REST request DTO for binding telemetry points to topology assets.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.

 */
public record BindTelemetryPointRequest(
        @Schema(description = "Telemetry point identifier", example = "point-001")
        @NotBlank
        @Size(max = 80)
        String pointId,
        @Valid
        @NotNull
        @Schema(description = "Neutral topology asset reference")
        TopologyAssetReferenceRequest topologyAssetReference,
        @Valid
        @NotNull
        @Schema(description = "Telemetry binding role catalog reference")
        TelemetryTypeReferenceRequest bindingRole,
        @Schema(description = "Binding validity start instant")
        Instant validFrom) {
}
