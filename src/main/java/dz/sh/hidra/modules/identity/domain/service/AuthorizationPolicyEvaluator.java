/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationPolicyEvaluator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Evaluates basic identity authorization decisions.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import dz.sh.hidra.modules.identity.domain.model.AuthorizationDecision;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.IdentityId;

import java.time.Instant;

/**
 * Domain service for basic authorization decision construction.
 *
 * <p>Full RBAC/ABAC graph evaluation is performed by application services using repositories.
 * This domain service keeps decision creation consistent.</p>
 */
public class AuthorizationPolicyEvaluator {

    public AuthorizationDecision permit(AuthorizationEvaluationRequest request, String reasonCode, String reasonMessage) {
        return decision(request, AuthorizationDecisionValue.PERMIT, reasonCode, reasonMessage);
    }

    public AuthorizationDecision deny(AuthorizationEvaluationRequest request, String reasonCode, String reasonMessage) {
        return decision(request, AuthorizationDecisionValue.DENY, reasonCode, reasonMessage);
    }

    private AuthorizationDecision decision(
            AuthorizationEvaluationRequest request,
            AuthorizationDecisionValue value,
            String reasonCode,
            String reasonMessage
    ) {
        return new AuthorizationDecision(
                IdentityId.newId().value(),
                request.userId(),
                request.permissionCode(),
                request.resourceType(),
                request.resourceReferenceId(),
                request.scope(),
                value,
                reasonCode,
                reasonMessage,
                null,
                null,
                null,
                Instant.now(),
                null,
                null
        );
    }
}
