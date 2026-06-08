/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAssignmentResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow assignments.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for workflow assignments.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowAssignmentResponse(
        @Schema(description = "Workflow assignment identifier", example = "assignment-001")
        String id,
        @Schema(description = "Workflow task identifier", example = "task-001")
        String taskId,
        @Schema(description = "Assigned actor snapshot")
        WorkflowActorReferenceResponse actor,
        @Schema(description = "Assigned organization unit identifier", example = "org-trc")
        String organizationUnitId,
        @Schema(description = "Assigned organization unit name snapshot", example = "TRC")
        String organizationUnitNameSnapshot,
        @Schema(description = "Role code snapshot", example = "VALIDATOR")
        String roleCodeSnapshot,
        @Schema(description = "Assignment technical status", example = "ASSIGNED")
        String status,
        @Schema(description = "Assignment timestamp")
        Instant assignedAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
}
