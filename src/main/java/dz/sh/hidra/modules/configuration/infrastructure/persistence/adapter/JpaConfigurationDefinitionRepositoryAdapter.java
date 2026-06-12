/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConfigurationDefinitionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ConfigurationDefinition.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationDefinitionRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationDefinition;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ConfigurationDefinitionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ConfigurationDefinition.
 */
@Component
public class JpaConfigurationDefinitionRepositoryAdapter implements ConfigurationDefinitionRepositoryPort {

    private final ConfigurationDefinitionJpaRepository repository;

    public JpaConfigurationDefinitionRepositoryAdapter(ConfigurationDefinitionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ConfigurationDefinitionJpaRepository must not be null.");
    }

    @Override
    public ConfigurationDefinition save(ConfigurationDefinition model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ConfigurationDefinition> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
