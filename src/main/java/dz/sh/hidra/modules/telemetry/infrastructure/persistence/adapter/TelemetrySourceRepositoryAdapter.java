/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter for telemetry source repository port.
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
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetrySourceRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryProtocolReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceTypeReference;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetrySourceJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetrySourceJpaRepository;

/**
 * Persistence adapter for telemetry source repository port.
 */
@Repository
public class TelemetrySourceRepositoryAdapter implements TelemetrySourceRepositoryPort {

    private final TelemetrySourceJpaRepository sourceRepository;
    private final TelemetryPersistenceMapper mapper;

    public TelemetrySourceRepositoryAdapter(
            TelemetrySourceJpaRepository sourceRepository,
            TelemetryPersistenceMapper mapper) {

        this.sourceRepository = Objects.requireNonNull(sourceRepository, "TelemetrySourceJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryPersistenceMapper must not be null.");
    }

    @Override
    public TelemetrySource save(TelemetrySource source) {
        return mapper.toDomain(sourceRepository.save(mapper.toEntity(source)));
    }

    @Override
    public Optional<TelemetrySource> findById(TelemetrySourceId id) {
        return sourceRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<TelemetrySource> findByCode(TelemetryCode code) {
        return sourceRepository.findByCode(code.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCode(TelemetryCode code) {
        return sourceRepository.existsByCode(code.value());
    }

    @Override
    public PageResult<TelemetrySource> findAll(
            String searchText,
            TelemetrySourceTypeReference sourceType,
            TelemetryProtocolReference protocol,
            TelemetrySourceStatus status,
            PageRequest pageRequest) {

        Pageable pageable = TelemetrySpringPageables.from(pageRequest);
        Page<TelemetrySourceJpaEntity> page;

        if (searchText != null && !searchText.isBlank()) {
            page = sourceRepository.findByCodeContainingIgnoreCaseOrNameFrContainingIgnoreCase(searchText, searchText, pageable);
        } else if (sourceType != null && status != null) {
            page = sourceRepository.findBySourceTypeIdAndStatus(sourceType.id(), status.name(), pageable);
        } else if (sourceType != null) {
            page = sourceRepository.findBySourceTypeId(sourceType.id(), pageable);
        } else if (protocol != null) {
            page = sourceRepository.findByProtocolId(protocol.id(), pageable);
        } else if (status != null) {
            page = sourceRepository.findByStatus(status.name(), pageable);
        } else {
            page = sourceRepository.findAll(pageable);
        }

        return TelemetrySpringPageables.toPageResult(page, mapper::toDomain);
    }
}
