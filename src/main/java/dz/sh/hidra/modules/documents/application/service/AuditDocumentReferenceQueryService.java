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
public final class AuditDocumentReferenceQueryService implements AuditDocumentReferenceContract {
    private final DocumentRepositoryPort repository;
    public AuditDocumentReferenceQueryService(DocumentRepositoryPort repository) {this.repository=java.util.Objects.requireNonNull(repository);}
    public boolean exists(String id) {return id!=null && !id.isBlank() && repository.findById(id.trim()).isPresent();}
}
