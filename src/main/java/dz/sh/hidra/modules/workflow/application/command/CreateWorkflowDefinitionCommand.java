/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateWorkflowDefinitionCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.command
 *
 * @Description : Command to create a workflow definition.
 *
 */
package dz.sh.hidra.modules.workflow.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;
import java.util.Objects;

/**
 * Command to create a workflow definition.
 *
 * <p>Architecture role:
 * Application-layer command contract for the workflow module. It carries validated
 * workflow domain value objects and catalog references only. It must not depend on persistence,
 * REST, Spring, JPA, telemetry implementation classes, topology implementation classes, planning,
 * monitoring, incidents, audit implementation, integration, analytics, reporting, or notification.
 */
public record CreateWorkflowDefinitionCommand(
        WorkflowCode code,
        WorkflowLocalizedName name,
        WorkflowTypeReference type) implements Command {

    public CreateWorkflowDefinitionCommand {
        code = Objects.requireNonNull(code, "CreateWorkflowDefinitionCommand code must not be null.");
        name = Objects.requireNonNull(name, "CreateWorkflowDefinitionCommand name must not be null.");
        type = Objects.requireNonNull(type, "CreateWorkflowDefinitionCommand type must not be null.");
    }
}
