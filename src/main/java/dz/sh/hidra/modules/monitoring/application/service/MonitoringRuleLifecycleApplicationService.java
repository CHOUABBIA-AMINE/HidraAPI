/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleLifecycleApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.service
 *
 * @Description : Coordinates current grant checks, immutable versioning and atomic activation.
 *
 */
package dz.sh.hidra.modules.monitoring.application.service;

import dz.sh.hidra.modules.monitoring.application.port.in.ActivateMonitoringRuleRevisionUseCase;
import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleAuthorizationPort;
import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleRevisionPort;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRuleRevision;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringLifecycleStatus;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonitoringRuleLifecycleApplicationService implements ActivateMonitoringRuleRevisionUseCase {
    private final MonitoringRuleRevisionPort revisions;
    private final MonitoringRuleAuthorizationPort authorization;
    public MonitoringRuleLifecycleApplicationService(MonitoringRuleRevisionPort revisions,
            MonitoringRuleAuthorizationPort authorization){
        this.revisions=Objects.requireNonNull(revisions);this.authorization=Objects.requireNonNull(authorization);
    }
    private MonitoringRuleRevisionPort.RuleScope allowed(String ruleId,MonitoringRuleAuthorizationPort.Operation op){
        var scope=revisions.scope(ruleId).orElseThrow(
                ()->new AccessDeniedException("Protected Monitoring rule unavailable."));
        authorization.require(op,scope.assetType(),scope.assetId(),scope.company());
        return scope;
    }
    @Override @Transactional
    public MonitoringRuleRevision revise(String ruleId,Draft draft){
        Objects.requireNonNull(draft);
        var scope=allowed(ruleId,MonitoringRuleAuthorizationPort.Operation.REVISE);
        if(draft.validFrom()==null||draft.validTo()==null
                ||!draft.validFrom().isBefore(draft.validTo())
                ||!revisions.policyAvailable(draft.evidencePolicyCode()))
            throw new IllegalArgumentException("Missing or invalid rule policy/window.");
        var context=revisions.ruleContext(ruleId);
        if(context.ruleType()!=MonitoringRuleType.PLAN_COMPARISON||context.telemetryPointId()==null
                ||(context.status()!=MonitoringLifecycleStatus.DRAFT
                    &&context.status()!=MonitoringLifecycleStatus.ACTIVE))
            throw new IllegalArgumentException("Unsupported rule family or missing point.");
        authorization.requirePoint(context.telemetryPointId(),scope.assetType(),scope.assetId(),
                draft.unitId(),Instant.now());
        authorization.requirePoint(context.telemetryPointId(),scope.assetType(),scope.assetId(),
                draft.unitId(),draft.validFrom());
        var actor=authorization.require(MonitoringRuleAuthorizationPort.Operation.REVISE,
                scope.assetType(),scope.assetId(),scope.company()).actor();
        return revisions.create(new MonitoringRuleRevision(ruleId,1,scope.company(),scope.assetType(),
                scope.assetId(),context.telemetryPointId(),context.ruleType(),draft.unitId(),
                draft.evidencePolicyCode(),draft.validFrom(),draft.validTo(),actor,
                Instant.now(),null,null,null,null));
    }
    @Override @Transactional
    public MonitoringRuleRevision activate(String ruleId,long revision,Activation activation){
        Objects.requireNonNull(activation);
        var scope=allowed(ruleId,MonitoringRuleAuthorizationPort.Operation.ACTIVATE);
        var context=revisions.ruleContext(ruleId);
        if(context.status()!=MonitoringLifecycleStatus.DRAFT
                &&context.status()!=MonitoringLifecycleStatus.ACTIVE)
            throw new IllegalStateException("Monitoring rule lifecycle prohibits activation.");
        var candidate=revisions.exact(ruleId,revision).orElseThrow(
                ()->new NoSuchElementException("Protected Monitoring version unavailable."));
        if(!scope.company().equals(candidate.companyPartyId())
                ||!scope.assetType().equals(candidate.assetType())||!scope.assetId().equals(candidate.assetId())
                ||activation.effectiveFrom()==null||activation.effectiveUntil()==null
                ||activation.effectiveFrom().isBefore(candidate.validFrom())
                ||activation.effectiveUntil().isAfter(candidate.validTo())
                ||!activation.effectiveFrom().isBefore(activation.effectiveUntil())
                ||!revisions.policyAvailable(candidate.evidencePolicyCode()))
            throw new IllegalArgumentException("Ineligible rule activation.");
        authorization.requirePoint(candidate.telemetryPointId(),candidate.assetType(),candidate.assetId(),
                candidate.unitId(),Instant.now());
        authorization.requirePoint(candidate.telemetryPointId(),candidate.assetType(),candidate.assetId(),
                candidate.unitId(),activation.effectiveFrom());
        var actor=authorization.require(MonitoringRuleAuthorizationPort.Operation.ACTIVATE,
                scope.assetType(),scope.assetId(),scope.company()).actor();
        return revisions.activate(ruleId,revision,activation.expectedHeadRevision(),
                activation.effectiveFrom(),activation.effectiveUntil(),actor);
    }
    @Override @Transactional
    public MonitoringRuleRevision exact(String ruleId,long revision){
        allowed(ruleId,MonitoringRuleAuthorizationPort.Operation.READ);
        return revisions.exact(ruleId,revision).orElseThrow(
                ()->new NoSuchElementException("Protected Monitoring version unavailable."));
    }
    @Override @Transactional
    public MonitoringRuleRevision effective(String ruleId,Instant at){
        if(at==null)throw new IllegalArgumentException("Effective instant required.");
        allowed(ruleId,MonitoringRuleAuthorizationPort.Operation.READ);
        return revisions.effective(ruleId,at).orElseThrow(
                ()->new NoSuchElementException("Protected Monitoring version unavailable."));
    }
    @Override @Transactional
    public List<MonitoringRuleRevision> history(String ruleId,int limit){
        allowed(ruleId,MonitoringRuleAuthorizationPort.Operation.READ);
        if(limit<1||limit>100)throw new IllegalArgumentException("Bounded history required.");
        return revisions.history(ruleId,limit);
    }
}
