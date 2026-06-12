/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportTableResultRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportTableResult.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportTableResultRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportTableResult;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportTableResultJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportTableResult.
 */
@Component
public class JpaReportTableResultRepositoryAdapter implements ReportTableResultRepositoryPort {

    private final ReportTableResultJpaRepository repository;

    public JpaReportTableResultRepositoryAdapter(ReportTableResultJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportTableResultJpaRepository must not be null.");
    }

    @Override
    public ReportTableResult save(ReportTableResult model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportTableResult> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
