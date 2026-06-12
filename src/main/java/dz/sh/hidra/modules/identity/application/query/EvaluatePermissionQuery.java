/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvaluatePermissionQuery
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.query
 *
 * @Description : Query to evaluate an identity permission.
 *
 */
package dz.sh.hidra.modules.identity.application.query;

import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;

/**
 * Query to evaluate whether a user has a permission.
 *
 * @param userId user identifier
 * @param permissionCode requested permission code
 * @param resourceType optional resource type
 * @param resourceReferenceId optional resource reference
 * @param scope authorization scope
 */
public record EvaluatePermissionQuery(
        String userId,
        String permissionCode,
        String resourceType,
        String resourceReferenceId,
        AuthorizationScope scope
) {
}
