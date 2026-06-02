/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateRoleRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.request
 *
 * @Description : REST request body for creating an identity role.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * REST request body for creating an identity role.
 *
 * <p>Business role: captures the role code and display name for a new identity role.</p>
 *
 * <p>Architecture role: API-layer request DTO mapped to {@code CreateRoleCommand}.</p>
 *
 * <p>Validation responsibility: uses Bean Validation to require a stable uppercase role
 * code and a bounded display name.</p>
 *
 * <p>Usage: consumed by {@code IdentityRoleController} role creation endpoint.</p>
 */
public record CreateRoleRequest(
        @NotBlank
        @Size(min = 3, max = 64)
        @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]*$")
        String roleCode,

        @NotBlank
        @Size(min = 3, max = 100)
        String roleName
) {
}
