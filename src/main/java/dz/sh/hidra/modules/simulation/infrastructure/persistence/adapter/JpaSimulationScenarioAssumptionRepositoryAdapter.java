/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationScenarioAssumptionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationScenarioAssumption.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationScenarioAssumptionRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationScenarioAssumption;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationScenarioAssumptionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationScenarioAssumption.
 */
@Component
public class JpaSimulationScenarioAssumptionRepositoryAdapter implements SimulationScenarioAssumptionRepositoryPort {

    private final SimulationScenarioAssumptionJpaRepository repository;

    public JpaSimulationScenarioAssumptionRepositoryAdapter(SimulationScenarioAssumptionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationScenarioAssumptionJpaRepository must not be null.");
    }

    @Override
    public SimulationScenarioAssumption save(SimulationScenarioAssumption model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationScenarioAssumption> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
