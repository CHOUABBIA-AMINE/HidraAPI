/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsProjectionDefinitionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsProjectionDefinition.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsProjectionDefinitionRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsProjectionDefinition;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsProjectionDefinitionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsProjectionDefinition.
 */
@Component
public class JpaAnalyticsProjectionDefinitionRepositoryAdapter implements AnalyticsProjectionDefinitionRepositoryPort {

    private final AnalyticsProjectionDefinitionJpaRepository repository;

    public JpaAnalyticsProjectionDefinitionRepositoryAdapter(AnalyticsProjectionDefinitionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsProjectionDefinitionJpaRepository must not be null.");
    }

    @Override
    public AnalyticsProjectionDefinition save(AnalyticsProjectionDefinition model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsProjectionDefinition> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
