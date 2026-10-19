/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedEngineeringDraftAdmissionCoordinator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.*;
import dz.sh.hidra.modules.party.application.contract.topology.TopologyProtectedPartyEligibilityContract;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Command;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringNetworkDraftRepositoryPort;
import dz.sh.hidra.platform.persistence.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;

@Service
public class ProtectedEngineeringDraftAdmissionCoordinator {
    private final TopologyProtectedEngineeringDraftAuthorizationContract identity;
    private final TopologyProtectedPartyEligibilityContract party;
    private final EngineeringNetworkDraftRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    public ProtectedEngineeringDraftAdmissionCoordinator(TopologyProtectedEngineeringDraftAuthorizationContract i,
            TopologyProtectedPartyEligibilityContract p,EngineeringNetworkDraftRepositoryPort r,ProtectedTransactionExecutor e){
        identity=Objects.requireNonNull(i);party=Objects.requireNonNull(p);repository=Objects.requireNonNull(r);executor=Objects.requireNonNull(e);
    }
    @Transactional(propagation=Propagation.NEVER)
    public Prepared prepare(){if(TransactionSynchronizationManager.isActualTransactionActive())throw invalid();return new Prepared(this,identity.prepare());}
    @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Prepared p,Command cmd){
        try {
            executor.beginAdmission();
            if(p==null || p.owner!=this || p.thread!=Thread.currentThread() || !p.used.compareAndSet(false,true)
                || !TransactionSynchronizationManager.isSynchronizationActive())throw invalid();
            Request request=new Request(Action.valueOf(cmd.kind().name()),cmd.companyPartyId(),cmd.pipelineSystemId());
            var i=identity.acquire(p.identity,request);var o=party.acquire(cmd.companyPartyId());var r=repository.acquire(cmd);
            var fences=new ArrayList<ProtectedRelationFence>();
            var fi=identity.finalFences(i);var fp=party.finalFences(o);var fr=repository.finalFences(r);
            if(fi.size()!=24 || fp.size()!=2 || fr.size()!=5)throw invalid();
            fences.addAll(fi);fences.addAll(fp);fences.addAll(fr);
            if(fences.stream().map(ProtectedRelationFence::relation).distinct().count()!=31)throw invalid();
            var a=new Admission(this,request,i,o,r,List.copyOf(fences));TransactionSynchronizationManager.registerSynchronization(a);return a;
        }catch(RuntimeException e){executor.rejectAdmission();throw e;}
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public Contribution collect(Admission admission){
        try {var a=current(admission);if(!a.used.compareAndSet(false,true))throw invalid();
            var i=identity.contribute(a.identity);var p=party.read(a.party);
            if(i.companyDecision()!=Decision.PERMIT || i.networkDecision()!=Decision.PERMIT)
                throw new org.springframework.security.access.AccessDeniedException("Engineering draft denied");
            if(!i.request().equals(a.request) || !p.partyId().equals(a.request.companyPartyId())
                || p.backendPid()!=i.backendPid() || p.transactionId()!=i.transactionId())throw invalid();
            repository.eligible(a.repository);identity.requireCurrent(a.identity);
            a.result=new Contribution(a.request,p.generation(),i.bindingId(),i.sessionId(),i.userId(),
                i.sampledAt(),i.validUntil(),i.backendPid(),i.transactionId());
            a.permit=executor.sealAdmission();requireCurrent(a);return a.result;
        }catch(RuntimeException e){executor.rejectAdmission();throw e;}
    }
    private static final Set<String> DRAFT_DML_HASHES=Set.of("e8f934e6f6ea4d39b81b5239ab77434447aff88ca147f9ba242bdb9dee3629d7","364c7d9ff7e4e8d307a15ab41d27f69c4a4359b084c5360b153ec94034d75630","ea65144cc041c124b9021fecc0670a6c647992c31f6c05881c472a015dd89035");
    private static String digest(String sql){try{return HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256")
        .digest(sql.getBytes(java.nio.charset.StandardCharsets.UTF_8)));}catch(java.security.NoSuchAlgorithmException e){throw new IllegalStateException(e);}}
    private static boolean compiled(){return StackWalker.getInstance().walk(f->f.anyMatch(x->x.getClassName().equals(
        "dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationService")));}
    @Transactional(propagation=Propagation.MANDATORY)
    public void beginProvisional(Admission a,Set<String> sql){try {
        requireCurrent(a);if(!compiled() || !Set.of(Action.CREATE,Action.UPDATE).contains(a.request.action()) || sql==null
            || sql.size()!=3 || !sql.stream().map(ProtectedEngineeringDraftAdmissionCoordinator::digest)
                .collect(java.util.stream.Collectors.toUnmodifiableSet()).equals(DRAFT_DML_HASHES))throw invalid();
        executor.beginProvisional(a.permit,sql);
    }catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    @Transactional(propagation=Propagation.MANDATORY)
    public void endProvisional(Admission a){try {current(a);if(!compiled())throw invalid();executor.endProvisional(a.permit);requireCurrent(a);}
        catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    public EngineeringNetworkDraftRepositoryPort.Admission repository(Admission a){return current(a).repository;}
    public List<ProtectedRelationFence> finalFences(Admission a){return current(a).fences;}
    @Transactional(propagation=Propagation.MANDATORY)
    public void requireCurrent(Admission a){try {current(a);if(a.result==null)throw invalid();executor.requireAdmission();identity.requireCurrent(a.identity);}
        catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    private Admission current(Admission a){if(a==null || a.owner!=this || a.thread!=Thread.currentThread() || !a.active
        || !TransactionSynchronizationManager.isActualTransactionActive() || TransactionSynchronizationManager.isCurrentTransactionReadOnly())throw invalid();return a;}
    private static IllegalStateException invalid(){return new IllegalStateException("Engineering draft admission unavailable");}
    public static final class Prepared {
        private final ProtectedEngineeringDraftAdmissionCoordinator owner;private final Thread thread=Thread.currentThread();
        private final TopologyProtectedEngineeringDraftAuthorizationContract.Prepared identity;private final AtomicBoolean used=new AtomicBoolean();
        private Prepared(ProtectedEngineeringDraftAdmissionCoordinator owner,TopologyProtectedEngineeringDraftAuthorizationContract.Prepared identity){this.owner=owner;this.identity=identity;}
        @Override public String toString(){return "Prepared[opaque draft]";}
    }
    public static final class Admission implements TransactionSynchronization {
        private final ProtectedEngineeringDraftAdmissionCoordinator owner;private final Thread thread=Thread.currentThread();private final Request request;
        private final TopologyProtectedEngineeringDraftAuthorizationContract.Admission identity;
        private final TopologyProtectedPartyEligibilityContract.Admission party;private final EngineeringNetworkDraftRepositoryPort.Admission repository;
        private final List<ProtectedRelationFence> fences;private final AtomicBoolean used=new AtomicBoolean();private boolean active=true;
        private Contribution result;private ProtectedTransactionExecutor.ProvisionalPermit permit;
        private Admission(ProtectedEngineeringDraftAdmissionCoordinator o,Request q,TopologyProtectedEngineeringDraftAuthorizationContract.Admission i,
            TopologyProtectedPartyEligibilityContract.Admission p,EngineeringNetworkDraftRepositoryPort.Admission r,List<ProtectedRelationFence> f){
            owner=o;request=q;identity=i;party=p;repository=r;fences=f;}
        @Override public void suspend(){active=false;}@Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "Admission[opaque draft]";}
    }
    public record Contribution(Request request,long partyGeneration,String bindingId,String sessionId,String userId,
            Instant sampledAt,Instant validUntil,int backendPid,long transactionId) {}
}
