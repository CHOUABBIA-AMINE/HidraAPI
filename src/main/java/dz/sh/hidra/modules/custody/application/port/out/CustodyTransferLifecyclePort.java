/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTransferLifecyclePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 * @Description : Owns T21 immutable persistence and serialized lifecycle transactions.
 */
package dz.sh.hidra.modules.custody.application.port.out;
import dz.sh.hidra.modules.custody.application.port.in.CustodyTransferLifecycleUseCase.*;
import dz.sh.hidra.modules.custody.domain.model.CustodyPeriodAdmission;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
public interface CustodyTransferLifecyclePort {
    record Scope(String periodId,String agreementId,String transferPointId,String assetType,String assetId,
                 String status,Instant periodStart,Instant periodEnd,String storedCompanyId,String admissionCompanyId,String admissionFacilityId){}
    record Receipt(String id,String periodId,String companyId,String transferPointId,String meterRunId,
                 String productPublicationId,String agreementPartyId,String measurementTypeId,
                 String unitId,String quantityBasis,Instant intervalStart,Instant intervalEnd,
                 BigDecimal quantity,String intervalEvidenceId){}
    record Stored(String operation,UUID commandId,String actorId,String fingerprint,String periodId,String resourceId){}
    record Decision(String instanceId,String taskId,String actionId,String reviewerId,Instant actedAt){}
    Optional<Scope> scopeForPeriod(String periodId);
    Optional<Scope> scopeForTicket(String ticketId);
    Optional<Scope> scopeForReconciliation(String reconciliationId);
    Optional<Scope> scopeForDiscrepancy(String discrepancyId);
    void lockPeriod(String periodId);
    List<Receipt> receipts(String periodId);
    Optional<Reconciliation> reconciliation(String id);
    List<Reconciliation> reconciliations(String periodId);
    Optional<Ticket> ticket(String id);
    List<Ticket> history(String ticketId);
    Optional<Discrepancy> discrepancy(String id);
    List<Discrepancy> discrepancies(String reconciliationId);
    Optional<Stored> command(String operation,UUID commandId);
    void insertReconciliation(Reconciliation snapshot,Ticket ticket,Discrepancy discrepancy,Stored command);
    void insertDisposition(String discrepancyId,String disposition,String reason,String evidenceReference,
                    String actorId,Instant at,Stored command);
    void insertFinalization(String ticketId,Decision approval,Instant at,Stored command);
    void insertCorrection(String originalId,String reason,String evidenceReference,
                    Reconciliation reconciliation,Ticket ticket,Discrepancy discrepancy,Stored command);

    record PointScope(String id,String code,String assetType,String assetId){}
    Optional<PointScope> lockPoint(String transferPointId);
    Optional<Scope> lockReadScope(String periodId);
    void visitFacilityPoints(String facilityId,java.util.function.Consumer<PointScope> visitor);
    DiscoveryPage<DiscoveredPeriod> periods(PointScope point,String companyId,String periodCode,int page,int size,long offset);
    DiscoveryPage<Ticket> tickets(Scope scope,String companyId,int page,int size,long offset);

    record AdmissionPoint(String id,String assetType,String assetId,Instant effectiveFrom,Instant effectiveTo){}
    Optional<AdmissionPoint> admissionPoint(String pointId,boolean exclusive);
    Optional<AdmissionAgreement> lockAdmissionAgreement(String pointId,String agreementId);
    DiscoveryPage<AdmissionAgreement> admissionAgreements(String pointId,String agreementNumber,int page,int size,long offset);
    Optional<CustodyPeriodAdmission> admissionByCommand(String companyId,String pointId,UUID commandId);
    void insertAdmission(CustodyPeriodAdmission admission);
}
