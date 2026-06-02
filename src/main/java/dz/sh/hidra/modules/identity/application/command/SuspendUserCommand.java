/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuspendUserCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.command
 *
 * @Description : Command record for suspending an identity user.
 *
 */
package dz.sh.hidra.modules.identity.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.UserId;

/**
 * Command used to suspend an active identity user.
 *
 * <p>Business role: identifies the user whose lifecycle should transition to suspended.</p>
 *
 * <p>Architecture role: application-layer command implementing the kernel
 * {@link Command} marker without framework or platform security dependencies.</p>
 *
 * <p>Validation responsibility: rejects a missing user identifier.</p>
 *
 * <p>Usage: pass this record to the future suspend-user use case.</p>
 */
public record SuspendUserCommand(UserId userId) implements Command {

    public SuspendUserCommand {
        userId = requireNonNull(userId, "UserId");
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
