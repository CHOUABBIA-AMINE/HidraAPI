/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StartWorkflowInstanceCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.command
 *
 * @Description : Command to start a workflow instance.
 *
 */
package dz.sh.hidra.modules.workflow.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;
import java.util.Objects;

/**
 * Command to start a workflow instance.
 *
 * <p>Architecture role:
 * Application-layer command contract for the workflow module. It carries validated
 * workflow domain value objects and catalog references only. It must not depend on persistence,
 * REST, Spring, JPA, telemetry implementation classes, topology implementation classes, planning,
 * monitoring, incidents, audit implementation, integration, analytics, reporting, or notification.
 */
public record StartWorkflowInstanceCommand(
        WorkflowDefinitionId definitionId,
        WorkflowTargetReference target,
        WorkflowActorReference startedBy,
        WorkflowCorrelationId correlationId) implements Command {

    public StartWorkflowInstanceCommand {
        definitionId = Objects.requireNonNull(definitionId, "StartWorkflowInstanceCommand definitionId must not be null.");
        target = Objects.requireNonNull(target, "StartWorkflowInstanceCommand target must not be null.");
        startedBy = Objects.requireNonNull(startedBy, "StartWorkflowInstanceCommand startedBy must not be null.");
    }
}
