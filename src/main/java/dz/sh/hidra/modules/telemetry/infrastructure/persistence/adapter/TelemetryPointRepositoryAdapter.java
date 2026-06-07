/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter for telemetry point repository port.
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
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySignalTypeReference;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryPointJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryPointJpaRepository;

/**
 * Persistence adapter for telemetry point repository port.
 */
@Repository
public class TelemetryPointRepositoryAdapter implements TelemetryPointRepositoryPort {

    private final TelemetryPointJpaRepository pointRepository;
    private final TelemetryPersistenceMapper mapper;

    public TelemetryPointRepositoryAdapter(
            TelemetryPointJpaRepository pointRepository,
            TelemetryPersistenceMapper mapper) {

        this.pointRepository = Objects.requireNonNull(pointRepository, "TelemetryPointJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryPersistenceMapper must not be null.");
    }

    @Override
    public TelemetryPoint save(TelemetryPoint point) {
        return mapper.toDomain(pointRepository.save(mapper.toEntity(point)));
    }

    @Override
    public Optional<TelemetryPoint> findById(TelemetryPointId id) {
        return pointRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<TelemetryPoint> findByCode(TelemetryCode code) {
        return pointRepository.findByCode(code.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCode(TelemetryCode code) {
        return pointRepository.existsByCode(code.value());
    }

    @Override
    public PageResult<TelemetryPoint> findAll(
            String searchText,
            TelemetryDeviceId deviceId,
            TelemetryPointTypeReference pointType,
            TelemetrySignalTypeReference signalType,
            TelemetryPointStatus status,
            PageRequest pageRequest) {

        Pageable pageable = TelemetrySpringPageables.from(pageRequest);
        Page<TelemetryPointJpaEntity> page;

        if (searchText != null && !searchText.isBlank()) {
            page = pointRepository.findByCodeContainingIgnoreCaseOrNameFrContainingIgnoreCase(searchText, searchText, pageable);
        } else if (deviceId != null && status != null) {
            page = pointRepository.findByDeviceIdAndStatus(deviceId.value(), status.name(), pageable);
        } else if (deviceId != null) {
            page = pointRepository.findByDeviceId(deviceId.value(), pageable);
        } else if (pointType != null) {
            page = pointRepository.findByPointTypeId(pointType.id(), pageable);
        } else if (signalType != null) {
            page = pointRepository.findBySignalTypeId(signalType.id(), pageable);
        } else if (status != null) {
            page = pointRepository.findByStatus(status.name(), pageable);
        } else {
            page = pointRepository.findAll(pageable);
        }

        return TelemetrySpringPageables.toPageResult(page, mapper::toDomain);
    }
}
