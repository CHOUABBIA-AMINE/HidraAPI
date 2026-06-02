/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignRoleToUserUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.in
 *
 * @Description : Inbound use-case port for assigning a role to an identity user.
 *
 */
    package dz.sh.hidra.modules.identity.application.port.in;

    import dz.sh.hidra.modules.identity.application.command.AssignRoleToUserCommand;
import dz.sh.hidra.modules.identity.application.dto.UserDto;

    /**
     * Inbound use-case port for assigning an identity role to a user.
     *
     * <p>Business role: exposes the application boundary for assigning an identity role to a user in the identity
     * module.</p>
     *
     * <p>Architecture role: input port implemented by a future application service and
     * called by API adapters. It does not depend on controllers, persistence adapters,
     * platform security plumbing, or organization structures.</p>
     *
     * <p>Validation responsibility: implementations should validate command or query
     * presence and enforce use-case-specific identity rules.</p>
     *
     * <p>Usage: depend on this interface from the API layer when invoking the use case.</p>
     */
    public interface AssignRoleToUserUseCase {

        UserDto assignRoleToUser(AssignRoleToUserCommand command);
    }
