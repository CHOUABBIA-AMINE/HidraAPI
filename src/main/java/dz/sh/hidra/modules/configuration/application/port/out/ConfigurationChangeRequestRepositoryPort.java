/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationChangeRequestRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.out
 *
 * @Description : Repository port for ConfigurationChangeRequest.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.out;

import dz.sh.hidra.modules.configuration.domain.model.ConfigurationChangeRequest;

import java.util.Optional;

/**
 * Repository port for ConfigurationChangeRequest.
 */
public interface ConfigurationChangeRequestRepositoryPort {

    ConfigurationChangeRequest save(ConfigurationChangeRequest model);

    Optional<ConfigurationChangeRequest> findById(String id);
}
