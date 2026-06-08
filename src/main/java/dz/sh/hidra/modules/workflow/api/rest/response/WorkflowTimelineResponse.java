/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTimelineResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for workflow timelines.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * REST response DTO for workflow timelines.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowTimelineResponse(
        @Schema(description = "Workflow instance identifier", example = "instance-001")
        String instanceId,
        @Schema(description = "Neutral workflow target reference")
        WorkflowTargetReferenceResponse target,
        @Schema(description = "Workflow actions ordered as timeline events")
        List<WorkflowActionResponse> actions,
        @Schema(description = "Workflow comments ordered as timeline events")
        List<WorkflowCommentResponse> comments,
        @Schema(description = "Workflow tasks")
        List<WorkflowTaskResponse> tasks) {
    public WorkflowTimelineResponse {
        actions = actions == null ? List.of() : List.copyOf(actions);
        comments = comments == null ? List.of() : List.copyOf(comments);
        tasks = tasks == null ? List.of() : List.copyOf(tasks);
    }

}
