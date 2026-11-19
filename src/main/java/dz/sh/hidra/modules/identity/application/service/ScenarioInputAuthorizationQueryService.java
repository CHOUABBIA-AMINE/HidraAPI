/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScenarioInputAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 * @Description : Rechecks direct grants and deny precedence for a signed LOCAL actor in the owner transaction.
 */
package dz.sh.hidra.modules.identity.application.service;


import dz.sh.hidra.modules.identity.application.contract.simulation.ScenarioInputAuthorizationContract;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationAssertionPort;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationEvidencePort;
import dz.sh.hidra.modules.identity.application.port.out.IncidentExplicitGrantEvidencePort;
import dz.sh.hidra.modules.identity.application.port.out.TelemetryNativeLocalActorPort;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** No broad role, global, indirect or inferred authorization substitutes for paired direct grants. */
@Service
public class ScenarioInputAuthorizationQueryService implements ScenarioInputAuthorizationContract {
    private static final Set<String> ALLOWED=Set.of(READ,WRITE);
    private final TelemetryNativeLocalActorPort nativeActors;
    private final IncidentExplicitGrantEvidencePort direct;
    private final AuthorizationAssertionPort assertions;
    private final AuthorizationEvidencePort evidence;
    private final AuthorizationPolicyEvaluator evaluator=new AuthorizationPolicyEvaluator();

    public ScenarioInputAuthorizationQueryService(TelemetryNativeLocalActorPort nativeActors,
            IncidentExplicitGrantEvidencePort direct,AuthorizationAssertionPort assertions,
            AuthorizationEvidencePort evidence) {
        this.nativeActors=Objects.requireNonNull(nativeActors);
        this.direct=Objects.requireNonNull(direct);
        this.assertions=Objects.requireNonNull(assertions);
        this.evidence=Objects.requireNonNull(evidence);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public String require(String permission,String companyId,String pipelineSystemId) {
        if(!ALLOWED.contains(permission) || !id(companyId,80)||!id(pipelineSystemId,80))throw denied();
        String actor=nativeActors.currentLocalActor();
        String company="simulation-company:"+companyId;
        Instant now=Instant.now();
        var c=new AuthorizationScope(ScopeType.CUSTOM,company,null);
        var p=new AuthorizationScope(ScopeType.PIPELINE_SYSTEM,pipelineSystemId,null);
        if(!direct.currentDirectPermit(actor,permission,ScopeType.CUSTOM,company,now)
                ||!direct.currentDirectPermit(actor,permission,ScopeType.PIPELINE_SYSTEM,pipelineSystemId,now))
            throw denied();
        VerifiedAuthorizationAssertion assertion=assertions.currentFor(actor);
        if(!permit(actor,permission,c,assertion,now)||!permit(actor,permission,p,assertion,now))
            throw denied();
        // Both scopes and the bearer were resolved anew inside this transaction.
        return actor;
    }
    private boolean permit(String actor,String permission,AuthorizationScope scope,
                           VerifiedAuthorizationAssertion assertion,Instant at) {
        var request=new AuthorizationEvaluationRequest(actor,permission,null,null,scope);
        return evaluator.evaluate(request,evidence.load(request,at,assertion),at).decision()
                ==AuthorizationDecisionValue.PERMIT;
    }
    private static boolean id(String id,int limit) {
        return id!=null&&!id.isBlank()&&id.equals(id.strip())&&id.length()<=limit
               &&id.codePoints().noneMatch(Character::isISOControl);
    }
    private static NoSuchElementException denied() {
        return new NoSuchElementException("Scoped scenario inputs unavailable");
    }
}
