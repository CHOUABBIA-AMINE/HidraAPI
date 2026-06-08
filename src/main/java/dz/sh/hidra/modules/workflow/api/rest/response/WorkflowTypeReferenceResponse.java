/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTypeReferenceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow type catalog references.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response DTO for workflow type catalog references.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowTypeReferenceResponse(
        @Schema(description = "Workflow catalog entry identifier", example = "workflow-type-telemetry-validation")
        String id,
        @Schema(description = "Workflow catalog entry code", example = "TELEMETRY_VALIDATION")
        String code,
        @Schema(description = "Localized workflow catalog label", example = "Validation télémétrie")
        String label,
        @Schema(description = "Resolved label locale", example = "fr")
        String locale) {
}
