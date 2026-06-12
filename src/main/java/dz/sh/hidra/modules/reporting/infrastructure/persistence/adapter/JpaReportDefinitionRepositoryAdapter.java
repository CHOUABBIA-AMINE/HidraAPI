/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportDefinitionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportDefinition.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportDefinitionRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportDefinition;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportDefinitionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportDefinition.
 */
@Component
public class JpaReportDefinitionRepositoryAdapter implements ReportDefinitionRepositoryPort {

    private final ReportDefinitionJpaRepository repository;

    public JpaReportDefinitionRepositoryAdapter(ReportDefinitionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportDefinitionJpaRepository must not be null.");
    }

    @Override
    public ReportDefinition save(ReportDefinition model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportDefinition> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
