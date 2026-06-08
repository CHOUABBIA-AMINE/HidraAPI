/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ApproveWorkflowTaskRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.request
 *
 * @Description : REST request DTO for approving workflow tasks.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * REST request DTO for approving workflow tasks.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record ApproveWorkflowTaskRequest(
        @Valid
        @NotNull
        @Schema(description = "Actor applying the decision")
        WorkflowActorReferenceRequest actor,
        @Valid
        @Schema(description = "Organization unit snapshot")
        WorkflowOrganizationReferenceRequest organization,
        @Valid
        @Schema(description = "Decision reason catalog reference")
        WorkflowReasonReferenceRequest reason,
        @Schema(description = "Decision note", example = "Reviewed and accepted")
        @Size(max = 2000)
        String note,
        @Schema(description = "Decision comment", example = "All values are consistent")
        @Size(max = 2000)
        String comment,
        @Schema(description = "Optional correlation identifier", example = "corr-20260607-005")
        @Size(max = 120)
        String correlationId) {
}
