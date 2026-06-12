/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsSubjectAreaRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsSubjectArea.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsSubjectAreaRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsSubjectArea;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsSubjectAreaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsSubjectArea.
 */
@Component
public class JpaAnalyticsSubjectAreaRepositoryAdapter implements AnalyticsSubjectAreaRepositoryPort {

    private final AnalyticsSubjectAreaJpaRepository repository;

    public JpaAnalyticsSubjectAreaRepositoryAdapter(AnalyticsSubjectAreaJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsSubjectAreaJpaRepository must not be null.");
    }

    @Override
    public AnalyticsSubjectArea save(AnalyticsSubjectArea model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsSubjectArea> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
