/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedProductDraftAdmissionCoordinator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.service
 *
 * @Description : Provides company-owned catalog-linked product metadata drafts.
 *
 */
package dz.sh.hidra.modules.custody.application.service;

import dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductDraftAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductDraftAuthorizationContract.*;
import dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract;
import dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Command;
import dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Failure;
import dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Reason;
import dz.sh.hidra.modules.custody.application.port.out.ProductEngineeringDraftRepositoryPort;
import dz.sh.hidra.platform.persistence.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;

@Service
public class ProtectedProductDraftAdmissionCoordinator {
    private final CustodyProtectedProductDraftAuthorizationContract identity;
    private final CustodyProtectedPartyEligibilityContract party;
    private final ProductEngineeringDraftRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    public ProtectedProductDraftAdmissionCoordinator(CustodyProtectedProductDraftAuthorizationContract identity,
            CustodyProtectedPartyEligibilityContract party,ProductEngineeringDraftRepositoryPort repository,
            ProtectedTransactionExecutor executor) {
        this.identity=Objects.requireNonNull(identity);this.party=Objects.requireNonNull(party);
        this.repository=Objects.requireNonNull(repository);this.executor=Objects.requireNonNull(executor);
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
            var request=new Request(Action.valueOf(command.kind().name()),command.companyPartyId(),repository.catalogProductId(storage));
            var nativeIdentity=identity.acquire(prepared.identity,request);
            var owner=party.acquire(command.companyPartyId());
            var fences=new ArrayList<ProtectedRelationFence>();
            var fi=identity.finalFences(nativeIdentity);var fp=party.finalFences(owner);var fr=repository.finalFences(storage);
            if(fi.size()!=24 || fp.size()!=2 || fr.size()!=4)throw invalid();
            fences.addAll(fi);fences.addAll(fp);fences.addAll(fr);
            if(fences.stream().map(ProtectedRelationFence::relation).distinct().count()!=30)throw invalid();
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
            repository.eligible(a.repository);identity.requireCurrent(a.identity);
            a.result=new Contribution(a.request,p.generation(),i.bindingId(),i.sessionId(),i.userId(),
                    i.sampledAt(),i.validUntil(),i.backendPid(),i.transactionId());
            a.permit=executor.sealAdmission();requireCurrent(a);return a.result;
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    private static final Set<String> PRODUCT_DML_HASHES=Set.of("17eb3368805ddaedc2d8cfa69fd6e845d9b0d855cea3945bcf69c5c7dee8801f",
        "2b27eab51711aed1b86389fef86cc9ac72c52e45feb4802a5c74da4df7811241");
    private static String digest(String sql) {
        try{return HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(sql.getBytes(java.nio.charset.StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private static boolean compiledService(){return StackWalker.getInstance().walk(frames->frames.anyMatch(f->f.getClassName().equals(
            "dz.sh.hidra.modules.custody.application.service.ProductEngineeringDraftApplicationService")));}
    @Transactional(propagation=Propagation.MANDATORY)
    public void beginProvisional(Admission admission,Set<String> statements) {
        try {
            var a=current(admission);requireCurrent(a);
            if(!compiledService() || a.request.action()==Action.READ || statements==null || statements.size()!=2
                    || !statements.stream().map(ProtectedProductDraftAdmissionCoordinator::digest)
                        .collect(java.util.stream.Collectors.toUnmodifiableSet()).equals(PRODUCT_DML_HASHES))throw invalid();
            executor.beginProvisional(a.permit,statements);
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public void endProvisional(Admission a) {
        try {current(a);if(!compiledService())throw invalid();executor.endProvisional(a.permit);requireCurrent(a);}
        catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    public ProductEngineeringDraftRepositoryPort.Admission repository(Admission a){return current(a).repository;}
    public List<ProtectedRelationFence> finalFences(Admission a){return current(a).fences;}
    // Explicit checks run even on an unbound thread so a rejected reuse poisons its issuer.
    public void requireCurrent(Admission a) {
        try {current(a);if(a.result==null)throw invalid();executor.requireAdmission();identity.requireCurrent(a.identity);}
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
        private final ProtectedProductDraftAdmissionCoordinator owner;private final Thread thread=Thread.currentThread();
        private final CustodyProtectedProductDraftAuthorizationContract.Prepared identity;private final AtomicBoolean used=new AtomicBoolean();
        private Prepared(ProtectedProductDraftAdmissionCoordinator owner,CustodyProtectedProductDraftAuthorizationContract.Prepared identity){this.owner=owner;this.identity=identity;}
        @Override public String toString(){return "Prepared[opaque product draft]";}
    }
    public static final class Admission implements TransactionSynchronization {
        private final ProtectedProductDraftAdmissionCoordinator owner;private final Thread thread=Thread.currentThread();
        private final Request request;private final CustodyProtectedProductDraftAuthorizationContract.Admission identity;
        private final CustodyProtectedPartyEligibilityContract.Admission party;private final ProductEngineeringDraftRepositoryPort.Admission repository;
        private final List<ProtectedRelationFence> fences;private final AtomicBoolean used=new AtomicBoolean();
        private volatile boolean active=true,rejected;private Contribution result;private ProtectedTransactionExecutor.ProvisionalPermit permit;
        private Admission(ProtectedProductDraftAdmissionCoordinator owner,Request request,
                CustodyProtectedProductDraftAuthorizationContract.Admission identity,CustodyProtectedPartyEligibilityContract.Admission party,
                ProductEngineeringDraftRepositoryPort.Admission repository,List<ProtectedRelationFence> fences) {
            this.owner=owner;this.request=request;this.identity=identity;this.party=party;this.repository=repository;this.fences=fences;
        }
        @Override public void suspend(){active=false;rejected=true;}
        @Override public void beforeCommit(boolean readOnly){if(rejected || !active || readOnly || thread!=Thread.currentThread())throw invalid();}
        @Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "Admission[opaque product draft]";}
    }
    public record Contribution(Request request,long partyGeneration,String bindingId,String sessionId,String userId,
            Instant sampledAt,Instant validUntil,int backendPid,long transactionId) {}
}
