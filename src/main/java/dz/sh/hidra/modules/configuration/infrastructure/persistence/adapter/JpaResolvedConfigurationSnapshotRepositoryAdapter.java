/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaResolvedConfigurationSnapshotRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ResolvedConfigurationSnapshot.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ResolvedConfigurationSnapshotRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ResolvedConfigurationSnapshot;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ResolvedConfigurationSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ResolvedConfigurationSnapshot.
 */
@Component
public class JpaResolvedConfigurationSnapshotRepositoryAdapter implements ResolvedConfigurationSnapshotRepositoryPort {

    private final ResolvedConfigurationSnapshotJpaRepository repository;

    public JpaResolvedConfigurationSnapshotRepositoryAdapter(ResolvedConfigurationSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ResolvedConfigurationSnapshotJpaRepository must not be null.");
    }

    @Override
    public ResolvedConfigurationSnapshot save(ResolvedConfigurationSnapshot model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ResolvedConfigurationSnapshot> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
