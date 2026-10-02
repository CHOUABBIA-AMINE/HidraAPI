/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RolePermissionGrantRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Repository port for RolePermissionGrant.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.RolePermissionGrant;

import java.util.Optional;

/**
 * Repository port for RolePermissionGrant.
 */
public interface RolePermissionGrantRepositoryPort {

    RolePermissionGrant save(RolePermissionGrant model);

    Optional<RolePermissionGrant> findById(String id);
}
