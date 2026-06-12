/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportAccessPolicyRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportAccessPolicy.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportAccessPolicyRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportAccessPolicy;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportAccessPolicyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportAccessPolicy.
 */
@Component
public class JpaReportAccessPolicyRepositoryAdapter implements ReportAccessPolicyRepositoryPort {

    private final ReportAccessPolicyJpaRepository repository;

    public JpaReportAccessPolicyRepositoryAdapter(ReportAccessPolicyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportAccessPolicyJpaRepository must not be null.");
    }

    @Override
    public ReportAccessPolicy save(ReportAccessPolicy model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportAccessPolicy> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
