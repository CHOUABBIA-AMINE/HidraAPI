/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMaintenanceWorkOrderRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MaintenanceWorkOrder.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.MaintenanceWorkOrderRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.MaintenanceWorkOrder;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.MaintenanceWorkOrderJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MaintenanceWorkOrder.
 */
@Component
public class JpaMaintenanceWorkOrderRepositoryAdapter implements MaintenanceWorkOrderRepositoryPort {

    private final MaintenanceWorkOrderJpaRepository repository;

    public JpaMaintenanceWorkOrderRepositoryAdapter(MaintenanceWorkOrderJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MaintenanceWorkOrderJpaRepository must not be null.");
    }

    @Override
    public MaintenanceWorkOrder save(MaintenanceWorkOrder model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MaintenanceWorkOrder> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
