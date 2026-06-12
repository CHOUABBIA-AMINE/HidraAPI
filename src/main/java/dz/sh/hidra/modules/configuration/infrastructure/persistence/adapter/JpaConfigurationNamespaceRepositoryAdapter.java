/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConfigurationNamespaceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ConfigurationNamespace.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationNamespaceRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationNamespace;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ConfigurationNamespaceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ConfigurationNamespace.
 */
@Component
public class JpaConfigurationNamespaceRepositoryAdapter implements ConfigurationNamespaceRepositoryPort {

    private final ConfigurationNamespaceJpaRepository repository;

    public JpaConfigurationNamespaceRepositoryAdapter(ConfigurationNamespaceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ConfigurationNamespaceJpaRepository must not be null.");
    }

    @Override
    public ConfigurationNamespace save(ConfigurationNamespace model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ConfigurationNamespace> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
