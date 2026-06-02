/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ActivateUserRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.request
 *
 * @Description : REST request body for activating an identity user.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * REST request body for activating an identity user.
 *
 * <p>Business role: confirms the user identifier for an activation request.</p>
 *
 * <p>Architecture role: API-layer request DTO mapped to {@code ActivateUserCommand};
 * it does not expose domain aggregates, repositories, JPA entities, or platform security
 * plumbing.</p>
 *
 * <p>Validation responsibility: uses Bean Validation to require the user identifier.</p>
 *
 * <p>Usage: consumed by {@code IdentityUserController} for activation endpoints.</p>
 */
public record ActivateUserRequest(
        @NotBlank
        @Size(max = 36)
        String userId
) {
}
