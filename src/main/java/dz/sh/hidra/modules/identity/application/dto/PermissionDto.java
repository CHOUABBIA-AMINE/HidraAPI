/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.dto
 *
 * @Description : Application DTO representing an identity permission.
 *
 */
package dz.sh.hidra.modules.identity.application.dto;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.PermissionName;

/**
 * Application DTO representing an identity permission.
 *
 * <p>Business role: carries permission catalog data across identity application
 * boundaries without exposing persistence entities or REST response contracts.</p>
 *
 * <p>Architecture role: immutable application-layer data transfer record used by
 * use-case ports and application services.</p>
 *
 * <p>Validation responsibility: requires permission identifier, permission code, and
 * permission name. Blank descriptions are normalized to {@code null}.</p>
 *
 * <p>Usage: return this DTO from permission listing and user-permission use cases.</p>
 */
public record PermissionDto(
        PermissionId id,
        PermissionCode code,
        PermissionName name,
        String description
) {

    public PermissionDto {
        id = requireNonNull(id, "PermissionId");
        code = requireNonNull(code, "PermissionCode");
        name = requireNonNull(name, "PermissionName");
        description = normalize(description);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
