/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateRoleCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.command
 *
 * @Description : Command record for creating an identity role.
 *
 */
package dz.sh.hidra.modules.identity.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleName;

/**
 * Command used to create an identity role.
 *
 * <p>Business role: carries the validated role code and display name used to define a
 * role in the identity access model.</p>
 *
 * <p>Architecture role: application-layer command implementing the kernel
 * {@link Command} marker. It remains independent from controllers, persistence, platform
 * security plumbing, and organization structures.</p>
 *
 * <p>Validation responsibility: rejects missing role code or role name values.</p>
 *
 * <p>Usage: pass this record to the future create-role use case.</p>
 */
public record CreateRoleCommand(
        RoleCode roleCode,
        RoleName roleName
) implements Command {

    public CreateRoleCommand {
        roleCode = requireNonNull(roleCode, "RoleCode");
        roleName = requireNonNull(roleName, "RoleName");
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
