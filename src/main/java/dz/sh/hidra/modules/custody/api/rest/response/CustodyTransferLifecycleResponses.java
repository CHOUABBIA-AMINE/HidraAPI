/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTransferLifecycleResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : REST Adapter
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.response
 * @Description : Explicit immutable reconciliation, ticket and discrepancy REST responses.
 */
package dz.sh.hidra.modules.custody.api.rest.response;
import dz.sh.hidra.modules.custody.application.port.in.CustodyTransferLifecycleUseCase.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
public final class CustodyTransferLifecycleResponses {
    private CustodyTransferLifecycleResponses(){}
    public record ReconciliationView(String id,String ticketId,String parentTicketId,String periodId,
        String agreementId,String transferPointId,String companyId,String meterRunSnapshotId,
        String productPublicationId,String agreementPartyId,String quantityBasis,String unitId,
        List<String> receiptIds,BigDecimal observedTotal,BigDecimal ticketTotal,BigDecimal differenceQuantity,
        String selectionVersion,String digest,String reconciledBy,Instant reconciledAt){
        public static ReconciliationView from(Reconciliation r){
            var s=r.snapshot();
            return new ReconciliationView(r.id(),r.ticketId(),r.parentTicketId(),s.periodId(),
                s.agreementId(),s.transferPointId(),s.companyId(),r.meterRunId(),
                r.productPublicationId(),r.agreementPartyId(),s.quantityBasis(),s.unitId(),
                s.receiptIds(),s.observedTotal(),s.ticketTotal(),s.difference(),
                s.selectionVersion(),r.digest(),s.actorId(),s.reconciledAt());
        }
    }
    public record TicketView(String id,String reconciliationId,String periodId,String agreementId,
        String companyId,String transferPointId,String calculationId,String approvalTargetDigest,
        String parentTicketId,String successorTicketId,long version,String status,BigDecimal quantity,
        String unitId,String quantityBasis,boolean official,boolean acceptedForCustody,
        String issuedBy,Instant issuedAt,String reviewerId,Instant finalizedAt){
        public static TicketView from(Ticket t){
            return new TicketView(t.id(),t.reconciliationId(),t.periodId(),t.agreementId(),
                t.companyId(),t.transferPointId(),t.calculationId(),t.approvalTargetDigest(),
                t.parentTicketId(),t.successorId(),t.version(),t.status(),t.quantity(),t.unitId(),
                "OBSERVED",false,false,t.issuedBy(),t.issuedAt(),t.reviewerId(),t.finalizedAt());
        }
    }
    public record DiscrepancyView(String id,String reconciliationId,String ticketId,
        BigDecimal differenceQuantity,String unitId,String status,String disposition,
        String reason,String evidenceReference,String disposedBy,Instant openedAt,Instant disposedAt){
        public static DiscrepancyView from(Discrepancy d){
            return new DiscrepancyView(d.id(),d.reconciliationId(),d.ticketId(),
                d.difference(),d.unitId(),d.disposition()==null?"OPEN":"DISPOSED",
                d.disposition(),d.reason(),d.evidenceReference(),d.disposedBy(),d.openedAt(),d.disposedAt());
        }
    }
}
