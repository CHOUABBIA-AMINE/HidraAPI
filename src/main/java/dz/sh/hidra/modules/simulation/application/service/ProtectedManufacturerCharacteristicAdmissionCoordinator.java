/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedManufacturerCharacteristicAdmissionCoordinator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.service
 *
 * @Description : One native, Party, source and terminal-approval admission per physical transaction.
 *
 */
package dz.sh.hidra.modules.simulation.application.service;

import dz.sh.hidra.modules.simulation.application.port.in.ManufacturerCharacteristicRevisionUseCase.*;
import dz.sh.hidra.modules.simulation.application.port.out.*;
import dz.sh.hidra.modules.simulation.application.port.out.ManufacturerCharacteristicRevisionRepositoryPort.*;
import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision;
import dz.sh.hidra.modules.identity.application.contract.simulation.SimulationManufacturerCharacteristicAuthorizationContract;
import dz.sh.hidra.modules.party.application.contract.simulation.SimulationManufacturerEvidenceContract;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import dz.sh.hidra.platform.configuration.HidraPaginationProperties;
import org.springframework.core.env.Environment;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** No Workflow approval, certificate reference or Party manufacturer role is an authorization token. */
@Service
public final class ProtectedManufacturerCharacteristicAdmissionCoordinator {
    private static final String TARGET_DOMAIN="HIDRA_MANUFACTURER_CHARACTERISTIC_APPROVAL_TARGET_V1";
    private static final Set<String> PINNED_DML_HASHES=Set.of(
        "2af4bb321a4c0aa685d79e9703541cba7e4ad1d6500c312beaef72c582a3e7c0",
        "6d7338e5d51dcd102be55156fdd703e99bfa2623325178839a67d475cb42af6a");
    private final SimulationManufacturerCharacteristicAuthorizationContract identity;
    private final SimulationManufacturerEvidenceContract party;
    private final ManufacturerCharacteristicApprovalEvidencePort approvals;
    private final ManufacturerCharacteristicRevisionRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    private final HidraPaginationProperties pagination;
    private final ManufacturerCharacteristicHistoryCursorPort cursors;
    private final int maximumPrincipals;
    public ProtectedManufacturerCharacteristicAdmissionCoordinator(
            SimulationManufacturerCharacteristicAuthorizationContract identity,
            SimulationManufacturerEvidenceContract party,ManufacturerCharacteristicApprovalEvidencePort approvals,
            ManufacturerCharacteristicRevisionRepositoryPort repository,ProtectedTransactionExecutor executor,
            HidraPaginationProperties pagination,ManufacturerCharacteristicHistoryCursorPort cursors,
            Environment environment) {
        this.identity=Objects.requireNonNull(identity);this.party=Objects.requireNonNull(party);
        this.approvals=Objects.requireNonNull(approvals);this.repository=Objects.requireNonNull(repository);
        this.executor=Objects.requireNonNull(executor);
        this.pagination=Objects.requireNonNull(pagination);
        this.cursors=Objects.requireNonNull(cursors);
        this.maximumPrincipals=Objects.requireNonNull(environment).getRequiredProperty(
                "hidra.simulation.manufacturer-history.max-subjects",Integer.class);
        if(maximumPrincipals<1 || maximumPrincipals>64)
            throw new IllegalArgumentException("Invalid manufacturer history subject budget");
    }

    public Prepared prepare(Command command) {
        executor.requireNoProtectedContext();
        if(command==null || TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isSynchronizationActive())throw denied();
        return new Prepared(this,command,null,identity.prepare());
    }

