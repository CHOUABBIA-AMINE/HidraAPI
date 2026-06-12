/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationEvidenceLinkRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationEvidenceLink.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationEvidenceLinkRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEvidenceLink;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationEvidenceLinkJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationEvidenceLink.
 */
@Component
public class JpaSimulationEvidenceLinkRepositoryAdapter implements SimulationEvidenceLinkRepositoryPort {

    private final SimulationEvidenceLinkJpaRepository repository;

    public JpaSimulationEvidenceLinkRepositoryAdapter(SimulationEvidenceLinkJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SimulationEvidenceLinkJpaRepository must not be null.");
    }

    @Override
    public SimulationEvidenceLink save(SimulationEvidenceLink model) {
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SimulationEvidenceLink> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
