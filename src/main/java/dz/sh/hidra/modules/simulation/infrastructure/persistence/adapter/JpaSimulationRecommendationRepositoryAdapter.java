/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationRecommendationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationRecommendation.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationRecommendationRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationRecommendation;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationRecommendationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationRecommendation.
 */
@Component
public class JpaSimulationRecommendationRepositoryAdapter implements SimulationRecommendationRepositoryPort {

    private final SimulationRecommendationJpaRepository repository;

    public JpaSimulationRecommendationRepositoryAdapter(SimulationRecommendationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationRecommendationJpaRepository must not be null.");
    }

    @Override
    public SimulationRecommendation save(SimulationRecommendation model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationRecommendation> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
