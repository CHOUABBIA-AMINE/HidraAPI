/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CheckPermissionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.request
 *
 * @Description : REST request body for checking an identity permission.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * REST request body for checking an identity permission.
 *
 * <p>Business role: captures the user and permission involved in a business access
 * decision.</p>
 *
 * <p>Architecture role: API-layer request DTO mapped to {@code CheckPermissionQuery};
 * it does not implement Spring Security authorization plumbing.</p>
 *
 * <p>Validation responsibility: uses Bean Validation to require a user identifier and
 * canonical permission code.</p>
 *
 * <p>Usage: consumed by {@code IdentityPermissionController} permission check endpoint.</p>
 */
public record CheckPermissionRequest(
        @NotBlank
        @Size(max = 36)
        String userId,

        @NotBlank
        @Size(max = 120)
        @Pattern(regexp = "^[a-z][a-z0-9-]*:[a-z][a-z0-9-]*:[a-z][a-z0-9-]*$")
        String permissionCode
) {
}
