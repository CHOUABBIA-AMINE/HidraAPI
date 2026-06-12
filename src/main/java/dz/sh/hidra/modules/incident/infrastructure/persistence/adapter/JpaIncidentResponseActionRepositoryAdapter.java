/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentResponseActionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IncidentResponseAction.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentResponseActionRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentResponseAction;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentResponseActionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IncidentResponseAction.
 */
@Component
public class JpaIncidentResponseActionRepositoryAdapter implements IncidentResponseActionRepositoryPort {

    private final IncidentResponseActionJpaRepository repository;

    public JpaIncidentResponseActionRepositoryAdapter(IncidentResponseActionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IncidentResponseActionJpaRepository must not be null.");
    }

    @Override
    public IncidentResponseAction save(IncidentResponseAction model) {
        return IncidentPersistenceMapper.toDomain(repository.save(IncidentPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IncidentResponseAction> findById(String id) {
        return repository.findById(id).map(IncidentPersistenceMapper::toDomain);
    }
}
