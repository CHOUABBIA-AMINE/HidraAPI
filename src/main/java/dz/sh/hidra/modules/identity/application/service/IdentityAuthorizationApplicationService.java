/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthorizationApplicationService
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Application service for identity authorization evaluation.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationDecisionRepositoryPort;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.domain.model.AuthorizationDecision;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;

import java.util.Objects;

/**
 * Application service for identity authorization evaluation.
 */
public class IdentityAuthorizationApplicationService implements EvaluatePermissionUseCase {

    private final AuthorizationDecisionRepositoryPort authorizationDecisionRepositoryPort;
    private final AuthorizationPolicyEvaluator authorizationPolicyEvaluator;

    public IdentityAuthorizationApplicationService(
            AuthorizationDecisionRepositoryPort authorizationDecisionRepositoryPort,
            AuthorizationPolicyEvaluator authorizationPolicyEvaluator
    ) {
        this.authorizationDecisionRepositoryPort = Objects.requireNonNull(
                authorizationDecisionRepositoryPort,
                "Authorization decision repository port must not be null."
        );
        this.authorizationPolicyEvaluator = Objects.requireNonNull(
                authorizationPolicyEvaluator,
                "Authorization policy evaluator must not be null."
        );
    }

    @Override
    public PermissionDecisionDto evaluate(EvaluatePermissionQuery query) {
        Objects.requireNonNull(query, "Evaluate permission query must not be null.");

        AuthorizationEvaluationRequest request = new AuthorizationEvaluationRequest(
                query.userId(),
                query.permissionCode(),
                query.resourceType(),
                query.resourceReferenceId(),
                query.scope()
        );

        AuthorizationDecision decision = authorizationPolicyEvaluator.deny(
                request,
                "NO_GRANT_MATCHED",
                "No active grant matched the requested permission."
        );

        AuthorizationDecision savedDecision = authorizationDecisionRepositoryPort.save(decision);

        return new PermissionDecisionDto(
                savedDecision.decision() == AuthorizationDecisionValue.PERMIT,
                savedDecision.decision(),
                savedDecision.reasonCode(),
                savedDecision.reasonMessage(),
                savedDecision.evaluatedAt()
        );
    }
}
