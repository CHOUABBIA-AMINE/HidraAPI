/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordWorkflowActionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.request
 *
 * @Description : REST request for record workflow action.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.request;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import java.time.Instant;

/**
 * REST request for record workflow action.
 */
public record RecordWorkflowActionRequest(
        String instanceId,
        String taskId,
        WorkflowActionType actionType,
        WorkflowDecision decision,
        String reasonId,
        String decisionNote,
        String commentText,
        String actorId,
        String actorUsernameSnapshot,
        String actorDisplayNameSnapshot,
        String actorRoleCodeSnapshot,
        String organizationUnitId,
        String organizationUnitNameSnapshot,
        String organizationRoleCodeSnapshot,
        String correlationId,
        long actionSequence,
        String sourceSystem,
        Instant actedAt
) {
}
