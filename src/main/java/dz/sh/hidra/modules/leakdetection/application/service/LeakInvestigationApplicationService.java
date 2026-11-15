/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakInvestigationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.service
 * @Description : Authorized Leak candidate investigation, independent human disposition and protected history.
 */
package dz.sh.hidra.modules.leakdetection.application.service;

import dz.sh.hidra.modules.leakdetection.application.port.in.LeakInvestigationUseCase;
import dz.sh.hidra.modules.leakdetection.application.port.out.*;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionCase;
import dz.sh.hidra.modules.leakdetection.domain.service.LeakInvestigationGuard;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakDetectionCaseStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakVerificationActionType;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeakInvestigationApplicationService implements LeakInvestigationUseCase {
    private static final String READ="leakdetection:candidates:read";
    private static final String EXECUTE="leakdetection:candidates:execute";
    private static final String REVIEW="leakdetection:cases:review";
    private final LeakInvestigationStorePort store;
    private final LeakCandidateResourceAuthorizationPort scope;
    private final LeakCandidateRepositoryPort candidates;
    private final LeakDetectionCaseRepositoryPort cases;
    private final LeakEvaluationTriggerPort triggers;
    private final LeakEvidenceLinkPort links;
    private final LeakMonitoringEvidencePort monitoring;
    private final LeakInvestigationGuard guard=new LeakInvestigationGuard();

    public LeakInvestigationApplicationService(LeakInvestigationStorePort store,
            LeakCandidateResourceAuthorizationPort scope,LeakCandidateRepositoryPort candidates,
            LeakDetectionCaseRepositoryPort cases,LeakEvaluationTriggerPort triggers,
            LeakEvidenceLinkPort links,LeakMonitoringEvidencePort monitoring) {
        this.store=Objects.requireNonNull(store);this.scope=Objects.requireNonNull(scope);
        this.candidates=Objects.requireNonNull(candidates);this.cases=Objects.requireNonNull(cases);
        this.triggers=Objects.requireNonNull(triggers);this.links=Objects.requireNonNull(links);
        this.monitoring=Objects.requireNonNull(monitoring);
    }

    @Override @Transactional
    public Receipt open(String candidateId, String requestId) {
        String id=required(candidateId,80),key=required(requestId,80);
        scope.requireCandidate(EXECUTE,id);
        var candidate=candidates.findById(id).orElseThrow(LeakInvestigationApplicationService::hidden);
        var source=currentSource(id);
        if(!source.assetType().equals(candidate.topologyAssetType())
                ||!source.assetId().equals(candidate.topologyAssetId())
                ||!List.of("NEW","UNDER_REVIEW").contains(candidate.status().name()))
            throw hidden();
        String actor=scope.requireAsset(EXECUTE,candidate.topologyAssetType(),candidate.topologyAssetId());
        String hash=digest(key+"|"+id+"|"+source.evaluationId());
        store.serializeCandidate(id);
        var prior=store.caseForCandidate(id);
        if(prior.isPresent()) {
            var existing=store.replay(prior.orElseThrow(),key,hash).orElseThrow(
                    ()->new IllegalStateException("Candidate already has a different governed case."));
            if(!actor.equals(existing.actorId()))throw new IllegalStateException("Leak opening replay actor mismatch.");
            return replay(existing);
        }
        Instant at=Instant.now();
        String caseId=UUID.randomUUID().toString();
        var row=new LeakDetectionCase(caseId,"T16-"+UUID.randomUUID(),id,
                candidate.topologyAssetType(),candidate.topologyAssetId(),candidate.topologyAssetCode(),
                null,LeakDetectionCaseStatus.OPEN,candidate.severityLevel(),candidate.confidenceScore(),
                at,null,actor,null,null,candidate.correlationId(),at,at);
        cases.save(row); // JPA Topology ownership validates before the T16 same-transaction head is flushed.
        store.register(caseId,id,key,hash,actor,at);
        return store.replay(caseId,key,hash).orElseThrow();
    }

    @Override @Transactional
    public Receipt execute(String rawId, Command c) {
        Objects.requireNonNull(c,"Leak investigation request required.");
        String id=required(rawId,80),key=required(c.requestId(),80),op=required(c.operation(),20);
        if(c.expectedVersion()<0 || !List.of("ASSIGN","EVIDENCE","ACTION","SUBMIT","REVIEW").contains(op))
            throw new IllegalArgumentException("Invalid Leak investigation operation or revision.");
        var head=store.view(id); // Existence is never returned before the current case/asset grant.
        scope.requireCandidate("REVIEW".equals(op)?REVIEW:EXECUTE,head.candidateId());
        var source=currentSource(head.candidateId());
        var candidate=candidates.findById(head.candidateId()).orElseThrow(LeakInvestigationApplicationService::hidden);
        if(!candidate.topologyAssetType().equals(source.assetType())
                ||!candidate.topologyAssetId().equals(source.assetId()))throw hidden();
        String actor=scope.requireAsset("REVIEW".equals(op)?REVIEW:EXECUTE,
                candidate.topologyAssetType(),candidate.topologyAssetId());
        String hash=digest(c);
        var replay=store.replay(id,key,hash);
        if(replay.isPresent()) {
            if(!actor.equals(replay.orElseThrow().actorId()))
                throw new IllegalStateException("Leak replay actor mismatch.");
            return replay(replay.orElseThrow());
        }
        var state=store.lock(id);
        replay=store.replay(id,key,hash);
        if(replay.isPresent()) {
            if(!actor.equals(replay.orElseThrow().actorId()))
                throw new IllegalStateException("Leak replay actor mismatch.");
            return replay(replay.orElseThrow());
        }
        if(!head.candidateId().equals(state.candidateId()) || state.version()!=c.expectedVersion())
            throw new IllegalStateException("Stale or mismatched Leak case revision.");
        guard.require(state.parentStatus(),state.state(),op,state.investigatorId(),
                actor,state.hasEvidence(),state.hasAction());
        switch(op) {
            case "ASSIGN" -> required(c.detail(),1000);
            case "EVIDENCE" -> {
                if(!"MONITORING_DEVIATION".equals(required(c.referenceType(),40))
                        ||!source.evaluationId().equals(required(c.referenceId(),80))
                        ||!links.hasEvaluation(state.candidateId(),source.evaluationId()))
                    throw hidden();
                if(!List.of("SUPPORTING","CONTRADICTING").contains(c.direction()))
                    throw new IllegalArgumentException("Evidence direction required.");
                required(c.detail(),1000);
            }
            case "ACTION" -> {
                LeakVerificationActionType.valueOf(required(c.referenceType(),80));
                // A declared action is a documentary result, not direct physical proof.
                required(c.detail(),1000);
            }
            case "SUBMIT" -> {
                if(!List.of("DISMISSED","VERIFIED_LEAK","INSUFFICIENT_EVIDENCE").contains(c.disposition()))
                    throw new IllegalArgumentException("Typed Leak disposition required.");
                required(c.detail(),1000);
            }
            case "REVIEW" -> {
                if(!List.of("APPROVE","REJECT").contains(c.direction()))
                    throw new IllegalArgumentException("Independent review verdict required.");
                required(c.detail(),1000);
            }
            default -> throw new IllegalArgumentException("Unknown Leak action.");
        }
        return store.append(state,c,actor,hash,Instant.now());
    }

    @Override @Transactional
    public View history(String rawId) {
        String id=required(rawId,80);
        var head=store.view(id);
        scope.requireCandidate(READ,head.candidateId());
        currentSource(head.candidateId());
        return new View(id,head.candidateId(),head.parentStatus(),head.state(),head.version(),
                head.investigatorId(),head.submittedById(),head.reviewedById(),head.disposition(),
                store.history(id));
    }

    private LeakMonitoringEvidencePort.Snapshot currentSource(String id) {
        var t=triggers.byCandidate(id).orElseThrow(LeakInvestigationApplicationService::hidden);
        var src=monitoring.authorized(t.evaluationId()); // Monitoring retains independent READ authority.
        if(!src.evaluationId().equals(t.evaluationId())
                ||!src.companyPartyId().equals(t.companyPartyId())
                ||!src.assetType().equals(t.assetType())||!src.assetId().equals(t.assetId())
                ||!links.hasEvaluation(id,t.evaluationId()))throw hidden();
        return src;
    }
    private static Receipt replay(Receipt x) {
        return new Receipt(x.caseId(),x.eventId(),x.operation(),x.version(),
                x.investigationState(),x.actorId(),x.recordedAt(),true);
    }
    private static NoSuchElementException hidden(){
        return new NoSuchElementException("Protected Leak case unavailable.");
    }
    private static String required(String s,int max) {
        if(s==null||s.isBlank()||s.length()>max||!s.equals(s.strip()))
            throw new IllegalArgumentException("Invalid Leak investigation declaration.");
        return s;
    }
    private static String digest(Command c){
        StringBuilder b=new StringBuilder();
        for(String s:new String[]{c.requestId(),String.valueOf(c.expectedVersion()),c.operation(),
                c.referenceType(),c.referenceId(),c.direction(),c.detail(),c.disposition()}){
            String v=s==null?"":s;b.append(s==null?"N":"V").append(v.length()).append(':').append(v);
        }
        return digest(b.toString());
    }
    private static String digest(String s){
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(
                s.getBytes(StandardCharsets.UTF_8)));}
        catch(NoSuchAlgorithmException e){throw new IllegalStateException(e);}
    }
}
