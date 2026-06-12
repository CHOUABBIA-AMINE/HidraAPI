/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMaintenanceWorkOrderTaskRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MaintenanceWorkOrderTask.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.MaintenanceWorkOrderTaskRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.MaintenanceWorkOrderTask;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.MaintenanceWorkOrderTaskJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MaintenanceWorkOrderTask.
 */
@Component
public class JpaMaintenanceWorkOrderTaskRepositoryAdapter implements MaintenanceWorkOrderTaskRepositoryPort {

    private final MaintenanceWorkOrderTaskJpaRepository repository;

    public JpaMaintenanceWorkOrderTaskRepositoryAdapter(MaintenanceWorkOrderTaskJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MaintenanceWorkOrderTaskJpaRepository must not be null.");
    }

    @Override
    public MaintenanceWorkOrderTask save(MaintenanceWorkOrderTask model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MaintenanceWorkOrderTask> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
