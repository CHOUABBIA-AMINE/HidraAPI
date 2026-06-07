/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CloseTelemetryPointBindingRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : REST request DTO for closing telemetry point topology bindings.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST request DTO for closing telemetry point topology bindings.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.

 */
public record CloseTelemetryPointBindingRequest(
        @Schema(description = "Binding closure instant. Defaults to current time when omitted.")
        Instant closedAt) {
}
