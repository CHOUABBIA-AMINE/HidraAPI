/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetrySourceEndpointRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TelemetrySourceEndpoint.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetrySourceEndpointRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySourceEndpoint;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetrySourceEndpointJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for TelemetrySourceEndpoint.
 */
@Component
public class JpaTelemetrySourceEndpointRepositoryAdapter implements TelemetrySourceEndpointRepositoryPort {

    private final TelemetrySourceEndpointJpaRepository repository;

    public JpaTelemetrySourceEndpointRepositoryAdapter(TelemetrySourceEndpointJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TelemetrySourceEndpointJpaRepository must not be null.");
    }

    @Override
    public TelemetrySourceEndpoint save(TelemetrySourceEndpoint model) {
        return TelemetryPersistenceMapper.toDomain(
                repository.save(TelemetryPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<TelemetrySourceEndpoint> findById(String id) {
        return repository.findById(id).map(TelemetryPersistenceMapper::toDomain);
    }
}
