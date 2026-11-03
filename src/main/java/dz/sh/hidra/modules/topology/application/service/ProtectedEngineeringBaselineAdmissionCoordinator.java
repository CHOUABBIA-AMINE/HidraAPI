/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedEngineeringBaselineAdmissionCoordinator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Combines native baseline and source authorities in one protected physical transaction.
 *
 */
package dz.sh.hidra.modules.topology.application.service;


import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineDraftUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineDraftRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineSourcePort;
import dz.sh.hidra.modules.topology.domain.service.EngineeringBaselineCompletenessPolicy;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Component;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.support.*;

@Component
public final class ProtectedEngineeringBaselineAdmissionCoordinator {
    private final TopologyEngineeringBaselineAuthorizationContract identity;
    private final EngineeringBaselineDraftRepositoryPort storage;
    private final EngineeringBaselineSourcePort sources;
    private final ProtectedTransactionExecutor executor;
    private final EngineeringBaselineCompletenessPolicy policy=new EngineeringBaselineCompletenessPolicy();
    public ProtectedEngineeringBaselineAdmissionCoordinator(TopologyEngineeringBaselineAuthorizationContract identity,
            EngineeringBaselineDraftRepositoryPort storage,EngineeringBaselineSourcePort sources,ProtectedTransactionExecutor executor){
        this.identity=Objects.requireNonNull(identity);this.storage=Objects.requireNonNull(storage);
        this.sources=Objects.requireNonNull(sources);this.executor=Objects.requireNonNull(executor);
    }
    public Prepared prepare(Command command){
        executor.requireNoProtectedContext();Objects.requireNonNull(command);
        if(TransactionSynchronizationManager.isActualTransactionActive()||TransactionSynchronizationManager.isSynchronizationActive())throw denied();
        return new Prepared(this,command,identity.prepare());
    }
    public Admission acquire(Prepared p,Command command){
        try{
            executor.beginAdmission();
            if(p==null||p.owner!=this||p.thread!=Thread.currentThread()||!p.command.equals(command)||!p.used.compareAndSet(false,true))throw denied();
            storage.lockSourcesAndStorage();
            var auth=identity.acquire(p.prepared,new TopologyEngineeringBaselineAuthorizationContract.Request(
                TopologyEngineeringBaselineAuthorizationContract.Action.valueOf(command.action().name()),
                command.companyPartyId(),command.pipelineSystemId()));
            var o=identity.contribute(auth);
            var origin=new EngineeringBaselineSourcePort.Original(o.bindingId(),o.sessionId(),o.userId(),
                o.backendPid(),o.transactionId(),o.databaseRole(),o.sampledAt(),o.validUntil());
            var stored=storage.select(command,origin);
            var source=sources.capture(storage.manifest(stored),origin,r->identity.requireSourceRead(auth,
                new TopologyEngineeringBaselineAuthorizationContract.Source(
                    TopologyEngineeringBaselineAuthorizationContract.SourceKind.valueOf(r.kind().name()),r.referenceId(),r.units())));
            var t=new Admission(this,command,auth,stored,source);
            t.fences=union(identity.finalFences(auth),storage.finalFences(),sources.finalFences(source));
            t.permit=executor.sealAdmission();identity.requireCurrent(auth);
            return t;
        }catch(RuntimeException failure){executor.rejectAdmission();throw failure;}
    }
    public Result perform(Admission a){
        var t=ticket(a);if(t.performed)throw denied();
        try{
            executor.requireAdmission();identity.requireCurrent(t.identity);
            sources.requireCurrent(t.sources);
            var completeness=policy.inspect(storage.manifest(t.storage),sources.facts(t.sources));
            if(t.command.mutating()){
                var sql=storage.mutationSql();
                if(!sql.stream().map(ProtectedEngineeringBaselineAdmissionCoordinator::hash).collect(java.util.stream.Collectors.toSet())
                    .equals(BASELINE_DML_HASHES))throw denied();
                executor.beginProvisional(t.permit,sql);
                t.result=storage.apply(t.storage,completeness);
                executor.endProvisional(t.permit);
            }else t.result=storage.apply(t.storage,completeness);
            identity.requireCurrent(t.identity);sources.requireCurrent(t.sources);
            t.performed=true;return t.result;
        }catch(RuntimeException failure){executor.rejectAdmission();throw failure;}
    }
    public void flushAndPrepare(Admission a){
        var t=ticket(a);if(!t.performed||t.frozen)throw denied();
        try{
            executor.requireEngineeringBaselineFinalPreparation();
            storage.flush();identity.requireCurrent(t.identity);sources.requireCurrent(t.sources);
            storage.prepareFinal(t.storage,t.fences);t.frozen=true;
        }catch(RuntimeException failure){executor.rejectAdmission();throw failure;}
    }
    public void finalGuard(Admission a){
        var t=ticket(a);if(!t.frozen||t.guarded)throw denied();
        if(!storage.finalGuard(t.storage)){executor.rejectAdmission();throw denied();}
        identity.markFinal(t.identity);t.guarded=true;
    }
    private Admission ticket(Admission t){
        if(t==null||t.owner!=this||t.thread!=Thread.currentThread()
            ||!TransactionSynchronizationManager.isActualTransactionActive())throw denied();
        return t;
    }
    @SafeVarargs private static List<ProtectedRelationFence> union(List<ProtectedRelationFence>... groups){
        var entries=new TreeMap<String,ProtectedRelationFence.Mode>();
        for(var group:groups)for(var f:group){
            var old=entries.get(f.relation());
            if(old==null||rank(f.mode())>rank(old))entries.put(f.relation(),f.mode());
        }
        return entries.entrySet().stream().map(e->new ProtectedRelationFence(e.getKey(),e.getValue())).toList();
    }
    private static int rank(ProtectedRelationFence.Mode m){return switch(m){case ACCESS_SHARE->0;case SHARE->1;case SHARE_ROW_EXCLUSIVE->2;};}
    private static String hash(String sql){
        try{return HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(sql.getBytes(java.nio.charset.StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private static final Set<String> BASELINE_DML_HASHES=Set.of("7afbfe5a5299afc1b30ef68a48469b5eee1fad1b2e14d31116e804e3b229efaf","e2c3cdd241a4f8e247df680f1de454a99a691e424d5bbb7bde38f9e6a8688b9a","24d7b34421accbb4a76d96bd43a387793f9a474ae2f6e2764c4e0bebf69cf118","195b32bc5d19606ad6782d80959f36a19129bbb449af1be08af8b054a1345026");
    private static AccessDeniedException denied(){return new AccessDeniedException("Engineering baseline draft denied");}
    public static final class Prepared {
        private final ProtectedEngineeringBaselineAdmissionCoordinator owner;private final Command command;
        private final TopologyEngineeringBaselineAuthorizationContract.Prepared prepared;
        private final Thread thread=Thread.currentThread();private final AtomicBoolean used=new AtomicBoolean();
        private Prepared(ProtectedEngineeringBaselineAdmissionCoordinator owner,Command command,TopologyEngineeringBaselineAuthorizationContract.Prepared prepared){
            this.owner=owner;this.command=command;this.prepared=prepared;
        }
    }
    public static final class Admission {
        private final ProtectedEngineeringBaselineAdmissionCoordinator owner;private final Command command;
        private final TopologyEngineeringBaselineAuthorizationContract.Admission identity;
        private final EngineeringBaselineDraftRepositoryPort.Admission storage;
        private final EngineeringBaselineSourcePort.Admission sources;private final Thread thread=Thread.currentThread();
        private List<ProtectedRelationFence> fences;private ProtectedTransactionExecutor.ProvisionalPermit permit;
        private boolean performed,frozen,guarded;private Result result;
        private Admission(ProtectedEngineeringBaselineAdmissionCoordinator owner,Command command,
                TopologyEngineeringBaselineAuthorizationContract.Admission identity,
                EngineeringBaselineDraftRepositoryPort.Admission storage,EngineeringBaselineSourcePort.Admission sources){
            this.owner=owner;this.command=command;this.identity=identity;this.storage=storage;this.sources=sources;
        }
    }
}
