/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditDocumentReferenceQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 *
 * @Description : Enforces Audit evidence integrity and explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.documents.application.service;
import dz.sh.hidra.modules.documents.application.port.out.DocumentRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.Document;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AuditDocumentReferenceQueryServiceTest {
 @Test void onlyOwnerProvenReferencesResolve() {
  var repo=mock(DocumentRepositoryPort.class);var service=new AuditDocumentReferenceQueryService(repo);
  when(repo.findById("known")).thenReturn(Optional.of(mock(Document.class)));
  assertFalse(service.exists(null));assertFalse(service.exists(" "));assertFalse(service.exists("missing"));assertTrue(service.exists(" known "));
 }
}
