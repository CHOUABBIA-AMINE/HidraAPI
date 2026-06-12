/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsProjectionRunRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsProjectionRun.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsProjectionRunRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsProjectionRun;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsProjectionRunJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsProjectionRun.
 */
@Component
public class JpaAnalyticsProjectionRunRepositoryAdapter implements AnalyticsProjectionRunRepositoryPort {

    private final AnalyticsProjectionRunJpaRepository repository;

    public JpaAnalyticsProjectionRunRepositoryAdapter(AnalyticsProjectionRunJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsProjectionRunJpaRepository must not be null.");
    }

    @Override
    public AnalyticsProjectionRun save(AnalyticsProjectionRun model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsProjectionRun> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
