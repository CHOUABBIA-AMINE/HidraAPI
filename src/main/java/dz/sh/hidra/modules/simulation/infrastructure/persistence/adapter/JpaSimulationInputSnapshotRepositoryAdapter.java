/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationInputSnapshotRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationInputSnapshot.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationInputSnapshotRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSnapshot;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationInputSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationInputSnapshot.
 */
@Component
public class JpaSimulationInputSnapshotRepositoryAdapter implements SimulationInputSnapshotRepositoryPort {

    private final SimulationInputSnapshotJpaRepository repository;

    public JpaSimulationInputSnapshotRepositoryAdapter(SimulationInputSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationInputSnapshotJpaRepository must not be null.");
    }

    @Override
    public SimulationInputSnapshot save(SimulationInputSnapshot model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationInputSnapshot> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
