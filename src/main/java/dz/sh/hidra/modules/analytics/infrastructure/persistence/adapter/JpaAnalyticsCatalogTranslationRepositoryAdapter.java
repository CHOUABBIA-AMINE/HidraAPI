/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsCatalogTranslation;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsCatalogTranslation.
 */
@Component
public class JpaAnalyticsCatalogTranslationRepositoryAdapter implements AnalyticsCatalogTranslationRepositoryPort {

    private final AnalyticsCatalogTranslationJpaRepository repository;

    public JpaAnalyticsCatalogTranslationRepositoryAdapter(AnalyticsCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public AnalyticsCatalogTranslation save(AnalyticsCatalogTranslation model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsCatalogTranslation> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
