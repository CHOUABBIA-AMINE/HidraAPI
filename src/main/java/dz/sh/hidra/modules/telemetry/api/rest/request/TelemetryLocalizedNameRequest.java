/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryLocalizedNameRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : REST request DTO for multilingual telemetry names.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * REST request DTO for multilingual telemetry names.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.

 * <p>Multilingual policy:
 * User-facing labels must support Arabic, French, and English. French is mandatory in the first implementation.
 */
public record TelemetryLocalizedNameRequest(
        @Schema(description = "Arabic display name", example = "ضغط الخط")
        @Size(max = 160)
        String nameAr,
        @Schema(description = "French display name", example = "Pression ligne")
        @NotBlank
        @Size(max = 160)
        String nameFr,
        @Schema(description = "English display name", example = "Line pressure")
        @Size(max = 160)
        String nameEn) {
}
