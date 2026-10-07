/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditWorkflowReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Enforces Audit evidence integrity and explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;
import dz.sh.hidra.modules.workflow.application.contract.audit.AuditWorkflowReferenceContract;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import org.springframework.stereotype.Service;
@Service
public final class AuditWorkflowReferenceQueryService implements AuditWorkflowReferenceContract {
    private final WorkflowInstanceRepositoryPort repository;
    public AuditWorkflowReferenceQueryService(WorkflowInstanceRepositoryPort repository) {this.repository=java.util.Objects.requireNonNull(repository);}
    public boolean exists(String id) {return id!=null && !id.isBlank() && repository.findById(id.trim()).isPresent();}
}
