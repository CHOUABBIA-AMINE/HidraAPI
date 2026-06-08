/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DelegateWorkflowTaskCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.command
 *
 * @Description : Command to delegate a workflow task.
 *
 */
package dz.sh.hidra.modules.workflow.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecisionNote;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;
import java.util.Objects;

/**
 * Command to delegate a workflow task.
 *
 * <p>Architecture role:
 * Application-layer command contract for the workflow module. It carries validated
 * workflow domain value objects and catalog references only. It must not depend on persistence,
 * REST, Spring, JPA, telemetry implementation classes, topology implementation classes, planning,
 * monitoring, incidents, audit implementation, integration, analytics, reporting, or notification.
 */
public record DelegateWorkflowTaskCommand(
        WorkflowTaskId taskId,
        WorkflowActorReference fromActor,
        WorkflowActorReference toActor,
        WorkflowOrganizationReference toOrganization,
        WorkflowReasonReference reason,
        WorkflowDecisionNote note,
        WorkflowCorrelationId correlationId) implements Command {

    public DelegateWorkflowTaskCommand {
        taskId = Objects.requireNonNull(taskId, "DelegateWorkflowTaskCommand taskId must not be null.");
        fromActor = Objects.requireNonNull(fromActor, "DelegateWorkflowTaskCommand fromActor must not be null.");
        reason = Objects.requireNonNull(reason, "DelegateWorkflowTaskCommand reason must not be null.");
    }
}
