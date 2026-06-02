/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterUserCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.command
 *
 * @Description : Command record for registering an identity user.
 *
 */
package dz.sh.hidra.modules.identity.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.Username;

/**
 * Command used to register a new HidraAPI identity user.
 *
 * <p>Business role: carries the validated username and email address required to create
 * a security identity in the identity module.</p>
 *
 * <p>Architecture role: application-layer command implementing the kernel
 * {@link Command} marker. It does not depend on REST DTOs, persistence entities,
 * platform security plumbing, or organization employee structures.</p>
 *
 * <p>Validation responsibility: rejects missing username and email address values.</p>
 *
 * <p>Usage: pass this record to the future register-user use case.</p>
 */
public record RegisterUserCommand(
        Username username,
        EmailAddress emailAddress
) implements Command {

    public RegisterUserCommand {
        username = requireNonNull(username, "Username");
        emailAddress = requireNonNull(emailAddress, "EmailAddress");
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
