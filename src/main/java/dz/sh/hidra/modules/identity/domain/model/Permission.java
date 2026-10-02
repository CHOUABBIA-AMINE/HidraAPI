/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Permission
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Represents one business access capability.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Represents one business access capability.
 *
     * @param id id
 * @param code code
 * @param nameAr nameAr
 * @param nameFr nameFr
 * @param nameEn nameEn
 * @param description description
 * @param permissionDomain permissionDomain
 * @param resourceType resourceType
 * @param action action
 * @param sensitive sensitive
 * @param status status
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record Permission(
        String id,
    String code,
    String nameAr,
    String nameFr,
    String nameEn,
    String description,
    String permissionDomain,
    String resourceType,
    String action,
    boolean sensitive,
    PermissionStatus status,
    Instant createdAt,
    Instant updatedAt
) {

    public Permission {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("Permission id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidIdentityValueException("Permission code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("Permission status must not be null.");
        }

    id = normalize(id);
    code = normalize(code);
    nameAr = normalize(nameAr);
    nameFr = normalize(nameFr);
    nameEn = normalize(nameEn);
    description = normalize(description);
    permissionDomain = normalize(permissionDomain);
    resourceType = normalize(resourceType);
    action = normalize(action);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
