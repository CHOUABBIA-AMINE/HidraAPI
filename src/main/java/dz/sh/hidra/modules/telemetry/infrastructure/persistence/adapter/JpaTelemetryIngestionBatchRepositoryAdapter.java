/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryIngestionBatchRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TelemetryIngestionBatch.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryIngestionBatchRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryIngestionBatch;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryIngestionBatchJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for TelemetryIngestionBatch.
 */
@Component
public class JpaTelemetryIngestionBatchRepositoryAdapter implements TelemetryIngestionBatchRepositoryPort {

    private final TelemetryIngestionBatchJpaRepository repository;

    public JpaTelemetryIngestionBatchRepositoryAdapter(TelemetryIngestionBatchJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TelemetryIngestionBatchJpaRepository must not be null.");
    }

    @Override
    public TelemetryIngestionBatch save(TelemetryIngestionBatch model) {
        return TelemetryPersistenceMapper.toDomain(
                repository.save(TelemetryPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<TelemetryIngestionBatch> findById(String id) {
        return repository.findById(id).map(TelemetryPersistenceMapper::toDomain);
    }
}
