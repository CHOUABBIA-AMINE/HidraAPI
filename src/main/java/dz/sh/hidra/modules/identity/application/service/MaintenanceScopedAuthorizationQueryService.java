/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : MaintenanceScopedAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 * @Description : T18 native-scoped Integrity/Assets governed work-order operations.
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.integrity.IntegrityMaintenanceScopedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.assets.AssetsMaintenanceScopedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaintenanceScopedAuthorizationQueryService implements
        IntegrityMaintenanceScopedAuthorizationContract,AssetsMaintenanceScopedAuthorizationContract {
    private static final Set<String> PERMISSIONS=Set.of(
        "integrity:maintenance:approve","integrity:maintenance:read",
        "assets:integrity-work-orders:handoff","assets:integrity-work-orders:assign",
        "assets:integrity-work-orders:execute","assets:integrity-work-orders:verify",
        "assets:integrity-work-orders:read");
    private final TelemetryNativeLocalActorPort actors;
    private final IncidentExplicitGrantEvidencePort grants;
    private final AuthorizationEvidencePort evidence;
    private final AuthorizationAssertionPort assertions;
    private final AuthorizationPolicyEvaluator evaluator=new AuthorizationPolicyEvaluator();
    public MaintenanceScopedAuthorizationQueryService(TelemetryNativeLocalActorPort actors,
            IncidentExplicitGrantEvidencePort grants,AuthorizationEvidencePort evidence,
            AuthorizationAssertionPort assertions){
        this.actors=Objects.requireNonNull(actors);this.grants=Objects.requireNonNull(grants);
        this.evidence=Objects.requireNonNull(evidence);this.assertions=Objects.requireNonNull(assertions);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public String currentActor(){return actors.currentLocalActor();}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void require(String permission,String company,String type,String asset){
        if(!eligible(currentActor(),permission,company,type,asset))
            throw new NoSuchElementException("Protected maintenance resource unavailable.");
    }
    /** Assignment is never inferred from a human-readable actor ID alone. */
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean assigneeEligible(String actor,String company,String type,String asset) {
        // An assignee need not currently be logged in; require current direct grants
        // under the same database transaction, not their own bearer assertion.
        if(actor==null||actor.isBlank()||company==null||company.isBlank()
                ||!"EQUIPMENT".equals(type)||asset==null||asset.isBlank())return false;
        String permission="assets:integrity-work-orders:execute";
        String companyRef="assets-company:"+company;
        if(companyRef.length()>120)return false;
        Instant at=Instant.now();
        return grants.currentDirectPermit(actor,permission,ScopeType.CUSTOM,companyRef,at)
                &&grants.currentDirectPermit(actor,permission,ScopeType.EQUIPMENT,asset,at);
    }
    private boolean eligible(String actor,String permission,String company,String type,String asset) {
        if(actor==null||actor.isBlank()||!PERMISSIONS.contains(permission)
                ||company==null||company.isBlank()||company.length()>96
                ||!"EQUIPMENT".equals(type)||asset==null||asset.isBlank())return false;
        String companyRef=permission.split(":")[0]+"-company:"+company;
        if(companyRef.length()>120)return false;
        Instant now=Instant.now();
        var companyScope=new AuthorizationScope(ScopeType.CUSTOM,companyRef,null);
        var equipmentScope=new AuthorizationScope(ScopeType.EQUIPMENT,asset,null);
        if(!grants.currentDirectPermit(actor,permission,ScopeType.CUSTOM,companyRef,now)
                ||!grants.currentDirectPermit(actor,permission,ScopeType.EQUIPMENT,asset,now))return false;
        VerifiedAuthorizationAssertion assertion=assertions.currentFor(actor);
        return permitted(actor,permission,companyScope,assertion,now)
                &&permitted(actor,permission,equipmentScope,assertion,now);
    }
    private boolean permitted(String actor,String permission,AuthorizationScope scope,
            VerifiedAuthorizationAssertion assertion,Instant now) {
        var request=new AuthorizationEvaluationRequest(actor,permission,null,null,scope);
        return evaluator.evaluate(request,evidence.load(request,now,assertion),now).decision()
                ==AuthorizationDecisionValue.PERMIT;
    }
}
