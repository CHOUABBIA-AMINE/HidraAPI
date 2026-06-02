/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.dto
 *
 * @Description : Application DTO representing an identity role.
 *
 */
package dz.sh.hidra.modules.identity.application.dto;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;

import java.util.List;

/**
 * Application DTO representing an identity role.
 *
 * <p>Business role: carries role data and granted permission summaries across identity
 * application boundaries.</p>
 *
 * <p>Architecture role: immutable application-layer data transfer record used by role
 * use-case ports and application services.</p>
 *
 * <p>Validation responsibility: requires role identity fields and status, and defensively
 * copies permission data.</p>
 *
 * <p>Usage: return this DTO from role creation, role listing, and permission-grant use
 * cases.</p>
 */
public record RoleDto(
        RoleId id,
        RoleCode code,
        RoleName name,
        RoleStatus status,
        List<PermissionDto> permissions
) {

    public RoleDto {
        id = requireNonNull(id, "RoleId");
        code = requireNonNull(code, "RoleCode");
        name = requireNonNull(name, "RoleName");
        status = requireNonNull(status, "RoleStatus");
        permissions = List.copyOf(requireNonNull(permissions, "permissions"));
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
