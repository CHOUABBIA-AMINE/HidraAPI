/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response for workflow action.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import java.time.Instant;

/**
 * REST response for workflow action.
 */
public record WorkflowActionResponse(
        String id,
        String instanceId,
        String taskId,
        WorkflowActionType actionType,
        WorkflowDecision decision,
        String reasonId,
        String actorId,
        String actorDisplayNameSnapshot,
        long actionSequence,
        Instant actedAt
) {
}
