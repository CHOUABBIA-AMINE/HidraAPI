/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAdministrationCommandUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.in
 *
 * @Description : Defines identity role, permission, and grant administration mutation use cases.
 *
 */
package dz.sh.hidra.modules.identity.application.port.in;

import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.CreatePermission;
import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.CreateRole;
import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.GrantPermissionToRole;
import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.GrantPermissionToUser;
import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.GrantRoleToUser;

/**
 * Mutation boundary for identity administration.
 */
public interface IdentityAdministrationCommandUseCase {

    IdentityAdministrationQueryUseCase.RoleView createRole(CreateRole command);

    IdentityAdministrationQueryUseCase.PermissionView createPermission(CreatePermission command);

    String grantRoleToUser(GrantRoleToUser command);

    String grantPermissionToRole(GrantPermissionToRole command);

    String grantPermissionToUser(GrantPermissionToUser command);
}
