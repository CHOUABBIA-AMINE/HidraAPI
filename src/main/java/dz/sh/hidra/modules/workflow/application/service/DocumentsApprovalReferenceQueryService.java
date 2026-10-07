/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsApprovalReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;
import dz.sh.hidra.modules.workflow.application.contract.documents.DocumentsApprovalReferenceContract;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import java.util.Objects;
import org.springframework.stereotype.Service;
@Service
public final class DocumentsApprovalReferenceQueryService implements DocumentsApprovalReferenceContract {
    private final WorkflowInstanceRepositoryPort instances;
    public DocumentsApprovalReferenceQueryService(WorkflowInstanceRepositoryPort instances){this.instances=Objects.requireNonNull(instances);}
    public boolean exists(String id){return id!=null && !id.isBlank() && instances.findById(id.trim()).isPresent();}
}
