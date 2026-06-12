/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationConstraintEvaluationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationConstraintEvaluation.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationConstraintEvaluationRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationConstraintEvaluation;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationConstraintEvaluationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationConstraintEvaluation.
 */
@Component
public class JpaSimulationConstraintEvaluationRepositoryAdapter implements SimulationConstraintEvaluationRepositoryPort {

    private final SimulationConstraintEvaluationJpaRepository repository;

    public JpaSimulationConstraintEvaluationRepositoryAdapter(SimulationConstraintEvaluationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationConstraintEvaluationJpaRepository must not be null.");
    }

    @Override
    public SimulationConstraintEvaluation save(SimulationConstraintEvaluation model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationConstraintEvaluation> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
