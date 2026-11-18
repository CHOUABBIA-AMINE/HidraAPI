/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaLifecycleApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.service
 * @Description : Current-authority-first HSE scoped CAPA and independent closure orchestration.
 */
package dz.sh.hidra.modules.hse.application.service;

import dz.sh.hidra.modules.hse.application.port.in.HseCapaLifecycleUseCase;
import dz.sh.hidra.modules.hse.application.port.in.HseCapaLifecycleUseCase.*;
import dz.sh.hidra.modules.hse.application.port.out.*;
import dz.sh.hidra.modules.hse.domain.model.*;
import dz.sh.hidra.modules.hse.domain.service.HseCapaLifecycleGuard;
import dz.sh.hidra.modules.hse.domain.value.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** All signed resource commands run in a lock-capable, rollback-on-failure transaction. */
@Service
public class HseCapaLifecycleApplicationService implements HseCapaLifecycleUseCase {
    private static final String CREATE="hse:capa:create", READ="hse:capa:read",
            EXECUTE="hse:capa:execute", REVIEW="hse:capa:review", CLOSE="hse:capa:close",
            ASSESS="hse:capa:assess";
    private final HseCapaAuthorityPort authority;
    private final HseCapaFacilityPort topology;
    private final HseCapaIncidentPort incidents;
    private final HseCapaDocumentsPort documents;
    private final HseCapaWorkflowPort workflows;
    private final HseCapaLifecycleStorePort store;
    private final HseCaseRepositoryPort cases;
    private final HseCorrectivePreventiveActionRepositoryPort capas;
    private final HseCapaLifecycleGuard guard=new HseCapaLifecycleGuard();

