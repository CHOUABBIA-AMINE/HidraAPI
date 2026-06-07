/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCatalogRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter for telemetry catalog repository port.
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
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryCatalogRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeCatalog;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTypeCatalogId;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryTypeCatalogJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryTypeCatalogJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryTypeTranslationJpaRepository;

/**
 * Persistence adapter for telemetry catalog repository port.
 */
@Repository
public class TelemetryCatalogRepositoryAdapter implements TelemetryCatalogRepositoryPort {

    private final TelemetryTypeCatalogJpaRepository catalogRepository;
    private final TelemetryTypeTranslationJpaRepository translationRepository;
    private final TelemetryPersistenceMapper mapper;

    public TelemetryCatalogRepositoryAdapter(
            TelemetryTypeCatalogJpaRepository catalogRepository,
            TelemetryTypeTranslationJpaRepository translationRepository,
            TelemetryPersistenceMapper mapper) {

        this.catalogRepository = Objects.requireNonNull(catalogRepository, "TelemetryTypeCatalogJpaRepository must not be null.");
        this.translationRepository = Objects.requireNonNull(translationRepository, "TelemetryTypeTranslationJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryPersistenceMapper must not be null.");
    }

    @Override
    public TelemetryTypeCatalog save(TelemetryTypeCatalog catalog) {
        TelemetryTypeCatalogJpaEntity saved = catalogRepository.save(mapper.toEntity(catalog));
        catalog.translations().forEach(translation -> translationRepository.save(mapper.toEntity(translation)));
        return mapper.toDomain(saved, translationRepository.findByTypeId(saved.getId()));
    }

    @Override
    public Optional<TelemetryTypeCatalog> findById(TelemetryTypeCatalogId id) {
        return catalogRepository.findById(id.value())
                .map(entity -> mapper.toDomain(entity, translationRepository.findByTypeId(entity.getId())));
    }

    @Override
    public Optional<TelemetryTypeCatalog> findByCatalogNameAndCode(String catalogName, TelemetryCode code) {
        return catalogRepository.findByCatalogNameAndCode(catalogName, code.value())
                .map(entity -> mapper.toDomain(entity, translationRepository.findByTypeId(entity.getId())));
    }

    @Override
    public PageResult<TelemetryTypeCatalog> findAll(String catalogName, Boolean active, PageRequest pageRequest) {
        Pageable pageable = TelemetrySpringPageables.from(pageRequest);
        Page<TelemetryTypeCatalogJpaEntity> page;

        if (catalogName != null && active != null) {
            page = catalogRepository.findByCatalogNameAndActive(catalogName, active, pageable);
        } else if (catalogName != null) {
            page = catalogRepository.findByCatalogName(catalogName, pageable);
        } else if (active != null) {
            page = catalogRepository.findByActive(active, pageable);
        } else {
            page = catalogRepository.findAll(pageable);
        }

        return TelemetrySpringPageables.toPageResult(
                page,
                entity -> mapper.toDomain(entity, translationRepository.findByTypeId(entity.getId())));
    }

    @Override
    public boolean existsByCatalogNameAndCode(String catalogName, TelemetryCode code) {
        return catalogRepository.existsByCatalogNameAndCode(catalogName, code.value());
    }
}
