/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalRoleMapping
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Maps an external role or claim to an internal role.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Maps an external role or claim to an internal role.
 *
     * @param id id
 * @param identityProviderId identityProviderId
 * @param roleId roleId
 * @param externalRoleCode externalRoleCode
 * @param claimName claimName
 * @param mappingMode mappingMode
 * @param scope scope
 * @param status status
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record ExternalRoleMapping(
        String id,
    String identityProviderId,
    String roleId,
    String externalRoleCode,
    String claimName,
    ExternalMappingMode mappingMode,
    AuthorizationScope scope,
    ExternalMappingStatus status,
    Instant createdAt,
    Instant updatedAt
) {

    public ExternalRoleMapping {
    id = normalize(id);
    identityProviderId = normalize(identityProviderId);
    roleId = normalize(roleId);
    externalRoleCode = normalize(externalRoleCode);
    claimName = normalize(claimName);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
