/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignRoleToUserRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.request
 *
 * @Description : REST request body for assigning an identity role to a user.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * REST request body for assigning an identity role to a user.
 *
 * <p>Business role: captures the role identifier that should be assigned to the user
 * identified by the route.</p>
 *
 * <p>Architecture role: API-layer request DTO mapped to
 * {@code AssignRoleToUserCommand}; it does not leak JPA entities or platform security
 * types.</p>
 *
 * <p>Validation responsibility: uses Bean Validation to require a role identifier.</p>
 *
 * <p>Usage: consumed by {@code IdentityUserController} role assignment endpoint.</p>
 */
public record AssignRoleToUserRequest(
        @NotBlank
        @Size(max = 36)
        String roleId
) {
}