    public Prepared preparePage(HistoryPageQuery query) {
        executor.requireNoProtectedContext();
        Objects.requireNonNull(query);
        if(!cursors.available())throw denied();
        int size=pagination.resolveSize(query.requestedSize(),pagination.maxSize());
        pagination.validateCursor(query.cursor(),pagination.maxCursorLength());
        var after=query.cursor()==null?null:cursors.decode(
                query.cursor(),query.companyPartyId(),query.sourceId(),size);
        Command command=new Command(Operation.LIST_HISTORY,query.companyPartyId(),query.sourceId(),
                null,null,null);
        Prepared prepared=prepare(command);
        prepared.pageQuery=query;
        prepared.pageSize=size;
        prepared.after=after;
        return prepared;
    }
    public Admission acquirePage(Prepared prepared) {
        if(prepared==null || prepared.pageQuery==null)throw denied();
        return acquire(prepared,prepared.command);
    }
    public Prepared prepareWorkflowTarget(String digest,boolean history) {
        executor.requireNoProtectedContext();
        if(digest==null || !digest.matches("[0-9a-f]{64}")
                ||TransactionSynchronizationManager.isActualTransactionActive()
                ||TransactionSynchronizationManager.isSynchronizationActive())throw denied();
        return new Prepared(this,null,new WorkflowTargetRequest(digest,history),identity.prepare());
    }

    public Admission acquire(Prepared prepared,Command command) {
        Admission a=null;
        try {
            executor.beginAdmission();
            checkPrepared(prepared,command,null);
            var storage=repository.acquire(command);
            var key=new Key(command.companyPartyId(),command.sourceId(),command.revisionId());
            var auth=identity.acquire(prepared.nativePrepared,authorization(command));
            var origin=identity.contribute(auth);
            if(origin.decision()!=SimulationManufacturerCharacteristicAuthorizationContract.Decision.PERMIT)throw denied();
            HistoryScan scan=prepared.pageQuery==null?null:repository.scanHistory(storage,
                    command.companyPartyId(),command.sourceId(),prepared.pageSize,
                    prepared.after,maximumPrincipals);
            List<Stored> selected = switch(command.operation()){
                case SAVE -> List.of();
                case LIST_HISTORY -> scan==null
                        ? repository.history(storage,command.companyPartyId(),command.sourceId()) : List.of();
                default -> List.of(repository.read(storage,key).orElseThrow(
                    ProtectedManufacturerCharacteristicAdmissionCoordinator::denied));
            };
            if(command.operation()==Operation.LIST_HISTORY && scan==null && selected.isEmpty())throw denied();
            ManufacturerCharacteristicRevision source=command.operation()==Operation.SAVE
                ?command.suppliedRevision():scan==null?selected.getFirst().revision():null;
            var grants=new ArrayList<PartyGrant>();
            if(scan!=null) {
                for(Principal principal:scan.principals())grants.add(partyGrant(
                        principal.manufacturerPartyId(),principal.manufacturerModelReference(),
                        command.companyPartyId(),origin));
            } else if(command.operation()==Operation.LIST_HISTORY){
                for(Stored item:selected)grants.add(partyGrant(item.revision(),command.companyPartyId(),origin));
            } else grants.add(partyGrant(source,command.companyPartyId(),origin));
            var fences=union(identity.finalFences(auth),repository.finalFences(storage),partyFences(grants));
            ManufacturerCharacteristicApprovalEvidencePort.Evidence approval=null;
            if(command.operation()==Operation.QUALIFY) {
                Stored stored=selected.getFirst();
                if(!stored.revision().effectiveAt(origin.observedAt()))throw denied();
                var ar=command.approval();
                var proof=new ManufacturerCharacteristicApprovalEvidencePort.Request(
                        stored.workflowTargetDigest(),ar.definitionId(),ar.definitionVersion(),ar.targetTypeId(),
                        ar.purposeId(),ar.instanceId(),ar.taskId(),ar.actionId(),origin.observedAt());
                approval=approvals.resolve(proof).orElseThrow(
                    ProtectedManufacturerCharacteristicAdmissionCoordinator::denied);
                if(approval.approvedAt().isAfter(origin.observedAt())
                        ||approval.approvedAt().isBefore(stored.revision().recordedAt()))throw denied();
                // Actual Workflow locks are held in this same physical transaction by
                // the Workflow-owned adapter; the final scalar verifies every fence.
                fences=union(fences,approval.workflowFences());
            }
            a=new Admission(this,command,prepared.target,storage,auth,origin,List.copyOf(grants),
                    List.copyOf(selected),approval,fences);
            a.pageQuery=prepared.pageQuery;
            a.pageSize=prepared.pageSize;
            a.pageScan=scan;
            TransactionSynchronizationManager.registerSynchronization(a);
            for(var grant:grants)party.requireCurrent(grant.admission());
            identity.requireCurrent(auth);
            a.permit=executor.sealAdmission();
            return a;
        }catch(RuntimeException fault){reject(a);throw denied();}
    }

