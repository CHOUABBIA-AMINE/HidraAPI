/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMaintenanceTaskTemplateRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MaintenanceTaskTemplate.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.MaintenanceTaskTemplateRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.MaintenanceTaskTemplate;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.MaintenanceTaskTemplateJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MaintenanceTaskTemplate.
 */
@Component
public class JpaMaintenanceTaskTemplateRepositoryAdapter implements MaintenanceTaskTemplateRepositoryPort {

    private final MaintenanceTaskTemplateJpaRepository repository;

    public JpaMaintenanceTaskTemplateRepositoryAdapter(MaintenanceTaskTemplateJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MaintenanceTaskTemplateJpaRepository must not be null.");
    }

    @Override
    public MaintenanceTaskTemplate save(MaintenanceTaskTemplate model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MaintenanceTaskTemplate> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
