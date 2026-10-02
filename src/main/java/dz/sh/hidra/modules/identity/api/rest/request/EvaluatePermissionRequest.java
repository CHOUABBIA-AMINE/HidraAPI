/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvaluatePermissionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
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

/**
 * REST request for evaluating permission.
 *
 * @param userId user identifier
 * @param permissionCode permission code
 * @param resourceType optional resource type
 * @param resourceReferenceId optional resource reference
 * @param scope API-owned authorization scope representation
 */
public record EvaluatePermissionRequest(
        String userId,
        String permissionCode,
        String resourceType,
        String resourceReferenceId,
        AuthorizationScopeRequest scope
) {
}
