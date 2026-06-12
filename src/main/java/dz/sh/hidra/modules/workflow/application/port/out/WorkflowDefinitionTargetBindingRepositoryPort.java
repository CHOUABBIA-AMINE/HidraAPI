/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionTargetBindingRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Repository port for WorkflowDefinitionTargetBinding.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinitionTargetBinding;

import java.util.Optional;

/**
 * Repository port for WorkflowDefinitionTargetBinding.
 */
public interface WorkflowDefinitionTargetBindingRepositoryPort {

    WorkflowDefinitionTargetBinding save(WorkflowDefinitionTargetBinding model);

    Optional<WorkflowDefinitionTargetBinding> findById(String id);
}
