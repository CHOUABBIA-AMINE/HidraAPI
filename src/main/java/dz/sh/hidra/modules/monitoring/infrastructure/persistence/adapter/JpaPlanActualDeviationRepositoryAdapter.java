/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPlanActualDeviationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PlanActualDeviation.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.port.out.PlanActualDeviationRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.PlanActualDeviation;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper.MonitoringPersistenceMapper;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.PlanActualDeviationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PlanActualDeviation.
 */
@Component
public class JpaPlanActualDeviationRepositoryAdapter implements PlanActualDeviationRepositoryPort {

    private final PlanActualDeviationJpaRepository repository;

    public JpaPlanActualDeviationRepositoryAdapter(PlanActualDeviationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PlanActualDeviationJpaRepository must not be null.");
    }

    @Override
    public PlanActualDeviation save(PlanActualDeviation model) {
        return MonitoringPersistenceMapper.toDomain(repository.save(MonitoringPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<PlanActualDeviation> findById(String id) {
        return repository.findById(id).map(MonitoringPersistenceMapper::toDomain);
    }
}
