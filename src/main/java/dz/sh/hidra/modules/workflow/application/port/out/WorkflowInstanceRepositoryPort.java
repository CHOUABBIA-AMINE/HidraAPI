/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Outbound port for workflow instance persistence.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;
import java.util.Optional;

/**
 * Outbound port for workflow instance persistence.
 *
 * <p>Architecture role:
 * Outbound application port for workflow instance storage. Infrastructure adapters implement this interface and preserve the workflow aggregate boundary.
 */
public interface WorkflowInstanceRepositoryPort {

    WorkflowInstance save(WorkflowInstance instance);

    Optional<WorkflowInstance> findById(WorkflowInstanceId id);

    Optional<WorkflowInstance> findOpenByTarget(WorkflowTargetReference target);

    PageResult<WorkflowInstance> findAll(
            WorkflowDefinitionId definitionId,
            WorkflowTargetReference target,
            WorkflowInstanceStatus status,
            WorkflowActorReference startedBy,
            PageRequest pageRequest);
}
