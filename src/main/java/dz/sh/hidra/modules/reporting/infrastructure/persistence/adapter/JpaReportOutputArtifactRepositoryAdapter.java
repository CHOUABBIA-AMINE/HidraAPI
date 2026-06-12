/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportOutputArtifactRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportOutputArtifact.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportOutputArtifactRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportOutputArtifact;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportOutputArtifactJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportOutputArtifact.
 */
@Component
public class JpaReportOutputArtifactRepositoryAdapter implements ReportOutputArtifactRepositoryPort {

    private final ReportOutputArtifactJpaRepository repository;

    public JpaReportOutputArtifactRepositoryAdapter(ReportOutputArtifactJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportOutputArtifactJpaRepository must not be null.");
    }

    @Override
    public ReportOutputArtifact save(ReportOutputArtifact model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportOutputArtifact> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
