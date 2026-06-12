/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserPermissionGrant
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
