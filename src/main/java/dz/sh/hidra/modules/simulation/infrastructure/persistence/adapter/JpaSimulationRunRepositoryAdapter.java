/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationRunRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationRun.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationRunRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationRun;
import dz.sh.hidra.modules.simulation.domain.value.SimulationRunStatus;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationRunJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationRun.
 */
@Component
public class JpaSimulationRunRepositoryAdapter implements SimulationRunRepositoryPort {

    private final SimulationRunJpaRepository repository;

    public JpaSimulationRunRepositoryAdapter(SimulationRunJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationRunJpaRepository must not be null.");
    }

    @Override
    public SimulationRun save(SimulationRun model) {
        Objects.requireNonNull(model, "SimulationRun must not be null.");

        Optional<SimulationRun> existing = repository.findById(model.id())
                .map(SimulationPersistenceMapper::toDomain);

        if (existing.isPresent() && existing.get().status() == SimulationRunStatus.COMPLETED) {
            if (!existing.get().equals(model)) {
                throw new InvalidSimulationValueException(
                        "Completed SimulationRun business state is immutable."
                );
            }
            return existing.get();
        }

        return SimulationPersistenceMapper.toDomain(
                repository.save(SimulationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<SimulationRun> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }

    @Override
    public boolean isRunType(String runTypeId) {
        return runTypeId != null && !runTypeId.isBlank() && repository.isRunType(runTypeId.trim());
    }

    @Override
    public boolean isSolverProfile(String solverProfileId) {
        return solverProfileId != null
                && !solverProfileId.isBlank()
                && repository.isSolverProfile(solverProfileId.trim());
    }
}
