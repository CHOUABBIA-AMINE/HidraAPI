/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationCandidateChangeRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationCandidateChange.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationCandidateChangeRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationCandidateChange;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationCandidateChangeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationCandidateChange.
 */
@Component
public class JpaSimulationCandidateChangeRepositoryAdapter implements SimulationCandidateChangeRepositoryPort {

    private final SimulationCandidateChangeJpaRepository repository;

    public JpaSimulationCandidateChangeRepositoryAdapter(SimulationCandidateChangeJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationCandidateChangeJpaRepository must not be null.");
    }

    @Override
    public SimulationCandidateChange save(SimulationCandidateChange model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationCandidateChange> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
