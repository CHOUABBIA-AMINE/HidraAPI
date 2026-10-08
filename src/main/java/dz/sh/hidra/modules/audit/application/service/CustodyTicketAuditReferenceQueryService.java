/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTicketAuditReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.service
 *
 * @Description : Validates owner-controlled CustodyTransferTicket references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.audit.application.service;

import dz.sh.hidra.modules.audit.application.contract.custody.CustodyTicketAuditReferenceContract;
import dz.sh.hidra.modules.audit.application.port.out.AuditEventRepositoryPort;
import java.util.Objects;
import org.springframework.stereotype.Service;
@Service
public final class CustodyTicketAuditReferenceQueryService implements CustodyTicketAuditReferenceContract {
    private final AuditEventRepositoryPort repository;
    public CustodyTicketAuditReferenceQueryService(AuditEventRepositoryPort repository) {this.repository=Objects.requireNonNull(repository);}
    @Override public boolean matches(String id,String ticketId) {
        if(id==null || id.isBlank() || ticketId==null || ticketId.isBlank()) return false;
        return repository.findById(id.trim()).filter(e -> id.trim().equals(e.id()) && "custody".equals(e.targetModule())
                && "CUSTODY_TRANSFER_TICKET".equals(e.targetType()) && ticketId.trim().equals(e.targetId())).isPresent();
    }
}
