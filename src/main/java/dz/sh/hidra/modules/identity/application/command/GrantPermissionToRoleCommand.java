/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GrantPermissionToRoleCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.command
 *
 * @Description : Command record for granting an identity permission to a role.
 *
 */
package dz.sh.hidra.modules.identity.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;

/**
 * Command used to grant a permission to a role.
 *
 * <p>Business role: identifies the role and permission code involved in a permission
 * grant decision.</p>
 *
 * <p>Architecture role: application-layer command implementing the kernel
 * {@link Command} marker. It does not own platform authorization plumbing or persistence
 * mapping.</p>
 *
 * <p>Validation responsibility: rejects missing role identifier and permission code
 * values.</p>
 *
 * <p>Usage: pass this record to the future grant-permission-to-role use case.</p>
 */
public record GrantPermissionToRoleCommand(
        RoleId roleId,
        PermissionCode permissionCode
) implements Command {

    public GrantPermissionToRoleCommand {
        roleId = requireNonNull(roleId, "RoleId");
        permissionCode = requireNonNull(permissionCode, "PermissionCode");
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
