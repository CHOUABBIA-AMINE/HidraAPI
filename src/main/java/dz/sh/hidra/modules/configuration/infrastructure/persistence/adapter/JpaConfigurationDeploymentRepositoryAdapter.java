/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConfigurationDeploymentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ConfigurationDeployment.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationDeploymentRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationDeployment;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ConfigurationDeploymentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ConfigurationDeployment.
 */
@Component
public class JpaConfigurationDeploymentRepositoryAdapter implements ConfigurationDeploymentRepositoryPort {

    private final ConfigurationDeploymentJpaRepository repository;

    public JpaConfigurationDeploymentRepositoryAdapter(ConfigurationDeploymentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ConfigurationDeploymentJpaRepository must not be null.");
    }

    @Override
    public ConfigurationDeployment save(ConfigurationDeployment model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ConfigurationDeployment> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
