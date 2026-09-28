/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalPermissionMapping
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("ExternalPermissionMapping id must not be blank.");
        }
        // HRA-051 required: identityProviderId
        if (identityProviderId == null || identityProviderId.isBlank()) {
            throw new InvalidIdentityValueException("ExternalPermissionMapping identity provider id must not be blank.");
        }
        // HRA-051 required: permissionId
        if (permissionId == null || permissionId.isBlank()) {
            throw new InvalidIdentityValueException("ExternalPermissionMapping permission id must not be blank.");
        }
        // HRA-051 required: externalPermissionCode
        if (externalPermissionCode == null || externalPermissionCode.isBlank()) {
            throw new InvalidIdentityValueException("ExternalPermissionMapping external permission code must not be blank.");
        }
        // HRA-051 required: mappingMode
        if (mappingMode == null) {
            throw new InvalidIdentityValueException("ExternalPermissionMapping mapping mode must not be null.");
        }
        // HRA-051 required: effect
        if (effect == null) {
            throw new InvalidIdentityValueException("ExternalPermissionMapping effect must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("ExternalPermissionMapping status must not be null.");
        }

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
