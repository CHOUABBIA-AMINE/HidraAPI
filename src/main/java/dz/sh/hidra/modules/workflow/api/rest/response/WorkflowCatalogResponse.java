/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow catalog entries.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

/**
 * REST response DTO for workflow catalog entries.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowCatalogResponse(
        @Schema(description = "Workflow catalog entry identifier", example = "workflow-priority-normal")
        String id,
        @Schema(description = "Catalog family name", example = "PRIORITY")
        String catalogName,
        @Schema(description = "Language-neutral catalog code", example = "NORMAL")
        String code,
        @Schema(description = "Whether the catalog entry is active", example = "true")
        Boolean active,
        @Schema(description = "Sort order", example = "20")
        Integer sortOrder,
        @Schema(description = "Whether this row is system-defined", example = "true")
        Boolean systemDefined,
        @Schema(description = "Localized translations")
        List<WorkflowCatalogTranslationResponse> translations,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
    public WorkflowCatalogResponse {
        translations = translations == null ? List.of() : List.copyOf(translations);
    }

}
