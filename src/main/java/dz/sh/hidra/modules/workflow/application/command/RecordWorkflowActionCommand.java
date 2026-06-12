/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordWorkflowActionCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.command
 *
 * @Description : Command to record workflow action.
 *
 */
package dz.sh.hidra.modules.workflow.application.command;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;

import java.time.Instant;

/**
 * Command to record workflow action.
 */
public record RecordWorkflowActionCommand(
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
