/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsModelVersionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsModelVersion.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsModelVersionRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsModelVersion;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsModelVersionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsModelVersion.
 */
@Component
public class JpaAnalyticsModelVersionRepositoryAdapter implements AnalyticsModelVersionRepositoryPort {

    private final AnalyticsModelVersionJpaRepository repository;

    public JpaAnalyticsModelVersionRepositoryAdapter(AnalyticsModelVersionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsModelVersionJpaRepository must not be null.");
    }

    @Override
    public AnalyticsModelVersion save(AnalyticsModelVersion model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsModelVersion> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
