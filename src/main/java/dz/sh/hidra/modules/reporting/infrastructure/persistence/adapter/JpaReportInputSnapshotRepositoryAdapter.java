/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportInputSnapshotRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportInputSnapshot.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportInputSnapshotRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportInputSnapshot;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportInputSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportInputSnapshot.
 */
@Component
public class JpaReportInputSnapshotRepositoryAdapter implements ReportInputSnapshotRepositoryPort {

    private final ReportInputSnapshotJpaRepository repository;

    public JpaReportInputSnapshotRepositoryAdapter(ReportInputSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportInputSnapshotJpaRepository must not be null.");
    }

    @Override
    public ReportInputSnapshot save(ReportInputSnapshot model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportInputSnapshot> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
