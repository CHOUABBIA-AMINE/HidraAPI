/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : NotificationInboxAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 * @Description : Mandatory live paired direct grants and deny-precedence authorization for T27 inbox.
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.notification.NotificationInboxAuthorizationContract;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.application.port.out.*;
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
public class NotificationInboxAuthorizationQueryService
        implements NotificationInboxAuthorizationContract {
    private static final Set<String> ALLOWED=Set.of("notification:inbox:read",
            "notification:inbox:acknowledge");
    private final TelemetryNativeLocalActorPort actors;
    private final IncidentExplicitGrantEvidencePort grants;
    private final AuthorizationEvidencePort evidence;
    private final AuthorizationAssertionPort assertions;
    private final AuthorizationPolicyEvaluator evaluator=new AuthorizationPolicyEvaluator();
    public NotificationInboxAuthorizationQueryService(
            TelemetryNativeLocalActorPort actors, IncidentExplicitGrantEvidencePort grants,
            AuthorizationEvidencePort evidence, AuthorizationAssertionPort assertions) {
        this.actors=Objects.requireNonNull(actors);
        this.grants=Objects.requireNonNull(grants);
        this.evidence=Objects.requireNonNull(evidence);
        this.assertions=Objects.requireNonNull(assertions);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public String require(String permission,String companyId,String facilityId) {
        if (!ALLOWED.contains(permission) || companyId==null || companyId.isBlank()
                || companyId.length()>96 || facilityId==null || facilityId.isBlank()
                || facilityId.length()>120) throw hidden();
        String actor=actors.currentLocalActor();
        String company="notification-company:"+companyId;
        if (company.length()>120) throw hidden();
        Instant now=Instant.now();
        var companyScope=new AuthorizationScope(ScopeType.CUSTOM,company,null);
        var facilityScope=new AuthorizationScope(ScopeType.FACILITY,facilityId,null);
        if (!grants.currentDirectPermit(actor,permission,ScopeType.CUSTOM,company,now)
                || !grants.currentDirectPermit(actor,permission,ScopeType.FACILITY,facilityId,now))
            throw hidden();
        VerifiedAuthorizationAssertion assertion=assertions.currentFor(actor);
        if (!allowed(actor,permission,companyScope,assertion,now)
                || !allowed(actor,permission,facilityScope,assertion,now)) throw hidden();
        return actor;
    }
    private boolean allowed(String actor,String permission,AuthorizationScope scope,
            VerifiedAuthorizationAssertion assertion,Instant now) {
        var request=new AuthorizationEvaluationRequest(actor,permission,null,null,scope);
        return evaluator.evaluate(request,evidence.load(request,now,assertion),now).decision()
                ==AuthorizationDecisionValue.PERMIT;
    }
    private static NoSuchElementException hidden() {
        return new NoSuchElementException("Protected inbox unavailable.");
    }
}
