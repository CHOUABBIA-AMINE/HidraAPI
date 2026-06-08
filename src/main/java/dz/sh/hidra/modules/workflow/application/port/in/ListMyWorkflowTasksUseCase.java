/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListMyWorkflowTasksUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.in
 *
 * @Description : Inbound port for listing workflow tasks visible to an actor.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.in;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTaskDto;
import dz.sh.hidra.modules.workflow.application.query.ListMyWorkflowTasksQuery;

/**
 * Inbound port for listing workflow tasks visible to an actor.
 *
 * <p>Architecture role:
 * Inbound application port for the workflow module. It is implemented by application services and called by adapters such as REST controllers, schedulers, or future integration entry points.
 */
public interface ListMyWorkflowTasksUseCase {

    /**
     * Executes the use case.
     *
     * @param query use case input
     * @return use case result
     */
    PageResult<WorkflowTaskDto> listMyWorkflowTasks(ListMyWorkflowTasksQuery query);
}
