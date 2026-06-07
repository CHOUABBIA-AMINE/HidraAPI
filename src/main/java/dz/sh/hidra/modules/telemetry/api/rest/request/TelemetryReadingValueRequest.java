/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingValueRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : REST request DTO for telemetry reading values.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * REST request DTO for telemetry reading values.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.

 * <p>Validation policy:
 * Exactly one of numericValue, textValue, or booleanValue is expected. Domain value object validation enforces this rule.
 */
public record TelemetryReadingValueRequest(
        @Schema(description = "Numeric reading value", example = "42.250")
        BigDecimal numericValue,
        @Schema(description = "Text reading value", example = "OPEN")
        @Size(max = 500)
        String textValue,
        @Schema(description = "Boolean reading value", example = "true")
        Boolean booleanValue) {
}
