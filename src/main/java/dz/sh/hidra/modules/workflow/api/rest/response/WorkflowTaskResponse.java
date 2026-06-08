/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow tasks.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for workflow tasks.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowTaskResponse(
        @Schema(description = "Workflow task identifier", example = "task-001")
        String id,
        @Schema(description = "Workflow instance identifier", example = "instance-001")
        String instanceId,
        @Schema(description = "Workflow step identifier", example = "step-review")
        String stepId,
        @Schema(description = "Workflow task technical status", example = "OPEN")
        String status,
        @Schema(description = "Assigned actor snapshot")
        WorkflowActorReferenceResponse assignedActor,
        @Schema(description = "Assigned organization unit identifier", example = "org-trc")
        String assignedOrganizationUnitId,
        @Schema(description = "Assigned organization unit name snapshot", example = "TRC")
        String assignedOrganizationUnitNameSnapshot,
        @Schema(description = "Workflow priority catalog reference")
        WorkflowPriorityReferenceResponse priority,
        @Schema(description = "Task due timestamp")
        Instant dueAt,
        @Schema(description = "Claiming actor snapshot")
        WorkflowActorReferenceResponse claimedBy,
        @Schema(description = "Claim timestamp")
        Instant claimedAt,
        @Schema(description = "Completing actor snapshot")
        WorkflowActorReferenceResponse completedBy,
        @Schema(description = "Completion timestamp")
        Instant completedAt,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
}
