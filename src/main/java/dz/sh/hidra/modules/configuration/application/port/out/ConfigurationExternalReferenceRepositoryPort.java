/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationExternalReferenceRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.out
 *
 * @Description : Repository port for ConfigurationExternalReference.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.out;

import dz.sh.hidra.modules.configuration.domain.model.ConfigurationExternalReference;

import java.util.Optional;

/**
 * Repository port for ConfigurationExternalReference.
 */
public interface ConfigurationExternalReferenceRepositoryPort {

    ConfigurationExternalReference save(ConfigurationExternalReference model);

    Optional<ConfigurationExternalReference> findById(String id);
}
