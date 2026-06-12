/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationCandidateScoreRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationCandidateScore.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationCandidateScoreRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationCandidateScore;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationCandidateScoreJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationCandidateScore.
 */
@Component
public class JpaSimulationCandidateScoreRepositoryAdapter implements SimulationCandidateScoreRepositoryPort {

    private final SimulationCandidateScoreJpaRepository repository;

    public JpaSimulationCandidateScoreRepositoryAdapter(SimulationCandidateScoreJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationCandidateScoreJpaRepository must not be null.");
    }

    @Override
    public SimulationCandidateScore save(SimulationCandidateScore model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationCandidateScore> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
