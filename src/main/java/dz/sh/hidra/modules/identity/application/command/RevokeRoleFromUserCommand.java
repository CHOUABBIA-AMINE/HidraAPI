/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RevokeRoleFromUserCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.command
 *
 * @Description : Command record for revoking an identity role from a user.
 *
 */
package dz.sh.hidra.modules.identity.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.UserId;

/**
 * Command used to revoke a role from a user.
 *
 * <p>Business role: identifies the user and role involved in a role revocation decision.</p>
 *
 * <p>Architecture role: application-layer command implementing the kernel
 * {@link Command} marker without REST, persistence, platform security, or organization
 * dependencies.</p>
 *
 * <p>Validation responsibility: rejects missing user and role identifiers.</p>
 *
 * <p>Usage: pass this record to the future revoke-role-from-user use case.</p>
 */
public record RevokeRoleFromUserCommand(
        UserId userId,
        RoleId roleId
) implements Command {

    public RevokeRoleFromUserCommand {
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
