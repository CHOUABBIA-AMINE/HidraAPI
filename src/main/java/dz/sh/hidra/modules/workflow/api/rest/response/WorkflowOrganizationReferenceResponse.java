/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowOrganizationReferenceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow organization unit snapshots.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response DTO for workflow organization unit snapshots.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowOrganizationReferenceResponse(
        @Schema(description = "Organization unit identifier", example = "org-trc")
        String organizationUnitId,
        @Schema(description = "Organization unit name snapshot", example = "TRC")
        String organizationUnitNameSnapshot,
        @Schema(description = "Role or authority code snapshot", example = "VALIDATOR")
        String roleCodeSnapshot) {
}
