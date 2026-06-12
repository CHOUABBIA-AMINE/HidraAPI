/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConfigurationProfileRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ConfigurationProfile.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationProfileRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationProfile;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ConfigurationProfileJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ConfigurationProfile.
 */
@Component
public class JpaConfigurationProfileRepositoryAdapter implements ConfigurationProfileRepositoryPort {

    private final ConfigurationProfileJpaRepository repository;

    public JpaConfigurationProfileRepositoryAdapter(ConfigurationProfileJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ConfigurationProfileJpaRepository must not be null.");
    }

    @Override
    public ConfigurationProfile save(ConfigurationProfile model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ConfigurationProfile> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
