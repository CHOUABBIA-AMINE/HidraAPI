/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RolePermissionGrant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("RolePermissionGrant id must not be blank.");
        }
        // HRA-051 required: roleId
        if (roleId == null || roleId.isBlank()) {
            throw new InvalidIdentityValueException("RolePermissionGrant role id must not be blank.");
        }
        // HRA-051 required: permissionId
        if (permissionId == null || permissionId.isBlank()) {
            throw new InvalidIdentityValueException("RolePermissionGrant permission id must not be blank.");
        }
        // HRA-051 required: effect
        if (effect == null) {
            throw new InvalidIdentityValueException("RolePermissionGrant effect must not be null.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidIdentityValueException("RolePermissionGrant valid from must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("RolePermissionGrant status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidIdentityValueException("RolePermissionGrant valid to must not be before valid from.");
        }

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
