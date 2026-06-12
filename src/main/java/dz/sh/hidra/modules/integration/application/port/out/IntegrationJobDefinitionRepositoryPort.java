/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobDefinitionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Repository port for IntegrationJobDefinition.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

import dz.sh.hidra.modules.integration.domain.model.IntegrationJobDefinition;

import java.util.Optional;

/**
 * Repository port for IntegrationJobDefinition.
 */
public interface IntegrationJobDefinitionRepositoryPort {

    IntegrationJobDefinition save(IntegrationJobDefinition model);

    Optional<IntegrationJobDefinition> findById(String id);
}
