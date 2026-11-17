/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditDocumentReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 *
 * @Description : Enforces Audit evidence integrity and explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.documents.application.service;
import dz.sh.hidra.modules.documents.application.contract.audit.AuditDocumentReferenceContract;
import dz.sh.hidra.modules.documents.application.port.out.DocumentRepositoryPort;
import org.springframework.stereotype.Service;
@Service
public class AuditDocumentReferenceQueryService implements AuditDocumentReferenceContract {
    private final DocumentRepositoryPort repository;
    private final ControlledDocumentAccessService controlled;
    public AuditDocumentReferenceQueryService(DocumentRepositoryPort repository,ControlledDocumentAccessService controlled) {
        this.repository=java.util.Objects.requireNonNull(repository);this.controlled=java.util.Objects.requireNonNull(controlled);
    }
    @org.springframework.transaction.annotation.Transactional(readOnly=true)
    public boolean exists(String id) {
        if(id==null || id.isBlank())return false;
        controlled.forDocument(id.trim(),"documents:controlled:read");
        return repository.findById(id.trim()).isPresent();
    }
}
