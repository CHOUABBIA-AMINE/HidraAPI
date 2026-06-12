/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationCatalogTranslationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.out
 *
 * @Description : Repository port for ConfigurationCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.out;

import dz.sh.hidra.modules.configuration.domain.model.ConfigurationCatalogTranslation;

import java.util.Optional;

/**
 * Repository port for ConfigurationCatalogTranslation.
 */
public interface ConfigurationCatalogTranslationRepositoryPort {

    ConfigurationCatalogTranslation save(ConfigurationCatalogTranslation model);

    Optional<ConfigurationCatalogTranslation> findById(String id);
}
