/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationInputDatasetRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationInputDataset.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationInputDatasetRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputDataset;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationInputDatasetJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationInputDataset.
 */
@Component
public class JpaSimulationInputDatasetRepositoryAdapter implements SimulationInputDatasetRepositoryPort {

    private final SimulationInputDatasetJpaRepository repository;

    public JpaSimulationInputDatasetRepositoryAdapter(SimulationInputDatasetJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationInputDatasetJpaRepository must not be null.");
    }

    @Override
    public SimulationInputDataset save(SimulationInputDataset model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationInputDataset> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
