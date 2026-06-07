/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCatalogDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.dto
 *
 * @Description : Application DTO for telemetry controlled vocabulary catalog entries.
 *
 */
package dz.sh.hidra.modules.telemetry.application.dto;

import java.time.Instant;
import java.util.List;

/**
 * Application DTO for telemetry controlled vocabulary catalog entries.
 *
 * <p>Architecture role:
 * Application-layer telemetry data transfer projection. It is technology-neutral and must not depend
 * on REST, persistence, Spring, JPA, topology implementation classes, flow, risk, analytics,
 * workflow, reporting, or notification modules.
 */
public record TelemetryCatalogDto(
        String id,
        String catalogName,
        String code,
        Boolean active,
        Integer sortOrder,
        Boolean systemDefined,
        String resolvedLocale,
        String resolvedName,
        String resolvedDescription,
        List<TelemetryCatalogTranslationDto> translations,
        Instant createdAt,
        Instant updatedAt) {

    public TelemetryCatalogDto {
        translations = translations == null ? List.of() : List.copyOf(translations);
    }
}
