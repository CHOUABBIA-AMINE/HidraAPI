/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleScopedAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Requires paired current LOCAL actor grants and full DENY precedence.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.monitoring.MonitoringRuleScopedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.application.port.out.AlarmExplicitGrantEvidencePort;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationAssertionPort;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationEvidencePort;
import dz.sh.hidra.modules.identity.application.port.out.TelemetryNativeLocalActorPort;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonitoringRuleScopedAuthorizationQueryService
        implements MonitoringRuleScopedAuthorizationContract {
    private final TelemetryNativeLocalActorPort actors;
    private final AlarmExplicitGrantEvidencePort grants;
    private final AuthorizationAssertionPort assertions;
    private final AuthorizationEvidencePort evidence;
    private final AuthorizationPolicyEvaluator policy=new AuthorizationPolicyEvaluator();
    public MonitoringRuleScopedAuthorizationQueryService(TelemetryNativeLocalActorPort actors,
            AlarmExplicitGrantEvidencePort grants,AuthorizationAssertionPort assertions,
            AuthorizationEvidencePort evidence){
        this.actors=Objects.requireNonNull(actors);this.grants=Objects.requireNonNull(grants);
        this.assertions=Objects.requireNonNull(assertions);this.evidence=Objects.requireNonNull(evidence);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public String require(Operation op,String company,String type,String assetId){
        if(op==null||!valid(company)||!valid(assetId))throw denied();
        ScopeType assetType=switch(type==null?"":type){
            case "FACILITY" -> ScopeType.FACILITY;
            case "EQUIPMENT" -> ScopeType.EQUIPMENT;
            default -> throw denied();
        };
        String actor=actors.currentLocalActor();
        String permission=switch(op){
            case CREATE -> "monitoring:rule:create";
            case REVISE -> "monitoring:rule:revise";
            case ACTIVATE -> "monitoring:rule:activate";
            case EVALUATE -> "monitoring:rule:evaluate";
            case READ -> "monitoring:rule:read";
        };
        Instant at=Instant.now();
        var companyScope=new AuthorizationScope(ScopeType.CUSTOM,"monitoring-company:"+company,null);
        var assetScope=new AuthorizationScope(assetType,assetId,null);
        if(companyScope.scopeReferenceId().length()>120
                ||!grants.currentDirectPermit(actor,permission,ScopeType.CUSTOM,companyScope.scopeReferenceId(),at)
                ||!grants.currentDirectPermit(actor,permission,assetType,assetId,at))
            throw denied();
        VerifiedAuthorizationAssertion assertion=assertions.currentFor(actor);
        if(!permitted(actor,permission,companyScope,assertion,at)
                ||!permitted(actor,permission,assetScope,assertion,at))throw denied();
        return actor;
    }
    private boolean permitted(String actor,String permission,AuthorizationScope scope,
            VerifiedAuthorizationAssertion assertion,Instant at){
        var request=new AuthorizationEvaluationRequest(actor,permission,null,null,scope);
        return policy.evaluate(request,evidence.load(request,at,assertion),at).decision()
                == AuthorizationDecisionValue.PERMIT;
    }
    private static boolean valid(String s){return s!=null&&!s.isBlank()&&s.equals(s.strip());}
    private static AccessDeniedException denied(){
        return new AccessDeniedException("Protected Monitoring rule unavailable.");
    }
}
