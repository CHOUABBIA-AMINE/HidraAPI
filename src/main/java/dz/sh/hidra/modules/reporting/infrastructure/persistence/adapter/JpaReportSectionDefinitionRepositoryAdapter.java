/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportSectionDefinitionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportSectionDefinition.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportSectionDefinitionRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportSectionDefinition;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportSectionDefinitionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportSectionDefinition.
 */
@Component
public class JpaReportSectionDefinitionRepositoryAdapter implements ReportSectionDefinitionRepositoryPort {

    private final ReportSectionDefinitionJpaRepository repository;

    public JpaReportSectionDefinitionRepositoryAdapter(ReportSectionDefinitionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportSectionDefinitionJpaRepository must not be null.");
    }

    @Override
    public ReportSectionDefinition save(ReportSectionDefinition model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportSectionDefinition> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
