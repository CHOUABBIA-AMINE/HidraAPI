/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationProfileEntryRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.out
 *
 * @Description : Repository port for ConfigurationProfileEntry.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.out;

import dz.sh.hidra.modules.configuration.domain.model.ConfigurationProfileEntry;

import java.util.Optional;

/**
 * Repository port for ConfigurationProfileEntry.
 */
public interface ConfigurationProfileEntryRepositoryPort {

    ConfigurationProfileEntry save(ConfigurationProfileEntry model);

    Optional<ConfigurationProfileEntry> findById(String id);
}
