/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetWorkflowInstanceUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.in
 *
 * @Description : Inbound port for retrieving workflow instances.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.in;

import dz.sh.hidra.modules.workflow.application.dto.WorkflowInstanceDto;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowInstanceQuery;

/**
 * Inbound port for retrieving workflow instances.
 *
 * <p>Architecture role:
 * Inbound application port for the workflow module. It is implemented by application services and called by adapters such as REST controllers, schedulers, or future integration entry points.
 */
public interface GetWorkflowInstanceUseCase {

    /**
     * Executes the use case.
     *
     * @param query use case input
     * @return use case result
     */
    WorkflowInstanceDto getWorkflowInstance(GetWorkflowInstanceQuery query);
}
