/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvaluatePermissionRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.request
 *
 * @Description : REST request for evaluating an identity permission.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.request;

import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;

/**
 * REST request for evaluating permission.
 *
 * @param userId user identifier
 * @param permissionCode permission code
 * @param resourceType optional resource type
 * @param resourceReferenceId optional resource reference
 * @param scope authorization scope
 */
public record EvaluatePermissionRequest(
        String userId,
        String permissionCode,
        String resourceType,
        String resourceReferenceId,
        AuthorizationScope scope
) {
}
