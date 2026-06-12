/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationResultSummaryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationResultSummary.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationResultSummaryRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationResultSummary;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationResultSummaryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationResultSummary.
 */
@Component
public class JpaSimulationResultSummaryRepositoryAdapter implements SimulationResultSummaryRepositoryPort {

    private final SimulationResultSummaryJpaRepository repository;

    public JpaSimulationResultSummaryRepositoryAdapter(SimulationResultSummaryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationResultSummaryJpaRepository must not be null.");
    }

    @Override
    public SimulationResultSummary save(SimulationResultSummary model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationResultSummary> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
