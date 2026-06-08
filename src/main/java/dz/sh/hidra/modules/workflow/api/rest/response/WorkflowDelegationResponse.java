/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDelegationResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow delegations.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for workflow delegations.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowDelegationResponse(
        @Schema(description = "Workflow delegation identifier", example = "delegation-001")
        String id,
        @Schema(description = "Workflow task identifier", example = "task-001")
        String taskId,
        @Schema(description = "Delegating actor snapshot")
        WorkflowActorReferenceResponse fromActor,
        @Schema(description = "Target actor snapshot")
        WorkflowActorReferenceResponse toActor,
        @Schema(description = "Target organization unit identifier", example = "org-trc")
        String toOrganizationUnitId,
        @Schema(description = "Target organization unit name snapshot", example = "TRC")
        String toOrganizationUnitNameSnapshot,
        @Schema(description = "Delegation reason catalog reference")
        WorkflowReasonReferenceResponse reason,
        @Schema(description = "Delegation timestamp")
        Instant delegatedAt) {
}
