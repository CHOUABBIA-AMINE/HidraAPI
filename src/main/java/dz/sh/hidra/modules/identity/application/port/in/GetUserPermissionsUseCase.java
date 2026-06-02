/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetUserPermissionsUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.in
 *
 * @Description : Inbound use-case port for retrieving a user's effective permissions.
 *
 */
    package dz.sh.hidra.modules.identity.application.port.in;

    import dz.sh.hidra.modules.identity.application.dto.PermissionDto;
import dz.sh.hidra.modules.identity.application.query.GetUserPermissionsQuery;

import java.util.List;

    /**
     * Inbound use-case port for retrieving effective user permissions.
     *
     * <p>Business role: exposes the application boundary for retrieving effective user permissions in the identity
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
    public interface GetUserPermissionsUseCase {

        List<PermissionDto> getUserPermissions(GetUserPermissionsQuery query);
    }
