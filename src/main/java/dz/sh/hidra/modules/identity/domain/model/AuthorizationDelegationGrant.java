/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationDelegationGrant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
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
    Instant revokedAt,
    String reason
) {

    public AuthorizationDelegationGrant {
        if (reason == null || reason.isBlank()) {
            throw new InvalidIdentityValueException("Delegation reason must not be blank.");
        }
        reason = reason.trim();
        if (validTo == null) {
            throw new InvalidIdentityValueException("Delegation validTo is required.");
        }
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("AuthorizationDelegationGrant id must not be blank.");
        }
        // HRA-051 required: delegatorUserId
        if (delegatorUserId == null || delegatorUserId.isBlank()) {
            throw new InvalidIdentityValueException("AuthorizationDelegationGrant delegator user id must not be blank.");
        }
        // HRA-051 required: delegateUserId
        if (delegateUserId == null || delegateUserId.isBlank()) {
            throw new InvalidIdentityValueException("AuthorizationDelegationGrant delegate user id must not be blank.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidIdentityValueException("AuthorizationDelegationGrant valid from must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("AuthorizationDelegationGrant status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidIdentityValueException("AuthorizationDelegationGrant valid to must not be before valid from.");
        }

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
