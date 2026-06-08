/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CancelWorkflowInstanceRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.request
 *
 * @Description : REST request DTO for cancelling workflow instances.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * REST request DTO for cancelling workflow instances.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record CancelWorkflowInstanceRequest(
        @Valid
        @NotNull
        @Schema(description = "Actor cancelling the workflow")
        WorkflowActorReferenceRequest actor,
        @Valid
        @NotNull
        @Schema(description = "Cancellation reason catalog reference")
        WorkflowReasonReferenceRequest reason,
        @Schema(description = "Cancellation note", example = "Workflow cancelled by supervisor")
        @Size(max = 2000)
        String note,
        @Schema(description = "Optional correlation identifier", example = "corr-20260607-002")
        @Size(max = 120)
        String correlationId) {
}
