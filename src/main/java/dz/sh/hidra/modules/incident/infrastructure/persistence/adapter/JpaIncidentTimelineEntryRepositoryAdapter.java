/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentTimelineEntryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IncidentTimelineEntry.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentTimelineEntryRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentTimelineEntry;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentTimelineEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IncidentTimelineEntry.
 */
@Component
public class JpaIncidentTimelineEntryRepositoryAdapter implements IncidentTimelineEntryRepositoryPort {

    private final IncidentTimelineEntryJpaRepository repository;

    public JpaIncidentTimelineEntryRepositoryAdapter(IncidentTimelineEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IncidentTimelineEntryJpaRepository must not be null.");
    }

    @Override
    public IncidentTimelineEntry save(IncidentTimelineEntry model) {
        return IncidentPersistenceMapper.toDomain(repository.save(IncidentPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IncidentTimelineEntry> findById(String id) {
        return repository.findById(id).map(IncidentPersistenceMapper::toDomain);
    }
}
