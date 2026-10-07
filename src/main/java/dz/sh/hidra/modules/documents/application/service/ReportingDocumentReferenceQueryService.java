/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingDocumentReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
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
public final class ReportingDocumentReferenceQueryService implements ReportingDocumentReferenceContract {
    private final DocumentRepositoryPort documents;
    private final DocumentStorageObjectRepositoryPort storage;
    public ReportingDocumentReferenceQueryService(DocumentRepositoryPort documents,DocumentStorageObjectRepositoryPort storage){
        this.documents=Objects.requireNonNull(documents);this.storage=Objects.requireNonNull(storage);
    }
    public boolean documentExists(String id){return id!=null && !id.isBlank() && documents.findById(id.trim()).isPresent();}
    public boolean storageObjectExists(String id){return id!=null && !id.isBlank() && storage.findById(id.trim()).isPresent();}
}
