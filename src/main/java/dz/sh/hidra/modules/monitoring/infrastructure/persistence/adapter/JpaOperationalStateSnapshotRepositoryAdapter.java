/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOperationalStateSnapshotRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for OperationalStateSnapshot.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.port.out.OperationalStateSnapshotRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.OperationalStateSnapshot;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper.MonitoringPersistenceMapper;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.OperationalStateSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for OperationalStateSnapshot.
 */
@Component
public class JpaOperationalStateSnapshotRepositoryAdapter implements OperationalStateSnapshotRepositoryPort {

    private final OperationalStateSnapshotJpaRepository repository;

    public JpaOperationalStateSnapshotRepositoryAdapter(OperationalStateSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "OperationalStateSnapshotJpaRepository must not be null.");
    }

    @Override
    public OperationalStateSnapshot save(OperationalStateSnapshot model) {
        return MonitoringPersistenceMapper.toDomain(repository.save(MonitoringPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<OperationalStateSnapshot> findById(String id) {
        return repository.findById(id).map(MonitoringPersistenceMapper::toDomain);
    }
}
