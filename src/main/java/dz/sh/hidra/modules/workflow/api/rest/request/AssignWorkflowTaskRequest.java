/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignWorkflowTaskRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.request
 *
 * @Description : REST request DTO for assigning workflow tasks.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.time.Instant;

/**
 * REST request DTO for assigning workflow tasks.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record AssignWorkflowTaskRequest(
        @Valid
        @Schema(description = "Assigned actor snapshot")
        WorkflowActorReferenceRequest assignedActor,
        @Valid
        @Schema(description = "Assigned organization unit snapshot")
        WorkflowOrganizationReferenceRequest assignedOrganization,
        @Valid
        @Schema(description = "Priority catalog reference")
        WorkflowPriorityReferenceRequest priority,
        @Schema(description = "Optional due date")
        Instant dueAt,
        @Schema(description = "Optional correlation identifier", example = "corr-20260607-003")
        @Size(max = 120)
        String correlationId) {
}
