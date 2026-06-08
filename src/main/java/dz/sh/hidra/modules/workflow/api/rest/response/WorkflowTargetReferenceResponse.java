/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTargetReferenceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for neutral workflow target references.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response DTO for neutral workflow target references.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowTargetReferenceResponse(
        @Schema(description = "Target owning module", example = "telemetry")
        String targetModule,
        @Schema(description = "Workflow target type catalog reference")
        WorkflowTypeReferenceResponse targetType,
        @Schema(description = "Target identifier", example = "reading-001")
        String targetId,
        @Schema(description = "Target code snapshot", example = "reading-001")
        String targetCodeSnapshot,
        @Schema(description = "Target label snapshot", example = "Telemetry reading reading-001")
        String targetLabelSnapshot) {
}
