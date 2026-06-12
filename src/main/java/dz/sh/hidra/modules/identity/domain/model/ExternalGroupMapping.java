/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalGroupMapping
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Maps an external group to an internal identity group.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Maps an external group to an internal identity group.
 *
     * @param id id
 * @param identityProviderId identityProviderId
 * @param groupId groupId
 * @param externalGroupId externalGroupId
 * @param externalGroupName externalGroupName
 * @param externalGroupDn externalGroupDn
 * @param claimName claimName
 * @param mappingMode mappingMode
 * @param autoCreateMembership autoCreateMembership
 * @param status status
 * @param lastSyncedAt lastSyncedAt
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record ExternalGroupMapping(
        String id,
    String identityProviderId,
    String groupId,
    String externalGroupId,
    String externalGroupName,
    String externalGroupDn,
    String claimName,
    ExternalMappingMode mappingMode,
    boolean autoCreateMembership,
    ExternalMappingStatus status,
    Instant lastSyncedAt,
    Instant createdAt,
    Instant updatedAt
) {

    public ExternalGroupMapping {
    id = normalize(id);
    identityProviderId = normalize(identityProviderId);
    groupId = normalize(groupId);
    externalGroupId = normalize(externalGroupId);
    externalGroupName = normalize(externalGroupName);
    externalGroupDn = normalize(externalGroupDn);
    claimName = normalize(claimName);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
