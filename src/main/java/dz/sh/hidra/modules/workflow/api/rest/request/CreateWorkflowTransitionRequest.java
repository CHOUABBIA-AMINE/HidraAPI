/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateWorkflowTransitionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.request
 *
 * @Description : REST request DTO for creating workflow definition transitions.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * REST request DTO for creating workflow definition transitions.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record CreateWorkflowTransitionRequest(
        @Schema(description = "Source workflow step identifier", example = "step-review")
        @NotBlank
        @Size(max = 80)
        String fromStepId,
        @Schema(description = "Target workflow step identifier", example = "step-complete")
        @NotBlank
        @Size(max = 80)
        String toStepId,
        @Schema(description = "Workflow technical decision", example = "APPROVE")
        @NotBlank
        @Size(max = 40)
        String decision,
        @Schema(description = "Whether a reason is required", example = "false")
        boolean reasonRequired,
        @Schema(description = "Whether a comment is required", example = "false")
        boolean commentRequired) {
}
