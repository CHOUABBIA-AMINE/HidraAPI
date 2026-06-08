/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow actions.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for workflow actions.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowActionResponse(
        @Schema(description = "Workflow action identifier", example = "action-001")
        String id,
        @Schema(description = "Workflow instance identifier", example = "instance-001")
        String instanceId,
        @Schema(description = "Workflow task identifier", example = "task-001")
        String taskId,
        @Schema(description = "Workflow action type", example = "APPROVE")
        String actionType,
        @Schema(description = "Workflow decision", example = "APPROVE")
        String decision,
        @Schema(description = "Decision reason catalog reference")
        WorkflowReasonReferenceResponse reason,
        @Schema(description = "Decision note", example = "Reviewed and accepted")
        String decisionNote,
        @Schema(description = "Workflow comment", example = "All values are consistent")
        String comment,
        @Schema(description = "Actor snapshot")
        WorkflowActorReferenceResponse actor,
        @Schema(description = "Organization unit identifier", example = "org-trc")
        String organizationUnitId,
        @Schema(description = "Organization unit name snapshot", example = "TRC")
        String organizationUnitNameSnapshot,
        @Schema(description = "Correlation identifier", example = "corr-20260607-001")
        String correlationId,
        @Schema(description = "Action timestamp")
        Instant actedAt) {
}
