/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateWorkflowTaskCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.command
 *
 * @Description : Command to create a workflow task.
 *
 */
package dz.sh.hidra.modules.workflow.application.command;

import java.time.Instant;

/**
 * Command to create a workflow task.
 */
public record CreateWorkflowTaskCommand(
        String instanceId,
        String stepId,
        String assignedActorId,
        String assignedActorUsernameSnapshot,
        String assignedActorDisplayNameSnapshot,
        String assignedOrganizationUnitId,
        String assignedOrganizationUnitNameSnapshot,
        String assignedRoleCodeSnapshot,
        String priorityId,
        Instant dueAt,
        String assignmentModeId,
        String taskLabelSnapshot
) {
}
