/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Name        : AlarmScopedAuthorizationQueryService
 * @UpdatedOn   : 2026-11-08
 * @Type        : Class
 * @Layer       : Class
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 * @Description : Requires two exact native grants and full DENY-precedence evaluation.
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.alarm.AlarmScopedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.port.out.AlarmExplicitGrantEvidencePort;
import dz.sh.hidra.modules.identity.application.port.out.FoundationIdentityAuthorizationPort;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationEvidencePort;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationAssertionPort;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlarmScopedAuthorizationQueryService implements AlarmScopedAuthorizationContract {
    private final FoundationIdentityAuthorizationPort actors;
    private final AlarmExplicitGrantEvidencePort direct;
    private final AuthorizationEvidencePort evidence;
    private final AuthorizationAssertionPort assertions;
    private final AuthorizationPolicyEvaluator policy=new AuthorizationPolicyEvaluator();
    public AlarmScopedAuthorizationQueryService(FoundationIdentityAuthorizationPort actors,
            AlarmExplicitGrantEvidencePort direct,AuthorizationEvidencePort evidence,
            AuthorizationAssertionPort assertions) {
        this.actors=Objects.requireNonNull(actors);this.direct=Objects.requireNonNull(direct);
        this.evidence=Objects.requireNonNull(evidence);this.assertions=Objects.requireNonNull(assertions);
    }
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public boolean permitted(String permission,String company,String type,String id) {
        if(permission==null||!permission.startsWith("alarm:")||company==null||company.isBlank()
                ||id==null||id.isBlank())return false;
        final ScopeType assetType=switch(type==null?"":type) {
            case "FACILITY" -> ScopeType.FACILITY;
            case "EQUIPMENT" -> ScopeType.EQUIPMENT;
            default -> null;
        };
        if(assetType==null)return false;
        String actor=actors.authenticatedActor();
        Instant at=Instant.now();
        var companyScope=new AuthorizationScope(ScopeType.CUSTOM,"alarm-company:"+company,null);
        var assetScope=new AuthorizationScope(assetType,id,null);
        // A GLOBAL route grant alone is never an exact company or asset resource grant.
        if(!direct.currentDirectPermit(actor,permission,companyScope.scopeType(),companyScope.scopeReferenceId(),at)
                ||!direct.currentDirectPermit(actor,permission,assetScope.scopeType(),assetScope.scopeReferenceId(),at))
            return false;
        var assertion=assertions.currentFor(actor);
        return permits(actor,permission,companyScope,assertion,at)
            &&permits(actor,permission,assetScope,assertion,at);
    }
    private boolean permits(String actor,String permission,AuthorizationScope scope,
            VerifiedAuthorizationAssertion assertion,Instant at){
        var request=new AuthorizationEvaluationRequest(actor,permission,null,null,scope);
        var decision=policy.evaluate(request,evidence.load(request,at,assertion),at);
        return decision.decision()==AuthorizationDecisionValue.PERMIT;
    }
}
