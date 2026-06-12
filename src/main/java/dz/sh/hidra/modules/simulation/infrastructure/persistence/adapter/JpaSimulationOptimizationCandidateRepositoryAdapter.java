/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationOptimizationCandidateRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationOptimizationCandidate.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationOptimizationCandidateRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationOptimizationCandidate;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationOptimizationCandidateJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationOptimizationCandidate.
 */
@Component
public class JpaSimulationOptimizationCandidateRepositoryAdapter implements SimulationOptimizationCandidateRepositoryPort {

    private final SimulationOptimizationCandidateJpaRepository repository;

    public JpaSimulationOptimizationCandidateRepositoryAdapter(SimulationOptimizationCandidateJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationOptimizationCandidateJpaRepository must not be null.");
    }

    @Override
    public SimulationOptimizationCandidate save(SimulationOptimizationCandidate model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationOptimizationCandidate> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
