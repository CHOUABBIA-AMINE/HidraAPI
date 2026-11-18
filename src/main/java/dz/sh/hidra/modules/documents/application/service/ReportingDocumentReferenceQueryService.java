/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingDocumentReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 *
 * @Description : Enforces Reporting execution and output integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.documents.application.service;
import dz.sh.hidra.modules.documents.application.contract.reporting.ReportingDocumentReferenceContract;
import dz.sh.hidra.modules.documents.application.port.out.DocumentRepositoryPort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentStorageObjectRepositoryPort;
import java.util.Objects;
import org.springframework.stereotype.Service;
@Service
public class ReportingDocumentReferenceQueryService implements ReportingDocumentReferenceContract {
    private final DocumentRepositoryPort documents;
    private final DocumentStorageObjectRepositoryPort storage;
    private final ControlledDocumentAccessService controlled;
    public ReportingDocumentReferenceQueryService(DocumentRepositoryPort documents,DocumentStorageObjectRepositoryPort storage,
            ControlledDocumentAccessService controlled){
        this.documents=Objects.requireNonNull(documents);this.storage=Objects.requireNonNull(storage);
        this.controlled=Objects.requireNonNull(controlled);
    }
    // Both enrolled reference checks lock current grants before disclosing existence.
    // A read-only PostgreSQL transaction cannot execute their SELECT FOR SHARE.
    @org.springframework.transaction.annotation.Transactional
    public boolean documentExists(String id){
        if(id==null||id.isBlank())return false;
        controlled.forDocument(id.trim(),"documents:controlled:read");
        return documents.findById(id.trim()).isPresent();
    }
    @org.springframework.transaction.annotation.Transactional
    public boolean storageObjectExists(String id){
        if(id==null||id.isBlank())return false;
        controlled.forStorage(id.trim(),"documents:controlled:read");
        return storage.findById(id.trim()).isPresent();
    }
}
