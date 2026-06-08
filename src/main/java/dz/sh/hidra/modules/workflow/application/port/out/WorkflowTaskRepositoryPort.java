/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Outbound port for workflow task persistence.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;
import java.util.Optional;

/**
 * Outbound port for workflow task persistence.
 *
 * <p>Architecture role:
 * Outbound application port for workflow task storage and task worklist queries.
 */
public interface WorkflowTaskRepositoryPort {

    WorkflowTask save(WorkflowTask task);

    Optional<WorkflowTask> findById(WorkflowTaskId id);

    PageResult<WorkflowTask> findAll(
            WorkflowInstanceId instanceId,
            WorkflowTaskStatus status,
            WorkflowActorReference assignedActor,
            WorkflowOrganizationReference assignedOrganization,
            PageRequest pageRequest);

    PageResult<WorkflowTask> findVisibleToActor(
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization,
            WorkflowTaskStatus status,
            PageRequest pageRequest);
}
