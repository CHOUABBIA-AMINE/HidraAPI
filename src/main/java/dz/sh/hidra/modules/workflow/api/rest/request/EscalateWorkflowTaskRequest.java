/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EscalateWorkflowTaskRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.request
 *
 * @Description : REST request DTO for escalating workflow tasks.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * REST request DTO for escalating workflow tasks.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record EscalateWorkflowTaskRequest(
        @Valid
        @NotNull
        @Schema(description = "Actor escalating the task")
        WorkflowActorReferenceRequest actor,
        @Valid
        @Schema(description = "Organization unit snapshot")
        WorkflowOrganizationReferenceRequest organization,
        @Valid
        @NotNull
        @Schema(description = "Escalation reason catalog reference")
        WorkflowReasonReferenceRequest reason,
        @Schema(description = "Escalation note", example = "Task exceeded expected validation delay")
        @Size(max = 2000)
        String note,
        @Schema(description = "Optional correlation identifier", example = "corr-20260607-007")
        @Size(max = 120)
        String correlationId) {
}
