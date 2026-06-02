/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterUserRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.request
 *
 * @Description : REST request body for registering an identity user.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * REST request body for registering an identity user.
 *
 * <p>Business role: captures the username and email address submitted for user
 * registration.</p>
 *
 * <p>Architecture role: API-layer request DTO. It must not leak persistence entities,
 * domain aggregates, platform security objects, or organization structures.</p>
 *
 * <p>Validation responsibility: uses Bean Validation to require a username and valid
 * email address with bounded sizes.</p>
 *
 * <p>Usage: consumed by {@code IdentityUserController} and mapped to
 * {@code RegisterUserCommand}.</p>
 */
public record RegisterUserRequest(
        @NotBlank
        @Size(min = 3, max = 64)
        String username,

        @NotBlank
        @Email
        @Size(max = 254)
        String emailAddress
) {
}
