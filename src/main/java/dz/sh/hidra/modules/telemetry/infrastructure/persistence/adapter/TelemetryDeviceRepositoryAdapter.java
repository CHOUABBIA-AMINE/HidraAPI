/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDeviceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter for telemetry device repository port.
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
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryDeviceRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryDevice;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryDeviceJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryDeviceJpaRepository;

/**
 * Persistence adapter for telemetry device repository port.
 */
@Repository
public class TelemetryDeviceRepositoryAdapter implements TelemetryDeviceRepositoryPort {

    private final TelemetryDeviceJpaRepository deviceRepository;
    private final TelemetryPersistenceMapper mapper;

    public TelemetryDeviceRepositoryAdapter(
            TelemetryDeviceJpaRepository deviceRepository,
            TelemetryPersistenceMapper mapper) {

        this.deviceRepository = Objects.requireNonNull(deviceRepository, "TelemetryDeviceJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryPersistenceMapper must not be null.");
    }

    @Override
    public TelemetryDevice save(TelemetryDevice device) {
        return mapper.toDomain(deviceRepository.save(mapper.toEntity(device)));
    }

    @Override
    public Optional<TelemetryDevice> findById(TelemetryDeviceId id) {
        return deviceRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<TelemetryDevice> findByCode(TelemetryCode code) {
        return deviceRepository.findByCode(code.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCode(TelemetryCode code) {
        return deviceRepository.existsByCode(code.value());
    }

    @Override
    public PageResult<TelemetryDevice> findAll(
            String searchText,
            TelemetrySourceId sourceId,
            TelemetryDeviceTypeReference deviceType,
            TelemetryDeviceStatus status,
            PageRequest pageRequest) {

        Pageable pageable = TelemetrySpringPageables.from(pageRequest);
        Page<TelemetryDeviceJpaEntity> page;

        if (searchText != null && !searchText.isBlank()) {
            page = deviceRepository.findByCodeContainingIgnoreCaseOrNameFrContainingIgnoreCase(searchText, searchText, pageable);
        } else if (sourceId != null && status != null) {
            page = deviceRepository.findBySourceIdAndStatus(sourceId.value(), status.name(), pageable);
        } else if (sourceId != null) {
            page = deviceRepository.findBySourceId(sourceId.value(), pageable);
        } else if (deviceType != null) {
            page = deviceRepository.findByDeviceTypeId(deviceType.id(), pageable);
        } else if (status != null) {
            page = deviceRepository.findByStatus(status.name(), pageable);
        } else {
            page = deviceRepository.findAll(pageable);
        }

        return TelemetrySpringPageables.toPageResult(page, mapper::toDomain);
    }
}
