/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationAdmissionCoordinator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Coordinates ordered protected owner admission before the common Identity sample.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Request;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Operation;
import dz.sh.hidra.modules.party.application.contract.topology.TopologyProtectedPartyEligibilityContract;
import dz.sh.hidra.modules.topology.application.contract.station.StationProtectedFacilityOwnershipContract;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/** Internal contribution only; publication authority and final owner predicates belong to L. */
@Service
public class ProtectedStationAdmissionCoordinator {
    private final TopologyProtectedAuthorizationContract identity;
    private final TopologyProtectedPartyEligibilityContract party;
    private final StationProtectedFacilityOwnershipContract facility;
    private final ProtectedTransactionExecutor executor;

    public ProtectedStationAdmissionCoordinator(TopologyProtectedAuthorizationContract identity,
        TopologyProtectedPartyEligibilityContract party,StationProtectedFacilityOwnershipContract facility,
        ProtectedTransactionExecutor executor) {
        this.identity=Objects.requireNonNull(identity);this.party=Objects.requireNonNull(party);
        this.facility=Objects.requireNonNull(facility);this.executor=Objects.requireNonNull(executor);
    }

    @Transactional(propagation=Propagation.NEVER)
    public Prepared prepare() {
        if(TransactionSynchronizationManager.isActualTransactionActive()) throw invalid();
        return new Prepared(this,identity.prepare());
    }

