/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationCandidateChangeRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationCandidateChange.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationCandidateChangeRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationCandidateChange;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationCandidateChangeJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import dz.sh.hidra.modules.topology.application.contract.simulation.SimulationTopologyTargetContract;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationCatalogEntryJpaRepository;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationCandidateChange.
 */
@Component
public class JpaSimulationCandidateChangeRepositoryAdapter implements SimulationCandidateChangeRepositoryPort {

    private final SimulationCandidateChangeJpaRepository repository;

    private final SimulationCatalogEntryJpaRepository catalogs;
    private final SimulationTopologyTargetContract targets;

    @Autowired
    public JpaSimulationCandidateChangeRepositoryAdapter(SimulationCandidateChangeJpaRepository repository,
            SimulationCatalogEntryJpaRepository catalogs, SimulationTopologyTargetContract targets) {
        this.repository = Objects.requireNonNull(repository);
        this.catalogs = Objects.requireNonNull(catalogs);
        this.targets = Objects.requireNonNull(targets);
    }

    /** Older callers must not persist without admitted owner validation. */
    public JpaSimulationCandidateChangeRepositoryAdapter(SimulationCandidateChangeJpaRepository repository) {
        this.catalogs = null;
        this.targets = null;
        this.repository = Objects.requireNonNull(repository, "SimulationCandidateChangeJpaRepository must not be null.");
    }

    @Override
    @Transactional
    public SimulationCandidateChange save(SimulationCandidateChange model) {
        Objects.requireNonNull(model);
        if (catalogs == null || targets == null) {
            throw new InvalidSimulationValueException("Candidate change owner validation is unavailable.");
        }
        var prior = repository.findById(model.id());
        var type = catalogs.findLockedById(model.changeTypeId()).orElseThrow(() ->
                new InvalidSimulationValueException("Simulation change type does not exist."));
        boolean newType = prior.isEmpty() || !Objects.equals(prior.get().changeTypeId(), model.changeTypeId());
        if (!"SIMULATION_CHANGE_TYPE".equals(type.catalogName()) || (newType && !type.active())) {
            throw new InvalidSimulationValueException("An eligible SIMULATION_CHANGE_TYPE is required.");
        }
        if (!targets.exists(model.targetType(), model.targetId())) {
            throw new InvalidSimulationValueException("Topology candidate target is missing or unsupported.");
        }
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationCandidateChange> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
