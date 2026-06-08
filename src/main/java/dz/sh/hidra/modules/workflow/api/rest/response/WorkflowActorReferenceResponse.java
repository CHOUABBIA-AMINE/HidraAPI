/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActorReferenceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow actor snapshots.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response DTO for workflow actor snapshots.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowActorReferenceResponse(
        @Schema(description = "Identity actor identifier", example = "actor-001")
        String actorId,
        @Schema(description = "Actor username snapshot", example = "a.medjerab")
        String actorUsernameSnapshot,
        @Schema(description = "Actor display name snapshot", example = "Abir MEDJERAB")
        String actorDisplayNameSnapshot,
        @Schema(description = "Actor role code snapshot", example = "SUPERVISOR")
        String roleCodeSnapshot) {
}
