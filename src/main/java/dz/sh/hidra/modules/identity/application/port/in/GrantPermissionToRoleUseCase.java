/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GrantPermissionToRoleUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.in
 *
 * @Description : Inbound use-case port for granting a permission to a role.
 *
 */
    package dz.sh.hidra.modules.identity.application.port.in;

    import dz.sh.hidra.modules.identity.application.command.GrantPermissionToRoleCommand;
import dz.sh.hidra.modules.identity.application.dto.RoleDto;

    /**
     * Inbound use-case port for granting a permission to an identity role.
     *
     * <p>Business role: exposes the application boundary for granting a permission to an identity role in the identity
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
    public interface GrantPermissionToRoleUseCase {

        RoleDto grantPermissionToRole(GrantPermissionToRoleCommand command);
    }
