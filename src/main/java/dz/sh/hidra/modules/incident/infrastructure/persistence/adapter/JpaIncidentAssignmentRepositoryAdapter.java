/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentAssignmentRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IncidentAssignment.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentAssignmentRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentAssignment;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentAssignmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IncidentAssignment.
 */
@Component
public class JpaIncidentAssignmentRepositoryAdapter implements IncidentAssignmentRepositoryPort {

    private final IncidentAssignmentJpaRepository repository;

    public JpaIncidentAssignmentRepositoryAdapter(IncidentAssignmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IncidentAssignmentJpaRepository must not be null.");
    }

    @Override
    public IncidentAssignment save(IncidentAssignment model) {
        return IncidentPersistenceMapper.toDomain(repository.save(IncidentPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IncidentAssignment> findById(String id) {
        return repository.findById(id).map(IncidentPersistenceMapper::toDomain);
    }
}
