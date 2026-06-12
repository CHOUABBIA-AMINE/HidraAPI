/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationSensitivityAnalysisRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationSensitivityAnalysis.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationSensitivityAnalysisRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationSensitivityAnalysis;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationSensitivityAnalysisJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationSensitivityAnalysis.
 */
@Component
public class JpaSimulationSensitivityAnalysisRepositoryAdapter implements SimulationSensitivityAnalysisRepositoryPort {

    private final SimulationSensitivityAnalysisJpaRepository repository;

    public JpaSimulationSensitivityAnalysisRepositoryAdapter(SimulationSensitivityAnalysisJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationSensitivityAnalysisJpaRepository must not be null.");
    }

    @Override
    public SimulationSensitivityAnalysis save(SimulationSensitivityAnalysis model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationSensitivityAnalysis> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
