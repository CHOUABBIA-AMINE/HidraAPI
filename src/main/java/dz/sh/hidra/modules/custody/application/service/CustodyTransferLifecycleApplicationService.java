/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTransferLifecycleApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
 * @Type        : Class
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.service
 * @Description : Orchestrates authorized, append-only observed custody transfer decisions.
 */
package dz.sh.hidra.modules.custody.application.service;

import dz.sh.hidra.modules.custody.application.port.in.CustodyTransferLifecycleUseCase;
import dz.sh.hidra.modules.custody.application.port.out.CustodyTransferLifecyclePort;
import dz.sh.hidra.modules.custody.application.port.out.CustodyMeasurementCapturePort;
import dz.sh.hidra.modules.custody.domain.model.*;
import dz.sh.hidra.modules.custody.domain.service.*;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyMeasurementScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.custody.CustodyMeasurementAssetOwnershipContract;
import dz.sh.hidra.modules.workflow.application.contract.custody.CustodyTransferTicketApprovalEvidenceContract;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustodyTransferLifecycleApplicationService implements CustodyTransferLifecycleUseCase {
    private static final String PERIOD_CREATE="custody:periods:create";
    private static final String PERIOD_READ="custody:periods:read";
    private static final String RECON_READ="custody:reconciliations:read";
    private static final String RECON_WRITE="custody:reconciliations:reconcile";
    private static final String TICKET_READ="custody:tickets:read";
    private static final String TICKET_FINALIZE="custody:tickets:finalize";
    private static final String TICKET_CORRECT="custody:tickets:correct";
    private static final String DISC_READ="custody:discrepancies:read";
    private static final String DISC_DISPOSE="custody:discrepancies:dispose";

    private final CustodyTransferLifecyclePort persistence;
    private final CustodyMeasurementCapturePort capture;
    private final CustodyMeasurementAssetOwnershipContract ownership;
    private final CustodyMeasurementScopedAuthorizationContract identity;
    private final CustodyTransferTicketApprovalEvidenceContract workflow;
    private final org.springframework.transaction.support.TransactionTemplate pointProbe;
    private final CustodyReconciliationPolicy reconciliationPolicy=new CustodyReconciliationPolicy();
    private final CustodyTicketFinalizationPolicy finalizationPolicy=new CustodyTicketFinalizationPolicy();
    private final CustodyTicketCorrectionPolicy correctionPolicy=new CustodyTicketCorrectionPolicy();

    public CustodyTransferLifecycleApplicationService(CustodyTransferLifecyclePort persistence,
           CustodyMeasurementCapturePort capture,CustodyMeasurementAssetOwnershipContract ownership,
           CustodyMeasurementScopedAuthorizationContract identity,
           CustodyTransferTicketApprovalEvidenceContract workflow,
           org.springframework.transaction.PlatformTransactionManager manager){
        this.persistence=Objects.requireNonNull(persistence);this.capture=Objects.requireNonNull(capture);
        this.ownership=Objects.requireNonNull(ownership);this.identity=Objects.requireNonNull(identity);
        this.workflow=Objects.requireNonNull(workflow);
        pointProbe=new org.springframework.transaction.support.TransactionTemplate(manager);
        pointProbe.setPropagationBehavior(org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }
    private static NoSuchElementException unavailable(){return new NoSuchElementException("Protected custody transfer unavailable.");}
    private static IllegalStateException conflict(String reason){return new IllegalStateException(reason);}
    private static String required(String value){
        if(value==null||value.isBlank()||!value.equals(value.strip())||value.length()>80)
            throw new IllegalArgumentException("Exact Custody identifier required.");
        return value;
    }
    private static UUID command(UUID id){return Objects.requireNonNull(id,"Client command ID required.");}
    // These REST reads acquire the live Topology FOR SHARE ownership fence.
    // The database transaction must therefore be lock-capable (not READ ONLY);
    // no mutation is performed on successful read.
    private CustodyTransferLifecyclePort.Scope visible(Optional<CustodyTransferLifecyclePort.Scope> optional,String operation){
        var s=optional.orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        String company=ownership.activeOwner(s.assetType(),s.assetId())
                .orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        if(s.storedCompanyId()!=null&&!s.storedCompanyId().equals(company))throw unavailable();
        if(s.admissionCompanyId()!=null&&(!s.admissionCompanyId().equals(company)
                ||!"FACILITY".equals(s.assetType())||!s.admissionFacilityId().equals(s.assetId())))throw unavailable();
        identity.require(operation,company,s.transferPointId());
        return s;
    }
    private static Instant now(){return Instant.now().truncatedTo(ChronoUnit.MICROS);}
    private static String hash(Object... values) {
        try{
            MessageDigest md=MessageDigest.getInstance("SHA-256");
            for(Object value:values){
                if(value==null){md.update((byte)0);continue;}
                md.update((byte)1);
                byte[] bytes=value.toString().getBytes(StandardCharsets.UTF_8);
                md.update(ByteBuffer.allocate(4).putInt(bytes.length).array());
                md.update(bytes);
            }
            return HexFormat.of().formatHex(md.digest());
        }catch(NoSuchAlgorithmException e){throw new IllegalStateException("SHA-256 required",e);}
    }
    private CustodyTransferLifecyclePort.Stored receipt(String op,UUID commandId,String actor,
                   String fingerprint,String periodId,String resourceId){
        return new CustodyTransferLifecyclePort.Stored(op,commandId,actor,fingerprint,periodId,resourceId);
    }
    private Optional<String> replay(String op,UUID commandId,String actor,String fingerprint,String periodId){
        var stored=persistence.command(op,commandId);
        if(stored.isEmpty())return Optional.empty();
        var s=stored.get();
        if(!actor.equals(s.actorId())||!fingerprint.equals(s.fingerprint())
            ||!periodId.equals(s.periodId()))throw conflict("Command ID already bound to a different payload or scope.");
        return Optional.of(s.resourceId());
    }
    private record Selection(CustodyReconciliationSnapshot snapshot,String meter,String publication,String agreementParty,
                             String digest){}
    private Selection select(CustodyTransferLifecyclePort.Scope s,String company,BigDecimal requested,String actor){
        if(!"OPEN".equals(s.status())||!"FACILITY".equals(s.assetType()))
            throw new IllegalArgumentException("Only OPEN facility-scoped custody periods are supported.");
        var receipts=persistence.receipts(s.periodId());
        if(receipts.isEmpty())throw new IllegalArgumentException("No qualified observed receipts to reconcile.");
        List<CustodyReconciliationPolicy.Receipt> eligible=new ArrayList<>();
        for(var r:receipts){
            if(!s.periodId().equals(r.periodId())||!s.transferPointId().equals(r.transferPointId())
                ||!company.equals(r.companyId())||!"OBSERVED".equals(r.quantityBasis())
                ||r.intervalStart().isBefore(s.periodStart())||r.intervalEnd().isAfter(s.periodEnd()))
                throw conflict("Receipt scope or period evidence is ineligible.");
            var context=capture.lockContext(s.periodId(),r.meterRunId(),r.agreementPartyId())
                .orElseThrow(()->conflict("Qualified receipt context is no longer eligible."));
            if(!s.agreementId().equals(context.agreementId())||!company.equals(r.companyId())
                ||!s.assetId().equals(context.facilityId())||!s.transferPointId().equals(context.transferPointId())
                ||!s.assetType().equals(context.companyAssetType())||!r.meterRunId().equals(context.meterRunId())

                ||!capture.measurementTypeActive(r.measurementTypeId())
                ||!capture.currentProduct(context,company,contextPublication(r))
                ||outside(r.intervalStart(),r.intervalEnd(),context.agreementStart(),context.agreementEnd())
                ||outside(r.intervalStart(),r.intervalEnd(),context.transferStart(),context.transferEnd())
                ||outside(r.intervalStart(),r.intervalEnd(),context.meterStart(),context.meterEnd())
                ||outside(r.intervalStart(),r.intervalEnd(),context.partyStart(),context.partyEnd()))
                throw conflict("Current Custody product/meter/period eligibility changed.");
            eligible.add(new CustodyReconciliationPolicy.Receipt(r.id(),r.periodId(),s.agreementId(),
                r.transferPointId(),r.companyId(),r.meterRunId(),r.productPublicationId(),
                r.unitId(),r.quantityBasis(),r.intervalStart(),r.intervalEnd(),r.quantity()));
        }
        // The first pass establishes the observed baseline without author-supplied volume.
        var observed=reconciliationPolicy.reconcile(eligible,BigDecimal.ZERO,actor,now());
        BigDecimal total=requested==null?observed.observedTotal():requested;
        var snapshot=reconciliationPolicy.reconcile(eligible,total,actor,now());
        var first=receipts.getFirst();
        for(var r:receipts) {
            if(!first.agreementPartyId().equals(r.agreementPartyId())
                ||!first.measurementTypeId().equals(r.measurementTypeId())
                ||!first.unitId().equals(r.unitId())
                ||!first.meterRunId().equals(r.meterRunId())
                ||!first.productPublicationId().equals(r.productPublicationId()))
                throw conflict("Mixed agreement party, type, unit, meter or product receipts.");
        }
        List<Object> material=new ArrayList<>(List.of("T21-OBSERVED-1",s.periodId(),s.agreementId(),s.transferPointId(),
            company,first.meterRunId(),first.productPublicationId(),first.agreementPartyId(),snapshot.unitId(),
            snapshot.observedTotal().toPlainString(),snapshot.ticketTotal().toPlainString()));
        for(var r:receipts.stream().sorted(Comparator.comparing(CustodyTransferLifecyclePort.Receipt::intervalStart)
            .thenComparing(CustodyTransferLifecyclePort.Receipt::id)).toList()){
            material.add(r.id());material.add(r.intervalEvidenceId());
            material.add(r.intervalStart());material.add(r.intervalEnd());material.add(r.quantity().toPlainString());
        }
        return new Selection(snapshot,first.meterRunId(),first.productPublicationId(),first.agreementPartyId(),
            hash(material.toArray()));
    }
    private static boolean outside(Instant start,Instant end,Instant from,Instant to){
        return from!=null && start.isBefore(from)||to!=null&&end.isAfter(to);
    }
    private static UUID contextPublication(CustodyTransferLifecyclePort.Receipt r) {
        try{return UUID.fromString(r.productPublicationId());}
        catch(IllegalArgumentException e){throw new IllegalArgumentException("Invalid publication reference.",e);}
    }
    private static String newId(){return UUID.randomUUID().toString();}
    private Reconciliation makeReconciliation(Selection selection,String id,String ticketId,String parentTicketId){
        return new Reconciliation(id,selection.digest(),ticketId,parentTicketId,
            selection.meter(),selection.publication(),selection.agreementParty(),selection.snapshot());
    }
    private Ticket makeTicket(Reconciliation reconciliation,String ticketId,String parentTicketId,long version,
                       String issuer,String calculationId){
        var s=reconciliation.snapshot();
        return new Ticket(ticketId,reconciliation.id(),s.periodId(),s.agreementId(),
            s.companyId(),s.transferPointId(),calculationId,
            hash("T21-TICKET-APPROVAL-1",ticketId,reconciliation.digest()),issuer,parentTicketId,
            version,s.ticketTotal(),s.unitId(),now(),null,null,null,false);
    }
    private Discrepancy makeDiscrepancy(Reconciliation reconciliation){
        var s=reconciliation.snapshot();
        if(s.difference().signum()==0)return null;
        return new Discrepancy(newId(),reconciliation.id(),reconciliation.ticketId(),
            s.difference(),s.unitId(),null,null,null,null,now(),null);
    }
    @Override @Transactional
    public Period period(String id){
        var s=visible(persistence.scopeForPeriod(required(id)),PERIOD_READ);
        return new Period(s.periodId(),s.agreementId(),s.transferPointId(),
            ownership.activeOwner(s.assetType(),s.assetId()).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable),
            s.status(),s.periodStart(),s.periodEnd());
    }
    @Override @Transactional
    public List<Reconciliation> reconciliations(String periodId){
        var scope=visible(persistence.lockReadScope(required(periodId)),RECON_READ);
        String company=owner(scope);
        var rows=persistence.reconciliations(periodId);
        for(var row:rows){
            var snapshot=row.snapshot();
            if(!periodId.equals(snapshot.periodId())||!scope.agreementId().equals(snapshot.agreementId())
                    ||!company.equals(snapshot.companyId())||!scope.transferPointId().equals(snapshot.transferPointId()))
                throw unavailable();
        }
        return rows;
    }
    @Override @Transactional
    public Reconciliation reconcile(String periodId,ReconcileCommand command){
        Objects.requireNonNull(command);UUID key=command(command.commandId());
        var scope=visible(persistence.scopeForPeriod(required(periodId)),RECON_WRITE);
        persistence.lockPeriod(periodId);
        String actor=identity.actor(),fingerprint=hash("RECONCILE",actor,periodId,key,command.ticketQuantity());
        var existing=replay("RECONCILE",key,actor,fingerprint,periodId);
        if(existing.isPresent())return persistence.reconciliation(existing.get()).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        if(persistence.reconciliations(periodId).stream().anyMatch(r->r.parentTicketId()==null))
            throw conflict("An initial immutable reconciliation already exists.");
        String company=ownership.activeOwner(scope.assetType(),scope.assetId()).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        Selection selected=select(scope,company,command.ticketQuantity(),actor);
        String id=newId(),ticketId=newId();
        var reconciliation=makeReconciliation(selected,id,ticketId,null);
        var ticket=makeTicket(reconciliation,ticketId,null,1,actor,newId());
        persistence.insertReconciliation(reconciliation,ticket,makeDiscrepancy(reconciliation),
            receipt("RECONCILE",key,actor,fingerprint,periodId,id));
        return reconciliation;
    }
    @Override @Transactional
    public Reconciliation reconciliation(String id){
        visible(persistence.scopeForReconciliation(required(id)),RECON_READ);
        return persistence.reconciliation(id).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
    }
    @Override @Transactional
    public Ticket ticket(String id){
        visible(persistence.scopeForTicket(required(id)),TICKET_READ);
        return persistence.ticket(id).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
    }
    @Override @Transactional
    public List<Ticket> history(String id){
        var original=visible(persistence.scopeForTicket(required(id)),TICKET_READ);
        var scope=visible(persistence.lockReadScope(original.periodId()),TICKET_READ);
        String company=owner(scope);
        if(!original.transferPointId().equals(scope.transferPointId())||!original.agreementId().equals(scope.agreementId())
                ||!company.equals(original.storedCompanyId()))throw unavailable();
        var rows=persistence.history(id);
        if(rows.stream().noneMatch(t->id.equals(t.id())))throw unavailable();
        for(var row:rows)
            if(!scope.periodId().equals(row.periodId())||!scope.agreementId().equals(row.agreementId())
                    ||!company.equals(row.companyId())||!scope.transferPointId().equals(row.transferPointId()))
                throw unavailable();
        return rows;
    }
    private void sameFrozenSelection(Reconciliation prior,CustodyTransferLifecyclePort.Scope scope,String company){
        var current=select(scope,company,prior.snapshot().ticketTotal(),identity.actor());
        if(!prior.snapshot().receiptIds().equals(current.snapshot().receiptIds())
            ||prior.snapshot().observedTotal().compareTo(current.snapshot().observedTotal())!=0
            ||!prior.meterRunId().equals(current.meter())
            ||!prior.productPublicationId().equals(current.publication())
            ||!prior.agreementPartyId().equals(current.agreementParty())
            ||!prior.digest().equals(current.digest()))
            throw conflict("Frozen receipt eligibility or selection changed; explicit new version required.");
    }
    @Override @Transactional
    public Ticket finalizeTicket(String id,FinalizeCommand cmd){
        Objects.requireNonNull(cmd);UUID key=command(cmd.commandId());
        var scope=visible(persistence.scopeForTicket(required(id)),TICKET_FINALIZE);
        persistence.lockPeriod(scope.periodId());
        String actor=identity.actor();
        String fingerprint=hash("FINALIZE",actor,id,key,cmd.expectedVersion(),
            cmd.workflowInstanceId(),cmd.workflowTaskId(),cmd.workflowActionId());
        var replay=replay("FINALIZE",key,actor,fingerprint,scope.periodId());
        if(replay.isPresent())return persistence.ticket(replay.get()).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        Ticket ticket=persistence.ticket(id).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        if(ticket.version()!=cmd.expectedVersion()||ticket.finalizedAt()!=null
           ||ticket.successorId()!=null)throw conflict("Ticket was already finalized or version changed.");
        Reconciliation rec=persistence.reconciliation(ticket.reconciliationId())
            .orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        String company=ownership.activeOwner(scope.assetType(),scope.assetId()).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        sameFrozenSelection(rec,scope,company);
        var issues=persistence.discrepancies(rec.id());
        boolean unresolved=issues.stream().anyMatch(d->d.disposition()==null||!"ACCEPTED".equals(d.disposition()));
        boolean disposed=issues.isEmpty()||issues.stream().allMatch(d->"ACCEPTED".equals(d.disposition()));
        Instant evaluatedAt=now();
        var approval=workflow.resolve(new CustodyTransferTicketApprovalEvidenceContract.Request(ticket.id(),
            ticket.approvalTargetDigest(),rec.digest(),ticket.issuedBy(),rec.snapshot().actorId(),
            cmd.workflowInstanceId(),cmd.workflowTaskId(),cmd.workflowActionId(),evaluatedAt))
            .orElseThrow(()->conflict("No bound independent completed Workflow approval evidence."));
        var domainApproval=new CustodyTicketFinalizationPolicy.ApprovalEvidence(
            id,rec.digest(),approval.instanceId(),approval.taskId(),approval.actionId(),
            approval.actorId(),approval.actedAt(),true);
        finalizationPolicy.requireFinalizable(id,rec.digest(),rec.snapshot(),ticket.issuedBy(),
            rec.snapshot().actorId(),unresolved,disposed,domainApproval,evaluatedAt);
        persistence.insertFinalization(id,new CustodyTransferLifecyclePort.Decision(approval.instanceId(),
            approval.taskId(),approval.actionId(),approval.actorId(),approval.actedAt()),evaluatedAt,
            receipt("FINALIZE",key,actor,fingerprint,scope.periodId(),id));
        return persistence.ticket(id).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
    }
    @Override @Transactional
    public Ticket correctTicket(String id,CorrectCommand cmd){
        Objects.requireNonNull(cmd);UUID key=command(cmd.commandId());
        var scope=visible(persistence.scopeForTicket(required(id)),TICKET_CORRECT);
        persistence.lockPeriod(scope.periodId());
        String actor=identity.actor();
        required(cmd.reason());required(cmd.evidenceReference());
        if(cmd.revisedQuantity()==null)throw new IllegalArgumentException("Corrected ticket quantity required");
        String fingerprint=hash("CORRECT",actor,id,key,cmd.expectedVersion(),cmd.revisedQuantity(),
            cmd.reason(),cmd.evidenceReference());
        var replay=replay("CORRECT",key,actor,fingerprint,scope.periodId());
        if(replay.isPresent())return persistence.ticket(replay.get()).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        Ticket original=persistence.ticket(id).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        String successorId=newId(),reconciliationId=newId();
        var correction=new CustodyTicketCorrection(id,successorId,key,cmd.reason(),cmd.evidenceReference(),
            actor,cmd.expectedVersion(),now());
        correctionPolicy.requireCorrectable(correction,id,original.version(),original.finalizedAt()!=null,
            original.successorId()!=null,persistence.history(id).stream().map(Ticket::id).collect(java.util.stream.Collectors.toSet()));
        Reconciliation prior=persistence.reconciliation(original.reconciliationId())
            .orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        String company=ownership.activeOwner(scope.assetType(),scope.assetId()).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        sameFrozenSelection(prior,scope,company);
        Selection selected=select(scope,company,cmd.revisedQuantity(),actor);
        if(!selected.snapshot().receiptIds().equals(prior.snapshot().receiptIds())
           ||selected.snapshot().observedTotal().compareTo(prior.snapshot().observedTotal())!=0)
            throw conflict("Correction cannot replace immutable measured evidence.");
        var rec=makeReconciliation(selected,reconciliationId,successorId,id);
        var ticket=makeTicket(rec,successorId,id,original.version()+1,actor,newId());
        persistence.insertCorrection(id,cmd.reason(),cmd.evidenceReference(),rec,ticket,
            makeDiscrepancy(rec),receipt("CORRECT",key,actor,fingerprint,scope.periodId(),successorId));
        return ticket;
    }
    @Override @Transactional
    public List<Discrepancy> discrepancies(String reconciliationId){
        visible(persistence.scopeForReconciliation(required(reconciliationId)),DISC_READ);
        return persistence.discrepancies(reconciliationId);
    }
    @Override @Transactional
    public Discrepancy discrepancy(String id){
        visible(persistence.scopeForDiscrepancy(required(id)),DISC_READ);
        return persistence.discrepancy(id).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
    }
    @Override @Transactional
    public Discrepancy dispose(String id,DispositionCommand cmd){
        Objects.requireNonNull(cmd);UUID key=command(cmd.commandId());
        var scope=visible(persistence.scopeForDiscrepancy(required(id)),DISC_DISPOSE);
        persistence.lockPeriod(scope.periodId());
        String actor=identity.actor();
        if(!"ACCEPTED".equals(cmd.disposition())&&!"REJECTED".equals(cmd.disposition()))
            throw new IllegalArgumentException("Explicit ACCEPTED or REJECTED disposition required");
        required(cmd.reason());required(cmd.evidenceReference());
        String fingerprint=hash("DISPOSE",actor,id,key,cmd.disposition(),cmd.reason(),cmd.evidenceReference());
        var replay=replay("DISPOSE",key,actor,fingerprint,scope.periodId());
        if(replay.isPresent())return persistence.discrepancy(replay.get()).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        Discrepancy issue=persistence.discrepancy(id).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        Ticket ticket=persistence.ticket(issue.ticketId()).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        if(issue.disposition()!=null||ticket.finalizedAt()!=null)
            throw conflict("Discrepancy disposition is immutable or ticket is finalized.");
        persistence.insertDisposition(id,cmd.disposition(),cmd.reason(),cmd.evidenceReference(),actor,now(),
            receipt("DISPOSE",key,actor,fingerprint,scope.periodId(),id));
        return persistence.discrepancy(id).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
    }

    private String owner(CustodyTransferLifecyclePort.Scope scope){
        return ownership.activeOwner(scope.assetType(),scope.assetId()).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
    }
    // A denied MANDATORY check marks its transaction rollback-only. Isolate the
    // candidate probe, then retain the grant locks by reauthorizing every admitted point
    // in the enclosing transaction before it can contribute to the page or count.
    private boolean admittedPoint(String company,String point){
        try{
            pointProbe.executeWithoutResult(status->identity.require(PERIOD_READ,company,point));
        }catch(NoSuchElementException denial){
            if(!"Protected custody measurement unavailable.".equals(denial.getMessage()))throw denial;
            return false;
        }
        identity.require(PERIOD_READ,company,point);
        return true;
    }
    @Override @Transactional
    public DiscoveryPage<TransferPoint> discoverTransferPoints(String companyId,String facilityId,int page,int size){
        required(companyId);required(facilityId);
        if(!companyId.equals(ownership.activeOwner("FACILITY",facilityId).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable)))
            throw unavailable();
        var items=new ArrayList<TransferPoint>();
        long[] total={0};
        persistence.visitFacilityPoints(facilityId,point->{
            if(!"FACILITY".equals(point.assetType())||!facilityId.equals(point.assetId()))throw unavailable();
            if(!admittedPoint(companyId,point.id()))return;
            long offset=CustodyTransferLifecycleUseCase.offset(page,size);
            long index=total[0]++;
            if(index>=offset&&items.size()<size)
                items.add(new TransferPoint(point.id(),point.code(),companyId,facilityId));
        });
        if(total[0]==0)throw unavailable();
        return new DiscoveryPage<>(items,page,size,total[0]);
    }
    @Override @Transactional
    public DiscoveryPage<DiscoveredPeriod> discoverPeriods(String companyId,String transferPointId,String periodCode,int page,int size){
        required(companyId);required(transferPointId);
        var point=persistence.lockPoint(transferPointId).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        if(!companyId.equals(ownership.activeOwner(point.assetType(),point.assetId()).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable)))
            throw unavailable();
        identity.require(PERIOD_READ,companyId,point.id());
        if(periodCode!=null)required(periodCode);
        return persistence.periods(point,companyId,periodCode,page,size,CustodyTransferLifecycleUseCase.offset(page,size));
    }
    @Override @Transactional
    public DiscoveryPage<Ticket> listTickets(String periodId,int page,int size){
        var scope=visible(persistence.lockReadScope(required(periodId)),TICKET_READ);
        return persistence.tickets(scope,owner(scope),page,size,CustodyTransferLifecycleUseCase.offset(page,size));
    }

    private CustodyTransferLifecyclePort.AdmissionPoint admissionPoint(String companyId,String pointId,boolean exclusive){
        required(companyId);required(pointId);
        var point=persistence.admissionPoint(pointId,exclusive).orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        if(!"FACILITY".equals(point.assetType())||!companyId.equals(ownership.activeOwner(point.assetType(),point.assetId())
                .orElseThrow(CustodyTransferLifecycleApplicationService::unavailable)))throw unavailable();
        identity.require(PERIOD_CREATE,companyId,pointId);
        return point;
    }
    @Override @Transactional
    public DiscoveryPage<AdmissionAgreement> discoverAdmissionAgreements(String companyId,String transferPointId,
            String agreementNumber,int page,int size){
        admissionPoint(companyId,transferPointId,false);
        if(agreementNumber!=null)required(agreementNumber);
        return persistence.admissionAgreements(transferPointId,agreementNumber,page,size,
            CustodyTransferLifecycleUseCase.offset(page,size));
    }
    @Override @Transactional
    public AdmissionReceipt openPeriod(String companyId,String transferPointId,OpenPeriodCommand command){
        var point=admissionPoint(companyId,transferPointId,true);
        if(command==null||command.commandId()==null)throw new IllegalArgumentException("Exact admission command required.");
        required(command.agreementId());required(command.periodCode());
        dz.sh.hidra.modules.custody.domain.model.CustodyPeriodAdmission.interval(command.periodStart(),command.periodEnd());
        String actor=required(identity.actor());
        String fingerprint=hash("T64-ADMISSION-1",companyId,point.assetId(),transferPointId,actor,
            command.commandId(),command.agreementId(),command.periodCode(),command.periodStart(),command.periodEnd());
        var prior=persistence.admissionByCommand(companyId,transferPointId,command.commandId());
        if(prior.isPresent()){
            var a=prior.get();
            if(!point.assetId().equals(a.facilityId()))throw unavailable();
            if(!actor.equals(a.actorId())||!fingerprint.equals(a.fingerprint()))
                throw conflict("Admission command ID already bound to different evidence.");
            return admissionReceipt(a,true);
        }
        var agreement=persistence.lockAdmissionAgreement(transferPointId,command.agreementId())
            .orElseThrow(CustodyTransferLifecycleApplicationService::unavailable);
        var a=new dz.sh.hidra.modules.custody.domain.model.CustodyPeriodAdmission(UUID.randomUUID().toString(),
            companyId,point.assetId(),transferPointId,agreement.id(),command.periodCode(),command.periodStart(),
            command.periodEnd(),command.commandId(),actor,now(),fingerprint);
        a.requireWindow(point.effectiveFrom(),point.effectiveTo());
        a.requireWindow(agreement.validFrom(),agreement.validTo());
        persistence.insertAdmission(a);
        return admissionReceipt(a,false);
    }
    private static AdmissionReceipt admissionReceipt(dz.sh.hidra.modules.custody.domain.model.CustodyPeriodAdmission a,boolean replay){
        return new AdmissionReceipt(a.periodId(),a.companyId(),a.facilityId(),a.transferPointId(),a.agreementId(),
            a.periodCode(),a.periodStart(),a.periodEnd(),"OPEN",a.commandId(),a.actorId(),a.admittedAt(),replay);
    }
}
