/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentClosureRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IncidentClosure.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentClosureRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentClosure;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentClosureJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IncidentClosure.
 */
@Component
public class JpaIncidentClosureRepositoryAdapter implements IncidentClosureRepositoryPort {

    private final IncidentClosureJpaRepository repository;

    public JpaIncidentClosureRepositoryAdapter(IncidentClosureJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IncidentClosureJpaRepository must not be null.");
    }

    @Override
    public IncidentClosure save(IncidentClosure model) {
        return IncidentPersistenceMapper.toDomain(repository.save(IncidentPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IncidentClosure> findById(String id) {
        return repository.findById(id).map(IncidentPersistenceMapper::toDomain);
    }
}
