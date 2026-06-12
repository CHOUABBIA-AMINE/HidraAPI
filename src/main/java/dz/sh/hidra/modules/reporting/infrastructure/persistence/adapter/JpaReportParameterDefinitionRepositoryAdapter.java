/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportParameterDefinitionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportParameterDefinition.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportParameterDefinitionRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportParameterDefinition;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportParameterDefinitionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportParameterDefinition.
 */
@Component
public class JpaReportParameterDefinitionRepositoryAdapter implements ReportParameterDefinitionRepositoryPort {

    private final ReportParameterDefinitionJpaRepository repository;

    public JpaReportParameterDefinitionRepositoryAdapter(ReportParameterDefinitionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportParameterDefinitionJpaRepository must not be null.");
    }

    @Override
    public ReportParameterDefinition save(ReportParameterDefinition model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportParameterDefinition> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
