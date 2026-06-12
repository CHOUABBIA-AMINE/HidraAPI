/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TelemetryCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryCatalogTranslation;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for TelemetryCatalogTranslation.
 */
@Component
public class JpaTelemetryCatalogTranslationRepositoryAdapter implements TelemetryCatalogTranslationRepositoryPort {

    private final TelemetryCatalogTranslationJpaRepository repository;

    public JpaTelemetryCatalogTranslationRepositoryAdapter(TelemetryCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TelemetryCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public TelemetryCatalogTranslation save(TelemetryCatalogTranslation model) {
        return TelemetryPersistenceMapper.toDomain(
                repository.save(TelemetryPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<TelemetryCatalogTranslation> findById(String id) {
        return repository.findById(id).map(TelemetryPersistenceMapper::toDomain);
    }
}
