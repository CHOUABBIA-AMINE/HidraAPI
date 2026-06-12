/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationValidationFindingRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationValidationFinding.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationValidationFindingRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationValidationFinding;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationValidationFindingJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationValidationFinding.
 */
@Component
public class JpaSimulationValidationFindingRepositoryAdapter implements SimulationValidationFindingRepositoryPort {

    private final SimulationValidationFindingJpaRepository repository;

    public JpaSimulationValidationFindingRepositoryAdapter(SimulationValidationFindingJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationValidationFindingJpaRepository must not be null.");
    }

    @Override
    public SimulationValidationFinding save(SimulationValidationFinding model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationValidationFinding> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
