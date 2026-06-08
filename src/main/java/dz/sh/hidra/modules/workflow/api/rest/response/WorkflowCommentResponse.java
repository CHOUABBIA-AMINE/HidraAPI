/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCommentResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow comments.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for workflow comments.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowCommentResponse(
        @Schema(description = "Workflow comment identifier", example = "comment-001")
        String id,
        @Schema(description = "Workflow instance identifier", example = "instance-001")
        String instanceId,
        @Schema(description = "Workflow task identifier", example = "task-001")
        String taskId,
        @Schema(description = "Commenting actor snapshot")
        WorkflowActorReferenceResponse actor,
        @Schema(description = "Comment text", example = "Reading verified with field team")
        String text,
        @Schema(description = "Comment timestamp")
        Instant commentedAt) {
}
