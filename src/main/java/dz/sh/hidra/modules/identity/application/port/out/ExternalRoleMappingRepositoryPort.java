/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalRoleMappingRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Repository port for ExternalRoleMapping.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.ExternalRoleMapping;

import java.util.Optional;

/**
 * Repository port for ExternalRoleMapping.
 */
public interface ExternalRoleMappingRepositoryPort {

    ExternalRoleMapping save(ExternalRoleMapping model);

    Optional<ExternalRoleMapping> findById(String id);
}
