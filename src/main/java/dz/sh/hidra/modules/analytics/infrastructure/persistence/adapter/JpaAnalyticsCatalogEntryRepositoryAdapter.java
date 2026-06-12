/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsCatalogEntry.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsCatalogEntry;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsCatalogEntry.
 */
@Component
public class JpaAnalyticsCatalogEntryRepositoryAdapter implements AnalyticsCatalogEntryRepositoryPort {

    private final AnalyticsCatalogEntryJpaRepository repository;

    public JpaAnalyticsCatalogEntryRepositoryAdapter(AnalyticsCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public AnalyticsCatalogEntry save(AnalyticsCatalogEntry model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsCatalogEntry> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
