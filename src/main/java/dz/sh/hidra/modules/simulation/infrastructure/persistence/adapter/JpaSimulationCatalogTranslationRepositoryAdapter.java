/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationCatalogTranslation;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationCatalogTranslation.
 */
@Component
public class JpaSimulationCatalogTranslationRepositoryAdapter implements SimulationCatalogTranslationRepositoryPort {

    private final SimulationCatalogTranslationJpaRepository repository;

    public JpaSimulationCatalogTranslationRepositoryAdapter(SimulationCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public SimulationCatalogTranslation save(SimulationCatalogTranslation model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationCatalogTranslation> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
