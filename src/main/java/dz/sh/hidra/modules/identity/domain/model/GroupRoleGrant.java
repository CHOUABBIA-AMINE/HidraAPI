/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GroupRoleGrant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("GroupRoleGrant id must not be blank.");
        }
        // HRA-051 required: groupId
        if (groupId == null || groupId.isBlank()) {
            throw new InvalidIdentityValueException("GroupRoleGrant group id must not be blank.");
        }
        // HRA-051 required: roleId
        if (roleId == null || roleId.isBlank()) {
            throw new InvalidIdentityValueException("GroupRoleGrant role id must not be blank.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidIdentityValueException("GroupRoleGrant valid from must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("GroupRoleGrant status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidIdentityValueException("GroupRoleGrant valid to must not be before valid from.");
        }

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
