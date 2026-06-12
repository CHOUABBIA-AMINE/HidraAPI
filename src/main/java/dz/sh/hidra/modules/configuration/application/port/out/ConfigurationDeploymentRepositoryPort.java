/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDeploymentRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.out
 *
 * @Description : Repository port for ConfigurationDeployment.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.out;

import dz.sh.hidra.modules.configuration.domain.model.ConfigurationDeployment;

import java.util.Optional;

/**
 * Repository port for ConfigurationDeployment.
 */
public interface ConfigurationDeploymentRepositoryPort {

    ConfigurationDeployment save(ConfigurationDeployment model);

    Optional<ConfigurationDeployment> findById(String id);
}
