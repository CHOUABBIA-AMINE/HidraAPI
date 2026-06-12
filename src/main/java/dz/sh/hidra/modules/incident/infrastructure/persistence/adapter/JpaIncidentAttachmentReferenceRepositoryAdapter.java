/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentAttachmentReferenceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IncidentAttachmentReference.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentAttachmentReferenceRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentAttachmentReference;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentAttachmentReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IncidentAttachmentReference.
 */
@Component
public class JpaIncidentAttachmentReferenceRepositoryAdapter implements IncidentAttachmentReferenceRepositoryPort {

    private final IncidentAttachmentReferenceJpaRepository repository;

    public JpaIncidentAttachmentReferenceRepositoryAdapter(IncidentAttachmentReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IncidentAttachmentReferenceJpaRepository must not be null.");
    }

    @Override
    public IncidentAttachmentReference save(IncidentAttachmentReference model) {
        return IncidentPersistenceMapper.toDomain(repository.save(IncidentPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IncidentAttachmentReference> findById(String id) {
        return repository.findById(id).map(IncidentPersistenceMapper::toDomain);
    }
}
