/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConfigurationDefinitionVersionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ConfigurationDefinitionVersion.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationDefinitionVersionRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationDefinitionVersion;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ConfigurationDefinitionVersionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ConfigurationDefinitionVersion.
 */
@Component
public class JpaConfigurationDefinitionVersionRepositoryAdapter implements ConfigurationDefinitionVersionRepositoryPort {

    private final ConfigurationDefinitionVersionJpaRepository repository;

    public JpaConfigurationDefinitionVersionRepositoryAdapter(ConfigurationDefinitionVersionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ConfigurationDefinitionVersionJpaRepository must not be null.");
    }

    @Override
    public ConfigurationDefinitionVersion save(ConfigurationDefinitionVersion model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ConfigurationDefinitionVersion> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
