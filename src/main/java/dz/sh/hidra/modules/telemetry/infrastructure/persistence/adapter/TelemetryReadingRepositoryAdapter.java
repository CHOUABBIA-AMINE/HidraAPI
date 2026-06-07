/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter for telemetry reading repository port.
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
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryQualityCodeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryReadingJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryReadingJpaRepository;

/**
 * Persistence adapter for telemetry reading repository port.
 */
@Repository
public class TelemetryReadingRepositoryAdapter implements TelemetryReadingRepositoryPort {

    private final TelemetryReadingJpaRepository readingRepository;
    private final TelemetryPersistenceMapper mapper;

    public TelemetryReadingRepositoryAdapter(
            TelemetryReadingJpaRepository readingRepository,
            TelemetryPersistenceMapper mapper) {

        this.readingRepository = Objects.requireNonNull(readingRepository, "TelemetryReadingJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryPersistenceMapper must not be null.");
    }

    @Override
    public TelemetryReading save(TelemetryReading reading) {
        return mapper.toDomain(readingRepository.save(mapper.toEntity(reading)));
    }

    @Override
    public Optional<TelemetryReading> findById(TelemetryReadingId id) {
        return readingRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<TelemetryReading> findLatestByPointId(TelemetryPointId pointId) {
        return readingRepository.findFirstByPointIdOrderBySourceTimestampDesc(pointId.value()).map(mapper::toDomain);
    }

    @Override
    public PageResult<TelemetryReading> findAll(
            TelemetryPointId pointId,
            TelemetryQualityCodeReference qualityCode,
            TelemetryReadingState state,
            TelemetryTimestamp fromSourceTimestamp,
            TelemetryTimestamp toSourceTimestamp,
            TelemetryIngestionBatchId ingestionBatchId,
            PageRequest pageRequest) {

        Pageable pageable = TelemetrySpringPageables.from(pageRequest);
        Page<TelemetryReadingJpaEntity> page;

        if (pointId != null && fromSourceTimestamp != null && toSourceTimestamp != null) {
            page = readingRepository.findByPointIdAndSourceTimestampBetween(
                    pointId.value(),
                    fromSourceTimestamp.value(),
                    toSourceTimestamp.value(),
                    pageable);
        } else if (pointId != null) {
            page = readingRepository.findByPointId(pointId.value(), pageable);
        } else if (qualityCode != null) {
            page = readingRepository.findByQualityCodeId(qualityCode.id(), pageable);
        } else if (state != null) {
            page = readingRepository.findByState(state.name(), pageable);
        } else if (ingestionBatchId != null) {
            page = readingRepository.findByIngestionBatchId(ingestionBatchId.value(), pageable);
        } else {
            page = readingRepository.findAll(pageable);
        }

        return TelemetrySpringPageables.toPageResult(page, mapper::toDomain);
    }

    @Override
    public boolean existsByPointIdAndSourceTimestamp(TelemetryPointId pointId, TelemetryTimestamp sourceTimestamp) {
        return readingRepository.existsByPointIdAndSourceTimestamp(pointId.value(), sourceTimestamp.value());
    }
}
