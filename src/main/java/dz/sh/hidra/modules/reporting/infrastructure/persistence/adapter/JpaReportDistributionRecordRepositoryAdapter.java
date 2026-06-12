/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportDistributionRecordRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportDistributionRecord.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportDistributionRecordRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportDistributionRecord;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportDistributionRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportDistributionRecord.
 */
@Component
public class JpaReportDistributionRecordRepositoryAdapter implements ReportDistributionRecordRepositoryPort {

    private final ReportDistributionRecordJpaRepository repository;

    public JpaReportDistributionRecordRepositoryAdapter(ReportDistributionRecordJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportDistributionRecordJpaRepository must not be null.");
    }

    @Override
    public ReportDistributionRecord save(ReportDistributionRecord model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportDistributionRecord> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
