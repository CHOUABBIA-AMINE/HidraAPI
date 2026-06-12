/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDefinitionVersionRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.out
 *
 * @Description : Repository port for ConfigurationDefinitionVersion.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.out;

import dz.sh.hidra.modules.configuration.domain.model.ConfigurationDefinitionVersion;

import java.util.Optional;

/**
 * Repository port for ConfigurationDefinitionVersion.
 */
public interface ConfigurationDefinitionVersionRepositoryPort {

    ConfigurationDefinitionVersion save(ConfigurationDefinitionVersion model);

    Optional<ConfigurationDefinitionVersion> findById(String id);
}
