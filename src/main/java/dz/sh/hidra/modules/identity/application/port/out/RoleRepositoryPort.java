/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Outbound repository port for Role.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.Role;

import java.util.Optional;

/**
 * Outbound repository port for Role.
 */
public interface RoleRepositoryPort {

    Role save(Role model);

    Optional<Role> findById(String id);
}
