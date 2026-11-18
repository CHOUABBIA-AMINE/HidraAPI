/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaScopedAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 * @Description : Fail-closed current direct grant and policy evaluation for enrolled HSE CAPA operations.
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.hse.HseCapaScopedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.hse.HseActorContract;
import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class HseCapaScopedAuthorizationQueryService implements HseCapaScopedAuthorizationContract {
    private static final Set<String> PERMISSIONS=Set.of("hse:capa:create","hse:capa:read",
            "hse:capa:execute","hse:capa:review","hse:capa:close","hse:capa:assess");
    private final TelemetryNativeLocalActorPort local;
    private final HseActorContract eligible;
    private final IncidentExplicitGrantEvidencePort direct;
    private final AuthorizationEvidencePort evidence;
    private final AuthorizationAssertionPort assertions;
    private final AuthorizationPolicyEvaluator policy=new AuthorizationPolicyEvaluator();
    public HseCapaScopedAuthorizationQueryService(TelemetryNativeLocalActorPort local,
            HseActorContract eligible,IncidentExplicitGrantEvidencePort direct,
            AuthorizationEvidencePort evidence,AuthorizationAssertionPort assertions){
        this.local=local;this.eligible=eligible;this.direct=direct;this.evidence=evidence;
        this.assertions=assertions;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Actor require(String permission,String companyId,String facilityId){
        if(!PERMISSIONS.contains(permission)||companyId==null||companyId.isBlank()||companyId.length()>96
                ||facilityId==null||facilityId.length()>120||facilityId.isBlank())throw new SecurityException("HSE CAPA operation denied.");
        String actorId=local.currentLocalActor();
        Instant at=Instant.now();
        var actor=eligible.eligibleActor(actorId,at).filter(a->actorId.equals(a.id()))
                .orElseThrow(()->new SecurityException("HSE current actor unavailable."));
        // Registered T17 uses the company CUSTOM authority namespace already present at T24.
        var company=new AuthorizationScope(ScopeType.CUSTOM,"documents-company:"+companyId,null);
        var facility=new AuthorizationScope(ScopeType.FACILITY,facilityId,null);
        if(company.scopeReferenceId().length()>120
                ||!direct.currentDirectPermit(actorId,permission,ScopeType.CUSTOM,company.scopeReferenceId(),at)
                ||!direct.currentDirectPermit(actorId,permission,ScopeType.FACILITY,facilityId,at))
            throw new SecurityException("HSE CAPA operation denied.");
        var assertion=assertions.currentFor(actorId);
        if(!permitted(actorId,permission,company,assertion,at)
                ||!permitted(actorId,permission,facility,assertion,at))
            throw new SecurityException("HSE CAPA operation denied.");
        return new Actor(actor.id(),actor.displayName());
    }
    private boolean permitted(String actorId,String permission,AuthorizationScope scope,
            dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion assertion,Instant at){
        var req=new AuthorizationEvaluationRequest(actorId,permission,null,null,scope);
        return policy.evaluate(req,evidence.load(req,at,assertion),at).decision()==AuthorizationDecisionValue.PERMIT;
    }
}
