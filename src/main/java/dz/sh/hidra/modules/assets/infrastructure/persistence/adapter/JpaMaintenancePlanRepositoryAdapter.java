/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMaintenancePlanRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MaintenancePlan.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.MaintenancePlanRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.MaintenancePlan;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.MaintenancePlanJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MaintenancePlan.
 */
@Component
public class JpaMaintenancePlanRepositoryAdapter implements MaintenancePlanRepositoryPort {

    private final MaintenancePlanJpaRepository repository;

    public JpaMaintenancePlanRepositoryAdapter(MaintenancePlanJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MaintenancePlanJpaRepository must not be null.");
    }

    @Override
    public MaintenancePlan save(MaintenancePlan model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MaintenancePlan> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
