/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserGroupMembership
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Links a user to an identity security group.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Links a user to an identity security group.
 *
     * @param id id
 * @param userId userId
 * @param groupId groupId
 * @param membershipType membershipType
 * @param sourceProviderId sourceProviderId
 * @param sourceMappingId sourceMappingId
 * @param validFrom validFrom
 * @param validTo validTo
 * @param status status
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record UserGroupMembership(
        String id,
    String userId,
    String groupId,
    MembershipType membershipType,
    String sourceProviderId,
    String sourceMappingId,
    Instant validFrom,
    Instant validTo,
    MembershipStatus status,
    Instant createdAt,
    Instant updatedAt
) {

    public UserGroupMembership {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("UserGroupMembership id must not be blank.");
        }
        // HRA-051 required: userId
        if (userId == null || userId.isBlank()) {
            throw new InvalidIdentityValueException("UserGroupMembership user id must not be blank.");
        }
        // HRA-051 required: groupId
        if (groupId == null || groupId.isBlank()) {
            throw new InvalidIdentityValueException("UserGroupMembership group id must not be blank.");
        }
        // HRA-051 required: membershipType
        if (membershipType == null) {
            throw new InvalidIdentityValueException("UserGroupMembership membership type must not be null.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidIdentityValueException("UserGroupMembership valid from must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("UserGroupMembership status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidIdentityValueException("UserGroupMembership valid to must not be before valid from.");
        }

    id = normalize(id);
    userId = normalize(userId);
    groupId = normalize(groupId);
    sourceProviderId = normalize(sourceProviderId);
    sourceMappingId = normalize(sourceMappingId);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
