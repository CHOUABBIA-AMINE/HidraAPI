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
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PlanActualDeviation.
 */
@Component
public class JpaPlanActualDeviationRepositoryAdapter implements PlanActualDeviationRepositoryPort {

    private final PlanActualDeviationJpaRepository repository;

    private final PlanActualDeviationReferenceValidation validation;

    public JpaPlanActualDeviationRepositoryAdapter(PlanActualDeviationJpaRepository repository, PlanActualDeviationReferenceValidation validation) {
        this.repository = Objects.requireNonNull(repository, "PlanActualDeviationJpaRepository must not be null.");
        this.validation = Objects.requireNonNull(validation);
    }

    @Override
    @Transactional
    public PlanActualDeviation save(PlanActualDeviation model) {
        Objects.requireNonNull(model);
        var old = repository.findByIdForUpdate(model.id()).map(MonitoringPersistenceMapper::toDomain).orElse(null);
        var validated = validation.validate(model, old);
        return MonitoringPersistenceMapper.toDomain(repository.saveAndFlush(MonitoringPersistenceMapper.toEntity(validated)));
    }

    @Override
    public Optional<PlanActualDeviation> findById(String id) {
        return repository.findById(id).map(MonitoringPersistenceMapper::toDomain);
    }
}
