/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserRoleGrant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Assigns a role directly to a user.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Assigns a role directly to a user.
 *
     * @param id id
 * @param userId userId
 * @param roleId roleId
 * @param scope scope
 * @param grantReason grantReason
 * @param approvedByWorkflowId approvedByWorkflowId
 * @param validFrom validFrom
 * @param validTo validTo
 * @param status status
 * @param createdAt createdAt
 * @param revokedAt revokedAt
 * @param revokedReason revokedReason
 */
public record UserRoleGrant(
        String id,
    String userId,
    String roleId,
    AuthorizationScope scope,
    String grantReason,
    String approvedByWorkflowId,
    Instant validFrom,
    Instant validTo,
    GrantStatus status,
    Instant createdAt,
    Instant revokedAt,
    String revokedReason
) {

    public UserRoleGrant {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("UserRoleGrant id must not be blank.");
        }
        // HRA-051 required: userId
        if (userId == null || userId.isBlank()) {
            throw new InvalidIdentityValueException("UserRoleGrant user id must not be blank.");
        }
        // HRA-051 required: roleId
        if (roleId == null || roleId.isBlank()) {
            throw new InvalidIdentityValueException("UserRoleGrant role id must not be blank.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidIdentityValueException("UserRoleGrant valid from must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("UserRoleGrant status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidIdentityValueException("UserRoleGrant valid to must not be before valid from.");
        }

    id = normalize(id);
    userId = normalize(userId);
    roleId = normalize(roleId);
    grantReason = normalize(grantReason);
    approvedByWorkflowId = normalize(approvedByWorkflowId);
    revokedReason = normalize(revokedReason);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
