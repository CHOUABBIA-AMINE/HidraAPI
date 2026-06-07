/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCatalogResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST response DTO for telemetry controlled vocabulary catalog entries.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

/**
 * REST response DTO for telemetry controlled vocabulary catalog entries.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.
 */
public record TelemetryCatalogResponse(
        @Schema(description = "Catalog entry identifier", example = "telemetry-point-type-pressure")
        String id,
        @Schema(description = "Catalog family name", example = "POINT_TYPE")
        String catalogName,
        @Schema(description = "Language-neutral catalog code", example = "PRESSURE")
        String code,
        @Schema(description = "Whether the catalog entry is active", example = "true")
        Boolean active,
        @Schema(description = "Sort order", example = "10")
        Integer sortOrder,
        @Schema(description = "Whether the entry is system-defined", example = "true")
        Boolean systemDefined,
        @Schema(description = "Resolved locale", example = "fr")
        String resolvedLocale,
        @Schema(description = "Resolved localized name", example = "Pression")
        String resolvedName,
        @Schema(description = "Resolved localized description", example = "Description Pression")
        String resolvedDescription,
        @Schema(description = "Available translations")
        List<TelemetryCatalogTranslationResponse> translations,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
    public TelemetryCatalogResponse {
        translations = translations == null ? List.of() : List.copyOf(translations);
    }
}
