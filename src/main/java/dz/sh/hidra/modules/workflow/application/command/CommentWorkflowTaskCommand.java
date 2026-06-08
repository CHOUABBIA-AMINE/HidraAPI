/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CommentWorkflowTaskCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.command
 *
 * @Description : Command to add a comment to a workflow task or instance.
 *
 */
package dz.sh.hidra.modules.workflow.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;
import java.util.Objects;

/**
 * Command to add a comment to a workflow task or instance.
 *
 * <p>Architecture role:
 * Application-layer command contract for the workflow module. It carries validated
 * workflow domain value objects and catalog references only. It must not depend on persistence,
 * REST, Spring, JPA, telemetry implementation classes, topology implementation classes, planning,
 * monitoring, incidents, audit implementation, integration, analytics, reporting, or notification.
 */
public record CommentWorkflowTaskCommand(
        WorkflowInstanceId instanceId,
        WorkflowTaskId taskId,
        WorkflowActorReference actor,
        WorkflowOrganizationReference organization,
        WorkflowCommentText comment,
        WorkflowCorrelationId correlationId) implements Command {

    public CommentWorkflowTaskCommand {
        instanceId = Objects.requireNonNull(instanceId, "CommentWorkflowTaskCommand instanceId must not be null.");
        actor = Objects.requireNonNull(actor, "CommentWorkflowTaskCommand actor must not be null.");
        comment = Objects.requireNonNull(comment, "CommentWorkflowTaskCommand comment must not be null.");
    }
}
