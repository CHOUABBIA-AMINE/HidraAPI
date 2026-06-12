/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowSlaPolicyRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Repository port for WorkflowSlaPolicy.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowSlaPolicy;

import java.util.Optional;

/**
 * Repository port for WorkflowSlaPolicy.
 */
public interface WorkflowSlaPolicyRepositoryPort {

    WorkflowSlaPolicy save(WorkflowSlaPolicy model);

    Optional<WorkflowSlaPolicy> findById(String id);
}
