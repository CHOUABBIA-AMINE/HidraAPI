/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationSolverTraceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationSolverTrace.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationSolverTraceRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationSolverTrace;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationSolverTraceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationSolverTrace.
 */
@Component
public class JpaSimulationSolverTraceRepositoryAdapter implements SimulationSolverTraceRepositoryPort {

    private final SimulationSolverTraceJpaRepository repository;

    public JpaSimulationSolverTraceRepositoryAdapter(SimulationSolverTraceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationSolverTraceJpaRepository must not be null.");
    }

    @Override
    public SimulationSolverTrace save(SimulationSolverTrace model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationSolverTrace> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
