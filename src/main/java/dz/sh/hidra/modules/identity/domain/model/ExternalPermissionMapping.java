/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalPermissionMapping
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Maps an external permission claim to an internal permission.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Maps an external permission claim to an internal permission.
 *
     * @param id id
 * @param identityProviderId identityProviderId
 * @param permissionId permissionId
 * @param externalPermissionCode externalPermissionCode
 * @param claimName claimName
 * @param mappingMode mappingMode
 * @param effect effect
 * @param status status
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record ExternalPermissionMapping(
        String id,
    String identityProviderId,
    String permissionId,
    String externalPermissionCode,
    String claimName,
    ExternalMappingMode mappingMode,
    GrantEffect effect,
    ExternalMappingStatus status,
    Instant createdAt,
    Instant updatedAt
) {

    public ExternalPermissionMapping {
    id = normalize(id);
    identityProviderId = normalize(identityProviderId);
    permissionId = normalize(permissionId);
    externalPermissionCode = normalize(externalPermissionCode);
    claimName = normalize(claimName);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
