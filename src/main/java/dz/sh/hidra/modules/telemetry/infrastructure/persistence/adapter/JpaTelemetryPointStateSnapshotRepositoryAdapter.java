/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryPointStateSnapshotRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TelemetryPointStateSnapshot.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointStateSnapshotRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPointStateSnapshot;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryPointStateSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for TelemetryPointStateSnapshot.
 */
@Component
public class JpaTelemetryPointStateSnapshotRepositoryAdapter implements TelemetryPointStateSnapshotRepositoryPort {

    private final TelemetryPointStateSnapshotJpaRepository repository;

    public JpaTelemetryPointStateSnapshotRepositoryAdapter(TelemetryPointStateSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TelemetryPointStateSnapshotJpaRepository must not be null.");
    }

    @Override
    public TelemetryPointStateSnapshot save(TelemetryPointStateSnapshot model) {
        return TelemetryPersistenceMapper.toDomain(
                repository.save(TelemetryPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<TelemetryPointStateSnapshot> findById(String id) {
        return repository.findById(id).map(TelemetryPersistenceMapper::toDomain);
    }
}
