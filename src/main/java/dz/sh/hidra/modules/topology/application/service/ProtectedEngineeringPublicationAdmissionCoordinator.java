/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedEngineeringPublicationAdmissionCoordinator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Coordinates native publication authorization and exactly thirty-eight frozen relation fences.
 *
 */
package dz.sh.hidra.modules.topology.application.service;


import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.*;
import dz.sh.hidra.modules.party.application.contract.topology.TopologyProtectedPartyEligibilityContract;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase.Command;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase.Kind;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringRevisionPublicationRepositoryPort;
import dz.sh.hidra.platform.persistence.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;

@Service
public class ProtectedEngineeringPublicationAdmissionCoordinator {
    private final TopologyProtectedEngineeringDraftAuthorizationContract identity;
    private final TopologyProtectedPartyEligibilityContract party;
    private final EngineeringRevisionPublicationRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    public ProtectedEngineeringPublicationAdmissionCoordinator(TopologyProtectedEngineeringDraftAuthorizationContract i,
            TopologyProtectedPartyEligibilityContract p,EngineeringRevisionPublicationRepositoryPort r,ProtectedTransactionExecutor e){
        identity=Objects.requireNonNull(i);party=Objects.requireNonNull(p);repository=Objects.requireNonNull(r);executor=Objects.requireNonNull(e);
    }
    @Transactional(propagation=Propagation.NEVER)
    public Prepared prepare(){if(TransactionSynchronizationManager.isActualTransactionActive())throw invalid();return new Prepared(this,identity.prepare());}

    public static Action authorityAction(Kind kind){
        return switch(Objects.requireNonNull(kind)){
            case PUBLISH -> Action.PUBLICATION_PUBLISH;
            case WITHDRAW -> Action.PUBLICATION_WITHDRAW;
            case REACTIVATE -> Action.PUBLICATION_REACTIVATE;
            default -> Action.PUBLICATION_READ;
        };
    }

