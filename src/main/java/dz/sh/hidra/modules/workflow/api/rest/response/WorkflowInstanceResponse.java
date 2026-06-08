/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow instances.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

/**
 * REST response DTO for workflow instances.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowInstanceResponse(
        @Schema(description = "Workflow instance identifier", example = "instance-001")
        String id,
        @Schema(description = "Workflow definition identifier", example = "workflow-definition-001")
        String definitionId,
        @Schema(description = "Workflow definition version", example = "1")
        Integer definitionVersion,
        @Schema(description = "Neutral workflow target reference")
        WorkflowTargetReferenceResponse target,
        @Schema(description = "Workflow instance technical status", example = "IN_PROGRESS")
        String status,
        @Schema(description = "Current workflow step identifier", example = "step-review")
        String currentStepId,
        @Schema(description = "Actor who started the workflow")
        WorkflowActorReferenceResponse startedBy,
        @Schema(description = "Workflow start timestamp")
        Instant startedAt,
        @Schema(description = "Workflow completion timestamp")
        Instant completedAt,
        @Schema(description = "Workflow cancellation timestamp")
        Instant cancelledAt,
        @Schema(description = "Correlation identifier", example = "corr-20260607-001")
        String correlationId,
        @Schema(description = "Workflow tasks")
        List<WorkflowTaskResponse> tasks,
        @Schema(description = "Workflow actions")
        List<WorkflowActionResponse> actions,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
    public WorkflowInstanceResponse {
        tasks = tasks == null ? List.of() : List.copyOf(tasks);
        actions = actions == null ? List.of() : List.copyOf(actions);
    }

}
