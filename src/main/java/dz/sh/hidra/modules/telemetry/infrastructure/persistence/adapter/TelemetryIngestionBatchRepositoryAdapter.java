/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionBatchRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter for telemetry ingestion batch repository port.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryIngestionBatchRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryIngestionBatch;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryIngestionBatchJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryIngestionBatchJpaRepository;

/**
 * Persistence adapter for telemetry ingestion batch repository port.
 */
@Repository
public class TelemetryIngestionBatchRepositoryAdapter implements TelemetryIngestionBatchRepositoryPort {

    private final TelemetryIngestionBatchJpaRepository batchRepository;
    private final TelemetryPersistenceMapper mapper;

    public TelemetryIngestionBatchRepositoryAdapter(
            TelemetryIngestionBatchJpaRepository batchRepository,
            TelemetryPersistenceMapper mapper) {

        this.batchRepository = Objects.requireNonNull(batchRepository, "TelemetryIngestionBatchJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryPersistenceMapper must not be null.");
    }

    @Override
    public TelemetryIngestionBatch save(TelemetryIngestionBatch batch) {
        return mapper.toDomain(batchRepository.save(mapper.toEntity(batch)));
    }

    @Override
    public Optional<TelemetryIngestionBatch> findById(TelemetryIngestionBatchId id) {
        return batchRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public PageResult<TelemetryIngestionBatch> findAll(
            TelemetrySourceId sourceId,
            TelemetryIngestionBatchStatus status,
            TelemetryTimestamp startedFrom,
            TelemetryTimestamp startedTo,
            PageRequest pageRequest) {

        Pageable pageable = TelemetrySpringPageables.from(pageRequest);
        Page<TelemetryIngestionBatchJpaEntity> page;

        if (sourceId != null && status != null) {
            page = batchRepository.findBySourceIdAndStatus(sourceId.value(), status.name(), pageable);
        } else if (sourceId != null) {
            page = batchRepository.findBySourceId(sourceId.value(), pageable);
        } else if (status != null) {
            page = batchRepository.findByStatus(status.name(), pageable);
        } else if (startedFrom != null && startedTo != null) {
            page = batchRepository.findByStartedAtBetween(startedFrom.value(), startedTo.value(), pageable);
        } else {
            page = batchRepository.findAll(pageable);
        }

        return TelemetrySpringPageables.toPageResult(page, mapper::toDomain);
    }
}
