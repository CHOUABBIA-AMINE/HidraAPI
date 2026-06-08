/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowLocalizedNameRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.request
 *
 * @Description : REST request DTO for multilingual workflow names.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * REST request DTO for multilingual workflow names.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowLocalizedNameRequest(
        @Schema(description = "Arabic display name", example = "تحقق القياسات")
        @Size(max = 160)
        String nameAr,
        @Schema(description = "French display name", example = "Validation télémétrie")
        @NotBlank
        @Size(max = 160)
        String nameFr,
        @Schema(description = "English display name", example = "Telemetry validation")
        @Size(max = 160)
        String nameEn) {
}