    @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Prepared p,Command cmd){
        try {
            executor.beginAdmission();
            if(p==null || p.owner!=this || p.thread!=Thread.currentThread() || !p.used.compareAndSet(false,true)
                || !TransactionSynchronizationManager.isSynchronizationActive())throw invalid();
            Request request=new Request(authorityAction(cmd.kind()),cmd.companyPartyId(),cmd.pipelineSystemId());
            var i=identity.acquire(p.identity,request);
            var o=party.acquire(cmd.companyPartyId());
            var r=repository.acquire(cmd);
            var fences=new ArrayList<ProtectedRelationFence>();
            var fi=identity.finalFences(i);var fp=party.finalFences(o);var fr=repository.finalFences(r);
            int topologyCount=12;
            int total=38;
            if(fi.size()!=24 || fp.size()!=2 || fr.size()!=topologyCount)throw invalid();
            fences.addAll(fi);fences.addAll(fp);fences.addAll(fr);
            if(fences.stream().map(ProtectedRelationFence::relation).distinct().count()!=total)throw invalid();
            var a=new Admission(this,request,i,o,r,List.copyOf(fences),cmd.kind());
            TransactionSynchronizationManager.registerSynchronization(a);return a;
        }catch(RuntimeException e){executor.rejectAdmission();throw e;}
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public EngineeringRevisionPublicationRepositoryPort.Observation collect(Admission admission){
        try {var a=current(admission);if(!a.used.compareAndSet(false,true))throw invalid();
            var i=identity.contribute(a.identity);
            if(i.companyDecision()!=Decision.PERMIT || i.networkDecision()!=Decision.PERMIT)
                throw new org.springframework.security.access.AccessDeniedException("Publication denied");
            var p=party.read(a.party);
            if(!i.request().equals(a.request) || !p.partyId().equals(a.request.companyPartyId())
                || p.backendPid()!=i.backendPid() || p.transactionId()!=i.transactionId())throw invalid();
            repository.eligible(a.repository);identity.requireCurrent(a.identity);
            a.result=new EngineeringRevisionPublicationRepositoryPort.Observation(repository.scope(a.repository),i.userId(),
                i.bindingId(),i.sessionId(),p.generation(),i.sampledAt(),i.validUntil(),i.backendPid(),i.transactionId());
            repository.observe(a.repository,a.result);
            a.permit=executor.sealAdmission();requireCurrent(a);return a.result;
        }catch(RuntimeException e){executor.rejectAdmission();throw e;}
    }

    private static final Set<String> PUBLICATION_DML_HASHES=Set.of(
        "321c4652f15f2da9f76f5ca12395cd899ac3d55c49e8039bdd46b96a1d8fb155",
        "28c7c4023a6d0a597703912a57e0813c04b18fb6caf4d11a4cc91c50be5385b1",
        "76b726f5c9ead80cfebb8cf1b9f109f7f36a5438465f760687f9c8225b62467a",
        "845f50127991e910b122499565576ea758963779dfbcda3df382a92e38dc3ca1");
    private static String digest(String sql){try{return HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256")
        .digest(sql.getBytes(java.nio.charset.StandardCharsets.UTF_8)));}catch(java.security.NoSuchAlgorithmException e){throw new IllegalStateException(e);}}
    private static boolean compiled(){return StackWalker.getInstance().walk(f->f.anyMatch(x->x.getClassName().equals(
        "dz.sh.hidra.modules.topology.application.service.EngineeringRevisionPublicationApplicationService")));}
    @Transactional(propagation=Propagation.MANDATORY)
    public void beginProvisional(Admission a,Set<String> sql){try {
        requireCurrent(a);
        if(!compiled() || !Set.of(Action.PUBLICATION_PUBLISH,Action.PUBLICATION_WITHDRAW,Action.PUBLICATION_REACTIVATE).contains(a.request.action())
            || sql==null || sql.size()!=4 || !sql.stream().map(ProtectedEngineeringPublicationAdmissionCoordinator::digest)
                .collect(java.util.stream.Collectors.toUnmodifiableSet()).equals(PUBLICATION_DML_HASHES))
            throw invalid();
        executor.beginProvisional(a.permit,sql);
    }catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    @Transactional(propagation=Propagation.MANDATORY)
    public void endProvisional(Admission a){try {current(a);if(!compiled())throw invalid();executor.endProvisional(a.permit);requireCurrent(a);}
        catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    public EngineeringRevisionPublicationRepositoryPort.Admission repository(Admission a){return current(a).repository;}
    public List<ProtectedRelationFence> finalFences(Admission a){return current(a).fences;}
    @Transactional(propagation=Propagation.MANDATORY)
    public void requireCurrent(Admission a){try {current(a);if(a.result==null)throw invalid();executor.requireAdmission();identity.requireCurrent(a.identity);}
        catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    private Admission current(Admission a){if(a==null || a.owner!=this || a.thread!=Thread.currentThread() || !a.active
        || !TransactionSynchronizationManager.isActualTransactionActive() || TransactionSynchronizationManager.isCurrentTransactionReadOnly())throw invalid();return a;}
    private static IllegalStateException invalid(){return new IllegalStateException("Publication admission unavailable");}
    public static final class Prepared {
        private final ProtectedEngineeringPublicationAdmissionCoordinator owner;private final Thread thread=Thread.currentThread();
        private final TopologyProtectedEngineeringDraftAuthorizationContract.Prepared identity;private final AtomicBoolean used=new AtomicBoolean();
        private Prepared(ProtectedEngineeringPublicationAdmissionCoordinator owner,TopologyProtectedEngineeringDraftAuthorizationContract.Prepared identity){this.owner=owner;this.identity=identity;}
        @Override public String toString(){return "Prepared[opaque publication]";}
    }
    public static final class Admission implements TransactionSynchronization {
        private final ProtectedEngineeringPublicationAdmissionCoordinator owner;private final Thread thread=Thread.currentThread();private final Request request;
        private final TopologyProtectedEngineeringDraftAuthorizationContract.Admission identity;
        private final TopologyProtectedPartyEligibilityContract.Admission party;private final EngineeringRevisionPublicationRepositoryPort.Admission repository;
        private final List<ProtectedRelationFence> fences;private final AtomicBoolean used=new AtomicBoolean();private boolean active=true;
        private final Kind kind;
        private EngineeringRevisionPublicationRepositoryPort.Observation result;private ProtectedTransactionExecutor.ProvisionalPermit permit;
        private Admission(ProtectedEngineeringPublicationAdmissionCoordinator o,Request q,TopologyProtectedEngineeringDraftAuthorizationContract.Admission i,
            TopologyProtectedPartyEligibilityContract.Admission p,EngineeringRevisionPublicationRepositoryPort.Admission r,List<ProtectedRelationFence> f,Kind kind){
            owner=o;request=q;identity=i;party=p;repository=r;fences=f;this.kind=kind;}
        @Override public void suspend(){active=false;}@Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "Admission[opaque publication]";}
    }
}
