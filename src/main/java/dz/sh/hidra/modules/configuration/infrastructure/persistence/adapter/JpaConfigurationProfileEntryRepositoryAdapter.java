/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConfigurationProfileEntryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ConfigurationProfileEntry.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationProfileEntryRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationProfileEntry;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ConfigurationProfileEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ConfigurationProfileEntry.
 */
@Component
public class JpaConfigurationProfileEntryRepositoryAdapter implements ConfigurationProfileEntryRepositoryPort {

    private final ConfigurationProfileEntryJpaRepository repository;

    public JpaConfigurationProfileEntryRepositoryAdapter(ConfigurationProfileEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ConfigurationProfileEntryJpaRepository must not be null.");
    }

    @Override
    public ConfigurationProfileEntry save(ConfigurationProfileEntry model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ConfigurationProfileEntry> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
