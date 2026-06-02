/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.response
 *
 * @Description : REST response representing an identity user.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.response;

import java.util.List;

/**
 * REST response representing an identity user.
 *
 * <p>Business role: exposes identity user state, optional employee reference, and role
 * summaries to API clients.</p>
 *
 * <p>Architecture role: API-layer response DTO independent from JPA entities, domain
 * aggregates, platform security principals, and organization employee models.</p>
 *
 * <p>Validation responsibility: defensively copies nested roles.</p>
 *
 * <p>Usage: returned by identity user endpoints.</p>
 */
public record UserResponse(
        String id,
        String username,
        String emailAddress,
        String status,
        String employeeReference,
        List<RoleResponse> roles
) {

    public UserResponse {
        roles = roles == null ? List.of() : List.copyOf(roles);
    }
}
