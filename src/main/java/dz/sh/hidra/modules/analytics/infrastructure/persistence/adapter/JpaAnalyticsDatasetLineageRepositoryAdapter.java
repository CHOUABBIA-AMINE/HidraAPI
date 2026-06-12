/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsDatasetLineageRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsDatasetLineage.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsDatasetLineageRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsDatasetLineage;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsDatasetLineageJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsDatasetLineage.
 */
@Component
public class JpaAnalyticsDatasetLineageRepositoryAdapter implements AnalyticsDatasetLineageRepositoryPort {

    private final AnalyticsDatasetLineageJpaRepository repository;

    public JpaAnalyticsDatasetLineageRepositoryAdapter(AnalyticsDatasetLineageJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsDatasetLineageJpaRepository must not be null.");
    }

    @Override
    public AnalyticsDatasetLineage save(AnalyticsDatasetLineage model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsDatasetLineage> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
