/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GroupRoleGrantRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Repository port for GroupRoleGrant.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.GroupRoleGrant;

import java.util.Optional;

/**
 * Repository port for GroupRoleGrant.
 */
public interface GroupRoleGrantRepositoryPort {

    GroupRoleGrant save(GroupRoleGrant model);

    Optional<GroupRoleGrant> findById(String id);
}