    public Admission acquireWorkflowTarget(Prepared prepared,String digest,boolean history) {
        Admission a=null;
        try {
            executor.beginAdmission();
            checkPrepared(prepared,null,new WorkflowTargetRequest(digest,history));
            var storage=repository.acquire(null);
            Stored stored=repository.byWorkflowTarget(storage,digest).orElseThrow(
                    ProtectedManufacturerCharacteristicAdmissionCoordinator::denied);
            var request=new SimulationManufacturerCharacteristicAuthorizationContract.Request(
                history?SimulationManufacturerCharacteristicAuthorizationContract.Operation.LIST_HISTORY:
                         SimulationManufacturerCharacteristicAuthorizationContract.Operation.READ_REVISION,
                    stored.key().companyPartyId(),stored.key().sourceId(),history?null:stored.key().revisionId());
            var auth=identity.acquire(prepared.nativePrepared,request);
            var origin=identity.contribute(auth);
            if(origin.decision()!=SimulationManufacturerCharacteristicAuthorizationContract.Decision.PERMIT)throw denied();
            var grant=partyGrant(stored.revision(),stored.key().companyPartyId(),origin);
            var fences=union(identity.finalFences(auth),repository.finalFences(storage),party.finalFences(grant.admission()));
            a=new Admission(this,null,prepared.target,storage,auth,origin,List.of(grant),List.of(stored),
                    null,fences);
            TransactionSynchronizationManager.registerSynchronization(a);
            party.requireCurrent(grant.admission());identity.requireCurrent(auth);
            a.permit=executor.sealAdmission();
            return a;
        }catch(RuntimeException fault){reject(a);throw denied();}
    }

    private PartyGrant partyGrant(
            ManufacturerCharacteristicRevision revision,String company,
            SimulationManufacturerCharacteristicAuthorizationContract.Contribution identityResult) {
        return partyGrant(revision.manufacturerPartyId(),revision.manufacturerModelReference(),company,identityResult);
    }
    private PartyGrant partyGrant(String manufacturer,String model,String company,
            SimulationManufacturerCharacteristicAuthorizationContract.Contribution identityResult) {
        var requirement=new SimulationManufacturerEvidenceContract.Requirement(company,manufacturer,model);
        var admission=party.acquire(requirement);
        var evidence=party.read(admission);
        if(!company.equals(evidence.companyPartyId())
                ||!manufacturer.equals(evidence.manufacturerPartyId())
                ||!model.equals(evidence.manufacturerModelReference())
                ||evidence.backendPid()!=identityResult.backendPid()
                ||evidence.transactionId()!=identityResult.transactionId()
                ||!evidence.databaseRole().equals(identityResult.databaseRole())
                ||evidence.observedAt().isBefore(identityResult.observedAt())
                ||!evidence.observedAt().isBefore(identityResult.validUntil())
                ||(evidence.validUntil()!=null && !evidence.observedAt().isBefore(evidence.validUntil())))
            throw denied();
        return new PartyGrant(admission,evidence);
    }
    private record PartyGrant(SimulationManufacturerEvidenceContract.Admission admission,
                             SimulationManufacturerEvidenceContract.Contribution evidence) {}

