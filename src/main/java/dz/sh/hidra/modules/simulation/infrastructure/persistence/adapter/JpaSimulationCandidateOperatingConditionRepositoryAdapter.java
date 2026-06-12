/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationCandidateOperatingConditionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationCandidateOperatingCondition.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationCandidateOperatingConditionRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationCandidateOperatingCondition;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationCandidateOperatingConditionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationCandidateOperatingCondition.
 */
@Component
public class JpaSimulationCandidateOperatingConditionRepositoryAdapter implements SimulationCandidateOperatingConditionRepositoryPort {

    private final SimulationCandidateOperatingConditionJpaRepository repository;

    public JpaSimulationCandidateOperatingConditionRepositoryAdapter(SimulationCandidateOperatingConditionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationCandidateOperatingConditionJpaRepository must not be null.");
    }

    @Override
    public SimulationCandidateOperatingCondition save(SimulationCandidateOperatingCondition model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationCandidateOperatingCondition> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
