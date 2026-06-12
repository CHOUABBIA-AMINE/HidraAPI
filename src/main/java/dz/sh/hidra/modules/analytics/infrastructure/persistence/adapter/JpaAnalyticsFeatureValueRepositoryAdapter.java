/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsFeatureValueRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsFeatureValue.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsFeatureValueRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsFeatureValue;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsFeatureValueJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsFeatureValue.
 */
@Component
public class JpaAnalyticsFeatureValueRepositoryAdapter implements AnalyticsFeatureValueRepositoryPort {

    private final AnalyticsFeatureValueJpaRepository repository;

    public JpaAnalyticsFeatureValueRepositoryAdapter(AnalyticsFeatureValueJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsFeatureValueJpaRepository must not be null.");
    }

    @Override
    public AnalyticsFeatureValue save(AnalyticsFeatureValue model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsFeatureValue> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
