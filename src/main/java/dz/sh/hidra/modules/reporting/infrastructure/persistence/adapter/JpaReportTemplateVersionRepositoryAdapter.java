/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportTemplateVersionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportTemplateVersion.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportTemplateVersionRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportTemplateVersion;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportTemplateVersionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportTemplateVersion.
 */
@Component
public class JpaReportTemplateVersionRepositoryAdapter implements ReportTemplateVersionRepositoryPort {

    private final ReportTemplateVersionJpaRepository repository;

    public JpaReportTemplateVersionRepositoryAdapter(ReportTemplateVersionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportTemplateVersionJpaRepository must not be null.");
    }

    @Override
    public ReportTemplateVersion save(ReportTemplateVersion model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportTemplateVersion> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
