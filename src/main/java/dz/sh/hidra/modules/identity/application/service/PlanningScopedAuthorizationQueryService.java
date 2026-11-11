/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningScopedAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Evaluates exact Planning grants through current Identity policy.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.planning.PlanningScopedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationAssertionPort;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationEvidencePort;
import dz.sh.hidra.modules.identity.application.port.out.FoundationIdentityAuthorizationPort;
import dz.sh.hidra.modules.identity.application.port.out.PlanningExplicitGrantEvidencePort;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;
import java.util.LinkedHashSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanningScopedAuthorizationQueryService implements PlanningScopedAuthorizationContract {

    private static final String COMPANY_PREFIX = "planning-company:";
    private final FoundationIdentityAuthorizationPort actors;
    private final PlanningExplicitGrantEvidencePort direct;
    private final AuthorizationEvidencePort evidence;
    private final AuthorizationAssertionPort assertions;
    private final AuthorizationPolicyEvaluator policy = new AuthorizationPolicyEvaluator();

    public PlanningScopedAuthorizationQueryService(FoundationIdentityAuthorizationPort actors,
            PlanningExplicitGrantEvidencePort direct, AuthorizationEvidencePort evidence,
            AuthorizationAssertionPort assertions) {
        this.actors = actors;
        this.direct = direct;
        this.evidence = evidence;
        this.assertions = assertions;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Access current(Operation operation) {
        if (operation == Operation.PERIOD_CREATE || operation == Operation.PLAN_CREATE || operation == Operation.PLAN_AUTHOR || operation == Operation.PLAN_REVIEW) {
            direct.fenceCurrentAuthorization();
        }
        String actor = actors.authenticatedActor();
        String permission = switch (operation) {
            case PERIOD_READ -> "planning:scoped-period:read";
            case PERIOD_CREATE -> "planning:scoped-period:create";
            case PLAN_READ -> "planning:scoped-plan:read";
            case PLAN_CREATE -> "planning:scoped-plan:create";
            case PLAN_AUTHOR -> "planning:scoped-plan:author";
            case PLAN_REVIEW -> "planning:scoped-plan:review";
        };
        Instant at = Instant.now();
        VerifiedAuthorizationAssertion assertion = assertions.currentFor(actor);
        var companies = new LinkedHashSet<String>();
        var assets = new LinkedHashSet<Asset>();
        for (var candidate : new LinkedHashSet<>(direct.candidates(actor, permission, at))) {
            String reference = candidate.reference();
            if (reference.isBlank() || !reference.equals(reference.strip())) {
                continue;
            }
            if (candidate.type() == ScopeType.CUSTOM) {
                if (reference.startsWith(COMPANY_PREFIX) && reference.length() > COMPANY_PREFIX.length()
                        && permitted(actor, permission, candidate.type(), reference, assertion, at)) {
                    companies.add(reference.substring(COMPANY_PREFIX.length()));
                }
            } else if (permitted(actor, permission, candidate.type(), reference, assertion, at)) {
                assets.add(new Asset(candidate.type().name(), reference));
            }
        }
        return new Access(actor, companies, assets);
    }

    private boolean permitted(String actor, String permission, ScopeType type, String reference,
            VerifiedAuthorizationAssertion assertion, Instant at) {
        var request = new AuthorizationEvaluationRequest(actor, permission, null, null,
                new AuthorizationScope(type, reference, null));
        return policy.evaluate(request, evidence.load(request, at, assertion), at).decision()
                == AuthorizationDecisionValue.PERMIT;
    }
}