    private static SimulationManufacturerCharacteristicAuthorizationContract.Request authorization(Command c) {
        return new SimulationManufacturerCharacteristicAuthorizationContract.Request(
                SimulationManufacturerCharacteristicAuthorizationContract.Operation.valueOf(c.operation().name()),
                c.companyPartyId(),c.sourceId(),c.revisionId());
    }

    public Result perform(Admission admission) {
        Admission a=current(admission);
        try {
            if(!a.used.compareAndSet(false,true))throw denied();
            identity.requireCurrent(a.auth);for(var p:a.partyTickets)party.requireCurrent(p.admission());
            Instant now=a.origin.observedAt();
            if(a.command==null){
                Stored source=a.selected.getFirst();
                a.result=new Result(a.target.history()?Operation.LIST_HISTORY:Operation.READ_REVISION,
                    source.key().companyPartyId(),source.key().sourceId(),source.key().revisionId(),
                    source.payloadSha256(),null,List.of(),now,
                    repository.qualification(a.storage,source.key()).isPresent());
                return a.result;
            }
            Command c=a.command;
            switch(c.operation()){
                case SAVE -> {
                    var key=new Key(c.companyPartyId(),c.sourceId(),c.revisionId());
                    byte[] bytes=repository.encode(c.suppliedRevision());String sourceSha=repository.sha256(bytes);
                    String target=targetDigest(key,sourceSha);
                    var desired=new Stored(key,c.suppliedRevision(),sourceSha,target,
                        a.origin.userId(),a.origin.bindingId(),a.origin.sessionId(),
                        a.origin.backendPid(),a.origin.transactionId(),a.origin.databaseRole(),now,bytes);
                    var existing=repository.read(a.storage,key);
                    Stored effective;
                    if(existing.isPresent()){
                        effective=existing.get();
                        if(!effective.payloadSha256().equals(sourceSha)
                                ||!Arrays.equals(effective.canonicalPayload(),bytes)
                                ||!effective.creatorUserId().equals(a.origin.userId())
                                ||!effective.bindingId().equals(a.origin.bindingId())
                                ||!effective.sessionId().equals(a.origin.sessionId())
                                ||!effective.revision().equals(c.suppliedRevision()))throw denied();
                    }else{
                        String sql=mutationSql(true);
                        executor.beginProvisional(a.permit,Set.of(sql));
                        repository.append(a.storage,desired);
                        executor.endProvisional(a.permit);
                        effective=repository.read(a.storage,key).orElseThrow(
                            ProtectedManufacturerCharacteristicAdmissionCoordinator::denied);
                        if(!effective.payloadSha256().equals(sourceSha)
                                ||!Arrays.equals(effective.canonicalPayload(),bytes)
                                ||!effective.workflowTargetDigest().equals(target))throw denied();
                    }
                    a.result=new Result(Operation.SAVE,c.companyPartyId(),c.sourceId(),c.revisionId(),
                        sourceSha,null,List.of(),now,false);
                }
                case QUALIFY -> {
                    Stored source=a.selected.getFirst();
                    var key=source.key();
                    var proof=a.approval;
                    String id=targetDigest(key,source.payloadSha256()).substring(0,64);
                    var desired=new Qualification(id,key,source.payloadSha256(),source.workflowTargetDigest(),
                        proof.instanceId(),proof.taskId(),proof.actionId(),proof.actorId(),proof.actorDisplayName(),
                        proof.approvedAt(),now,a.origin.userId(),a.origin.bindingId(),
                        a.origin.backendPid(),a.origin.transactionId(),a.origin.databaseRole());
                    var previous=repository.qualification(a.storage,key);
                    if(previous.isPresent()){
                        if(!previous.get().workflowActionId().equals(proof.actionId())
                                ||!previous.get().sourceSha256().equals(source.payloadSha256()))throw denied();
                    }else{
                        executor.beginProvisional(a.permit,Set.of(mutationSql(false)));
                        repository.appendQualification(a.storage,desired);
                        executor.endProvisional(a.permit);
                        var winner=repository.qualification(a.storage,key).orElseThrow(
                            ProtectedManufacturerCharacteristicAdmissionCoordinator::denied);
                        if(!winner.workflowActionId().equals(proof.actionId())
                                ||!winner.sourceSha256().equals(source.payloadSha256()))throw denied();
                    }
                    a.result=new Result(Operation.QUALIFY,key.companyPartyId(),key.sourceId(),key.revisionId(),
                        source.payloadSha256(),null,List.of(),now,true);
                }
                case LIST_HISTORY -> {
                    var views=a.selected.stream().map(s->new RevisionSummary(
                        s.key().sourceId(),s.key().revisionId(),s.payloadSha256(),
                        s.revision().recordedAt(),s.revision().effectiveFrom(),s.revision().effectiveUntil(),
                        repository.qualification(a.storage,s.key()).isPresent())).toList();
                    a.result=new Result(Operation.LIST_HISTORY,c.companyPartyId(),c.sourceId(),null,null,
                        null,views,now,false);
                }
                case READ_REVISION,READ_CERTIFICATE -> {
                    Stored v=a.selected.getFirst();
                    var original=v.revision();
                    // A normal read does not disclose certificate references without the dedicated permission.
                    ManufacturerCharacteristicRevision exposed=c.operation()==Operation.READ_CERTIFICATE?original:
                        new ManufacturerCharacteristicRevision(original.sourceId(),original.revisionId(),
                            original.recordedAt(),original.effectiveFrom(),original.effectiveUntil(),original.origin(),
                            original.manufacturerPartyId(),original.manufacturerModelReference(),
                            original.documentReference(),original.documentSha256(),original.referenceConditions(),
                            null,original.family());
                    a.result=new Result(c.operation(),c.companyPartyId(),c.sourceId(),c.revisionId(),
                        v.payloadSha256(),exposed,List.of(),now,
                        repository.qualification(a.storage,v.key()).isPresent());
                }
            }
            identity.requireCurrent(a.auth);for(var p:a.partyTickets)party.requireCurrent(p.admission());
            return a.result;
        }catch(RuntimeException fault){reject(a);throw denied();}
    }


