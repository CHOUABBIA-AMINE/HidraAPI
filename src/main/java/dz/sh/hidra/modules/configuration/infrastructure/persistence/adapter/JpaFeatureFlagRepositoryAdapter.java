/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaFeatureFlagRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for FeatureFlag.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.FeatureFlagRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.FeatureFlag;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.FeatureFlagJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for FeatureFlag.
 */
@Component
public class JpaFeatureFlagRepositoryAdapter implements FeatureFlagRepositoryPort {

    private final FeatureFlagJpaRepository repository;

    public JpaFeatureFlagRepositoryAdapter(FeatureFlagJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "FeatureFlagJpaRepository must not be null.");
    }

    @Override
    public FeatureFlag save(FeatureFlag model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<FeatureFlag> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