    @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Prepared prepared,Request request) {
        try {
            executor.beginAdmission();
            if(prepared==null || !prepared.used.compareAndSet(false,true) || prepared.owner!=this
                || prepared.thread!=Thread.currentThread() || request==null
                || request.companyPartyId().length()>80 || request.facilityId().length()>80
                || !TransactionSynchronizationManager.isSynchronizationActive()) throw invalid();
            var i=identity.acquire(prepared.identity,request);
            var p=party.acquire(request.companyPartyId());
            var f=facility.acquire(request.facilityId());
            var identityFences=identity.finalFences(i);
            var partyFences=party.finalFences(p);
            var facilityFences=facility.finalFences(f);
            if(identityFences==null || identityFences.size()!=24 || partyFences==null || partyFences.size()!=2
                || facilityFences==null || facilityFences.size()!=2)throw invalid();
            var fences=new java.util.ArrayList<dz.sh.hidra.platform.persistence.ProtectedRelationFence>();
            fences.addAll(identityFences);fences.addAll(partyFences);fences.addAll(facilityFences);
            if(fences.stream().map(dz.sh.hidra.platform.persistence.ProtectedRelationFence::relation).distinct().count()!=28)throw invalid();
            var admission=new Admission(this,request,i,p,f,java.util.List.copyOf(fences));
            TransactionSynchronizationManager.registerSynchronization(admission);
            return admission;
        } catch(RuntimeException failure) { executor.rejectAdmission();throw failure; }
    }

    @Transactional(propagation=Propagation.MANDATORY)
    public Contribution collect(Admission admission) {
        try {
            var a=current(admission);
            if(!a.used.compareAndSet(false,true)) throw invalid();
            var i=identity.contribute(a.identity);
            var p=party.read(a.party);
            var f=facility.read(a.facility);
            if(i==null || p==null || f==null || i.combinedDecision()!=TopologyProtectedAuthorizationContract.Decision.PERMIT
                || i.companyDecision()!=TopologyProtectedAuthorizationContract.Decision.PERMIT
                || i.facilityDecision()!=TopologyProtectedAuthorizationContract.Decision.PERMIT
                || i.operation()!=a.request.operation() || !i.companyPartyId().equals(a.request.companyPartyId())
                || !i.facilityId().equals(a.request.facilityId()) || !p.partyId().equals(a.request.companyPartyId())
                || !f.facilityId().equals(a.request.facilityId()) || !f.ownerPartyId().equals(p.partyId())
                || p.backendPid()!=i.backendPid() || f.backendPid()!=i.backendPid()
                || p.transactionId()!=i.transactionId() || f.transactionId()!=i.transactionId()) throw invalid();
            identity.requireCurrent(a.identity);
            current(a);
            a.result=new Contribution(i.operation(),p.partyId(),f.facilityId(),p.generation(),f.generation(),
                i.bindingId(),i.sessionId(),i.userId(),i.sampledAt(),i.validUntil(),i.backendPid(),i.transactionId());
            a.provisional=executor.sealAdmission();
            requireCurrent(a);
            return a.result;
        } catch(RuntimeException failure) {
            if(admission!=null) admission.active=false;
            executor.rejectAdmission();throw failure;
        }
    }

    private static final java.util.Set<String> STATION_GRAPH_DML_HASHES=java.util.Set.of(
        "1da26cb5a0a20932ad80d47f5564fb3b2d55a9bfb9f47436105b1bdf8a86e98f",
        "e3380cc4991b667311a47f4723d055feadf9bbb75c199b30518381d99cef0ec2",
        "269e222f46502b45c777ec281f24148cb68a12d5f533e10c5d6a23280274c1fa",
        "6208166015b18ca53fbb356823ec24217093bc1f241f25959a438bf86bbcd388");
    private static String digest(String sql) {
        try {
            return java.util.HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256")
                    .digest(sql.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        } catch(java.security.NoSuchAlgorithmException absent) {
            throw new IllegalStateException("SHA-256 unavailable",absent);
        }
    }
    private static boolean reviewed(java.util.Set<String> sql) {
        return sql!=null && sql.size()==STATION_GRAPH_DML_HASHES.size()
            && sql.stream().allMatch(java.util.Objects::nonNull)
            && sql.stream().map(ProtectedStationAdmissionCoordinator::digest)
                    .collect(java.util.stream.Collectors.toUnmodifiableSet()).equals(STATION_GRAPH_DML_HASHES);
    }
    private static boolean compiledOperationCaller() {
        return StackWalker.getInstance().walk(frames->frames.anyMatch(f->f.getClassName().equals(
            "dz.sh.hidra.modules.topology.application.service.ProtectedStationGraphOperationService")));
    }
    /** Only the original K coordinator may open this issue-bound, single-use provisional transition. */
    @Transactional(propagation=Propagation.MANDATORY)
    public void beginProvisional(Admission admission,java.util.Set<String> exactStatements) {
        try {
            requireCurrent(admission);
            if(!compiledOperationCaller() || !reviewed(exactStatements)
                || !java.util.EnumSet.of(
                    Operation.AUTHOR,Operation.PUBLISH,Operation.WITHDRAW,Operation.REACTIVATE)
                    .contains(admission.request.operation())) throw invalid();
            executor.beginProvisional(admission.provisional,exactStatements);
        } catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public void endProvisional(Admission admission) {
        try {
            if(current(admission).result==null || !compiledOperationCaller())throw invalid();
            executor.endProvisional(admission.provisional);
            requireCurrent(admission);
        } catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }

    @Transactional(propagation=Propagation.MANDATORY)
    public void requireCurrent(Admission admission) {
        try {
            var a=current(admission);
            if(a.result==null) throw invalid();
            executor.requireAdmission();identity.requireCurrent(a.identity);
        } catch(RuntimeException failure) {
            if(admission!=null) admission.active=false;
            executor.rejectAdmission();throw failure;
        }
    }

    /** Frozen owner requirements collected before the eligibility sample, never caller authority. */
    public java.util.List<dz.sh.hidra.platform.persistence.ProtectedRelationFence> finalFences(Admission admission) {
        return current(admission).fences;
    }

    private Admission current(Admission a) {
        if(a==null || a.owner!=this || a.thread!=Thread.currentThread() || !a.active
            || !TransactionSynchronizationManager.isActualTransactionActive()
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly()) throw invalid();
        return a;
    }
    private static IllegalStateException invalid() { return new IllegalStateException("Protected station admission unavailable"); }

    public static final class Prepared {
        private final ProtectedStationAdmissionCoordinator owner;
        private final Thread thread=Thread.currentThread();
        private final TopologyProtectedAuthorizationContract.Prepared identity;
        private final AtomicBoolean used=new AtomicBoolean();
        private Prepared(ProtectedStationAdmissionCoordinator owner,TopologyProtectedAuthorizationContract.Prepared identity) {
            this.owner=owner;this.identity=identity;
        }
        @Override public String toString() { return "Prepared[opaque station admission]"; }
    }
    public static final class Admission implements TransactionSynchronization {
        private final ProtectedStationAdmissionCoordinator owner;
        private final Thread thread=Thread.currentThread();
        private final Request request;
        private final TopologyProtectedAuthorizationContract.Admission identity;
        private final TopologyProtectedPartyEligibilityContract.Admission party;
        private final StationProtectedFacilityOwnershipContract.Admission facility;
        private final java.util.List<dz.sh.hidra.platform.persistence.ProtectedRelationFence> fences;
        private final AtomicBoolean used=new AtomicBoolean();
        private volatile boolean active=true;
        private Contribution result;
        private ProtectedTransactionExecutor.ProvisionalPermit provisional;
        private Admission(ProtectedStationAdmissionCoordinator owner,Request request,
            TopologyProtectedAuthorizationContract.Admission identity,TopologyProtectedPartyEligibilityContract.Admission party,
            StationProtectedFacilityOwnershipContract.Admission facility,java.util.List<dz.sh.hidra.platform.persistence.ProtectedRelationFence> fences) {
            this.owner=owner;this.request=request;this.identity=identity;this.party=party;this.facility=facility;this.fences=fences;
        }
        @Override public void suspend() { active=false; }
        @Override public void afterCompletion(int status) { active=false; }
        @Override public String toString() { return "Admission[opaque station owners]"; }
    }
    /** Scalar observation, never an operation permission or transferable credential. */
    public record Contribution(Operation operation,String companyPartyId,String facilityId,
        long partyGeneration,long facilityGeneration,String bindingId,String sessionId,String userId,
        Instant sampledAt,Instant validUntil,int backendPid,long transactionId) {
        public Contribution {
            Objects.requireNonNull(operation);Objects.requireNonNull(sampledAt);Objects.requireNonNull(validUntil);
            for(String value:new String[]{companyPartyId,facilityId,bindingId,sessionId,userId})
                if(value==null || value.isBlank() || !value.equals(value.trim())) throw new IllegalArgumentException("Invalid owner reference");
            if(partyGeneration<=0 || facilityGeneration<=0 || backendPid<=0 || transactionId<=0 || !sampledAt.isBefore(validUntil))
                throw new IllegalArgumentException("Invalid owner observation");
        }
        @Override public String toString() { return "Contribution[station owner scalars]"; }
    }
}
