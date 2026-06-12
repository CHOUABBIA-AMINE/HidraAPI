/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationDelegationGrant
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Represents delegated authorization between users.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Represents delegated authorization between users.
 *
     * @param id id
 * @param delegatorUserId delegatorUserId
 * @param delegateUserId delegateUserId
 * @param permissionId permissionId
 * @param roleId roleId
 * @param scope scope
 * @param approvedByWorkflowId approvedByWorkflowId
 * @param validFrom validFrom
 * @param validTo validTo
 * @param status status
 * @param createdAt createdAt
 * @param revokedAt revokedAt
 */
public record AuthorizationDelegationGrant(
        String id,
    String delegatorUserId,
    String delegateUserId,
    String permissionId,
    String roleId,
    AuthorizationScope scope,
    String approvedByWorkflowId,
    Instant validFrom,
    Instant validTo,
    DelegationStatus status,
    Instant createdAt,
    Instant revokedAt
) {

    public AuthorizationDelegationGrant {
    id = normalize(id);
    delegatorUserId = normalize(delegatorUserId);
    delegateUserId = normalize(delegateUserId);
    permissionId = normalize(permissionId);
    roleId = normalize(roleId);
    approvedByWorkflowId = normalize(approvedByWorkflowId);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