    public HistoryPageResult performPage(Admission admission) {
        Admission a=current(admission);
        try {
            if(a.pageQuery==null || a.pageScan==null || !a.used.compareAndSet(false,true))
                throw denied();
            identity.requireCurrent(a.auth);
            for(var p:a.partyTickets)party.requireCurrent(p.admission());
            var views=new ArrayList<RevisionSummary>();
            for(RevisionSummary raw:a.pageScan.summaries()) {
                var key=new Key(a.command.companyPartyId(),a.command.sourceId(),raw.revisionId());
                views.add(new RevisionSummary(raw.sourceId(),raw.revisionId(),raw.payloadSha256(),
                        raw.recordedAt(),raw.effectiveFrom(),raw.effectiveUntil(),
                        repository.qualification(a.storage,key).isPresent()));
            }
            String next=null;
            if(a.pageScan.hasNext()){
                if(views.isEmpty())throw denied();
                var last=views.getLast();
                next=cursors.encode(a.command.companyPartyId(),a.command.sourceId(),a.pageSize,
                        new ManufacturerCharacteristicHistoryCursorPort.Position(
                                last.recordedAt(),last.revisionId()));
                pagination.validateCursor(next,pagination.maxCursorLength());
            }
            Instant now=a.origin.observedAt();
            a.result=new Result(Operation.LIST_HISTORY,a.command.companyPartyId(),
                    a.command.sourceId(),null,null,null,views,now,false);
            a.pageResult=new HistoryPageResult(views,next,now);
            identity.requireCurrent(a.auth);
            for(var p:a.partyTickets)party.requireCurrent(p.admission());
            return a.pageResult;
        }catch(RuntimeException fault){reject(a);throw denied();}
    }

