/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : EvidenceLinkedLeakCandidateApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.service
 * @Description : T13 atomic suspected-leak evidence and ownership handling.
 */
package dz.sh.hidra.modules.leakdetection.application.service;

import dz.sh.hidra.modules.leakdetection.application.port.in.EvidenceLinkedLeakCandidateUseCase;
import dz.sh.hidra.modules.leakdetection.application.port.out.*;
import dz.sh.hidra.modules.leakdetection.domain.exception.LeakEvaluationTriggerConflictException;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakCandidate;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakEvaluationTrigger;
import dz.sh.hidra.modules.leakdetection.domain.service.LeakConfidenceClassifier;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakCandidateStatus;
import dz.sh.hidra.modules.topology.application.contract.leakdetection.LeakDetectionTopologyAssetContract;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** T13 is a human suspicion declaration, never a leak algorithm or safety verdict. */
@Service
public class EvidenceLinkedLeakCandidateApplicationService implements EvidenceLinkedLeakCandidateUseCase {
    private static final String WRITE="leakdetection:candidates:execute";
    private static final String READ="leakdetection:candidates:read";
    private final LeakMonitoringEvidencePort monitoring;
    private final LeakCandidateResourceAuthorizationPort scope;
    private final LeakEvaluationTriggerPort triggers;
    private final LeakEvidenceLinkPort links;
    private final LeakCandidateRepositoryPort candidates;
    private final LeakDetectionTopologyAssetContract topology;
    private final LeakConfidenceClassifier classifier;

    public EvidenceLinkedLeakCandidateApplicationService(LeakMonitoringEvidencePort monitoring,
            LeakCandidateResourceAuthorizationPort scope, LeakEvaluationTriggerPort triggers,
            LeakEvidenceLinkPort links, LeakCandidateRepositoryPort candidates,
            LeakDetectionTopologyAssetContract topology, LeakConfidenceClassifier classifier) {
        this.monitoring=Objects.requireNonNull(monitoring);
        this.scope=Objects.requireNonNull(scope);
        this.triggers=Objects.requireNonNull(triggers);
        this.links=Objects.requireNonNull(links);
        this.candidates=Objects.requireNonNull(candidates);
        this.topology=Objects.requireNonNull(topology);
        this.classifier=Objects.requireNonNull(classifier);
    }

    @Override @Transactional
    public Result create(Command cmd) {
        Objects.requireNonNull(cmd,"Candidate request is required.");
        String evaluationId=required(cmd.evaluationId(),80);
        String profileId=required(cmd.profileId(),80);
        String reason=required(cmd.reason(),500);
        BigDecimal confidence=Objects.requireNonNull(cmd.declaredConfidence(),"Declared confidence required.");
        if(confidence.compareTo(BigDecimal.ZERO)<0||confidence.compareTo(BigDecimal.ONE)>0
                ||confidence.scale()>6)throw new IllegalArgumentException("Invalid declared confidence.");

        var src=monitoring.authorized(evaluationId); // Monitoring independently checks current source READ.
        String actor=scope.requireSource(WRITE,src.companyPartyId(),src.assetType(),src.assetId());
        qualify(src);
        var profile=candidates.profile(profileId).orElseThrow(EvidenceLinkedLeakCandidateApplicationService::unavailable);
        if(!profile.active()||!profile.assetType().equals(src.assetType())
                ||!profile.assetId().equals(src.assetId()))throw unavailable();
        var asset=topology.resolve(src.assetType(),src.assetId());
        if(!asset.supported()||!asset.exists())throw unavailable();

        triggers.serialize(evaluationId);
        var old=triggers.byEvaluation(evaluationId);
        if(old.isPresent()) {
            var trigger=old.orElseThrow();
            sameSource(trigger,src);
            if(!trigger.sameCommand(profileId,confidence,reason))throw new LeakEvaluationTriggerConflictException();
            if(!links.hasEvaluation(trigger.candidateId(),evaluationId))throw new IllegalStateException("Missing immutable Leak evidence.");
            return result(candidates.findById(trigger.candidateId()).orElseThrow(
                    EvidenceLinkedLeakCandidateApplicationService::unavailable),trigger,false);
        }

        Instant now=Instant.now();
        String candidateId=UUID.randomUUID().toString();
        var candidate=new LeakCandidate(candidateId,null,profileId,"T13-"+UUID.randomUUID(),
                src.assetType(),src.assetId(),asset.code(),asset.name(),src.evaluatedAt(),
                src.evaluatedAt(),confidence,classifier.classify(confidence),LeakCandidateStatus.NEW,
                reason,UUID.randomUUID().toString(),now,now);
        candidates.save(candidate); // Existing ACTIVE profile guard retained.
        candidates.flush(); // Required before JDBC trigger FK and identity checks on this transaction.
        var trigger=new LeakEvaluationTrigger(src.evaluationId(),candidateId,src.ruleId(),src.ruleRevision(),
                src.companyPartyId(),src.assetType(),src.assetId(),src.unitId(),src.targetId(),
                src.approvalId(),src.trustedReadingId(),src.evaluatedAt(),profileId,confidence,
                reason,actor,now);
        triggers.save(trigger);
        links.linkEvaluation(candidateId,evaluationId);
        return result(candidate,trigger,true);
    }

    @Override @Transactional
    public Result evidence(String candidateId) {
        scope.requireCandidate(READ,candidateId); // No trigger existence disclosed before owner READ.
        var trigger=triggers.byCandidate(candidateId).orElseThrow(
                EvidenceLinkedLeakCandidateApplicationService::unavailable);
        var src=monitoring.authorized(trigger.evaluationId());
        sameSource(trigger,src);
        if(!links.hasEvaluation(candidateId,trigger.evaluationId()))throw new IllegalStateException("Missing immutable Leak evidence.");
        return result(candidates.findById(candidateId).orElseThrow(
                EvidenceLinkedLeakCandidateApplicationService::unavailable),trigger,false);
    }

    private static void qualify(LeakMonitoringEvidencePort.Snapshot src) {
        if(!"COMPUTED".equals(src.outcome())||src.difference()==null
                ||src.difference().signum()==0||src.ruleRevision()<=0
                ||src.unitId()==null||src.targetId()==null||src.approvalId()==null
                ||src.trustedReadingId()==null||src.evaluatedAt()==null)
            throw new IllegalArgumentException("Only an approved, computed nonzero observation is eligible.");
    }
    private static void sameSource(LeakEvaluationTrigger t,LeakMonitoringEvidencePort.Snapshot s) {
        if(!t.sameSource(s.ruleId(),s.ruleRevision(),s.companyPartyId(),s.assetType(),s.assetId(),
                s.unitId(),s.targetId(),s.approvalId(),s.trustedReadingId(),s.evaluatedAt()))
            throw unavailable();
    }
    private static Result result(LeakCandidate c,LeakEvaluationTrigger t,boolean created) {
        return new Result(c.id(),t.evaluationId(),t.ruleId(),t.ruleRevision(),t.profileId(),
                t.companyPartyId(),t.assetType(),t.assetId(),t.unitId(),t.targetId(),
                t.approvalId(),t.trustedReadingId(),t.evaluatedAt(),t.declaredConfidence(),
                t.reason(),c.status().name(),created);
    }
    private static String required(String v,int max) {
        if(v==null||v.isBlank()||v.length()>max||!v.equals(v.strip()))
            throw new IllegalArgumentException("Missing or invalid candidate declaration.");
        return v;
    }
    private static NoSuchElementException unavailable() {
        return new NoSuchElementException("Protected Leak candidate unavailable.");
    }
}
