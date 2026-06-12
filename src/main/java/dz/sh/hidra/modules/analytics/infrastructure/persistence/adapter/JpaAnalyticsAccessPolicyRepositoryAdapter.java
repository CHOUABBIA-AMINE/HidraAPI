/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsAccessPolicyRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AnalyticsAccessPolicy.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsAccessPolicyRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsAccessPolicy;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsAccessPolicyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AnalyticsAccessPolicy.
 */
@Component
public class JpaAnalyticsAccessPolicyRepositoryAdapter implements AnalyticsAccessPolicyRepositoryPort {

    private final AnalyticsAccessPolicyJpaRepository repository;

    public JpaAnalyticsAccessPolicyRepositoryAdapter(AnalyticsAccessPolicyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AnalyticsAccessPolicyJpaRepository must not be null.");
    }

    @Override
    public AnalyticsAccessPolicy save(AnalyticsAccessPolicy model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AnalyticsAccessPolicy> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
