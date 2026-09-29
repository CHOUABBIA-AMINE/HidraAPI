/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationScopeRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.request
 *
 * @Description : API-owned wire representation of an authorization scope.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.request;

import dz.sh.hidra.modules.identity.domain.value.ScopeType;

/**
 * REST representation of an authorization scope.
 *
 * <p>The record deliberately owns the REST structure while reusing only the HRA-102-approved
 * same-module {@link ScopeType} enum vocabulary. Domain normalization remains in
 * {@code AuthorizationScope} and is invoked only by the REST mapper.</p>
 *
 * @param scopeType scope vocabulary; a null value normalizes to GLOBAL at the domain boundary
 * @param scopeReferenceId stable scope reference for non-global scopes
 * @param scopeCodeSnapshot optional scope-code snapshot
 */
public record AuthorizationScopeRequest(
        ScopeType scopeType,
        String scopeReferenceId,
        String scopeCodeSnapshot
) {
}
