/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryScopedAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Requires paired company and exact source-point native grants in the same transaction.
 *
 */
package dz.sh.hidra.modules.identity.application.service;


import dz.sh.hidra.modules.identity.application.contract.telemetry.TelemetryScopedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.port.out.TelemetryExplicitGrantEvidencePort;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TelemetryScopedAuthorizationQueryService implements TelemetryScopedAuthorizationContract {
    private final TelemetryNativeLocalActorPort actors;
    private final TelemetryExplicitGrantEvidencePort direct;
    private final AuthorizationEvidencePort evidence;
    private final AuthorizationAssertionPort assertions;
    private final AuthorizationPolicyEvaluator policy = new AuthorizationPolicyEvaluator();
    public TelemetryScopedAuthorizationQueryService(TelemetryNativeLocalActorPort actors,
            TelemetryExplicitGrantEvidencePort direct, AuthorizationEvidencePort evidence,
            AuthorizationAssertionPort assertions) {
        this.actors=Objects.requireNonNull(actors);this.direct=Objects.requireNonNull(direct);
        this.evidence=Objects.requireNonNull(evidence);this.assertions=Objects.requireNonNull(assertions);
    }
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void require(Operation operation,String company,String source,String point) {
        if(operation==null || !valid(company) || !valid(source) || !valid(point))
            throw new AccessDeniedException("Protected telemetry resource unavailable.");
        String permission = operation==Operation.INGEST
                ? "telemetry:raw-reading:ingest" : "telemetry:raw-reading:read";
        String actor = actors.currentLocalActor();
        Instant at = Instant.now();
        var companyScope = new AuthorizationScope(ScopeType.CUSTOM,"telemetry-company:"+company,null);
        var pointScope = new AuthorizationScope(ScopeType.CUSTOM,"telemetry-source-point:"+source+":"+point,null);
        if (companyScope.scopeReferenceId().length()>120 || pointScope.scopeReferenceId().length()>120
                || !direct.currentDirectPermit(actor,permission,ScopeType.CUSTOM,companyScope.scopeReferenceId(),at)
                || !direct.currentDirectPermit(actor,permission,ScopeType.CUSTOM,pointScope.scopeReferenceId(),at))
            throw new AccessDeniedException("Protected telemetry resource unavailable.");
        VerifiedAuthorizationAssertion assertion = assertions.currentFor(actor);
        if (!permitted(actor,permission,companyScope,assertion,at)
                || !permitted(actor,permission,pointScope,assertion,at))
            throw new AccessDeniedException("Protected telemetry resource unavailable.");
    }
    private boolean permitted(String actor,String permission,AuthorizationScope scope,
            VerifiedAuthorizationAssertion assertion,Instant at) {
        var request = new AuthorizationEvaluationRequest(actor,permission,null,null,scope);
        return policy.evaluate(request,evidence.load(request,at,assertion),at).decision()
                == AuthorizationDecisionValue.PERMIT;
    }
    private static boolean valid(String value) {
        return value!=null && !value.isBlank() && value.equals(value.strip());
    }
}
