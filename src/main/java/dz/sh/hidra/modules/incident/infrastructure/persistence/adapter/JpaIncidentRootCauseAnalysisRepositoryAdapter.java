/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentRootCauseAnalysisRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IncidentRootCauseAnalysis.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentRootCauseAnalysisRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentRootCauseAnalysis;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentRootCauseAnalysisJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IncidentRootCauseAnalysis.
 */
@Component
public class JpaIncidentRootCauseAnalysisRepositoryAdapter implements IncidentRootCauseAnalysisRepositoryPort {

    private final IncidentRootCauseAnalysisJpaRepository repository;

    public JpaIncidentRootCauseAnalysisRepositoryAdapter(IncidentRootCauseAnalysisJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IncidentRootCauseAnalysisJpaRepository must not be null.");
    }

    @Override
    public IncidentRootCauseAnalysis save(IncidentRootCauseAnalysis model) {
        return IncidentPersistenceMapper.toDomain(repository.save(IncidentPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IncidentRootCauseAnalysis> findById(String id) {
        return repository.findById(id).map(IncidentPersistenceMapper::toDomain);
    }
}
