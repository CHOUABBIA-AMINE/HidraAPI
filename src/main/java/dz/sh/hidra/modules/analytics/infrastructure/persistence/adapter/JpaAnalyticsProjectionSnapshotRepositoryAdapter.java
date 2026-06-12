/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsProjectionSnapshotRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsProjectionSnapshot.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsProjectionSnapshotRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsProjectionSnapshot;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsProjectionSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsProjectionSnapshot.
 */
@Component
public class JpaAnalyticsProjectionSnapshotRepositoryAdapter implements AnalyticsProjectionSnapshotRepositoryPort {

    private final AnalyticsProjectionSnapshotJpaRepository repository;

    public JpaAnalyticsProjectionSnapshotRepositoryAdapter(AnalyticsProjectionSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsProjectionSnapshotJpaRepository must not be null.");
    }

    @Override
    public AnalyticsProjectionSnapshot save(AnalyticsProjectionSnapshot model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsProjectionSnapshot> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
