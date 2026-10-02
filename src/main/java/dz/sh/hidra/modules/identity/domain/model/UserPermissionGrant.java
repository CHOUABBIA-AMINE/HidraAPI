/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserPermissionGrant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Assigns a permission directly to a user.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Assigns a permission directly to a user.
 *
     * @param id id
 * @param userId userId
 * @param permissionId permissionId
 * @param effect effect
 * @param scope scope
 * @param grantReason grantReason
 * @param approvedByWorkflowId approvedByWorkflowId
 * @param emergencyAccess emergencyAccess
 * @param validFrom validFrom
 * @param validTo validTo
 * @param status status
 * @param createdAt createdAt
 * @param revokedAt revokedAt
 */
public record UserPermissionGrant(
        String id,
    String userId,
    String permissionId,
    GrantEffect effect,
    AuthorizationScope scope,
    String grantReason,
    String approvedByWorkflowId,
    boolean emergencyAccess,
    Instant validFrom,
    Instant validTo,
    GrantStatus status,
    Instant createdAt,
    Instant revokedAt
) {

    public UserPermissionGrant {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("UserPermissionGrant id must not be blank.");
        }
        // HRA-051 required: userId
        if (userId == null || userId.isBlank()) {
            throw new InvalidIdentityValueException("UserPermissionGrant user id must not be blank.");
        }
        // HRA-051 required: permissionId
        if (permissionId == null || permissionId.isBlank()) {
            throw new InvalidIdentityValueException("UserPermissionGrant permission id must not be blank.");
        }
        // HRA-051 required: effect
        if (effect == null) {
            throw new InvalidIdentityValueException("UserPermissionGrant effect must not be null.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidIdentityValueException("UserPermissionGrant valid from must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("UserPermissionGrant status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidIdentityValueException("UserPermissionGrant valid to must not be before valid from.");
        }

    id = normalize(id);
    userId = normalize(userId);
    permissionId = normalize(permissionId);
    grantReason = normalize(grantReason);
    approvedByWorkflowId = normalize(approvedByWorkflowId);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
