/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserRoleGrant
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
