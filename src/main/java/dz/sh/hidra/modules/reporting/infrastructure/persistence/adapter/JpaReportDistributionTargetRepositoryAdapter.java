/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportDistributionTargetRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportDistributionTarget.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportDistributionTargetRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportDistributionTarget;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportDistributionTargetJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportDistributionTarget.
 */
@Component
public class JpaReportDistributionTargetRepositoryAdapter implements ReportDistributionTargetRepositoryPort {

    private final ReportDistributionTargetJpaRepository repository;

    public JpaReportDistributionTargetRepositoryAdapter(ReportDistributionTargetJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportDistributionTargetJpaRepository must not be null.");
    }

    @Override
    public ReportDistributionTarget save(ReportDistributionTarget model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportDistributionTarget> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
