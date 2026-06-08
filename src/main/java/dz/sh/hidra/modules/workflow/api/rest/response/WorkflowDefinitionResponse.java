/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow definitions.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

/**
 * REST response DTO for workflow definitions.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowDefinitionResponse(
        @Schema(description = "Workflow definition identifier", example = "workflow-definition-001")
        String id,
        @Schema(description = "Workflow definition business code", example = "TELEMETRY_READING_VALIDATION")
        String code,
        @Schema(description = "Multilingual workflow definition name")
        WorkflowLocalizedNameResponse name,
        @Schema(description = "Workflow type catalog reference")
        WorkflowTypeReferenceResponse type,
        @Schema(description = "Workflow definition technical lifecycle status", example = "ACTIVE")
        String status,
        @Schema(description = "Workflow definition version", example = "1")
        Integer version,
        @Schema(description = "Workflow definition steps")
        List<WorkflowStepResponse> steps,
        @Schema(description = "Workflow definition transitions")
        List<WorkflowTransitionResponse> transitions,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
    public WorkflowDefinitionResponse {
        steps = steps == null ? List.of() : List.copyOf(steps);
        transitions = transitions == null ? List.of() : List.copyOf(transitions);
    }

}
