/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedSuppliedFluidPropertyAdmissionCoordinator
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

import dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedFluidPropertyAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedFluidPropertyAuthorizationContract.*;
import dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract;
import dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Command;
import dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Failure;
import dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.Reason;
import dz.sh.hidra.modules.custody.application.port.out.SuppliedFluidPropertyRevisionRepositoryPort;
import dz.sh.hidra.platform.persistence.*;
import dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract;
import dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract.Need;
import dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract.Role;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Facts;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;

@Service
public class ProtectedSuppliedFluidPropertyAdmissionCoordinator {
    private final CustodyProtectedFluidPropertyAuthorizationContract identity;
    private final CustodyProtectedPartyEligibilityContract party;
    private final SuppliedFluidPropertyRevisionRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    private final CustodyProtectedFluidUnitContract units;
    public ProtectedSuppliedFluidPropertyAdmissionCoordinator(CustodyProtectedFluidPropertyAuthorizationContract identity,
            CustodyProtectedPartyEligibilityContract party,SuppliedFluidPropertyRevisionRepositoryPort repository,
            ProtectedTransactionExecutor executor,CustodyProtectedFluidUnitContract units) {
        this.identity=Objects.requireNonNull(identity);this.party=Objects.requireNonNull(party);
        this.repository=Objects.requireNonNull(repository);this.executor=Objects.requireNonNull(executor);this.units=Objects.requireNonNull(units);
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
            if(fi.size()!=24 || fp.size()!=2 || fr.size()!=6)throw invalid();
            fences.addAll(fi);fences.addAll(fp);fences.addAll(fr);
            if(fences.stream().map(ProtectedRelationFence::relation).distinct().count()!=32)throw invalid();
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
            repository.eligible(a.repository);
            a.units=units.acquire(needs(repository.facts(a.repository)));var u=units.read(a.units);
            if(u.backendPid()!=i.backendPid() || u.transactionId()!=i.transactionId())throw invalid();
            var fu=units.finalFences(a.units);if(fu.size()!=2)throw invalid();
            var combined=new ArrayList<>(a.fences);combined.addAll(fu);
            if(combined.size()!=34 || combined.stream().map(ProtectedRelationFence::relation).distinct().count()!=34)throw invalid();a.fences=List.copyOf(combined);
            var snapshots=u.units().stream().map(x->new UnitSnapshot(x.need().unitId(),x.need().role().name(),x.code(),x.symbol(),x.dimension(),x.updatedAt())).toList();
            a.result=new Contribution(a.request,p.generation(),i.bindingId(),i.sessionId(),i.userId(),
                    i.sampledAt(),i.validUntil(),i.backendPid(),i.transactionId(),snapshots);
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
    private static final Set<String> FLUID_DML_HASHES=Set.of("1bb18e11e43f0b770a4661f1ea36117f68ff7c7bfcc6dbb2e37b6a3975644eeb","316a9738f278d5c72e0f457040fe69c68ca96049b2917a57ab24e4e603ad48f2");
    private static String digest(String sql) {
        try{return HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(sql.getBytes(java.nio.charset.StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private static boolean compiledService(){return StackWalker.getInstance().walk(frames->frames.anyMatch(f->f.getClassName().equals(
            "dz.sh.hidra.modules.custody.application.service.SuppliedFluidPropertyRevisionApplicationService")));}
    @Transactional(propagation=Propagation.MANDATORY)
    public void beginProvisional(Admission admission,Set<String> statements) {
        try {
            var a=current(admission);requireCurrent(a);
            if(!compiledService() || a.request.action()==Action.READ || statements==null || statements.size()!=2
                    || !statements.stream().map(ProtectedSuppliedFluidPropertyAdmissionCoordinator::digest)
                        .collect(java.util.stream.Collectors.toUnmodifiableSet()).equals(FLUID_DML_HASHES))throw invalid();
            executor.beginProvisional(a.permit,statements);
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public void endProvisional(Admission a) {
        try {current(a);if(!compiledService())throw invalid();executor.endProvisional(a.permit);requireCurrent(a);}
        catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    public SuppliedFluidPropertyRevisionRepositoryPort.Admission repository(Admission a){return current(a).repository;}
    public List<ProtectedRelationFence> finalFences(Admission a){return current(a).fences;}
    // Explicit checks run even on an unbound thread so a rejected reuse poisons its issuer.
    public void requireCurrent(Admission a) {
        try {current(a);if(a.result==null)throw invalid();executor.requireAdmission();identity.requireCurrent(a.identity);units.requireCurrent(a.units);}
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
        private final ProtectedSuppliedFluidPropertyAdmissionCoordinator owner;private final Thread thread=Thread.currentThread();
        private final CustodyProtectedFluidPropertyAuthorizationContract.Prepared identity;private final AtomicBoolean used=new AtomicBoolean();
        private Prepared(ProtectedSuppliedFluidPropertyAdmissionCoordinator owner,CustodyProtectedFluidPropertyAuthorizationContract.Prepared identity){this.owner=owner;this.identity=identity;}
        @Override public String toString(){return "Prepared[opaque product draft]";}
    }
    public static final class Admission implements TransactionSynchronization {
        private final ProtectedSuppliedFluidPropertyAdmissionCoordinator owner;private final Thread thread=Thread.currentThread();
        private final Request request;private final CustodyProtectedFluidPropertyAuthorizationContract.Admission identity;
        private final CustodyProtectedPartyEligibilityContract.Admission party;private final SuppliedFluidPropertyRevisionRepositoryPort.Admission repository;
        private List<ProtectedRelationFence> fences;private CustodyProtectedFluidUnitContract.Admission units;private final AtomicBoolean used=new AtomicBoolean();
        private volatile boolean active=true,rejected;private Contribution result;private ProtectedTransactionExecutor.ProvisionalPermit permit;
        private Admission(ProtectedSuppliedFluidPropertyAdmissionCoordinator owner,Request request,
                CustodyProtectedFluidPropertyAuthorizationContract.Admission identity,CustodyProtectedPartyEligibilityContract.Admission party,
                SuppliedFluidPropertyRevisionRepositoryPort.Admission repository,List<ProtectedRelationFence> fences) {
            this.owner=owner;this.request=request;this.identity=identity;this.party=party;this.repository=repository;this.fences=fences;
        }
        @Override public void suspend(){active=false;rejected=true;}
        @Override public void beforeCommit(boolean readOnly){if(rejected || !active || readOnly || thread!=Thread.currentThread())throw invalid();}
        @Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "Admission[opaque product draft]";}
    }
    public record Contribution(Request request,long partyGeneration,String bindingId,String sessionId,String userId,
            Instant sampledAt,Instant validUntil,int backendPid,long transactionId,List<UnitSnapshot> units) {public Contribution {units=List.copyOf(units);}}
    public record UnitSnapshot(String unitId,String role,String code,String symbol,String dimension,Instant updatedAt) {}
}
