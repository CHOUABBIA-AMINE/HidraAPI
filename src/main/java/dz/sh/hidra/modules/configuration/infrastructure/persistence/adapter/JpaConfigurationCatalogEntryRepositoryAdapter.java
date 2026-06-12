/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConfigurationCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ConfigurationCatalogEntry.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationCatalogEntry;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ConfigurationCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ConfigurationCatalogEntry.
 */
@Component
public class JpaConfigurationCatalogEntryRepositoryAdapter implements ConfigurationCatalogEntryRepositoryPort {

    private final ConfigurationCatalogEntryJpaRepository repository;

    public JpaConfigurationCatalogEntryRepositoryAdapter(ConfigurationCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ConfigurationCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public ConfigurationCatalogEntry save(ConfigurationCatalogEntry model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ConfigurationCatalogEntry> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
