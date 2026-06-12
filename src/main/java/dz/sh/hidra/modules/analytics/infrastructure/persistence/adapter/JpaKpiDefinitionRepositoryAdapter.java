/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaKpiDefinitionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for KpiDefinition.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.KpiDefinitionRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.KpiDefinition;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.KpiDefinitionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for KpiDefinition.
 */
@Component
public class JpaKpiDefinitionRepositoryAdapter implements KpiDefinitionRepositoryPort {

    private final KpiDefinitionJpaRepository repository;

    public JpaKpiDefinitionRepositoryAdapter(KpiDefinitionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "KpiDefinitionJpaRepository must not be null.");
    }

    @Override
    public KpiDefinition save(KpiDefinition model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<KpiDefinition> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
