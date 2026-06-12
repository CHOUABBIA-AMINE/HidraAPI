/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentEscalationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IncidentEscalation.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentEscalation;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentEscalationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IncidentEscalation.
 */
@Component
public class JpaIncidentEscalationRepositoryAdapter implements IncidentEscalationRepositoryPort {

    private final IncidentEscalationJpaRepository repository;

    public JpaIncidentEscalationRepositoryAdapter(IncidentEscalationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IncidentEscalationJpaRepository must not be null.");
    }

    @Override
    public IncidentEscalation save(IncidentEscalation model) {
        return IncidentPersistenceMapper.toDomain(repository.save(IncidentPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IncidentEscalation> findById(String id) {
        return repository.findById(id).map(IncidentPersistenceMapper::toDomain);
    }
}
