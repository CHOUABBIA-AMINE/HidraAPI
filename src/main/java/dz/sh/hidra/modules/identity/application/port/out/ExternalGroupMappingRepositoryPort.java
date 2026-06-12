/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalGroupMappingRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Repository port for ExternalGroupMapping.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.ExternalGroupMapping;

import java.util.Optional;

/**
 * Repository port for ExternalGroupMapping.
 */
public interface ExternalGroupMappingRepositoryPort {

    ExternalGroupMapping save(ExternalGroupMapping model);

    Optional<ExternalGroupMapping> findById(String id);
}
