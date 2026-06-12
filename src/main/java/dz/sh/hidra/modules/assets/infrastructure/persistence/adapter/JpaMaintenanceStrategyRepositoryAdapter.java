/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMaintenanceStrategyRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MaintenanceStrategy.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.MaintenanceStrategyRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.MaintenanceStrategy;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.MaintenanceStrategyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MaintenanceStrategy.
 */
@Component
public class JpaMaintenanceStrategyRepositoryAdapter implements MaintenanceStrategyRepositoryPort {

    private final MaintenanceStrategyJpaRepository repository;

    public JpaMaintenanceStrategyRepositoryAdapter(MaintenanceStrategyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MaintenanceStrategyJpaRepository must not be null.");
    }

    @Override
    public MaintenanceStrategy save(MaintenanceStrategy model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MaintenanceStrategy> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
