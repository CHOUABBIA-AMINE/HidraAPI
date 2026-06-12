/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentEvidenceLinkRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IncidentEvidenceLink.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentEvidenceLinkRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentEvidenceLink;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentEvidenceLinkJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IncidentEvidenceLink.
 */
@Component
public class JpaIncidentEvidenceLinkRepositoryAdapter implements IncidentEvidenceLinkRepositoryPort {

    private final IncidentEvidenceLinkJpaRepository repository;

    public JpaIncidentEvidenceLinkRepositoryAdapter(IncidentEvidenceLinkJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IncidentEvidenceLinkJpaRepository must not be null.");
    }

    @Override
    public IncidentEvidenceLink save(IncidentEvidenceLink model) {
        return IncidentPersistenceMapper.toDomain(repository.save(IncidentPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IncidentEvidenceLink> findById(String id) {
        return repository.findById(id).map(IncidentPersistenceMapper::toDomain);
    }
}
