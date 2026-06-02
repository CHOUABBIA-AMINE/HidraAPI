/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GrantPermissionToRoleRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.request
 *
 * @Description : REST request body for granting an identity permission to a role.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * REST request body for granting an identity permission to a role.
 *
 * <p>Business role: captures the permission code to grant to the role identified by the
 * route.</p>
 *
 * <p>Architecture role: API-layer request DTO mapped to
 * {@code GrantPermissionToRoleCommand}; it does not expose persistence entities or
 * platform authorization internals.</p>
 *
 * <p>Validation responsibility: uses Bean Validation to enforce the
 * {@code context:resource:action} permission-code shape.</p>
 *
 * <p>Usage: consumed by {@code IdentityRoleController} permission grant endpoint.</p>
 */
public record GrantPermissionToRoleRequest(
        @NotBlank
        @Size(max = 120)
        @Pattern(regexp = "^[a-z][a-z0-9-]*:[a-z][a-z0-9-]*:[a-z][a-z0-9-]*$")
        String permissionCode
) {
}
