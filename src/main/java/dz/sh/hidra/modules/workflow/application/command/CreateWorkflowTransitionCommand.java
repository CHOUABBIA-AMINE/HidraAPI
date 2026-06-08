/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateWorkflowTransitionCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.command
 *
 * @Description : Command to create a workflow definition transition.
 *
 */
package dz.sh.hidra.modules.workflow.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;
import java.util.Objects;

/**
 * Command to create a workflow definition transition.
 *
 * <p>Architecture role:
 * Application-layer command contract for the workflow module. It carries validated
 * workflow domain value objects and catalog references only. It must not depend on persistence,
 * REST, Spring, JPA, telemetry implementation classes, topology implementation classes, planning,
 * monitoring, incidents, audit implementation, integration, analytics, reporting, or notification.
 */
public record CreateWorkflowTransitionCommand(
        WorkflowDefinitionId definitionId,
        WorkflowStepId fromStepId,
        WorkflowStepId toStepId,
        WorkflowDecision decision,
        boolean reasonRequired,
        boolean commentRequired) implements Command {

    public CreateWorkflowTransitionCommand {
        definitionId = Objects.requireNonNull(definitionId, "CreateWorkflowTransitionCommand definitionId must not be null.");
        fromStepId = Objects.requireNonNull(fromStepId, "CreateWorkflowTransitionCommand fromStepId must not be null.");
        toStepId = Objects.requireNonNull(toStepId, "CreateWorkflowTransitionCommand toStepId must not be null.");
        decision = Objects.requireNonNull(decision, "CreateWorkflowTransitionCommand decision must not be null.");
    }
}
