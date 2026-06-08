/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogTranslationResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow catalog translations.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for workflow catalog translations.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowCatalogTranslationResponse(
        @Schema(description = "Translation identifier", example = "workflow-priority-normal-fr")
        String id,
        @Schema(description = "Workflow catalog entry identifier", example = "workflow-priority-normal")
        String catalogId,
        @Schema(description = "Translation locale", example = "fr")
        String locale,
        @Schema(description = "Localized display name", example = "Normale")
        String name,
        @Schema(description = "Localized description", example = "Priorité normale")
        String description,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
}
