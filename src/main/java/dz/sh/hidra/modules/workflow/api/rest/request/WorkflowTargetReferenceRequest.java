/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTargetReferenceRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.request
 *
 * @Description : REST request DTO for neutral workflow target references.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * REST request DTO for neutral workflow target references.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowTargetReferenceRequest(
        @Schema(description = "Target owning module", example = "telemetry")
        @NotBlank
        @Size(max = 80)
        String targetModule,
        @Valid
        @NotNull
        @Schema(description = "Workflow target type catalog reference")
        WorkflowTargetTypeReferenceRequest targetType,
        @Schema(description = "Target identifier", example = "reading-001")
        @NotBlank
        @Size(max = 120)
        String targetId,
        @Schema(description = "Optional target code snapshot", example = "reading-001")
        @Size(max = 120)
        String targetCodeSnapshot,
        @Schema(description = "Optional target label snapshot", example = "Telemetry reading reading-001")
        @Size(max = 240)
        String targetLabelSnapshot) {
}
