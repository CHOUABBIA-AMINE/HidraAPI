/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakDetectionScopedAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 * @Description : T13 exact owner contract and fail-closed source/asset authority.
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.leakdetection.LeakDetectionScopedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.port.out.LeakDetectionExplicitGrantEvidencePort;
import dz.sh.hidra.modules.identity.application.port.out.TelemetryNativeLocalActorPort;
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

/** No global grant can substitute for both current company and exact asset grants. */
@Service
public class LeakDetectionScopedAuthorizationQueryService implements LeakDetectionScopedAuthorizationContract {
    private final TelemetryNativeLocalActorPort actors;
    private final LeakDetectionExplicitGrantEvidencePort grants;
    private final AuthorizationEvidencePort evidence;
    private final AuthorizationAssertionPort assertions;
    private final AuthorizationPolicyEvaluator policy = new AuthorizationPolicyEvaluator();

    public LeakDetectionScopedAuthorizationQueryService(TelemetryNativeLocalActorPort actors,
            LeakDetectionExplicitGrantEvidencePort grants,
            AuthorizationEvidencePort evidence, AuthorizationAssertionPort assertions) {
        this.actors=Objects.requireNonNull(actors);
        this.grants=Objects.requireNonNull(grants);
        this.evidence=Objects.requireNonNull(evidence);
        this.assertions=Objects.requireNonNull(assertions);
    }
    @Override
    public String authenticatedActor() { return actors.currentLocalActor(); }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public boolean permitted(String permission,String company,String type,String assetId) {
        if (permission==null || !java.util.Set.of("leakdetection:candidates:execute",
                "leakdetection:candidates:read").contains(permission)
                || company==null || company.isBlank() || assetId==null || assetId.isBlank()) return false;
        ScopeType assetType=switch(type==null?"":type) {
            case "FACILITY" -> ScopeType.FACILITY;
            case "EQUIPMENT" -> ScopeType.EQUIPMENT;
            default -> null;
        };
        if(assetType==null)return false;
        String actor=actors.currentLocalActor();
        Instant at=Instant.now();
        var companyScope=new AuthorizationScope(ScopeType.CUSTOM,"leakdetection-company:"+company,null);
        var assetScope=new AuthorizationScope(assetType,assetId,null);
        if(companyScope.scopeReferenceId().length()>120
                ||!grants.currentDirectPermit(actor,permission,companyScope.scopeType(),companyScope.scopeReferenceId(),at)
                ||!grants.currentDirectPermit(actor,permission,assetType,assetId,at))return false;
        VerifiedAuthorizationAssertion assertion=assertions.currentFor(actor);
        return permitted(actor,permission,companyScope,assertion,at)
                &&permitted(actor,permission,assetScope,assertion,at);
    }
    private boolean permitted(String actor,String permission,AuthorizationScope scope,
            VerifiedAuthorizationAssertion assertion,Instant at) {
        var request=new AuthorizationEvaluationRequest(actor,permission,null,null,scope);
        return policy.evaluate(request,evidence.load(request,at,assertion),at).decision()
                ==AuthorizationDecisionValue.PERMIT;
    }
}
