/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsDatasetVersionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsDatasetVersion.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsDatasetVersionRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsDatasetVersion;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsDatasetVersionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsDatasetVersion.
 */
@Component
public class JpaAnalyticsDatasetVersionRepositoryAdapter implements AnalyticsDatasetVersionRepositoryPort {

    private final AnalyticsDatasetVersionJpaRepository repository;

    public JpaAnalyticsDatasetVersionRepositoryAdapter(AnalyticsDatasetVersionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsDatasetVersionJpaRepository must not be null.");
    }

    @Override
    public AnalyticsDatasetVersion save(AnalyticsDatasetVersion model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsDatasetVersion> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
