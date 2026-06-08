/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateWorkflowStepCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.command
 *
 * @Description : Command to create a workflow definition step.
 *
 */
package dz.sh.hidra.modules.workflow.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import java.util.Objects;

/**
 * Command to create a workflow definition step.
 *
 * <p>Architecture role:
 * Application-layer command contract for the workflow module. It carries validated
 * workflow domain value objects and catalog references only. It must not depend on persistence,
 * REST, Spring, JPA, telemetry implementation classes, topology implementation classes, planning,
 * monitoring, incidents, audit implementation, integration, analytics, reporting, or notification.
 */
public record CreateWorkflowStepCommand(
        WorkflowDefinitionId definitionId,
        WorkflowCode stepCode,
        WorkflowLocalizedName name,
        int stepOrder,
        boolean mandatory) implements Command {

    public CreateWorkflowStepCommand {
        definitionId = Objects.requireNonNull(definitionId, "CreateWorkflowStepCommand definitionId must not be null.");
        stepCode = Objects.requireNonNull(stepCode, "CreateWorkflowStepCommand stepCode must not be null.");
        name = Objects.requireNonNull(name, "CreateWorkflowStepCommand name must not be null.");
    }
}
