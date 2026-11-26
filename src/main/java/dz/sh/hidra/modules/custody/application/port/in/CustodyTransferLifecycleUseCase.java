/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTransferLifecycleUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.in
 * @Description : Defines authorized T21 reconciliation, ticket and discrepancy lifecycle operations.
 */
package dz.sh.hidra.modules.custody.application.port.in;
import dz.sh.hidra.modules.custody.domain.model.CustodyReconciliationSnapshot;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
public interface CustodyTransferLifecycleUseCase {
    record Period(String id,String agreementId,String transferPointId,String companyId,
                  String status,Instant periodStart,Instant periodEnd){}
    record Reconciliation(String id,String digest,String ticketId,String parentTicketId,
                  String meterRunId,String productPublicationId,String agreementPartyId,
                  CustodyReconciliationSnapshot snapshot){}
    record Ticket(String id,String reconciliationId,String periodId,String agreementId,
                  String companyId,String transferPointId,String calculationId,String approvalTargetDigest,
                  String issuedBy,String parentTicketId,long version,BigDecimal quantity,String unitId,
                  Instant issuedAt,Instant finalizedAt,String reviewerId,String successorId,boolean successorFinalized) {
        public String status(){return finalizedAt==null?"PENDING_SOFTWARE_FINALIZATION":
                    successorId==null?"SOFTWARE_FINALIZED":successorFinalized?"SUPERSEDED":"SOFTWARE_FINALIZED_CORRECTION_PENDING";}
        public boolean effectiveFinalized(){return finalizedAt!=null&&(successorId==null||!successorFinalized);}
    }
    record Discrepancy(String id,String reconciliationId,String ticketId,BigDecimal difference,
                  String unitId,String disposition,String reason,String evidenceReference,
                  String disposedBy,Instant openedAt,Instant disposedAt){}
    record ReconcileCommand(UUID commandId,BigDecimal ticketQuantity){}
    record FinalizeCommand(UUID commandId,long expectedVersion,String workflowInstanceId,
                  String workflowTaskId,String workflowActionId){}
    record CorrectCommand(UUID commandId,long expectedVersion,BigDecimal revisedQuantity,
                  String reason,String evidenceReference){}
    record DispositionCommand(UUID commandId,String disposition,String reason,String evidenceReference){}
    Period period(String id);
    List<Reconciliation> reconciliations(String periodId);
    Reconciliation reconcile(String periodId,ReconcileCommand command);
    Reconciliation reconciliation(String id);
    Ticket ticket(String id);
    List<Ticket> history(String id);
    Ticket finalizeTicket(String ticketId,FinalizeCommand command);
    Ticket correctTicket(String ticketId,CorrectCommand command);
    List<Discrepancy> discrepancies(String reconciliationId);
    Discrepancy discrepancy(String id);
    Discrepancy dispose(String id,DispositionCommand command);

    record DiscoveryPage<T>(List<T> items,int page,int size,long total) {
        public DiscoveryPage { items=List.copyOf(items); }
        public long totalPages(){return total/size+(total%size==0?0:1);}
    }
    record TransferPoint(String id,String code,String companyId,String facilityId){}
    record DiscoveredPeriod(String id,String periodCode,String agreementId,String transferPointId,
                            String companyId,String status,Instant periodStart,Instant periodEnd){}
    static long offset(int page,int size){
        if(page<0||size<1||size>200)throw new IllegalArgumentException("Invalid custody page.");
        return Math.multiplyExact((long)page,size);
    }
    DiscoveryPage<TransferPoint> discoverTransferPoints(String companyId,String facilityId,int page,int size);
    DiscoveryPage<DiscoveredPeriod> discoverPeriods(String companyId,String transferPointId,String periodCode,int page,int size);
    DiscoveryPage<Ticket> listTickets(String periodId,int page,int size);

    record AdmissionAgreement(String id,String agreementNumber,String transferPointId,
                              Instant validFrom,Instant validTo,String status){}
    record OpenPeriodCommand(UUID commandId,String agreementId,String periodCode,Instant periodStart,Instant periodEnd){}
    record AdmissionReceipt(String periodId,String companyId,String facilityId,String transferPointId,String agreementId,
            String periodCode,Instant periodStart,Instant periodEnd,String initialStatus,UUID commandId,
            String openedByActorId,Instant admittedAt,boolean replayed){}
    DiscoveryPage<AdmissionAgreement> discoverAdmissionAgreements(String companyId,String transferPointId,
        String agreementNumber,int page,int size);
    AdmissionReceipt openPeriod(String companyId,String transferPointId,OpenPeriodCommand command);
}