    public HseCapaLifecycleApplicationService(HseCapaAuthorityPort authority,
            HseCapaFacilityPort topology,HseCapaIncidentPort incidents,
            HseCapaDocumentsPort documents,HseCapaWorkflowPort workflows,
            HseCapaLifecycleStorePort store,HseCaseRepositoryPort cases,
            HseCorrectivePreventiveActionRepositoryPort capas) {
        this.authority=Objects.requireNonNull(authority);this.topology=Objects.requireNonNull(topology);
        this.incidents=Objects.requireNonNull(incidents);this.documents=Objects.requireNonNull(documents);
        this.workflows=Objects.requireNonNull(workflows);this.store=Objects.requireNonNull(store);
        this.cases=Objects.requireNonNull(cases);this.capas=Objects.requireNonNull(capas);
    }
    @Override @Transactional
    public CaseResult enroll(String company,String facility,Enroll command){
        Objects.requireNonNull(command,"HSE case enrollment command required.");
        var actor=current(CREATE,company,facility);
        String request=text(command.requestId(),80);
        String digest=digest("ENROLL",company,facility,request,command.incidentId(),command.caseNumber(),
                command.title(),command.description(),command.caseTypeId(),command.severityId(),actor.id());
        // Recheck source ownership and independent T16 review also for a matching replay.
        var source=incidents.reviewed(text(command.incidentId(),80),company,facility)
                .orElseThrow(HseCapaLifecycleApplicationService::hidden);
        store.serializeEnrollment(company,facility,request);
        var prior=store.created(company,facility,request);
        if(prior.isPresent()){
            current(READ,company,facility);
            var x=prior.orElseThrow();
            if(!digest.equals(x.createDigest())||!actor.id().equals(x.actorId()))throw conflict();
            return new CaseResult(store.caseView(x.caseId()),true);
        }
        Instant now=Instant.now();
        var facilityProof=topology.active(facility).orElseThrow(HseCapaLifecycleApplicationService::hidden);
        var model=new HseCase(HseId.newId().value(),text(command.caseNumber(),80),
                text(command.title(),255),command.description(),text(command.caseTypeId(),80),
                text(command.severityId(),80),null,HseCaseStatus.OPEN,HseCaseSourceType.INCIDENT_REFERENCE,
                source.id(),source.code(),source.title(),"topology","FACILITY",facility,
                facilityProof.code(),facilityProof.label(),null,now,actor.id(),actor.displayName(),
                null,null,null,null,null,null,null,now,now);
        cases.save(model);
        cases.flush(); // Physical owner row is required before the foreign-keyed scoped binding.
        store.bind(new HseCapaLifecycleStorePort.Scope(model.id(),company,facility,source.id(),
                request,digest,actor.id(),now));
        return new CaseResult(store.caseView(model.id()),false);
    }
    @Override @Transactional
    public Page<CaseView> cases(String company,String facility,int page,int size){
        current(READ,company,facility);page(page,size);
        var pageOfCases=store.cases(company,facility,page,size);
        for(var item:pageOfCases.items())
            incidents.reviewed(item.incidentId(),company,facility)
                .orElseThrow(HseCapaLifecycleApplicationService::hidden);
        return pageOfCases;
    }
    @Override @Transactional
    public CaseView caseDetail(String company,String facility,String id){
        current(READ,company,facility);bound(id,company,facility);
        return store.caseView(id);
    }
    @Override @Transactional
    public CapaResult propose(String company,String facility,String caseId,Propose command){
        Objects.requireNonNull(command,"CAPA proposal required.");
        var actor=current(CREATE,company,facility);
        bound(caseId,company,facility);
        store.lockCase(caseId);
        var old=store.replay(caseId,text(command.requestId(),80));
        String digest=digest("PROPOSE",company,facility,caseId,command.requestId(),
                Long.toString(command.expectedCaseVersion()),command.actionNumber(),command.actionTypeId(),command.title(),command.description(),
                command.ownerActorId(),actor.id());
        if(old.isPresent()){current(READ,company,facility);
            return existingCapa(old.orElseThrow(),digest,actor.id(),company,facility);}
        if(!"OPEN".equals(store.caseView(caseId).status()))throw conflict();
        if(store.history(caseId,0,1).total()!=command.expectedCaseVersion())throw conflict();
        if(!actor.id().equals(command.ownerActorId()))throw new SecurityException("Current proposer must own the assigned action.");
        Instant now=Instant.now();
        var capa=new HseCorrectivePreventiveAction(HseId.newId().value(),caseId,
                text(command.actionNumber(),80),text(command.actionTypeId(),80),
                text(command.title(),255),command.description(),actor.id(),actor.displayName(),
                null,null,null,null,true,null,null,CapaStatus.PROPOSED,null,null,now,now);
        capas.save(capa);
        capas.flush(); // Required before the transactional append-only CAPA head/event.
        store.addCapa(capa.id(),caseId,actor.id(),actor.id(),command.requestId(),digest,actor.id(),now);
        return new CapaResult(store.capaView(caseId,capa.id()),false);
    }
    @Override @Transactional
    public Page<CapaView> capas(String company,String facility,String caseId,int page,int size){
        current(READ,company,facility);bound(caseId,company,facility);page(page,size);
        return store.capas(caseId,page,size);
    }
    @Override @Transactional
    public CapaView capaDetail(String company,String facility,String caseId,String capaId){
        current(READ,company,facility);bound(caseId,company,facility);
        return store.capaView(caseId,capaId);
    }
    @Override @Transactional
    public CapaResult approve(String company,String facility,String caseId,String capaId,Action command){
        return transition(company,facility,caseId,capaId,"APPROVE",EXECUTE,command);
    }
    @Override @Transactional
    public CapaResult assign(String company,String facility,String caseId,String capaId,Action command){
        return transition(company,facility,caseId,capaId,"ASSIGN",EXECUTE,command);
    }
    @Override @Transactional
    public CapaResult evidence(String company,String facility,String caseId,String capaId,Action command){
        return transition(company,facility,caseId,capaId,"EVIDENCE",EXECUTE,command);
    }
    @Override @Transactional
    public CapaResult complete(String company,String facility,String caseId,String capaId,Action command){
        return transition(company,facility,caseId,capaId,"COMPLETE",EXECUTE,command);
    }
    @Override @Transactional
    public CapaResult verify(String company,String facility,String caseId,String capaId,Action command){
        return transition(company,facility,caseId,capaId,"VERIFY",REVIEW,command);
    }
    private CapaResult transition(String company,String facility,String caseId,String capaId,
            String operation,String permission,Action command){
        Objects.requireNonNull(command,"CAPA action required.");
        var actor=current(permission,company,facility);
        current(READ,company,facility);
        bound(caseId,company,facility);
        String key=text(command.requestId(),80);
        String reason=text(command.reason(),500);
        if(command.expectedVersion()<0)throw new IllegalArgumentException("Negative CAPA revision.");
        String digest=digest(operation,company,facility,caseId,capaId,key,
                Long.toString(command.expectedVersion()),reason,command.referenceId(),
                command.supportingId(),command.finding(),actor.id());
        store.lockCase(caseId);
        var old=store.replay(caseId,key);
        if(old.isPresent())return existingCapa(old.orElseThrow(),digest,actor.id(),company,facility);
        store.lockCapa(capaId);
        old=store.replay(caseId,key);
        if(old.isPresent())return existingCapa(old.orElseThrow(),digest,actor.id(),company,facility);
        var state=store.state(caseId,capaId);
        if(state.version()!=command.expectedVersion())throw conflict();
        String ref=null;
        String before=state.status().name();
        boolean hasEvidence=!store.evidence(capaId).isEmpty();
        boolean approved=false;
        boolean independent=false;
        HseCapaVerification.Finding finding=null;
        HseCapaEvidence accepted=null;
        HseCapaVerification verification=null;
        switch(operation) {
            case "APPROVE" -> {
                var decision=workflows.approved(text(command.referenceId(),80),"HSE_CAPA",capaId)
                        .orElseThrow(HseCapaLifecycleApplicationService::hidden);
                if(!actor.id().equals(decision.approverId())
                        ||decision.approverId().equals(state.proposerId())
                        ||decision.approverId().equals(state.ownerId()))throw new SecurityException("Independent CAPA approval required.");
                approved=true;ref=decision.actionId();
            }
            case "ASSIGN" -> {
                if(!actor.id().equals(state.ownerId())
                        ||!actor.id().equals(text(command.referenceId(),80)))
                    throw new SecurityException("Approved CAPA owner must accept assignment.");
                ref=actor.id();
            }
            case "EVIDENCE" -> {
                requireOwner(state,actor.id());
                var proof=documents.accepted(company,facility,
                        text(command.referenceId(),80),text(command.supportingId(),80))
                        .orElseThrow(HseCapaLifecycleApplicationService::hidden);
                accepted=new HseCapaEvidence(HseId.newId().value(),capaId,proof.documentId(),
                        proof.versionId(),proof.acceptanceId(),proof.sha256(),proof.contentLength(),
                        company,facility,actor.id(),Instant.now());
                ref=accepted.id();
            }
            case "COMPLETE" -> {
                requireOwner(state,actor.id());
                if(!hasEvidence)throw new IllegalStateException("Accepted Documents evidence required.");
                ref=state.evidenceId();
            }
            case "VERIFY" -> {
                guard.independent(state.proposerId(),state.ownerId(),actor.id());
                var all=store.evidence(capaId);
                var matched=all.stream().filter(e->e.id().equals(command.referenceId())).findFirst()
                        .orElseThrow(HseCapaLifecycleApplicationService::hidden);
                var live=documents.accepted(company,facility,matched.documentId(),matched.versionId())
                        .orElseThrow(HseCapaLifecycleApplicationService::hidden);
                sameDocumentEvidence(matched,live,company,facility);
                finding=HseCapaVerification.Finding.valueOf(text(command.finding(),40));
                verification=new HseCapaVerification(HseId.newId().value(),capaId,matched.id(),
                        finding,reason,actor.id(),Instant.now());
                independent=true;ref=verification.id();
            }
            default -> throw new IllegalArgumentException("Unsupported enrolled operation.");
        }
        var after=guard.next(state.status(),operation,hasEvidence||accepted!=null,approved,
                independent,finding);
        Instant now=Instant.now();
        store.advance(caseId,capaId,key,digest,actor.id(),operation,before,after.name(),
                state.version(),ref,reason,now);
        if(accepted!=null)store.attach(accepted);
        if(verification!=null)store.verify(verification);
        return new CapaResult(store.capaView(caseId,capaId),false);
    }
    @Override @Transactional
    public ImpactReceipt assessImpact(String company, String facility, String caseId, AssessImpact command) {
        var actor = current(ASSESS, company, facility);
        current(READ, company, facility);
        bound(caseId, company, facility);
        Objects.requireNonNull(command);
        String key = text(command.requestId(), 80);
        String description = text(command.description(), 500);
        String type = text(command.impactTypeId(), 80);
        var domain = Objects.requireNonNull(command.impactDomain());
        var severity = Objects.requireNonNull(command.severity());
        long expected = command.expectedCaseVersion();
        if (expected < 0) throw new IllegalArgumentException("Negative HSE case revision.");
        String hash = digest("ASSESS_IMPACT", company, facility, caseId, key, Long.toString(expected),
                domain.name(), type, severity.name(), description, actor.id());
        store.lockCase(caseId);
        var replay = store.replay(caseId, key);
        if (replay.isPresent()) {
            var prior = replay.orElseThrow();
            if (!"ASSESS_IMPACT".equals(prior.operation()) || !hash.equals(prior.digest())
                    || !actor.id().equals(prior.actorId())) throw conflict();
            return new ImpactReceipt(prior.referenceId(), caseId, prior.version(), true);
        }
        String status = store.caseView(caseId).status();
        if (!List.of("OPEN", "IN_PROGRESS").contains(status)
                || store.history(caseId, 0, 1).total() != expected
                || store.admittedAssessment(caseId).isPresent()) throw conflict();
        var assessment = new HseCaseImpactAssessment(HseId.newId().value(), caseId, domain,
                type, severity, description, actor.id(), Instant.now());
        store.recordAssessment(assessment, status, expected + 1, key, hash);
        return new ImpactReceipt(assessment.id(), caseId, expected + 1, false);
    }

