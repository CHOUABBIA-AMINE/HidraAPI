/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationResultSeriesReferenceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationResultSeriesReference.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationResultSeriesReferenceRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationResultSeriesReference;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationResultSeriesReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationResultSeriesReference.
 */
@Component
public class JpaSimulationResultSeriesReferenceRepositoryAdapter implements SimulationResultSeriesReferenceRepositoryPort {

    private final SimulationResultSeriesReferenceJpaRepository repository;

    public JpaSimulationResultSeriesReferenceRepositoryAdapter(SimulationResultSeriesReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationResultSeriesReferenceJpaRepository must not be null.");
    }

    @Override
    public SimulationResultSeriesReference save(SimulationResultSeriesReference model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationResultSeriesReference> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
