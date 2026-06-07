/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingValueResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST response DTO for telemetry reading values.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/**
 * REST response DTO for telemetry reading values.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.
 */
public record TelemetryReadingValueResponse(
        @Schema(description = "Numeric reading value", example = "42.250")
        BigDecimal numericValue,
        @Schema(description = "Text reading value", example = "OPEN")
        String textValue,
        @Schema(description = "Boolean reading value", example = "true")
        Boolean booleanValue) {
}
