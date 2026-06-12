/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentImpactAssessmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IncidentImpactAssessment.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentImpactAssessmentRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentImpactAssessment;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentImpactAssessmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IncidentImpactAssessment.
 */
@Component
public class JpaIncidentImpactAssessmentRepositoryAdapter implements IncidentImpactAssessmentRepositoryPort {

    private final IncidentImpactAssessmentJpaRepository repository;

    public JpaIncidentImpactAssessmentRepositoryAdapter(IncidentImpactAssessmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IncidentImpactAssessmentJpaRepository must not be null.");
    }

    @Override
    public IncidentImpactAssessment save(IncidentImpactAssessment model) {
        return IncidentPersistenceMapper.toDomain(repository.save(IncidentPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IncidentImpactAssessment> findById(String id) {
        return repository.findById(id).map(IncidentPersistenceMapper::toDomain);
    }
}
