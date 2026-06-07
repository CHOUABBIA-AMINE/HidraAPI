/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPageDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.dto
 *
 * @Description : Application DTO for paginated telemetry projections.
 *
 */
package dz.sh.hidra.modules.telemetry.application.dto;

import java.util.List;

/**
 * Application DTO for paginated telemetry projections.
 *
 * <p>Architecture role:
 * Generic application-layer pagination projection. It remains independent from REST, persistence,
 * Spring, JPA, topology implementation classes, flow, risk, analytics, workflow, reporting, and
 * notification modules.
 *
 * @param items page items
 * @param page page number
 * @param size page size
 * @param totalElements total element count
 * @param totalPages total page count
 * @param <T> item type
 */
public record TelemetryPageDto<T>(
        List<T> items,
        Integer page,
        Integer size,
        Long totalElements,
        Integer totalPages) {

    public TelemetryPageDto {
        items = items == null ? List.of() : List.copyOf(items);
    }

    public static <T> TelemetryPageDto<T> empty(Integer page, Integer size) {
        return new TelemetryPageDto<>(List.of(), page, size, 0L, 0);
    }
}
