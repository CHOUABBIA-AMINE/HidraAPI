/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryDeviceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TelemetryDevice.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryDeviceRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryDevice;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryDeviceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for TelemetryDevice.
 */
@Component
public class JpaTelemetryDeviceRepositoryAdapter implements TelemetryDeviceRepositoryPort {

    private final TelemetryDeviceJpaRepository repository;

    public JpaTelemetryDeviceRepositoryAdapter(TelemetryDeviceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TelemetryDeviceJpaRepository must not be null.");
    }

    @Override
    public TelemetryDevice save(TelemetryDevice model) {
        return TelemetryPersistenceMapper.toDomain(
                repository.save(TelemetryPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<TelemetryDevice> findById(String id) {
        return repository.findById(id).map(TelemetryPersistenceMapper::toDomain);
    }
}
