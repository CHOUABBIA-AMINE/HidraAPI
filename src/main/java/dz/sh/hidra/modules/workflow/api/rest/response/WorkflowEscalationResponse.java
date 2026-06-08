/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowEscalationResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow escalations.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for workflow escalations.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowEscalationResponse(
        @Schema(description = "Workflow escalation identifier", example = "escalation-001")
        String id,
        @Schema(description = "Workflow task identifier", example = "task-001")
        String taskId,
        @Schema(description = "Workflow escalation rule identifier", example = "escalation-rule-001")
        String ruleId,
        @Schema(description = "Escalating actor snapshot")
        WorkflowActorReferenceResponse actor,
        @Schema(description = "Organization unit identifier", example = "org-trc")
        String organizationUnitId,
        @Schema(description = "Organization unit name snapshot", example = "TRC")
        String organizationUnitNameSnapshot,
        @Schema(description = "Escalation reason catalog reference")
        WorkflowReasonReferenceResponse reason,
        @Schema(description = "Escalation note", example = "Task exceeded expected validation delay")
        String note,
        @Schema(description = "Escalation technical status", example = "TRIGGERED")
        String status,
        @Schema(description = "Escalation timestamp")
        Instant escalatedAt) {
}
