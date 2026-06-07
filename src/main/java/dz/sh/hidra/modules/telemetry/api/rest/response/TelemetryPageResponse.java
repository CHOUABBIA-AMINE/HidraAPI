/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPageResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST response DTO for paginated telemetry responses.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * REST response DTO for paginated telemetry responses.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.
 */
public record TelemetryPageResponse<T>(
        @Schema(description = "Page items")
        List<T> items,
        @Schema(description = "Page number", example = "0")
        Integer page,
        @Schema(description = "Page size", example = "20")
        Integer size,
        @Schema(description = "Total element count", example = "125")
        Long totalElements,
        @Schema(description = "Total page count", example = "7")
        Integer totalPages) {
    public TelemetryPageResponse {
        items = items == null ? List.of() : List.copyOf(items);
    }

    public static <T> TelemetryPageResponse<T> empty(Integer page, Integer size) {
        return new TelemetryPageResponse<>(List.of(), page, size, 0L, 0);
    }
}
