/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationCatalogEntryRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.out
 *
 * @Description : Repository port for ConfigurationCatalogEntry.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.out;

import dz.sh.hidra.modules.configuration.domain.model.ConfigurationCatalogEntry;

import java.util.Optional;

/**
 * Repository port for ConfigurationCatalogEntry.
 */
public interface ConfigurationCatalogEntryRepositoryPort {

    ConfigurationCatalogEntry save(ConfigurationCatalogEntry model);

    Optional<ConfigurationCatalogEntry> findById(String id);
}
