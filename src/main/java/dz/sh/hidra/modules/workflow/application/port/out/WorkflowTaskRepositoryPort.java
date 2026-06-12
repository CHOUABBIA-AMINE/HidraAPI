/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Repository port for WorkflowTask.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;

import java.util.Optional;

/**
 * Repository port for WorkflowTask.
 */
public interface WorkflowTaskRepositoryPort {

    WorkflowTask save(WorkflowTask model);

    Optional<WorkflowTask> findById(String id);
}
