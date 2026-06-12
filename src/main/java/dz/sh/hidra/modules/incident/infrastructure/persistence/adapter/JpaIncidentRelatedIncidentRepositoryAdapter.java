/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentRelatedIncidentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IncidentRelatedIncident.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentRelatedIncidentRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentRelatedIncident;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentRelatedIncidentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IncidentRelatedIncident.
 */
@Component
public class JpaIncidentRelatedIncidentRepositoryAdapter implements IncidentRelatedIncidentRepositoryPort {

    private final IncidentRelatedIncidentJpaRepository repository;

    public JpaIncidentRelatedIncidentRepositoryAdapter(IncidentRelatedIncidentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IncidentRelatedIncidentJpaRepository must not be null.");
    }

    @Override
    public IncidentRelatedIncident save(IncidentRelatedIncident model) {
        return IncidentPersistenceMapper.toDomain(repository.save(IncidentPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IncidentRelatedIncident> findById(String id) {
        return repository.findById(id).map(IncidentPersistenceMapper::toDomain);
    }
}
