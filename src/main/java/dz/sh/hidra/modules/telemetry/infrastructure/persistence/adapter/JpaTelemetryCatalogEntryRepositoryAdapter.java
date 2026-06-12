/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TelemetryCatalogEntry.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryCatalogEntry;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for TelemetryCatalogEntry.
 */
@Component
public class JpaTelemetryCatalogEntryRepositoryAdapter implements TelemetryCatalogEntryRepositoryPort {

    private final TelemetryCatalogEntryJpaRepository repository;

    public JpaTelemetryCatalogEntryRepositoryAdapter(TelemetryCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TelemetryCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public TelemetryCatalogEntry save(TelemetryCatalogEntry model) {
        return TelemetryPersistenceMapper.toDomain(
                repository.save(TelemetryPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<TelemetryCatalogEntry> findById(String id) {
        return repository.findById(id).map(TelemetryPersistenceMapper::toDomain);
    }
}
