/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMonitoringEvaluationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MonitoringEvaluation.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringEvaluationRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringEvaluation;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper.MonitoringPersistenceMapper;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.MonitoringEvaluationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MonitoringEvaluation.
 */
@Component
public class JpaMonitoringEvaluationRepositoryAdapter implements MonitoringEvaluationRepositoryPort {

    private final MonitoringEvaluationJpaRepository repository;

    public JpaMonitoringEvaluationRepositoryAdapter(MonitoringEvaluationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MonitoringEvaluationJpaRepository must not be null.");
    }

    @Override
    public MonitoringEvaluation save(MonitoringEvaluation model) {
        return MonitoringPersistenceMapper.toDomain(repository.save(MonitoringPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MonitoringEvaluation> findById(String id) {
        return repository.findById(id).map(MonitoringPersistenceMapper::toDomain);
    }
}
