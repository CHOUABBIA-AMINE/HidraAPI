/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RolePermissionGrant
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Assigns a permission to a role.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Assigns a permission to a role.
 *
     * @param id id
 * @param roleId roleId
 * @param permissionId permissionId
 * @param effect effect
 * @param conditionExpression conditionExpression
 * @param validFrom validFrom
 * @param validTo validTo
 * @param status status
 * @param createdAt createdAt
 */
public record RolePermissionGrant(
        String id,
    String roleId,
    String permissionId,
    GrantEffect effect,
    String conditionExpression,
    Instant validFrom,
    Instant validTo,
    GrantStatus status,
    Instant createdAt
) {

    public RolePermissionGrant {
    id = normalize(id);
    roleId = normalize(roleId);
    permissionId = normalize(permissionId);
    conditionExpression = normalize(conditionExpression);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
