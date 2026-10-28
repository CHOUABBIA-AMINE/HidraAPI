/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedProductPublicationAdmissionCoordinator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.service
 *
 * @Description : Preserves exact authored fluid facts independently of numerical qualification.
 *
 */
package dz.sh.hidra.modules.custody.application.service;

import dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductPublicationAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductPublicationAuthorizationContract.*;
import dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.Command;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.Failure;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.Reason;
import dz.sh.hidra.modules.custody.application.port.out.ProductRevisionPublicationRepositoryPort;
import dz.sh.hidra.platform.persistence.*;
import dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract;
import dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract.Need;
import dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract.Role;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Facts;
import dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication;
import dz.sh.hidra.modules.workflow.application.contract.custody.CustodyProtectedProductPublicationApprovalContract;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;

@Service
public class ProtectedProductPublicationAdmissionCoordinator {
    private final CustodyProtectedProductPublicationAuthorizationContract identity;
    private final CustodyProtectedPartyEligibilityContract party;
    private final ProductRevisionPublicationRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    private final CustodyProtectedFluidUnitContract units;
    private final CustodyProtectedProductPublicationApprovalContract workflow;
    public ProtectedProductPublicationAdmissionCoordinator(CustodyProtectedProductPublicationAuthorizationContract identity,
            CustodyProtectedPartyEligibilityContract party,ProductRevisionPublicationRepositoryPort repository,
            ProtectedTransactionExecutor executor,CustodyProtectedFluidUnitContract units,CustodyProtectedProductPublicationApprovalContract workflow) {
        this.identity=Objects.requireNonNull(identity);this.party=Objects.requireNonNull(party);
        this.repository=Objects.requireNonNull(repository);this.executor=Objects.requireNonNull(executor);this.units=Objects.requireNonNull(units);this.workflow=Objects.requireNonNull(workflow);
    }
    @Transactional(propagation=Propagation.NEVER)
    public Prepared prepare(){if(TransactionSynchronizationManager.isActualTransactionActive())throw invalid();return new Prepared(this,identity.prepare());}
    @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Prepared prepared,Command command) {
        try {
            executor.beginAdmission();
            if(prepared==null || prepared.owner!=this || !prepared.used.compareAndSet(false,true)
                    || prepared.thread!=Thread.currentThread() || !TransactionSynchronizationManager.isSynchronizationActive())throw invalid();
            // A read/update has no caller-selected product. Resolve only its company-bound scalar
            // under storage fences; unavailable bindings share the same non-disclosing denial.
            var storage=repository.acquire(command);
            var scope=repository.scope(storage);
            var request=new Request(command.mutating()?Action.valueOf(command.kind().name()):Action.READ,scope.companyPartyId(),scope.catalogProductId());
            var nativeIdentity=identity.acquire(prepared.identity,request);
            var owner=party.acquire(scope.companyPartyId());
            var fences=new ArrayList<ProtectedRelationFence>();
            var fi=identity.finalFences(nativeIdentity);var fp=party.finalFences(owner);var fr=repository.finalFences(storage);
            if(fi.size()!=24 || fp.size()!=2 || fr.size()!=11)throw invalid();
            fences.addAll(fi);fences.addAll(fp);fences.addAll(fr);
            fences=new ArrayList<>(distinct(fences));
            if(fences.size()!=37)throw invalid();
            var admission=new Admission(this,request,nativeIdentity,owner,storage,List.copyOf(fences));
            TransactionSynchronizationManager.registerSynchronization(admission);return admission;
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public Contribution collect(Admission admission) {
        try {
            var a=current(admission);if(!a.used.compareAndSet(false,true))throw invalid();
            var i=identity.contribute(a.identity);
            if(i.companyDecision()!=Decision.PERMIT || i.productDecision()!=Decision.PERMIT)throw new Failure(Reason.DENIED);
            var p=party.read(a.party);
            if(!i.request().equals(a.request) || !p.partyId().equals(a.request.companyPartyId())
                    || p.backendPid()!=i.backendPid() || p.transactionId()!=i.transactionId())throw invalid();
            repository.authorized(a.repository,i.userId());
            ProductRevisionPublication.Policy policy=null;ProductRevisionPublication.Approval approval=null;
            List<ProductRevisionPublication.Unit> snapshots=List.of();
            if(repository.needsEligibility(a.repository)) {
                repository.eligible(a.repository);
                var selection=repository.selection(a.repository);String digest=repository.targetDigest(a.repository);
                a.workflow=workflow.acquire(new CustodyProtectedProductPublicationApprovalContract.Request(a.request.companyPartyId(),
                    selection==null?null:digest,selection==null?null:selection.instanceId(),selection==null?null:selection.taskId(),selection==null?null:selection.actionId()));
                var w=workflow.read(a.workflow);
                if(w.backendPid()!=i.backendPid() || w.transactionId()!=i.transactionId() || w.sampledAt().isBefore(i.sampledAt()) || !w.sampledAt().isBefore(i.validUntil()))throw invalid();
                var wp=w.policy();policy=new ProductRevisionPublication.Policy(wp.definitionId(),wp.definitionVersion(),wp.targetTypeId(),wp.purposeId());
                if(selection!=null){var e=w.approval();if(e==null || !digest.equals(e.targetDigest()))throw invalid();
                    var human=identity.reviewer(a.identity,e.actorId());
                    if(!e.actorId().equals(human.actorId()) || e.actedAt().isAfter(w.sampledAt()))throw invalid();
                    approval=new ProductRevisionPublication.Approval(e.targetDigest(),e.instanceId(),e.taskId(),e.actionId(),e.historyId(),e.actorId(),e.actorDisplayName(),e.actedAt());}
                a.units=units.acquire(needs(repository.facts(a.repository)));var u=units.read(a.units);
                if(u.backendPid()!=i.backendPid() || u.transactionId()!=i.transactionId())throw invalid();
                snapshots=u.units().stream().map(x->new ProductRevisionPublication.Unit(x.need().unitId(),x.need().role().name(),x.code(),x.symbol(),x.dimension(),x.updatedAt()))
                    .sorted(Comparator.comparing(ProductRevisionPublication.Unit::key)).toList();
                var combined=new ArrayList<>(a.fences);combined.addAll(workflow.finalFences(a.workflow));combined.addAll(units.finalFences(a.units));
                a.fences=distinct(combined);if(a.fences.size()!=50)throw invalid();
            }
            a.result=new Contribution(repository.scope(a.repository),p.generation(),i.bindingId(),i.sessionId(),i.userId(),
                    i.sampledAt(),i.validUntil(),i.backendPid(),i.transactionId(),snapshots,policy,approval);
            // This check covers eligibility and sealing; do not reread the same graph twice.
            a.permit=executor.sealAdmission();requireCurrent(a);return a.result;
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    private static List<Need> needs(Facts f) {
        var out=new TreeSet<Need>();
        if(f.properties()!=null)for(var p:f.properties()) {out.add(new Need(Role.valueOf(p.property().name()),p.unitId()));out.add(new Need(Role.ABSOLUTE_PRESSURE,p.absolutePressure().unitId()));out.add(new Need(Role.ABSOLUTE_TEMPERATURE,p.absoluteTemperature().unitId()));}
        if(f.methods()!=null)for(var m:f.methods()){out.add(new Need(Role.ABSOLUTE_PRESSURE,m.absolutePressure().unitId()));out.add(new Need(Role.ABSOLUTE_TEMPERATURE,m.absoluteTemperature().unitId()));}
        return CustodyProtectedFluidUnitContract.exactNeeds(List.copyOf(out));
    }
    private static final Set<String> PUBLICATION_DML_HASHES=Set.of("3c216e23ede48cc7eaf8a7d0fbd69ebb36ec492becfd258d7f2e9716f2abfc7b","e6366971cd3f33ccea87d34e808f76677fe1cd38326f0a4534777a3760211fb7","ec5361350fb1f8ab6b995e51425028d14e87305cefb469185b81d1a8616f0168","2a323fe9ab26838c24ae1f2bfd5cd2279e8b56da0d25c6e507f0300cb2cb4515","37a62c17c65bf88dca6462ff22c73c9c8c21f15bea4fc83435351d2fb071dd50","2a5cdfa1f5a75b72a4172371dce65a1d52189db7007f651feb3c93796535c68c");
    private static String digest(String sql) {
        try{return HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(sql.getBytes(java.nio.charset.StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private static boolean compiledService(){return StackWalker.getInstance().walk(frames->frames.anyMatch(f->f.getClassName().equals(
            "dz.sh.hidra.modules.custody.application.service.ProductRevisionPublicationApplicationService")));}
    @Transactional(propagation=Propagation.MANDATORY)
    public void beginProvisional(Admission admission,Set<String> statements) {
        try {
            var a=current(admission);requireCurrent(a);
            if(!compiledService() || a.request.action()==Action.READ || statements==null || statements.size()!=6
                    || !statements.stream().map(ProtectedProductPublicationAdmissionCoordinator::digest)
                        .collect(java.util.stream.Collectors.toUnmodifiableSet()).equals(PUBLICATION_DML_HASHES))throw invalid();
            executor.beginProvisional(a.permit,statements);
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public void endProvisional(Admission a) {
        try {current(a);if(!compiledService())throw invalid();executor.endProvisional(a.permit);requireCurrent(a);}
        catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    public ProductRevisionPublicationRepositoryPort.Admission repository(Admission a){return current(a).repository;}
    public List<ProtectedRelationFence> finalFences(Admission a){return current(a).fences;}
    // Explicit checks run even on an unbound thread so a rejected reuse poisons its issuer.
    public void requireCurrent(Admission a) {
        try {current(a);if(a.result==null)throw invalid();executor.requireAdmission();identity.requireCurrent(a.identity);if(a.units!=null)units.requireCurrent(a.units);if(a.workflow!=null)workflow.requireCurrent(a.workflow);}
        catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    private Admission current(Admission a) {
        if(a==null || a.owner!=this || a.thread!=Thread.currentThread() || !a.active || a.rejected
                || !TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            if(a!=null && a.owner==this)a.rejected=true;
            throw invalid();
        }
        return a;
    }
    private static Failure invalid(){return new Failure(Reason.UNAVAILABLE);}
    public static final class Prepared {
        private final ProtectedProductPublicationAdmissionCoordinator owner;private final Thread thread=Thread.currentThread();
        private final CustodyProtectedProductPublicationAuthorizationContract.Prepared identity;private final AtomicBoolean used=new AtomicBoolean();
        private Prepared(ProtectedProductPublicationAdmissionCoordinator owner,CustodyProtectedProductPublicationAuthorizationContract.Prepared identity){this.owner=owner;this.identity=identity;}
        @Override public String toString(){return "Prepared[opaque product draft]";}
    }
    public static final class Admission implements TransactionSynchronization {
        private final ProtectedProductPublicationAdmissionCoordinator owner;private final Thread thread=Thread.currentThread();
        private final Request request;private final CustodyProtectedProductPublicationAuthorizationContract.Admission identity;
        private final CustodyProtectedPartyEligibilityContract.Admission party;private final ProductRevisionPublicationRepositoryPort.Admission repository;
        private List<ProtectedRelationFence> fences;private CustodyProtectedProductPublicationApprovalContract.Admission workflow;private CustodyProtectedFluidUnitContract.Admission units;private final AtomicBoolean used=new AtomicBoolean();
        private volatile boolean active=true,rejected;private Contribution result;private ProtectedTransactionExecutor.ProvisionalPermit permit;
        private Admission(ProtectedProductPublicationAdmissionCoordinator owner,Request request,
                CustodyProtectedProductPublicationAuthorizationContract.Admission identity,CustodyProtectedPartyEligibilityContract.Admission party,
                ProductRevisionPublicationRepositoryPort.Admission repository,List<ProtectedRelationFence> fences) {
            this.owner=owner;this.request=request;this.identity=identity;this.party=party;this.repository=repository;this.fences=fences;
        }
        @Override public void suspend(){active=false;rejected=true;}
        @Override public void beforeCommit(boolean readOnly){if(rejected || !active || readOnly || thread!=Thread.currentThread())throw invalid();}
        @Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "Admission[opaque product draft]";}
    }
    public record Contribution(ProductRevisionPublication.Scope scope,long partyGeneration,String bindingId,String sessionId,String userId,
            Instant sampledAt,Instant validUntil,int backendPid,long transactionId,List<ProductRevisionPublication.Unit> units,
            ProductRevisionPublication.Policy policy,ProductRevisionPublication.Approval approval) {public Contribution {units=List.copyOf(units);}}
    private static List<ProtectedRelationFence> distinct(List<ProtectedRelationFence> fences){
        var map=new TreeMap<String,ProtectedRelationFence>();
        for(var f:fences){var prior=map.putIfAbsent(f.relation(),f);if(prior!=null && prior.mode()!=f.mode())throw invalid();}
        return List.copyOf(map.values());
    }
}
