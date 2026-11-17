/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsMetricScopedAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Exact current LOCAL actor and paired direct authorizations for Analytics metric.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.analytics.AnalyticsMetricScopedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnalyticsMetricScopedAuthorizationQueryService
        implements AnalyticsMetricScopedAuthorizationContract {
    private static final Set<String> PERMISSIONS=Set.of(
            "analytics:deviation-metric:execute","analytics:deviation-metric:read");
    private final TelemetryNativeLocalActorPort actors;
    private final IncidentExplicitGrantEvidencePort grants;
    private final AuthorizationEvidencePort evidence;
    private final AuthorizationAssertionPort assertions;
    private final AuthorizationPolicyEvaluator evaluator=new AuthorizationPolicyEvaluator();
    public AnalyticsMetricScopedAuthorizationQueryService(
            TelemetryNativeLocalActorPort actors,IncidentExplicitGrantEvidencePort grants,
            AuthorizationEvidencePort evidence,AuthorizationAssertionPort assertions) {
        this.actors=Objects.requireNonNull(actors);
        this.grants=Objects.requireNonNull(grants);
        this.evidence=Objects.requireNonNull(evidence);
        this.assertions=Objects.requireNonNull(assertions);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public String currentActor() { return actors.currentLocalActor(); }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void require(String permission,String company,String facility) {
        if(!PERMISSIONS.contains(permission)||company==null||company.isBlank()
                ||company.length()>96||facility==null||facility.isBlank())
            throw denied();
        String principal=actors.currentLocalActor();
        String companyRef="analytics-company:"+company;
        if(companyRef.length()>120)throw denied();
        Instant now=Instant.now();
        var companyScope=new AuthorizationScope(ScopeType.CUSTOM,companyRef,null);
        var facilityScope=new AuthorizationScope(ScopeType.FACILITY,facility,null);
        if(!grants.currentDirectPermit(principal,permission,ScopeType.CUSTOM,companyRef,now)
                ||!grants.currentDirectPermit(principal,permission,ScopeType.FACILITY,facility,now))
            throw denied();
        VerifiedAuthorizationAssertion assertion=assertions.currentFor(principal);
        if(!permitted(principal,permission,companyScope,assertion,now)
                ||!permitted(principal,permission,facilityScope,assertion,now)) throw denied();
    }
    private boolean permitted(String principal,String permission,AuthorizationScope scope,
            VerifiedAuthorizationAssertion assertion,Instant now){
        var request=new AuthorizationEvaluationRequest(principal,permission,null,null,scope);
        return evaluator.evaluate(request,evidence.load(request,now,assertion),now).decision()
                ==AuthorizationDecisionValue.PERMIT;
    }
    private static NoSuchElementException denied() {
        return new NoSuchElementException("Protected Analytics metric unavailable.");
    }
}
