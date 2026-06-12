/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMaintenanceExecutionRecordRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MaintenanceExecutionRecord.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.MaintenanceExecutionRecordRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.MaintenanceExecutionRecord;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.MaintenanceExecutionRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MaintenanceExecutionRecord.
 */
@Component
public class JpaMaintenanceExecutionRecordRepositoryAdapter implements MaintenanceExecutionRecordRepositoryPort {

    private final MaintenanceExecutionRecordJpaRepository repository;

    public JpaMaintenanceExecutionRecordRepositoryAdapter(MaintenanceExecutionRecordJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MaintenanceExecutionRecordJpaRepository must not be null.");
    }

    @Override
    public MaintenanceExecutionRecord save(MaintenanceExecutionRecord model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MaintenanceExecutionRecord> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
