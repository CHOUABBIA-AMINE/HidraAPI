/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMetricEvaluationRunRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MetricEvaluationRun.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.MetricEvaluationRunRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.MetricEvaluationRun;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.MetricEvaluationRunJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MetricEvaluationRun.
 */
@Component
public class JpaMetricEvaluationRunRepositoryAdapter implements MetricEvaluationRunRepositoryPort {

    private final MetricEvaluationRunJpaRepository repository;

    public JpaMetricEvaluationRunRepositoryAdapter(MetricEvaluationRunJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MetricEvaluationRunJpaRepository must not be null.");
    }

    @Override
    public MetricEvaluationRun save(MetricEvaluationRun model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MetricEvaluationRun> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