    @Override @Transactional
    public CaseResult close(String company,String facility,String caseId,Action command){
        Objects.requireNonNull(command,"HSE case closure required.");
        var actor=current(CLOSE,company,facility);
        current(READ,company,facility);
        var scope=bound(caseId,company,facility);
        String key=text(command.requestId(),80),reason=text(command.reason(),500);
        if(command.expectedVersion()<0)throw new IllegalArgumentException("Negative HSE case revision.");
        String digest=digest("CLOSE",company,facility,caseId,key,reason,
                command.referenceId(),Long.toString(command.expectedVersion()),actor.id());
        store.lockCase(caseId);
        var prior=store.replay(caseId,key);
        if(prior.isPresent()){
            var receipt=prior.orElseThrow();
            if(!"CLOSE".equals(receipt.operation())||!digest.equals(receipt.digest())
                    ||!actor.id().equals(receipt.actorId()))throw conflict();
            return new CaseResult(store.caseView(caseId),true);
        }
        if(store.history(caseId,0,1).total()!=command.expectedVersion())throw conflict();
        String assessmentId = store.admittedAssessment(caseId)
                .orElseThrow(HseCapaLifecycleApplicationService::conflict);
        var states=store.states(caseId);
        boolean allAccepted=!states.isEmpty();
        for(var item:states){
            var evidences=store.evidence(item.capaId());
            var reviews=store.verification(item.capaId());
            if(evidences.isEmpty()||reviews.stream().noneMatch(v->
                        v.finding()==HseCapaVerification.Finding.EFFECTIVE
                        &&evidences.stream().anyMatch(e->e.id().equals(v.evidenceId()))))
                allAccepted=false;
            for(var ev:evidences) {
                var live=documents.accepted(company,facility,ev.documentId(),ev.versionId())
                        .orElseThrow(HseCapaLifecycleApplicationService::hidden);
                sameDocumentEvidence(ev,live,company,facility);
            }
        }
        incidents.reviewed(scope.incidentId(),company,facility)
                .orElseThrow(HseCapaLifecycleApplicationService::hidden);
        var decision=workflows.approved(text(command.referenceId(),80),"HSE_CASE",caseId)
                .orElseThrow(HseCapaLifecycleApplicationService::hidden);
        if(actor.id().equals(decision.approverId())||scope.actorId().equals(decision.approverId())
                ||states.stream().anyMatch(s->decision.approverId().equals(s.ownerId())))
            throw new SecurityException("Independent HSE case closure review required.");
        guard.mayCloseCase(states.stream().map(HseCapaLifecycleStorePort.State::status).toList(),
                allAccepted,true,decision.digest()!=null);
        store.closeCase(caseId,actor.id(),actor.displayName(),reason,decision,key,digest,
                command.expectedVersion(),assessmentId,Instant.now());
        return new CaseResult(store.caseView(caseId),false);
    }
    @Override @Transactional
    public Page<EventView> history(String company,String facility,String caseId,int page,int size){
        current(READ,company,facility);bound(caseId,company,facility);page(page,size);
        return store.history(caseId,page,size);
    }
    private HseCapaAuthorityPort.Actor current(String permission,String company,String facility){
        var actor=authority.require(permission,text(company,120),text(facility,120));
        var facilityProof=topology.active(facility).orElseThrow(HseCapaLifecycleApplicationService::hidden);
        if(!company.equals(facilityProof.companyId()))throw hidden();
        return actor;
    }
    private HseCapaLifecycleStorePort.Scope bound(String caseId,String company,String facility){
        var scope=store.scope(text(caseId,80)).orElseThrow(HseCapaLifecycleApplicationService::hidden);
        if(!company.equals(scope.companyId())||!facility.equals(scope.facilityId()))throw hidden();
        // The HSE scope is not permission to bypass current Incident owner access.
        incidents.reviewed(scope.incidentId(),company,facility)
                .orElseThrow(HseCapaLifecycleApplicationService::hidden);
        return scope;
    }
    private CapaResult existingCapa(HseCapaLifecycleStorePort.Receipt prior,String digest,
            String actor,String company,String facility){
        if(!digest.equals(prior.digest())||!actor.equals(prior.actorId())
                ||prior.capaId()==null)throw conflict();
        if("APPROVE".equals(prior.operation())){
            var accepted=workflows.approved(prior.referenceId(),"HSE_CAPA",prior.capaId())
                    .orElseThrow(HseCapaLifecycleApplicationService::hidden);
            if(!actor.equals(accepted.approverId()))throw hidden();
        }
        if(java.util.Set.of("EVIDENCE","COMPLETE","VERIFY").contains(prior.operation())){
            var snapshots=store.evidence(prior.capaId());
            if(snapshots.isEmpty())throw hidden();
            for(var snapshot:snapshots) {
                var fresh=documents.accepted(company,facility,snapshot.documentId(),snapshot.versionId())
                        .orElseThrow(HseCapaLifecycleApplicationService::hidden);
                sameDocumentEvidence(snapshot,fresh,company,facility);
            }
        }
        return new CapaResult(store.capaView(prior.caseId(),prior.capaId()),true);
    }
    private static void sameDocumentEvidence(HseCapaEvidence stored,
            HseCapaDocumentsPort.Evidence current,String company,String facility){
        if(!stored.documentId().equals(current.documentId())
                ||!stored.versionId().equals(current.versionId())
                ||!stored.acceptanceId().equals(current.acceptanceId())
                ||!stored.sha256().equals(current.sha256())
                ||stored.contentLength()!=current.contentLength()
                ||!company.equals(current.companyId())||!facility.equals(current.facilityId())
                ||!company.equals(stored.companyId())||!facility.equals(stored.facilityId()))
            throw hidden();
    }
    private static void requireOwner(HseCapaLifecycleStorePort.State state,String actor){
        if(!actor.equals(state.ownerId()))throw new SecurityException("Current CAPA owner required.");
    }
    private static String text(String value,int max){
        if(value==null||value.isBlank()||value.length()>max||!value.equals(value.strip()))
            throw new IllegalArgumentException("Invalid bounded HSE value.");
        return value;
    }
    private static void page(int page,int size){
        if(page<0||size<1||size>100)throw new IllegalArgumentException("Invalid HSE page.");
    }
    private static String digest(String... values){
        try{
            MessageDigest hash=MessageDigest.getInstance("SHA-256");
            for(String v:values){
                byte[] bytes=String.valueOf(v).getBytes(StandardCharsets.UTF_8);
                hash.update(ByteBuffer.allocate(4).putInt(bytes.length).array());
                hash.update(bytes);
            }
            return HexFormat.of().formatHex(hash.digest());
        }catch(NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private static NoSuchElementException hidden(){
        return new NoSuchElementException("Protected HSE resource unavailable.");
    }
    private static IllegalStateException conflict(){return new IllegalStateException("Enrolled HSE command conflict.");}
}
