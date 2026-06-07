/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTypeReferenceRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : REST request DTO for telemetry catalog type references.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * REST request DTO for telemetry catalog type references.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.

 * <p>Controlled vocabulary policy:
 * Business taxonomy values are catalog references, not Java enums.
 */
public record TelemetryTypeReferenceRequest(
        @Schema(description = "Telemetry catalog entry identifier", example = "telemetry-point-type-pressure")
        @NotBlank
        @Size(max = 80)
        String id,
        @Schema(description = "Language-neutral catalog code", example = "PRESSURE")
        @NotBlank
        @Size(max = 120)
        String code) {
}
