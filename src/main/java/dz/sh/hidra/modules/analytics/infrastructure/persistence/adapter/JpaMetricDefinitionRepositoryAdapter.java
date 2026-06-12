/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMetricDefinitionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MetricDefinition.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.MetricDefinitionRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.MetricDefinition;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.MetricDefinitionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MetricDefinition.
 */
@Component
public class JpaMetricDefinitionRepositoryAdapter implements MetricDefinitionRepositoryPort {

    private final MetricDefinitionJpaRepository repository;

    public JpaMetricDefinitionRepositoryAdapter(MetricDefinitionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MetricDefinitionJpaRepository must not be null.");
    }

    @Override
    public MetricDefinition save(MetricDefinition model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MetricDefinition> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
