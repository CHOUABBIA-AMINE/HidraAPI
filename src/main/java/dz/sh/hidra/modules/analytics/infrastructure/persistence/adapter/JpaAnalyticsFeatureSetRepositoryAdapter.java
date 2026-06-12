/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsFeatureSetRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsFeatureSet.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsFeatureSetRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsFeatureSet;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsFeatureSetJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsFeatureSet.
 */
@Component
public class JpaAnalyticsFeatureSetRepositoryAdapter implements AnalyticsFeatureSetRepositoryPort {

    private final AnalyticsFeatureSetJpaRepository repository;

    public JpaAnalyticsFeatureSetRepositoryAdapter(AnalyticsFeatureSetJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsFeatureSetJpaRepository must not be null.");
    }

    @Override
    public AnalyticsFeatureSet save(AnalyticsFeatureSet model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsFeatureSet> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
