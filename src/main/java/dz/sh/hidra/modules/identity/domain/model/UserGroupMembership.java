/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserGroupMembership
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
