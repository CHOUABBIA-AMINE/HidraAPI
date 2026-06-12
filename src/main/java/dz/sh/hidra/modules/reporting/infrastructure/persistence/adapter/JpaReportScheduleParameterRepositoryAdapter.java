/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportScheduleParameterRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportScheduleParameter.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportScheduleParameterRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportScheduleParameter;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportScheduleParameterJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportScheduleParameter.
 */
@Component
public class JpaReportScheduleParameterRepositoryAdapter implements ReportScheduleParameterRepositoryPort {

    private final ReportScheduleParameterJpaRepository repository;

    public JpaReportScheduleParameterRepositoryAdapter(ReportScheduleParameterJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportScheduleParameterJpaRepository must not be null.");
    }

    @Override
    public ReportScheduleParameter save(ReportScheduleParameter model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportScheduleParameter> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
