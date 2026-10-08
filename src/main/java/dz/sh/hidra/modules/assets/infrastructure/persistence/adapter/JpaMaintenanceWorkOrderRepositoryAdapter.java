/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMaintenanceWorkOrderRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MaintenanceWorkOrder.
 */
@Component
public class JpaMaintenanceWorkOrderRepositoryAdapter implements MaintenanceWorkOrderRepositoryPort {

    private final MaintenanceWorkOrderJpaRepository repository;
    private final MaintenanceWorkOrderReferenceValidation validation;

    public JpaMaintenanceWorkOrderRepositoryAdapter(MaintenanceWorkOrderJpaRepository repository, MaintenanceWorkOrderReferenceValidation validation) {
        this.repository = Objects.requireNonNull(repository, "MaintenanceWorkOrderJpaRepository must not be null.");
        this.validation = Objects.requireNonNull(validation);
    }

    @Override
    @Transactional
    public MaintenanceWorkOrder save(MaintenanceWorkOrder model) {
        Objects.requireNonNull(model);
        var previous=repository.findByIdForUpdate(model.id()).map(AssetsPersistenceMapper::toDomain).orElse(null);
        validation.validate(model,previous);
        return AssetsPersistenceMapper.toDomain(repository.saveAndFlush(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MaintenanceWorkOrder> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
