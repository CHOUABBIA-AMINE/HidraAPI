/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMetricDefinitionVersionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MetricDefinitionVersion.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.MetricDefinitionVersionRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.MetricDefinitionVersion;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.MetricDefinitionVersionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MetricDefinitionVersion.
 */
@Component
public class JpaMetricDefinitionVersionRepositoryAdapter implements MetricDefinitionVersionRepositoryPort {

    private final MetricDefinitionVersionJpaRepository repository;

    public JpaMetricDefinitionVersionRepositoryAdapter(MetricDefinitionVersionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MetricDefinitionVersionJpaRepository must not be null.");
    }

    @Override
    public MetricDefinitionVersion save(MetricDefinitionVersion model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MetricDefinitionVersion> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
