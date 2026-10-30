/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationConfigurationAdmissionCoordinator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : One original native admission, source lock and sealed station configuration transaction.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyStationConfigurationAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyStationConfigurationAuthorizationContract.*;
import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.Command;
import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.Result;
import dz.sh.hidra.modules.topology.application.port.out.StationInstalledConfigurationRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.StationConfigurationSourceEvidencePort;
import dz.sh.hidra.modules.topology.application.port.out.StationInstalledConfigurationRepositoryPort.Snapshot;
import dz.sh.hidra.modules.topology.application.port.out.StationInstalledConfigurationRepositoryPort.Origin;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** Opaque issuer, fixed SQL statement pins and native identity requalification precede every owner result. */
@Service
public class ProtectedStationConfigurationAdmissionCoordinator {
    private final TopologyStationConfigurationAuthorizationContract identity;
    private final StationConfigurationSourceEvidencePort sources;
    private final StationInstalledConfigurationRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    // Exact SHA-256 pins are filled only from the compiled JDBC adapter's five constant statement texts.
    private static final Set<String> DML_HASHES=Set.of("a526f8833040c3c7b6c4510538438cbeb6d2b0b0a603f647bf30c0a2cca095f9","7ce1197ed30cc3278fc03074d45b0c483472529bfd09b2784db655a0ee924c5b","5591e291eb0dbdef51ad2150363e65506143b4551f5d70ba4f6cfacd776dc01c","ef47e69fb0ce03dd54cf45eb5ab98fb0a1a94705892bec66c517d19a31fe438c","390240eb897a42dd62ed11d94d9682d6cb103224e9ba121845da9b352e6b7f40");
    public ProtectedStationConfigurationAdmissionCoordinator(TopologyStationConfigurationAuthorizationContract identity,
            StationConfigurationSourceEvidencePort sources,
            StationInstalledConfigurationRepositoryPort repository,ProtectedTransactionExecutor executor) {
        this.identity=Objects.requireNonNull(identity);this.sources=Objects.requireNonNull(sources);
        this.repository=Objects.requireNonNull(repository);this.executor=Objects.requireNonNull(executor);
    }
    public Prepared prepare(Command command) {
        executor.requireNoProtectedContext();
        if(command==null || TransactionSynchronizationManager.isActualTransactionActive()
           || TransactionSynchronizationManager.isSynchronizationActive())throw denied();
        return new Prepared(this,command,identity.prepare());
    }
    private static Set<String> units(StationInstalledConfiguration c){
        if(c==null)return Set.of();
        var ids=new TreeSet<String>();
        for(var p:c.positions()) if(p.installedUnitId()!=null)ids.add(p.installedUnitId());
        return ids;
    }
    private static Request request(Command c,Snapshot s){
        var refs=new TreeSet<String>();refs.addAll(units(c.declaration()));
        refs.addAll(units(s.current()));refs.addAll(units(s.revision()));
        for(var old:s.history())refs.addAll(units(old));
        if(refs.size()>TopologyStationConfigurationAuthorizationContract.MAX_REFERENCED_UNITS)throw denied();
        return new Request(TopologyStationConfigurationAuthorizationContract.Operation.valueOf(c.operation().name()),
            c.companyPartyId(),c.facilityId(),c.revisionId(),List.copyOf(refs));
    }
    private static List<ProtectedRelationFence> union(List<ProtectedRelationFence>... groups){
        var found=new TreeMap<String,ProtectedRelationFence.Mode>();
        for(var group:groups)for(var f:group){
            var old=found.get(f.relation());
            if(old==null || ordinal(f.mode())>ordinal(old))found.put(f.relation(),f.mode());
        }
        return found.entrySet().stream().map(e->new ProtectedRelationFence(e.getKey(),e.getValue())).toList();
    }
    private static int ordinal(ProtectedRelationFence.Mode mode){
        return switch(mode){case ACCESS_SHARE->0;case SHARE->1;case SHARE_ROW_EXCLUSIVE->2;};
    }
    private static String digest(String sql){
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(sql.getBytes(StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException absent){throw new IllegalStateException(absent);}
    }
    private static void pins(Set<String> sql){
        if(sql==null || sql.size()!=DML_HASHES.size()
           || !sql.stream().map(ProtectedStationConfigurationAdmissionCoordinator::digest)
                .collect(java.util.stream.Collectors.toSet()).equals(DML_HASHES))throw denied();
    }
    public Admission acquire(Prepared prepared,Command command) {
        Admission a=null;
        try{
            executor.beginAdmission();
            if(prepared==null || prepared.owner!=this || prepared.thread!=Thread.currentThread()
              || !prepared.used.compareAndSet(false,true) || !prepared.command.equals(command))throw denied();
            var storage=repository.acquire(command);
            var snapshot=repository.observe(storage,command);
            var nativeTicket=identity.acquire(prepared.nativePrepared,request(command,snapshot));
            var nativeContribution=identity.contribute(nativeTicket);
            if(nativeContribution.overallDecision()!=Decision.PERMIT
              || !nativeContribution.request().equals(request(command,snapshot)))throw denied();
            StationConfigurationSourceEvidencePort.Admission sourceTicket=null;
            if(command.operation()==dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.Operation.SAVE){
                sourceTicket=sources.acquire(command.declaration());
                var source=sources.read(sourceTicket);
                if(!source.facilityId().equals(command.facilityId())
                  || !source.companyPartyId().equals(command.companyPartyId())
                  || source.backendPid()!=nativeContribution.backendPid()
                  || source.transactionId()!=nativeContribution.transactionId()
                  || !source.graphRevisionId().equals(command.declaration().pinnedGraph().revisionId())
                  || source.graphEpoch()!=command.declaration().graphHeadEpoch())throw denied();
            }
            var fences=union(identity.finalFences(nativeTicket),
                repository.finalFences(storage),sourceTicket==null?List.of():sources.finalFences(sourceTicket));
            a=new Admission(this,command,nativeTicket,nativeContribution,storage,snapshot,sourceTicket,fences);
            TransactionSynchronizationManager.registerSynchronization(a);
            identity.requireCurrent(nativeTicket);
            if(sourceTicket!=null)sources.requireCurrent(sourceTicket);
            a.permit=executor.sealAdmission();
            return a;
        }catch(RuntimeException e){reject(a);throw e;}
    }
    private Admission current(Admission a) {
        if(a==null || a.owner!=this || !a.active || a.thread!=Thread.currentThread()
            || !TransactionSynchronizationManager.isActualTransactionActive()
            || !TransactionSynchronizationManager.isSynchronizationActive())throw denied();
        return a;
    }
    private void requireCurrent(Admission a,boolean flushing){
        current(a);
        if(flushing)executor.requireStationConfigurationFinalPreparation();
        else executor.requireAdmission();
        identity.requireCurrent(a.nativeTicket);
        if(a.sourceTicket!=null)sources.requireCurrent(a.sourceTicket);
    }
    public Result perform(Admission admission){
        try{
            var a=current(admission);requireCurrent(a,false);
            if(!a.used.compareAndSet(false,true))throw denied();
            if(a.command.operation()==dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.Operation.SAVE
               && a.snapshot.requestDigest()==null){
                var sql=repository.mutationSql();pins(sql);
                executor.beginProvisional(a.permit,sql);
                a.result=repository.execute(a.storage,a.command,a.origin,a.snapshot);
                executor.endProvisional(a.permit);
            }else {
                a.result=repository.execute(a.storage,a.command,a.origin,a.snapshot);
            }
            requireCurrent(a,false);return a.result;
        }catch(RuntimeException e){reject(admission);throw e;}
    }
    public void prepareFinal(Admission admission) {
        try{
            var a=current(admission);
            if(a.result==null || a.frozen)throw denied();
            requireCurrent(a,true);
            repository.prepareFinal(a.storage,a.origin,a.fences);
            a.frozen=true;
        }catch(RuntimeException e){reject(admission);throw e;}
    }
    public boolean finalGuard(Admission admission){
        try{
            var a=current(admission);
            if(!a.frozen || a.guarded)throw denied();
            a.guarded=repository.finalGuard(a.storage);
            return a.guarded;
        }catch(RuntimeException e){reject(admission);throw e;}
    }
    public void flush(){
        repository.flush();
    }
    private void reject(Admission a){
        if(a!=null){a.active=false;repository.reject(a.storage);}
        executor.rejectAdmission();
    }
    private static AccessDeniedException denied(){return new AccessDeniedException("Station configuration operation unavailable");}
    public static final class Prepared{
        private final ProtectedStationConfigurationAdmissionCoordinator owner;
        private final Command command;private final TopologyStationConfigurationAuthorizationContract.Prepared nativePrepared;
        private final Thread thread=Thread.currentThread();private final AtomicBoolean used=new AtomicBoolean();
        private Prepared(ProtectedStationConfigurationAdmissionCoordinator owner,Command command,
            TopologyStationConfigurationAuthorizationContract.Prepared nativePrepared){
            this.owner=owner;this.command=command;this.nativePrepared=nativePrepared;
        }
        @Override public String toString(){return "Prepared[protected station configuration]";}
    }
    public static final class Admission implements TransactionSynchronization{
        private final ProtectedStationConfigurationAdmissionCoordinator owner;
        private final Command command;private final TopologyStationConfigurationAuthorizationContract.Admission nativeTicket;
        private final Contribution contribution;private final Origin origin;
        private final StationInstalledConfigurationRepositoryPort.Admission storage;
        private final Snapshot snapshot;private final StationConfigurationSourceEvidencePort.Admission sourceTicket;
        private final List<ProtectedRelationFence> fences;
        private final Thread thread=Thread.currentThread();private final AtomicBoolean used=new AtomicBoolean();
        private ProtectedTransactionExecutor.ProvisionalPermit permit;
        private boolean active=true,frozen,guarded;private Result result;
        private Admission(ProtectedStationConfigurationAdmissionCoordinator owner,Command command,
            TopologyStationConfigurationAuthorizationContract.Admission nativeTicket,Contribution contribution,
            StationInstalledConfigurationRepositoryPort.Admission storage,Snapshot snapshot,
            StationConfigurationSourceEvidencePort.Admission sourceTicket,List<ProtectedRelationFence> fences){
            this.owner=owner;this.command=command;this.nativeTicket=nativeTicket;this.contribution=contribution;
            this.origin=new Origin(contribution.bindingId(),contribution.sessionId(),contribution.userId(),
                contribution.sampledAt(),contribution.validUntil(),contribution.backendPid(),contribution.transactionId());
            this.storage=storage;this.snapshot=snapshot;this.sourceTicket=sourceTicket;this.fences=List.copyOf(fences);
        }
        @Override public void beforeCommit(boolean readOnly){
            if(!active || !guarded)throw denied();
        }
        @Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "Admission[original native station configuration operation]";}
    }
}
