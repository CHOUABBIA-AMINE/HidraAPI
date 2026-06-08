/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStepResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow definition steps.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for workflow definition steps.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowStepResponse(
        @Schema(description = "Workflow step identifier", example = "step-review")
        String id,
        @Schema(description = "Workflow definition identifier", example = "workflow-definition-001")
        String definitionId,
        @Schema(description = "Workflow step business code", example = "SUPERVISOR_REVIEW")
        String code,
        @Schema(description = "Multilingual step name")
        WorkflowLocalizedNameResponse name,
        @Schema(description = "Step order", example = "0")
        Integer stepOrder,
        @Schema(description = "Whether the step is mandatory", example = "true")
        Boolean mandatory,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
}
