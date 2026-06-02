/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.response
 *
 * @Description : REST response representing an identity role.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.response;

import java.util.List;

/**
 * REST response representing an identity role.
 *
 * <p>Business role: exposes role state and granted permissions to API clients.</p>
 *
 * <p>Architecture role: API-layer response DTO independent from JPA entities and domain
 * aggregates.</p>
 *
 * <p>Validation responsibility: defensively copies nested permissions.</p>
 *
 * <p>Usage: returned by identity role endpoints.</p>
 */
public record RoleResponse(
        String id,
        String code,
        String name,
        String status,
        List<PermissionResponse> permissions
) {

    public RoleResponse {
        permissions = permissions == null ? List.of() : List.copyOf(permissions);
    }
}
