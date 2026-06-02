/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.dto
 *
 * @Description : Application DTO representing an identity user.
 *
 */
package dz.sh.hidra.modules.identity.application.dto;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.EmployeeReference;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.Username;

import java.util.List;

/**
 * Application DTO representing an identity user.
 *
 * <p>Business role: carries user identity, lifecycle, optional employee reference, and
 * assigned role summaries across identity application boundaries.</p>
 *
 * <p>Architecture role: immutable application-layer data transfer record used by user
 * use-case ports and application services.</p>
 *
 * <p>Validation responsibility: requires user identifier, username, email address, and
 * status. Assigned roles are defensively copied. Employee reference remains optional and
 * generic to preserve the boundary with organization.</p>
 *
 * <p>Usage: return this DTO from user registration, lifecycle, assignment, and lookup
 * use cases.</p>
 */
public record UserDto(
        UserId id,
        Username username,
        EmailAddress emailAddress,
        UserStatus status,
        EmployeeReference employeeReference,
        List<RoleDto> roles
) {

    public UserDto {
        id = requireNonNull(id, "UserId");
        username = requireNonNull(username, "Username");
        emailAddress = requireNonNull(emailAddress, "EmailAddress");
        status = requireNonNull(status, "UserStatus");
        roles = List.copyOf(requireNonNull(roles, "roles"));
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
