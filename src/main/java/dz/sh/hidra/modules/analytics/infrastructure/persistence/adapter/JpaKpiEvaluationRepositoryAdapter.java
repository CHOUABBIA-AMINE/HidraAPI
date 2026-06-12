/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaKpiEvaluationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for KpiEvaluation.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.out.KpiEvaluationRepositoryPort;
import dz.sh.hidra.modules.analytics.domain.model.KpiEvaluation;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper.AnalyticsPersistenceMapper;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.KpiEvaluationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for KpiEvaluation.
 */
@Component
public class JpaKpiEvaluationRepositoryAdapter implements KpiEvaluationRepositoryPort {

    private final KpiEvaluationJpaRepository repository;

    public JpaKpiEvaluationRepositoryAdapter(KpiEvaluationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "KpiEvaluationJpaRepository must not be null.");
    }

    @Override
    public KpiEvaluation save(KpiEvaluation model) {
        return AnalyticsPersistenceMapper.toDomain(repository.save(AnalyticsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<KpiEvaluation> findById(String id) {
        return repository.findById(id).map(AnalyticsPersistenceMapper::toDomain);
    }
}
