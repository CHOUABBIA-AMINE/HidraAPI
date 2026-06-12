/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConfigurationChangeRequestRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ConfigurationChangeRequest.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationChangeRequestRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationChangeRequest;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ConfigurationChangeRequestJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ConfigurationChangeRequest.
 */
@Component
public class JpaConfigurationChangeRequestRepositoryAdapter implements ConfigurationChangeRequestRepositoryPort {

    private final ConfigurationChangeRequestJpaRepository repository;

    public JpaConfigurationChangeRequestRepositoryAdapter(ConfigurationChangeRequestJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ConfigurationChangeRequestJpaRepository must not be null.");
    }

    @Override
    public ConfigurationChangeRequest save(ConfigurationChangeRequest model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ConfigurationChangeRequest> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
