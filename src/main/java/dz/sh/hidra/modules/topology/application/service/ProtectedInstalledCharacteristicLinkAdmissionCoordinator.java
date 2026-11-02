/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedInstalledCharacteristicLinkAdmissionCoordinator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Original native Identity, Simulation/Party and Topology evidence in one protected transaction.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.InstalledCharacteristicLinkRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.InstalledCharacteristicLinkStoragePort;
import dz.sh.hidra.modules.topology.application.port.out.InstalledCharacteristicLinkStoragePort.Snapshot;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLinkApproval;
import dz.sh.hidra.modules.topology.domain.service.InstalledCharacteristicLinkApprovalTargetDigester;
import dz.sh.hidra.modules.topology.application.port.out.InstalledCharacteristicLinkApprovalEvidencePort;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledCharacteristicLinkAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledCharacteristicLinkAuthorizationContract.Origin;
import dz.sh.hidra.modules.simulation.application.contract.topology.TopologyManufacturerCharacteristicReferenceContract;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** Inward ports only; no direct Identity/Party/Simulation adapters or detached manufacturer service. */
@Component
public final class ProtectedInstalledCharacteristicLinkAdmissionCoordinator
        implements InstalledCharacteristicLinkRepositoryPort {
    private final TopologyInstalledCharacteristicLinkAuthorizationContract identity;
    private final TopologyManufacturerCharacteristicReferenceContract sources;
    private final InstalledCharacteristicLinkStoragePort storage;
    private final ProtectedTransactionExecutor executor;
    private final InstalledCharacteristicLinkApprovalEvidencePort workflow;
    private static final String DML_OWNER="HAPI-602";
    private static final Set<String> APPROVAL_DML_HASHES=Set.of(
        "f5db1affbfbf7ffef00c60d32a23ddc5907cc4e8da94d74eeff6fe74de20d059",
        "7e79f7f7fd8bd255834fd7d34054a94e3c04606cec4026448bdbb3cd1c1581a6");
    private static final Set<String> DML_HASHES=Set.of(
        "b510776b865bd270b8d8ca3739fd450dca8e2bc9fe11b26867329d1cf5d6bdeb",
        "c5811865b77b67d7bc20211a09264a36e36165001fbd80bae8a80abfc83d1bdd",
        "e6db88ec87ac68d0f27e41024e480412242e9dd4cd59b47c30ff3e3c070fe482",
        "04a48931f5568b11debf736be7095735432f2b452054896f341e9b53affa1f00");
    public ProtectedInstalledCharacteristicLinkAdmissionCoordinator(
            TopologyInstalledCharacteristicLinkAuthorizationContract identity,
            TopologyManufacturerCharacteristicReferenceContract sources,
            InstalledCharacteristicLinkStoragePort storage,ProtectedTransactionExecutor executor) {
        this(identity,sources,storage,executor,null); // legacy DRAFT-only test constructor
    }
    @Autowired
    public ProtectedInstalledCharacteristicLinkAdmissionCoordinator(
            TopologyInstalledCharacteristicLinkAuthorizationContract identity,
            TopologyManufacturerCharacteristicReferenceContract sources,
            InstalledCharacteristicLinkStoragePort storage,ProtectedTransactionExecutor executor,
            @org.springframework.context.annotation.Lazy InstalledCharacteristicLinkApprovalEvidencePort workflow){
        this.identity=Objects.requireNonNull(identity);this.sources=Objects.requireNonNull(sources);
        this.storage=Objects.requireNonNull(storage);this.executor=Objects.requireNonNull(executor);
        this.workflow=workflow;
    }
    private static AccessDeniedException denied(){return new AccessDeniedException("Installed characteristic link denied");}
    @Override public Prepared prepare(Command c) {
        executor.requireNoProtectedContext();
        if(c==null||TransactionSynchronizationManager.isActualTransactionActive()
                ||TransactionSynchronizationManager.isSynchronizationActive())throw denied();
        return new PreparedTicket(this,c,identity.prepare());
    }
    private static TopologyInstalledCharacteristicLinkAuthorizationContract.Request request(Command c) {
        return new TopologyInstalledCharacteristicLinkAuthorizationContract.Request(
            TopologyInstalledCharacteristicLinkAuthorizationContract.Operation.valueOf(c.operation().name()),
            c.companyPartyId(),c.installedUnitId(),c.linkId());
    }
    @Override public Admission acquire(Prepared prepared,Command c) {
        Ticket ticket=null;
        try {
            executor.beginAdmission();
            if(!(prepared instanceof PreparedTicket p)||p.owner!=this
                    ||p.thread!=Thread.currentThread()
                    ||!p.used.compareAndSet(false,true)||!p.command.equals(c))throw denied();
            var locked=storage.acquire(c);
            var nativeTicket=identity.acquire(p.nativePrepared,request(c));
            Origin origin=identity.contribute(nativeTicket);
            var snap=storage.observe(locked,c);
            var approvalObservation=isApproval(c.operation())?storage.observeApproval(locked,c):null;
            var verified=new ArrayList<TopologyManufacturerCharacteristicReferenceContract.Admission>();
            var all=new ArrayList<>(snap.sourceRows());
            if(c.operation()==Operation.SAVE_DRAFT)all.add(c.draft());
            for(var row:all){
                if(isApproval(c.operation()))storage.requireInstallation(locked,row);
                var source=new TopologyInstalledCharacteristicLinkAuthorizationContract.Source(
                    row.companyPartyId(),row.sourceId(),row.sourceRevisionId());
                identity.requireSourceRead(nativeTicket,source);
                var request=new TopologyManufacturerCharacteristicReferenceContract.Requested(
                    row.companyPartyId(),row.sourceId(),row.sourceRevisionId(),row.sourceSha256(),
                    row.kind().name(),row.effectiveFrom(),row.effectiveUntil());
                var original=new TopologyManufacturerCharacteristicReferenceContract.Original(
                    row.companyPartyId(),origin.bindingId(),origin.userId(),
                    origin.backendPid(),origin.transactionId(),origin.databaseRole(),
                    origin.observedAt(),origin.validUntil());
                var evidence=sources.acquire(request,original);
                var proof=sources.verify(evidence);
                if(!proof.companyPartyId().equals(row.companyPartyId())
                        ||!proof.sourceId().equals(row.sourceId())
                        ||!proof.revisionId().equals(row.sourceRevisionId())
                        ||!proof.payloadSha256().equals(row.sourceSha256())
                        ||!proof.kind().equals(row.kind().name())
                        ||proof.backendPid()!=origin.backendPid()||proof.transactionId()!=origin.transactionId()
                        ||!proof.databaseRole().equals(origin.databaseRole()))throw denied();
                verified.add(evidence);
            }
            var fences=union(identity.finalFences(nativeTicket),storage.finalFences(locked),
                    verified.stream().flatMap(t->sources.finalFences(t).stream()).toList());
            ticket=new Ticket(this,c,nativeTicket,origin,locked,snap,approvalObservation,verified,fences);
            TransactionSynchronizationManager.registerSynchronization(ticket);
            ticket.permit=executor.sealAdmission();
            requireCurrent(ticket,false);
            return ticket;
        }catch(RuntimeException failure){reject(ticket);throw failure;}
    }
    private static boolean isApproval(Operation op){
        return op==Operation.APPROVE||op==Operation.READ_APPROVAL||op==Operation.LIST_APPROVAL_HISTORY;
    }
    private static int rank(ProtectedRelationFence.Mode m) {
        return switch(m){case ACCESS_SHARE->0;case SHARE->1;case SHARE_ROW_EXCLUSIVE->2;};
    }
    @SafeVarargs private static List<ProtectedRelationFence> union(List<ProtectedRelationFence>... groups){
        var values=new TreeMap<String,ProtectedRelationFence.Mode>();
        for(var list:groups)for(var fence:list) {
            var old=values.get(fence.relation());
            if(old==null||rank(fence.mode())>rank(old))values.put(fence.relation(),fence.mode());
        }
        return values.entrySet().stream().map(x->new ProtectedRelationFence(x.getKey(),x.getValue())).toList();
    }
    private static String hash(String sql){
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(sql.getBytes(StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException ex){throw new IllegalStateException(ex);}
    }
    private static void pin(Set<String> sql,boolean approval){
        Set<String> expected=approval?APPROVAL_DML_HASHES:DML_HASHES;
        if(sql==null||sql.size()!=expected.size()||!sql.stream()
                .map(ProtectedInstalledCharacteristicLinkAdmissionCoordinator::hash)
                .collect(java.util.stream.Collectors.toSet()).equals(expected))throw denied();
    }
    private Ticket ticket(Admission a){
        if(!(a instanceof Ticket t)||t.owner!=this||!t.active||t.thread!=Thread.currentThread()
                ||!TransactionSynchronizationManager.isActualTransactionActive()
                ||!TransactionSynchronizationManager.isSynchronizationActive())throw denied();
        return t;
    }
    private void requireCurrent(Ticket t,boolean flushing) {
        ticket(t);
        if(flushing)executor.requireCharacteristicLinkFinalPreparation();
        else executor.requireAdmission();
        identity.requireCurrent(t.nativeTicket);
        for(var source:t.verified)sources.requireCurrent(source);
    }
    @Override public Result perform(Admission admission,Command c){
        var t=ticket(admission);
        try {
            if(!t.command.equals(c)||!t.used.compareAndSet(false,true)||t.result!=null)throw denied();
            requireCurrent(t,false);
            if(c.operation()==Operation.APPROVE){
                if(workflow==null||t.snapshot.revision()==null||t.snapshot.current()==null
                        ||!t.snapshot.current().equals(t.snapshot.revision())
                        ||t.snapshot.headEpoch()==null||!t.snapshot.headEpoch().equals(c.expectedEpoch())
                        ||t.approvalObservation==null||t.approvalObservation.draftActorUserId()==null
                        ||t.approvalObservation.draftActorUserId().equals(t.origin.userId()))
                    throw denied();
                var current=t.snapshot.revision();
                var keys=c.approval();
                var now=Instant.now().truncatedTo(ChronoUnit.MICROS);
                var approvedWorkflow=workflow.resolve(new InstalledCharacteristicLinkApprovalEvidencePort.Request(
                    current,keys.definitionId(),keys.definitionVersion(),keys.targetTypeId(),
                    keys.purposeId(),keys.instanceId(),keys.taskId(),keys.actionId(),now))
                    .orElseThrow(ProtectedInstalledCharacteristicLinkAdmissionCoordinator::denied);
                var approval=new InstalledCharacteristicLinkApproval(current,
                    t.approvalObservation.draftActorUserId(),t.origin.userId(),
                    InstalledCharacteristicLinkApprovalTargetDigester.digest(current),
                    approvedWorkflow,now);
                if(t.approvalObservation.receipt()==null){
                    var sql=storage.approvalMutationSql();pin(sql,true);
                    executor.beginProvisional(t.permit,sql);
                    t.result=storage.performApproval(t.storageTicket,c,t.origin,t.snapshot,t.approvalObservation,approval);
                    executor.endProvisional(t.permit);
                }else
                    t.result=storage.performApproval(t.storageTicket,c,t.origin,t.snapshot,t.approvalObservation,approval);
            }else if(isApproval(c.operation())){
                var source=storage.perform(t.storageTicket,c,t.origin,t.snapshot);
                t.result=new Result(source.link(),source.history(),false,
                    t.approvalObservation.nextBeforeRevision(),
                    t.approvalObservation.existing(),t.approvalObservation.history());
            }else if(c.operation()==Operation.SAVE_DRAFT&&t.snapshot.receipt()==null){
                var sql=storage.mutationSql();pin(sql,false);
                executor.beginProvisional(t.permit,sql);
                t.result=storage.perform(t.storageTicket,c,t.origin,t.snapshot);
                executor.endProvisional(t.permit);
            } else t.result=storage.perform(t.storageTicket,c,t.origin,t.snapshot);
            requireCurrent(t,false);
            return t.result;
        }catch(RuntimeException failure){reject(t);throw failure;}
    }
    @Override public void flush(){storage.flush();}
    @Override public void prepareFinal(Admission admission){
        var t=ticket(admission);
        try {
            if(t.result==null||t.frozen)throw denied();
            requireCurrent(t,true);
            storage.prepareFinal(t.storageTicket,t.origin,t.fences);
            t.frozen=true;
        }catch(RuntimeException failure){reject(t);throw failure;}
    }
    @Override public boolean finalGuard(Admission admission){
        var t=ticket(admission);
        try {
            if(!t.frozen||t.guarded)throw denied();
            if(!storage.finalGuard(t.storageTicket))throw denied();
            identity.markFinal(t.nativeTicket);
            t.guarded=true;
            return true;
        }catch(RuntimeException failure){reject(t);throw failure;}
    }
    private void reject(Admission admission){
        if(admission instanceof Ticket t){t.active=false;storage.reject(t.storageTicket);}
        executor.rejectAdmission();
    }
    private static final class PreparedTicket implements Prepared{
        final ProtectedInstalledCharacteristicLinkAdmissionCoordinator owner;
        final Command command;
        final TopologyInstalledCharacteristicLinkAuthorizationContract.Prepared nativePrepared;
        final Thread thread=Thread.currentThread();
        final AtomicBoolean used=new AtomicBoolean();
        PreparedTicket(ProtectedInstalledCharacteristicLinkAdmissionCoordinator owner,Command command,
                       TopologyInstalledCharacteristicLinkAuthorizationContract.Prepared nativePrepared){
            this.owner=owner;this.command=command;this.nativePrepared=nativePrepared;
        }
    }
    private static final class Ticket implements Admission,TransactionSynchronization {
        final ProtectedInstalledCharacteristicLinkAdmissionCoordinator owner;
        final Command command;
        final TopologyInstalledCharacteristicLinkAuthorizationContract.Admission nativeTicket;
        final Origin origin;
        final InstalledCharacteristicLinkStoragePort.Admission storageTicket;
        final Snapshot snapshot;
        final InstalledCharacteristicLinkStoragePort.ApprovalObservation approvalObservation;
        final List<TopologyManufacturerCharacteristicReferenceContract.Admission> verified;
        final List<ProtectedRelationFence> fences;
        final Thread thread=Thread.currentThread();
        final AtomicBoolean used=new AtomicBoolean();
        ProtectedTransactionExecutor.ProvisionalPermit permit;
        boolean active=true,frozen=false,guarded=false;
        Result result;
        Ticket(ProtectedInstalledCharacteristicLinkAdmissionCoordinator owner,Command command,
               TopologyInstalledCharacteristicLinkAuthorizationContract.Admission nativeTicket,
               Origin origin,InstalledCharacteristicLinkStoragePort.Admission storageTicket,Snapshot snapshot,
               InstalledCharacteristicLinkStoragePort.ApprovalObservation approvalObservation,
               List<TopologyManufacturerCharacteristicReferenceContract.Admission> verified,
               List<ProtectedRelationFence> fences){
            this.owner=owner;this.command=command;this.nativeTicket=nativeTicket;this.origin=origin;
            this.storageTicket=storageTicket;this.snapshot=snapshot;
            this.approvalObservation=approvalObservation;this.verified=List.copyOf(verified);
            this.fences=List.copyOf(fences);
        }
        @Override public void suspend(){active=false;}
        @Override public void beforeCommit(boolean readOnly){if(!active||!guarded)throw denied();}
        @Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "CharacteristicLinkAdmission[opaque]";}
    }
}
