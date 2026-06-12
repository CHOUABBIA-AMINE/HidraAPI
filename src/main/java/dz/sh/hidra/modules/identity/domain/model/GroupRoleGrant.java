/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GroupRoleGrant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Assigns a role to an identity security group.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Assigns a role to an identity security group.
 *
     * @param id id
 * @param groupId groupId
 * @param roleId roleId
 * @param scope scope
 * @param grantReason grantReason
 * @param approvedByWorkflowId approvedByWorkflowId
 * @param validFrom validFrom
 * @param validTo validTo
 * @param status status
 * @param createdAt createdAt
 */
public record GroupRoleGrant(
        String id,
    String groupId,
    String roleId,
    AuthorizationScope scope,
    String grantReason,
    String approvedByWorkflowId,
    Instant validFrom,
    Instant validTo,
    GrantStatus status,
    Instant createdAt
) {

    public GroupRoleGrant {
    id = normalize(id);
    groupId = normalize(groupId);
    roleId = normalize(roleId);
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