    /** Fixed source shape only; never trust a caller-provided SQL statement. */
    private String mutationSql(boolean source) {
        var texts=repository.mutationSql();
        if(texts==null||texts.size()!=2||!texts.stream().map(
                ProtectedManufacturerCharacteristicAdmissionCoordinator::sha).collect(
                java.util.stream.Collectors.toSet()).equals(PINNED_DML_HASHES))throw denied();
        return texts.stream().filter(x->x.startsWith(source
                ?"INSERT INTO public.hidra_simulation_manufacturer_characteristic_revision\n"
                :"INSERT INTO public.hidra_simulation_manufacturer_characteristic_qualification\n"))
                .findFirst().orElseThrow(ProtectedManufacturerCharacteristicAdmissionCoordinator::denied);
    }

    public void prepareFinal(Admission admission) {
        try{
            Admission a=current(admission);
            if(a.result==null||a.frozen)throw denied();
            identity.requireCurrent(a.auth);for(var p:a.partyTickets)party.requireCurrent(p.admission());
            repository.requireCurrent(a.storage);
            Instant latest=a.origin.validUntil();
            for(var grant:a.partyTickets) {
                Instant end=grant.evidence().validUntil();
                if(end!=null && end.isBefore(latest))latest=end;
            }
            if(a.command!=null && a.command.operation()==Operation.QUALIFY) {
                Instant applicabilityEnd=a.selected.getFirst().revision().effectiveUntil();
                if(applicabilityEnd!=null && applicabilityEnd.isBefore(latest))latest=applicabilityEnd;
            }
            if(!latest.isAfter(a.origin.observedAt()))throw denied();
            repository.prepareFinal(a.storage,a.fences,a.origin.observedAt(),latest);
            a.frozen=true;
        }catch(RuntimeException fault){reject(admission);throw denied();}
    }
    public boolean finalGuard(Admission admission) {
        Admission a=current(admission);
        if(!a.frozen||a.guarded)throw denied();
        // Exactly one closed scalar SQL is emitted by the storage adapter in the executor GUARD phase.
        a.guarded=repository.finalGuard(a.storage);
        return a.guarded;
    }
    public void flush(){repository.flush();}
    public void reject(Admission admission){if(admission!=null)admission.active=false;executor.rejectAdmission();}
    public String targetDigest(Key key,String sourceSha){return targetDigestStatic(key,sourceSha);}
    public static String targetDigestStatic(Key key,String sourceSha) {
        Objects.requireNonNull(key);
        if(sourceSha==null||!sourceSha.matches("[0-9a-f]{64}"))throw denied();
        String s=TARGET_DOMAIN+"|"+key.companyPartyId().length()+":"+key.companyPartyId()
            +"|"+key.sourceId().length()+":"+key.sourceId()
            +"|"+key.revisionId().length()+":"+key.revisionId()+"|"+sourceSha;
        return sha(s);
    }
    private static String sha(String s){
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(s.getBytes(StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException e){throw new IllegalStateException("SHA-256 unavailable",e);}
    }
    private static List<ProtectedRelationFence> union(List<ProtectedRelationFence>... groups){
        var map=new TreeMap<String,ProtectedRelationFence.Mode>();
        for(var group:groups)for(var f:group){
            var old=map.get(f.relation());
            if(old==null||rank(f.mode())>rank(old))map.put(f.relation(),f.mode());
        }
        return map.entrySet().stream().map(e->new ProtectedRelationFence(e.getKey(),e.getValue())).toList();
    }
    private static int rank(ProtectedRelationFence.Mode m) {
        return switch(m) {case ACCESS_SHARE -> 1;case SHARE -> 2;case SHARE_ROW_EXCLUSIVE -> 3;};
    }
    private List<ProtectedRelationFence> partyFences(List<PartyGrant> grants){
        if(grants.isEmpty())throw denied();
        return party.finalFences(grants.getFirst().admission());
    }
    private void checkPrepared(Prepared p,Command c,WorkflowTargetRequest r){
        if(p==null||p.owner!=this||p.thread!=Thread.currentThread()
                ||!p.used.compareAndSet(false,true)
                ||!Objects.equals(p.command,c)||!Objects.equals(p.target,r))throw denied();
    }
    private Admission current(Admission a) {
        if(a==null||a.owner!=this||!a.active||a.thread!=Thread.currentThread()
                ||!TransactionSynchronizationManager.isActualTransactionActive()
                ||!TransactionSynchronizationManager.isSynchronizationActive())throw denied();
        return a;
    }
    private static AccessDeniedException denied(){return new AccessDeniedException("Manufacturer characteristic unavailable");}
    public record WorkflowTargetView(String workflowTargetDigest,String revisionId) {}
    private record WorkflowTargetRequest(String digest,boolean history) {}
    public static final class Prepared {
        private final ProtectedManufacturerCharacteristicAdmissionCoordinator owner;
        private final Command command;private final WorkflowTargetRequest target;
        private final SimulationManufacturerCharacteristicAuthorizationContract.Prepared nativePrepared;
        private final Thread thread=Thread.currentThread();private final AtomicBoolean used=new AtomicBoolean();
        private HistoryPageQuery pageQuery;
        private int pageSize;
        private ManufacturerCharacteristicHistoryCursorPort.Position after;
        private Prepared(ProtectedManufacturerCharacteristicAdmissionCoordinator owner,Command command,
                WorkflowTargetRequest target,SimulationManufacturerCharacteristicAuthorizationContract.Prepared nativePrepared){
            this.owner=owner;this.command=command;this.target=target;this.nativePrepared=nativePrepared;
        }
        @Override public String toString(){return "ManufacturerPrepared[opaque]";}
    }
    public static final class Admission implements TransactionSynchronization {
        private final ProtectedManufacturerCharacteristicAdmissionCoordinator owner;
        private final Command command;private final WorkflowTargetRequest target;
        private final ManufacturerCharacteristicRevisionRepositoryPort.Admission storage;
        private final SimulationManufacturerCharacteristicAuthorizationContract.Admission auth;
        private final SimulationManufacturerCharacteristicAuthorizationContract.Contribution origin;
        private final List<PartyGrant> partyTickets;
        private final List<Stored> selected;private final ManufacturerCharacteristicApprovalEvidencePort.Evidence approval;
        private final List<ProtectedRelationFence> fences;
        private final Thread thread=Thread.currentThread();private final AtomicBoolean used=new AtomicBoolean();
        private ProtectedTransactionExecutor.ProvisionalPermit permit;
        private boolean active=true,frozen,guarded;
        private Result result;
        private HistoryPageQuery pageQuery;
        private int pageSize;
        private HistoryScan pageScan;
        private HistoryPageResult pageResult;
        private Admission(ProtectedManufacturerCharacteristicAdmissionCoordinator owner,Command command,
                WorkflowTargetRequest target,ManufacturerCharacteristicRevisionRepositoryPort.Admission storage,
                SimulationManufacturerCharacteristicAuthorizationContract.Admission auth,
                SimulationManufacturerCharacteristicAuthorizationContract.Contribution origin,
                List<PartyGrant> partyTickets,List<Stored> selected,
                ManufacturerCharacteristicApprovalEvidencePort.Evidence approval,List<ProtectedRelationFence> fences) {
            this.owner=owner;this.command=command;this.target=target;this.storage=storage;
            this.auth=auth;this.origin=origin;this.partyTickets=partyTickets;this.selected=selected;
            this.approval=approval;this.fences=fences;
        }
        public Result result(){return result;}
        @Override public void beforeCommit(boolean readOnly){if(!active||readOnly||!guarded)throw denied();}
        @Override public void afterCompletion(int status){active=false;}
        @Override public void suspend(){active=false;}
        @Override public String toString(){return "ManufacturerAdmission[opaque original transaction]";}
    }
}
