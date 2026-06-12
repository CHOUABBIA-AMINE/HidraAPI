/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateWorkflowTaskUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.in
 *
 * @Description : Use case for creating workflow tasks.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.in;

import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTaskSummaryDto;

/**
 * Use case for creating workflow tasks.
 */
public interface CreateWorkflowTaskUseCase {

    WorkflowTaskSummaryDto createWorkflowTask(CreateWorkflowTaskCommand command);
}
