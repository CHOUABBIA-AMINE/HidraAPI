/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaScopedConfigurationOverrideRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ScopedConfigurationOverride.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ScopedConfigurationOverrideRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ScopedConfigurationOverride;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ScopedConfigurationOverrideJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ScopedConfigurationOverride.
 */
@Component
public class JpaScopedConfigurationOverrideRepositoryAdapter implements ScopedConfigurationOverrideRepositoryPort {

    private final ScopedConfigurationOverrideJpaRepository repository;

    public JpaScopedConfigurationOverrideRepositoryAdapter(ScopedConfigurationOverrideJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ScopedConfigurationOverrideJpaRepository must not be null.");
    }

    @Override
    public ScopedConfigurationOverride save(ScopedConfigurationOverride model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ScopedConfigurationOverride> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
