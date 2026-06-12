/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationResultValueRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationResultValue.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationResultValueRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationResultValue;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationResultValueJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationResultValue.
 */
@Component
public class JpaSimulationResultValueRepositoryAdapter implements SimulationResultValueRepositoryPort {

    private final SimulationResultValueJpaRepository repository;

    public JpaSimulationResultValueRepositoryAdapter(SimulationResultValueJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationResultValueJpaRepository must not be null.");
    }

    @Override
    public SimulationResultValue save(SimulationResultValue model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationResultValue> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
