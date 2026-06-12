/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryExternalTagMappingRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TelemetryExternalTagMapping.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryExternalTagMappingRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryExternalTagMapping;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryExternalTagMappingJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for TelemetryExternalTagMapping.
 */
@Component
public class JpaTelemetryExternalTagMappingRepositoryAdapter implements TelemetryExternalTagMappingRepositoryPort {

    private final TelemetryExternalTagMappingJpaRepository repository;

    public JpaTelemetryExternalTagMappingRepositoryAdapter(TelemetryExternalTagMappingJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TelemetryExternalTagMappingJpaRepository must not be null.");
    }

    @Override
    public TelemetryExternalTagMapping save(TelemetryExternalTagMapping model) {
        return TelemetryPersistenceMapper.toDomain(
                repository.save(TelemetryPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<TelemetryExternalTagMapping> findById(String id) {
        return repository.findById(id).map(TelemetryPersistenceMapper::toDomain);
    }
}
