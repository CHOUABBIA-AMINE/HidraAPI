/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationDecision
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Records an authorization evaluation result.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Records an authorization evaluation result.
 *
     * @param id id
 * @param userId userId
 * @param permissionCode permissionCode
 * @param resourceType resourceType
 * @param resourceReferenceId resourceReferenceId
 * @param scope scope
 * @param decision decision
 * @param reasonCode reasonCode
 * @param reasonMessage reasonMessage
 * @param matchedGrantIds matchedGrantIds
 * @param matchedPolicyRuleIds matchedPolicyRuleIds
 * @param externalClaimsUsed externalClaimsUsed
 * @param evaluatedAt evaluatedAt
 * @param correlationId correlationId
 * @param requestId requestId
 */
public record AuthorizationDecision(
        String id,
    String userId,
    String permissionCode,
    String resourceType,
    String resourceReferenceId,
    AuthorizationScope scope,
    AuthorizationDecisionValue decision,
    String reasonCode,
    String reasonMessage,
    String matchedGrantIds,
    String matchedPolicyRuleIds,
    String externalClaimsUsed,
    Instant evaluatedAt,
    String correlationId,
    String requestId
) {

    public AuthorizationDecision {
    id = normalize(id);
    userId = normalize(userId);
    permissionCode = normalize(permissionCode);
    resourceType = normalize(resourceType);
    resourceReferenceId = normalize(resourceReferenceId);
    reasonCode = normalize(reasonCode);
    reasonMessage = normalize(reasonMessage);
    matchedGrantIds = normalize(matchedGrantIds);
    matchedPolicyRuleIds = normalize(matchedPolicyRuleIds);
    externalClaimsUsed = normalize(externalClaimsUsed);
    correlationId = normalize(correlationId);
    requestId = normalize(requestId);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
