/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow definition transitions.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for workflow definition transitions.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowTransitionResponse(
        @Schema(description = "Workflow transition identifier", example = "transition-review-approve")
        String id,
        @Schema(description = "Workflow definition identifier", example = "workflow-definition-001")
        String definitionId,
        @Schema(description = "Source step identifier", example = "step-review")
        String fromStepId,
        @Schema(description = "Target step identifier", example = "step-complete")
        String toStepId,
        @Schema(description = "Workflow technical decision", example = "APPROVE")
        String decision,
        @Schema(description = "Whether a reason is required", example = "false")
        Boolean reasonRequired,
        @Schema(description = "Whether a comment is required", example = "false")
        Boolean commentRequired,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
}
