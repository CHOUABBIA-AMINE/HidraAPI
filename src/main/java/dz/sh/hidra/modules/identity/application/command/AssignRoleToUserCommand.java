/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignRoleToUserCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.command
 *
 * @Description : Command record for assigning an identity role to a user.
 *
 */
package dz.sh.hidra.modules.identity.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.UserId;

/**
 * Command used to assign a role to a user.
 *
 * <p>Business role: identifies the user and role involved in a role assignment decision.</p>
 *
 * <p>Architecture role: application-layer command implementing the kernel
 * {@link Command} marker without depending on platform security authorities,
 * persistence entities, REST DTOs, or organization structures.</p>
 *
 * <p>Validation responsibility: rejects missing user and role identifiers.</p>
 *
 * <p>Usage: pass this record to the future assign-role-to-user use case.</p>
 */
public record AssignRoleToUserCommand(
        UserId userId,
        RoleId roleId
) implements Command {

    public AssignRoleToUserCommand {
        userId = requireNonNull(userId, "UserId");
        roleId = requireNonNull(roleId, "RoleId");
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
