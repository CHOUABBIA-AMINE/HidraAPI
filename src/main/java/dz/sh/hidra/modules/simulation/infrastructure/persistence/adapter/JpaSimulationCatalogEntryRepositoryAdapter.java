/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationCatalogEntry.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationCatalogEntry;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationCatalogEntry.
 */
@Component
public class JpaSimulationCatalogEntryRepositoryAdapter implements SimulationCatalogEntryRepositoryPort {

    private final SimulationCatalogEntryJpaRepository repository;

    public JpaSimulationCatalogEntryRepositoryAdapter(SimulationCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public SimulationCatalogEntry save(SimulationCatalogEntry model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationCatalogEntry> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
