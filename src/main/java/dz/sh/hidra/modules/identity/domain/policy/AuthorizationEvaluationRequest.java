/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationEvaluationRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.policy
 *
 * @Description : Represents a domain authorization evaluation request.
 *
 */
package dz.sh.hidra.modules.identity.domain.policy;

import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;

/**
 * Domain authorization evaluation request.
 *
 * @param userId user identifier
 * @param permissionCode requested permission code
 * @param resourceType optional resource type
 * @param resourceReferenceId optional resource reference
 * @param scope authorization scope
 */
public record AuthorizationEvaluationRequest(
        String userId,
        String permissionCode,
        String resourceType,
        String resourceReferenceId,
        AuthorizationScope scope
) {
}
