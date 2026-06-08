/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RejectWorkflowTaskCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.command
 *
 * @Description : Command to reject a workflow task.
 *
 */
package dz.sh.hidra.modules.workflow.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecisionNote;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;
import java.util.Objects;

/**
 * Command to reject a workflow task.
 *
 * <p>Architecture role:
 * Application-layer command contract for the workflow module. It carries validated
 * workflow domain value objects and catalog references only. It must not depend on persistence,
 * REST, Spring, JPA, telemetry implementation classes, topology implementation classes, planning,
 * monitoring, incidents, audit implementation, integration, analytics, reporting, or notification.
 */
public record RejectWorkflowTaskCommand(
        WorkflowInstanceId instanceId,
        WorkflowTaskId taskId,
        WorkflowActorReference actor,
        WorkflowOrganizationReference organization,
        WorkflowReasonReference reason,
        WorkflowDecisionNote note,
        WorkflowCommentText comment,
        WorkflowCorrelationId correlationId) implements Command {

    public RejectWorkflowTaskCommand {
        instanceId = Objects.requireNonNull(instanceId, "RejectWorkflowTaskCommand instanceId must not be null.");
        taskId = Objects.requireNonNull(taskId, "RejectWorkflowTaskCommand taskId must not be null.");
        actor = Objects.requireNonNull(actor, "RejectWorkflowTaskCommand actor must not be null.");
        reason = Objects.requireNonNull(reason, "RejectWorkflowTaskCommand reason must not be null.");
    }
}
