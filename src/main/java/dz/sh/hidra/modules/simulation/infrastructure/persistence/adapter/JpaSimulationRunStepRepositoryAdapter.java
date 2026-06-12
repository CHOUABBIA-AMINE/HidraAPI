/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationRunStepRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationRunStep.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationRunStepRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationRunStep;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationRunStepJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationRunStep.
 */
@Component
public class JpaSimulationRunStepRepositoryAdapter implements SimulationRunStepRepositoryPort {

    private final SimulationRunStepJpaRepository repository;

    public JpaSimulationRunStepRepositoryAdapter(SimulationRunStepJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationRunStepJpaRepository must not be null.");
    }

    @Override
    public SimulationRunStep save(SimulationRunStep model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationRunStep> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
