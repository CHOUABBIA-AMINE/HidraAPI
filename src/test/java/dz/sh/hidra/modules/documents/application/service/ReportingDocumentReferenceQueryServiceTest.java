/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingDocumentReferenceQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 *
 * @Description : Enforces Reporting execution and output integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.documents.application.service;
import dz.sh.hidra.modules.documents.application.port.out.DocumentRepositoryPort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentStorageObjectRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.Document;
import dz.sh.hidra.modules.documents.domain.model.DocumentStorageObject;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class ReportingDocumentReferenceQueryServiceTest {
    @Test void ownerSeparatesDocumentAndStorageLookupsWithoutLifecycleInference(){
        var documents=mock(DocumentRepositoryPort.class);var storage=mock(DocumentStorageObjectRepositoryPort.class);
        when(documents.findById("document")).thenReturn(Optional.of(mock(Document.class)));when(documents.findById("storage")).thenReturn(Optional.empty());
        when(storage.findById("storage")).thenReturn(Optional.of(mock(DocumentStorageObject.class)));when(storage.findById("document")).thenReturn(Optional.empty());
        var query=new ReportingDocumentReferenceQueryService(documents,storage);
        assertTrue(query.documentExists(" document "));assertTrue(query.storageObjectExists(" storage "));
        assertFalse(query.documentExists("storage"));assertFalse(query.storageObjectExists("document"));
        assertFalse(query.documentExists(null));assertFalse(query.storageObjectExists(" "));
        verify(storage,never()).isActiveStorageProvider(org.mockito.ArgumentMatchers.anyString());
    }
}
