/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateUserCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.command
 *
 * @Description : Command to create an identity user.
 *
 */
package dz.sh.hidra.modules.identity.application.command;

import dz.sh.hidra.modules.identity.domain.value.UserType;

/**
 * Command to create an identity user.
 *
 * @param username username
 * @param emailAddress email address
 * @param displayName display name
 * @param userType user type
 * @param employeeReferenceId optional employee reference
 */
public record CreateUserCommand(
        String username,
        String emailAddress,
        String displayName,
        UserType userType,
        String employeeReferenceId
) {
}
