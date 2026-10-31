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
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.util.NoSuchElementException;
import org.springframework.security.access.AccessDeniedException;
import java.util.Objects;
import org.springframework.stereotype.Service;
@Service
public final class DocumentsApprovalReferenceQueryService implements DocumentsApprovalReferenceContract {
    private final WorkflowQueryUseCase queries;
    public DocumentsApprovalReferenceQueryService(WorkflowQueryUseCase queries){this.queries=Objects.requireNonNull(queries);}
    public boolean exists(String id){
        if(id==null||id.isBlank())return false;
        try{return queries.instance(id.trim())!=null;}
        catch(NoSuchElementException | AccessDeniedException unavailable){return false;}
    }
}
