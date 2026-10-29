/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedInstalledUnitAdmissionCoordinator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements the protected installed-unit lifecycle within its owning boundary.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract;
import dz.sh.hidra.modules.party.application.contract.topology.TopologyInstalledUnitCompanyEvidenceContract;
import dz.sh.hidra.modules.party.application.contract.topology.TopologyInstalledUnitManufacturerEvidenceContract;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.*;
import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.time.Instant;
import dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure;
import dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure.Kind;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.support.*;

/** Trusted compiled owner: one admission, one mutation window and one final SQL guard. */
@Service
public class ProtectedInstalledUnitAdmissionCoordinator {
    private final TopologyInstalledUnitAuthorizationContract identity;
    private final TopologyInstalledUnitCompanyEvidenceContract company;
    private final TopologyInstalledUnitManufacturerEvidenceContract manufacturer;
    private final InstalledUnitPositionOwnershipPort positions;
    private final ProtectedInstalledUnitRepositoryPort storage;
    private final ProtectedTransactionExecutor executor;
    private static final Set<String> DML_HASHES=Set.of("98d9e2d0cd9de0a5c85161d0bb465f4ab310a0ebd6208036eeb26572b3c35f46","13de71cd409afd07b736979dad5ad802324a4a40718dd7f411229492c81cb716","d55fab38a80ea7a81636dceb7d3af50c6a8e1c14410b58e1e8ff25a0557ae278","a10ecf5d21bc1a8efcdf7a7cbbf5e23104309f243b223a93c997a61d880d5bd7","1503d363ee40899cad0081f42e84f728d974409236ebc5fe96e631e571330495");
    public ProtectedInstalledUnitAdmissionCoordinator(TopologyInstalledUnitAuthorizationContract identity,
            TopologyInstalledUnitCompanyEvidenceContract company,TopologyInstalledUnitManufacturerEvidenceContract manufacturer,
            InstalledUnitPositionOwnershipPort positions,ProtectedInstalledUnitRepositoryPort storage,ProtectedTransactionExecutor executor) {
        this.identity=Objects.requireNonNull(identity);this.company=Objects.requireNonNull(company);
        this.manufacturer=Objects.requireNonNull(manufacturer);this.positions=Objects.requireNonNull(positions);
        this.storage=Objects.requireNonNull(storage);this.executor=Objects.requireNonNull(executor);
    }
    public Prepared prepare(Command command) {
        if(TransactionSynchronizationManager.isActualTransactionActive() || TransactionSynchronizationManager.isSynchronizationActive())throw denied();
        return new Prepared(this,Objects.requireNonNull(command),identity.prepare());
    }
    private static TopologyInstalledUnitAuthorizationContract.Request request(Command c) {
        return new TopologyInstalledUnitAuthorizationContract.Request(
            TopologyInstalledUnitAuthorizationContract.Operation.valueOf(c.operation().name()),c.companyPartyId(),c.unitId(),
            c.installationId(),c.replacementInstallationId(),c.source()==null?null:c.source().facilityId(),
            c.destination()==null?null:c.destination().facilityId(),c.replacementUnitId());
    }
    public Admission acquire(Prepared prepared,Command command) {
        Admission a=null;
        try {
            executor.beginAdmission();
            if(prepared==null || prepared.owner!=this || prepared.thread!=Thread.currentThread() || !prepared.used.compareAndSet(false,true)
                || !prepared.command.equals(command) || !TransactionSynchronizationManager.isActualTransactionActive()
                || !TransactionSynchronizationManager.isSynchronizationActive())throw denied();
            var nativeTicket=identity.acquire(prepared.identity,request(command));
            var hint=storage.discover(command);
            InstalledUnit unit=hint.unit();
            if(command.operation()==Operation.REGISTER_UNIT) {
                if(unit!=null && !unit.equals(command.registration()))throw denied();unit=command.registration();
            }
            if(unit==null || !unit.id().equals(command.unitId()) || !unit.companyPartyId().equals(command.companyPartyId()))throw denied();
            dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.exact(unit.equipmentKindId());
            var companyTicket=company.acquire(command.companyPartyId());
            var units=new ArrayList<InstalledUnit>();units.add(unit);
            if(command.operation()==Operation.REPLACE) {
                var second=hint.replacementUnit();
                if(second==null || !second.id().equals(command.replacementUnitId())
                    || !second.companyPartyId().equals(command.companyPartyId()) || !second.equipmentKindId().equals(unit.equipmentKindId()))throw denied();
                units.add(second);
            }else if(hint.replacementUnit()!=null)throw denied();
            var manufacturers=new TreeMap<String,TopologyInstalledUnitManufacturerEvidenceContract.Admission>();
            var spares=new TreeMap<String,TopologyInstalledUnitManufacturerEvidenceContract.Admission>();
            for(String id:units.stream().filter(u->u.manufacturerParty().isKnown()).map(u->u.manufacturerParty().value()).distinct().sorted().toList()) {
                manufacturers.put(id,manufacturer.acquire(id));spares.put(id,manufacturer.acquire(id));
            }
            var descriptors=new TreeMap<String,Position>();
            for(var p:new Position[]{command.source(),command.destination()})if(p!=null) {
                var prior=descriptors.putIfAbsent(p.positionId(),p);if(prior!=null && !prior.equals(p))throw denied();
            }
            var positionTickets=new LinkedHashMap<String,InstalledUnitPositionOwnershipPort.Admission>();
            for(var p:descriptors.values())positionTickets.put(p.positionId(),positions.acquire(p.positionId()));
            var repository=storage.acquire(command,hint,expectedFences(!positionTickets.isEmpty()));
            a=new Admission(this,command,unit,hint.replacementUnit(),nativeTicket,companyTicket,manufacturers,spares,positionTickets,repository);
            TransactionSynchronizationManager.registerSynchronization(a);
            a.company=company.read(companyTicket);
            for(var e:manufacturers.entrySet())a.manufacturers.put(e.getKey(),manufacturer.read(e.getValue()));
            for(var p:descriptors.values()) {
                var actual=positions.read(positionTickets.get(p.positionId()));
                if(!actual.positionId().equals(p.positionId()) || !actual.facilityId().equals(p.facilityId())
                    || !actual.companyPartyId().equals(command.companyPartyId()) || !actual.equipmentTypeId().equals(unit.equipmentKindId()))throw denied();
                a.positionFacts.add(actual);
            }
            a.nativeFact=identity.contribute(nativeTicket);
            var n=a.nativeFact;
            if(!n.request().equals(request(command)) || n.overallDecision()!=TopologyInstalledUnitAuthorizationContract.Decision.PERMIT
                || !a.company.companyPartyId().equals(command.companyPartyId()) || a.company.backendPid()!=n.backendPid()
                || a.company.transactionId()!=n.transactionId())throw denied();
            for(var p:a.positionFacts)if(p.backendPid()!=n.backendPid() || p.transactionId()!=n.transactionId()
                || !p.databaseRole().equals(a.company.databaseRole()))throw denied();
            Instant until=n.validUntil();
            for(var e:a.manufacturers.entrySet()) {
                var f=e.getValue();
                if(!f.manufacturerPartyId().equals(e.getKey()) || f.backendPid()!=n.backendPid()
                    || f.transactionId()!=n.transactionId() || f.validFrom().isAfter(n.sampledAt()))throw denied();
                if(f.validTo()!=null && f.validTo().isBefore(until))until=f.validTo();
            }
            if(!n.sampledAt().isBefore(until))throw denied();
            a.origin=new ProtectedInstalledUnitRepositoryPort.Origin(n.userId(),n.bindingId(),n.sessionId(),n.sampledAt(),until,n.backendPid(),n.transactionId());
            a.fences=union(identity.finalFences(nativeTicket),company.finalFences(companyTicket),
                manufacturers.values().stream().flatMap(t->manufacturer.finalFences(t).stream()).toList(),positionTickets.values().stream().flatMap(t->positions.finalFences(t).stream()).toList(),storage.finalFences(repository));
            if(!a.fences.equals(expectedFences(!positionTickets.isEmpty())))throw denied();
            a.initial=storage.observe(repository);a.permit=executor.sealAdmission();return a;
        }catch(RuntimeException failure){reject(a);throw denial(failure);}
    }
    @SafeVarargs private static List<ProtectedRelationFence> union(List<ProtectedRelationFence>... groups) {
        var result=new TreeMap<String,ProtectedRelationFence.Mode>();
        for(var group:groups)for(var f:group)result.merge(f.relation(),f.mode(),(x,y)->rank(x)>=rank(y)?x:y);
        return result.entrySet().stream().map(e->new ProtectedRelationFence(e.getKey(),e.getValue())).toList();
    }
    private static int rank(ProtectedRelationFence.Mode m){return switch(m){case ACCESS_SHARE->0;case SHARE->1;case SHARE_ROW_EXCLUSIVE->2;};}
    private static List<ProtectedRelationFence> expectedFences(boolean placed) {
        var result=new ArrayList<ProtectedRelationFence>();
        for(String suffix:List.of("attribute_definition","authorization_policy","authorization_policy_rule","authorization_policy_version","external_group_mapping","external_identity","external_permission_mapping","external_role_mapping","group","group_role_grant","local_credential","login_session","operation_binding","permission","provider","role","role_permission_grant","subject_security_attribute","user","user_group_membership","user_permission_grant","user_role_grant"))result.add(new ProtectedRelationFence("public.hidra_identity_"+suffix,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
        result.add(new ProtectedRelationFence("public.hidra_identity_external_mapping_generation",ProtectedRelationFence.Mode.ACCESS_SHARE));
        result.add(new ProtectedRelationFence("public.hidra_identity_bound_source_claim_evidence",ProtectedRelationFence.Mode.SHARE));
        for(String suffix:List.of("party","role","role_assignment","manufacturer_profile"))result.add(new ProtectedRelationFence("public.hidra_party_"+suffix,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
        result.add(new ProtectedRelationFence("public.hidra_party_eligibility_generation",ProtectedRelationFence.Mode.ACCESS_SHARE));
        if(placed) {
            result.add(new ProtectedRelationFence("public.hidra_topology_equipment",ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
            result.add(new ProtectedRelationFence("public.hidra_topology_facility",ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
            result.add(new ProtectedRelationFence("public.hidra_topology_facility_owner_generation",ProtectedRelationFence.Mode.ACCESS_SHARE));
        }
        for(String suffix:List.of("installed_unit","installed_unit_installation","installed_unit_installation_event","installed_unit_operation_request"))result.add(new ProtectedRelationFence("public.hidra_topology_"+suffix,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
        result.add(new ProtectedRelationFence("public.hidra_topology_equipment_type",ProtectedRelationFence.Mode.ACCESS_SHARE));
        return union(result);
    }
    private Admission current(Admission a) {
        if(a==null || a.owner!=this || !a.active || a.thread!=Thread.currentThread()
            || !TransactionSynchronizationManager.isActualTransactionActive() || !a.resources.equals(TransactionSynchronizationManager.getResourceMap()))throw denied();
        return a;
    }
    private void requireCurrent(Admission a) { requireCurrent(a,false); }
    private void requireCurrent(Admission a,boolean flushing) {
        current(a);if(flushing)executor.requireInstalledUnitFinalPreparation();else executor.requireAdmission();identity.requireCurrent(a.identity);company.requireCurrent(a.companyTicket);
        for(var p:a.positions.values())positions.requireCurrent(p);
    }
    public Result perform(Admission admission) {
        try {
            var a=current(admission);requireCurrent(a);if(!a.used.compareAndSet(false,true))throw denied();
            var s=a.initial;var c=a.command;
            if(c.source()!=null)requireSource(a,s.source());
            if(s.receiptDigest()!=null) {
                if(!c.mutation() || !s.receiptDigest().equals(storage.commandDigest(c)) || s.receiptResult()==null)throw denied();
                a.result=s.receiptResult().replay();storage.verifyResult(a.repository,a.result,true);return a.result;
            }
            InstalledUnitInstallation source=s.source(),next=null;var history=new ArrayList<>(s.history());
            switch(c.operation()) {
                case REGISTER_UNIT,READ_UNIT -> {}
                case RECORD_INSTALLATION -> {
                    if(source!=null)throw denied();source=new InstalledUnitInstallation(c.installationId(),c.unitId(),c.destination().positionId(),c.companyPartyId(),
                        c.effectiveFrom(),c.effectiveUntil(),a.origin.sampledAt(),c.evidenceReference(),1);history.add(source);
                }
                case CLOSE_INSTALLATION,RELOCATE,REPLACE -> {
                    requireSource(a,source);
                    if(source.effectiveUntil()!=null || source.revision()!=c.expectedRevision())throw new InstalledUnitLifecycleFailure(Kind.CONFLICT,null);
                    if(!c.effectiveUntil().isAfter(source.effectiveFrom()))throw new InstalledUnitLifecycleFailure(Kind.INVALID,null);
                    source=new InstalledUnitInstallation(source.installationId(),source.installedUnitId(),source.equipmentPositionId(),source.companyPartyId(),
                        source.effectiveFrom(),c.effectiveUntil(),source.recordedAt(),source.evidenceReference(),source.revision()+1);history.add(source);
                    if(c.operation()==Operation.RELOCATE || c.operation()==Operation.REPLACE) {
                        if(s.replacement()!=null)throw new InstalledUnitLifecycleFailure(Kind.CONFLICT,null);
                        next=new InstalledUnitInstallation(c.replacementInstallationId(),c.operation()==Operation.REPLACE?c.replacementUnitId():c.unitId(),c.destination().positionId(),c.companyPartyId(),
                            c.effectiveFrom(),null,a.origin.sampledAt(),c.evidenceReference(),1);
                    }
                }
                case READ_INSTALLATION,READ_HISTORY -> requireSource(a,source);
            }
            a.result=new Result(a.unit,source,next,c.operation()==Operation.READ_HISTORY||c.mutation()?history:List.of(),false,a.replacementUnit);
            if(c.mutation()) {
                var sql=storage.mutationSql();var hashes=sql.stream().map(ProtectedInstalledUnitAdmissionCoordinator::digest).collect(java.util.stream.Collectors.toSet());
                if(sql.size()!=DML_HASHES.size() || !hashes.equals(DML_HASHES))throw denied();
                executor.beginProvisional(a.permit,sql);storage.apply(a.repository,a.result,a.origin);executor.endProvisional(a.permit);
            }
            // Only pinned owned-storage DML intervened; source fences remain held. Full owner
            // revalidation follows the managed flush, before the sole final statement.
            storage.verifyResult(a.repository,a.result,false);current(a);return a.result;
        }catch(RuntimeException failure){reject(admission);throw denial(failure);}
    }
    private static void requireSource(Admission a,InstalledUnitInstallation source) {
        if(source==null)throw new InstalledUnitLifecycleFailure(Kind.NOT_FOUND,null);
        if(!source.installedUnitId().equals(a.command.unitId()) || !source.companyPartyId().equals(a.command.companyPartyId())
            || !source.equipmentPositionId().equals(a.command.source().positionId()))throw denied();
    }
    public void prepareFinal(Admission admission) {
        try {
            var a=current(admission);if(!a.used.get() || a.result==null || a.frozen)throw denied();requireCurrent(a,true);
            for(var e:a.spareManufacturers.entrySet()) {
                var old=a.manufacturers.get(e.getKey());var fresh=manufacturer.read(e.getValue());
                if(!old.manufacturerPartyId().equals(fresh.manufacturerPartyId()) || old.partyGeneration()!=fresh.partyGeneration()
                    || !old.roleCode().equals(fresh.roleCode()) || !old.roleAssignmentId().equals(fresh.roleAssignmentId())
                    || !old.validFrom().equals(fresh.validFrom()) || !Objects.equals(old.validTo(),fresh.validTo())
                    || old.qualificationRequired()!=fresh.qualificationRequired() || !old.manufacturerCapability().equals(fresh.manufacturerCapability())
                    || !old.qualificationStatus().equals(fresh.qualificationStatus()) || old.backendPid()!=fresh.backendPid() || old.transactionId()!=fresh.transactionId())throw denied();
                manufacturer.finalFences(e.getValue());
            }
            storage.verifyResult(a.repository,a.result,a.result.replayed());storage.freeze(a.repository,a.origin,a.fences);a.frozen=true;
        }catch(RuntimeException failure){reject(admission);throw denial(failure);}
    }
    public boolean finalGuard(Admission admission) {
        try{var a=current(admission);if(!a.frozen || a.guarded)throw denied();a.guarded=storage.finalGuard(a.repository);return a.guarded;}
        catch(RuntimeException failure){reject(admission);throw denial(failure);}
    }
    private static String digest(String text) {
        try{return HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(text.getBytes(java.nio.charset.StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private void reject(Admission a) {
        if(a!=null){a.active=false;storage.reject(a.repository);}executor.rejectAdmission();
    }
    private static AccessDeniedException denied(){return new AccessDeniedException("Installed-unit operation unavailable");}
    private static RuntimeException denial(RuntimeException cause) {
        if(cause instanceof InstalledUnitLifecycleFailure)return cause;
        // Persistence translation wraps owner IllegalStateException subtypes; keep their exact meaning.
        if(cause instanceof org.springframework.dao.DataAccessException
            && cause.getCause() instanceof InstalledUnitLifecycleFailure typed)
            return new InstalledUnitLifecycleFailure(typed.kind(),cause);
        return new InstalledUnitLifecycleFailure(cause instanceof AccessDeniedException?Kind.DENIED:Kind.UNAVAILABLE,cause);
    }
    public static final class Prepared {
        private final ProtectedInstalledUnitAdmissionCoordinator owner;private final Command command;
        private final TopologyInstalledUnitAuthorizationContract.Prepared identity;private final Thread thread=Thread.currentThread();
        private final AtomicBoolean used=new AtomicBoolean();
        private Prepared(ProtectedInstalledUnitAdmissionCoordinator owner,Command command,TopologyInstalledUnitAuthorizationContract.Prepared identity){this.owner=owner;this.command=command;this.identity=identity;}
    }
    public static final class Admission implements TransactionSynchronization {
        private final ProtectedInstalledUnitAdmissionCoordinator owner;private final Command command;private final InstalledUnit unit,replacementUnit;
        private final TopologyInstalledUnitAuthorizationContract.Admission identity;
        private final TopologyInstalledUnitCompanyEvidenceContract.Admission companyTicket;
        private final Map<String,TopologyInstalledUnitManufacturerEvidenceContract.Admission> manufacturerTickets,spareManufacturers;
        private final Map<String,InstalledUnitPositionOwnershipPort.Admission> positions;
        private final ProtectedInstalledUnitRepositoryPort.Admission repository;
        private final Map<Object,Object> resources=Map.copyOf(TransactionSynchronizationManager.getResourceMap());
        private final Thread thread=Thread.currentThread();private final AtomicBoolean used=new AtomicBoolean();
        private final List<InstalledUnitPositionOwnershipPort.Contribution> positionFacts=new ArrayList<>();
        private volatile boolean active=true;private boolean frozen,guarded;
        private TopologyInstalledUnitCompanyEvidenceContract.Contribution company;
        private final Map<String,TopologyInstalledUnitManufacturerEvidenceContract.Contribution> manufacturers=new TreeMap<>();
        private TopologyInstalledUnitAuthorizationContract.Contribution nativeFact;
        private ProtectedInstalledUnitRepositoryPort.Origin origin;private List<ProtectedRelationFence> fences;
        private ProtectedInstalledUnitRepositoryPort.Snapshot initial;private ProtectedTransactionExecutor.ProvisionalPermit permit;private Result result;
        private Admission(ProtectedInstalledUnitAdmissionCoordinator owner,Command command,InstalledUnit unit,InstalledUnit replacementUnit,
                TopologyInstalledUnitAuthorizationContract.Admission identity,TopologyInstalledUnitCompanyEvidenceContract.Admission company,
                Map<String,TopologyInstalledUnitManufacturerEvidenceContract.Admission> manufacturers,Map<String,TopologyInstalledUnitManufacturerEvidenceContract.Admission> spares,
                Map<String,InstalledUnitPositionOwnershipPort.Admission> positions,ProtectedInstalledUnitRepositoryPort.Admission repository) {
            this.owner=owner;this.command=command;this.unit=unit;this.replacementUnit=replacementUnit;this.identity=identity;companyTicket=company;manufacturerTickets=Collections.unmodifiableMap(new TreeMap<>(manufacturers));
            spareManufacturers=Collections.unmodifiableMap(new TreeMap<>(spares));this.positions=Map.copyOf(positions);this.repository=repository;
        }
        @Override public void suspend(){active=false;owner.storage.reject(repository);}
        @Override public void afterCompletion(int status){active=false;}
        @Override public void beforeCommit(boolean readOnly){if(!active || !guarded || thread!=Thread.currentThread())throw denied();}
    }
}
