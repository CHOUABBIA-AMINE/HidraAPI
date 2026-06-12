/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAuditOutboxReferenceRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Repository port for WorkflowAuditOutboxReference.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowAuditOutboxReference;

import java.util.Optional;

/**
 * Repository port for WorkflowAuditOutboxReference.
 */
public interface WorkflowAuditOutboxReferenceRepositoryPort {

    WorkflowAuditOutboxReference save(WorkflowAuditOutboxReference model);

    Optional<WorkflowAuditOutboxReference> findById(String id);
}
