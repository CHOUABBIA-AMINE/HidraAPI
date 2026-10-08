/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTicketAuditReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.semantic
 *
 * @Description : Validates owner-controlled CustodyTransferTicket references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.audit.semantic;

import dz.sh.hidra.modules.audit.application.service.CustodyTicketAuditReferenceQueryService;
import dz.sh.hidra.modules.audit.application.port.out.AuditEventRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditEvent;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CustodyTicketAuditReferenceContractTest {
    @Test void wrongModuleTypeOrTicketCannotAttestEvidence() {
        var repository=mock(AuditEventRepositoryPort.class);var event=mock(AuditEvent.class);when(repository.findById("audit")).thenReturn(Optional.of(event));
        var service=new CustodyTicketAuditReferenceQueryService(repository);when(event.id()).thenReturn("audit");when(event.targetModule()).thenReturn("custody");
        when(event.targetType()).thenReturn("CUSTODY_TRANSFER_TICKET");when(event.targetId()).thenReturn("ticket");assertTrue(service.matches("audit","ticket"));
        assertFalse(service.matches("audit","other"));when(event.targetType()).thenReturn("OTHER");assertFalse(service.matches("audit","ticket"));
        when(event.targetType()).thenReturn("CUSTODY_TRANSFER_TICKET");when(event.targetModule()).thenReturn("risk");assertFalse(service.matches("audit","ticket"));
    }
    @Test void missingEvidenceIsRejected() {var repository=mock(AuditEventRepositoryPort.class);when(repository.findById("missing")).thenReturn(Optional.empty());assertFalse(new CustodyTicketAuditReferenceQueryService(repository).matches("missing","ticket"));}
}
