/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedAuditEvidenceAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Bounded T28 governed Risk approval scoped Audit evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.audit.ScopedAuditEvidenceAuthorizationContract;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public final class ScopedAuditEvidenceAuthorizationQueryService
        implements ScopedAuditEvidenceAuthorizationContract {
    private static final Set<String> PERMISSIONS=Set.of(
            "audit:scoped-evidence:read","audit:scoped-evidence:export");
    private final TelemetryNativeLocalActorPort actors;
    private final IncidentExplicitGrantEvidencePort grants;
    private final AuthorizationEvidencePort evidence;
    private final AuthorizationAssertionPort assertions;
    private final AuthorizationPolicyEvaluator evaluator=new AuthorizationPolicyEvaluator();
    public ScopedAuditEvidenceAuthorizationQueryService(TelemetryNativeLocalActorPort actors,
            IncidentExplicitGrantEvidencePort grants,AuthorizationEvidencePort evidence,
            AuthorizationAssertionPort assertions) {
        this.actors=Objects.requireNonNull(actors);
        this.grants=Objects.requireNonNull(grants);
        this.evidence=Objects.requireNonNull(evidence);
        this.assertions=Objects.requireNonNull(assertions);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public String nativeActor(){return actors.currentLocalActor();}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean permitted(String actor,String companyId,String facilityId,boolean export) {
        if(actor==null||companyId==null||companyId.isBlank()||companyId.length()>96
                ||facilityId==null||facilityId.isBlank()||facilityId.length()>120)return false;
        // Re-evaluate the native token as well as the database permission snapshot on every operation.
        if(!actor.equals(actors.currentLocalActor()))return false;
        String company="audit-company:"+companyId;
        if(company.length()>120)return false;
        var companyScope=new AuthorizationScope(ScopeType.CUSTOM,company,null);
        var facilityScope=new AuthorizationScope(ScopeType.FACILITY,facilityId,null);
        Instant at=Instant.now();
        VerifiedAuthorizationAssertion assertion=assertions.currentFor(actor);
        for(String permission:export?PERMISSIONS:Set.of("audit:scoped-evidence:read")) {
            if(!grants.currentDirectPermit(actor,permission,ScopeType.CUSTOM,company,at)
                    ||!grants.currentDirectPermit(actor,permission,ScopeType.FACILITY,facilityId,at)
                    ||!allows(actor,permission,companyScope,assertion,at)
                    ||!allows(actor,permission,facilityScope,assertion,at))return false;
        }
        return true;
    }
    private boolean allows(String actor,String permission,AuthorizationScope scope,
            VerifiedAuthorizationAssertion assertion,Instant at) {
        var request=new AuthorizationEvaluationRequest(actor,permission,null,null,scope);
        return evaluator.evaluate(request,evidence.load(request,at,assertion),at).decision()
                ==AuthorizationDecisionValue.PERMIT;
    }
}
