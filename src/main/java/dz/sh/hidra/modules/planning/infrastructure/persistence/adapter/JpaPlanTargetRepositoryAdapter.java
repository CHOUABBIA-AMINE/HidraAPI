/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPlanTargetRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PlanTarget.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.PlanTargetRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.PlanTarget;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanTargetJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PlanTarget.
 */
@Component
public class JpaPlanTargetRepositoryAdapter implements PlanTargetRepositoryPort {

    private final PlanTargetJpaRepository repository;

    private final PlanTargetReferenceValidation validation;

    public JpaPlanTargetRepositoryAdapter(PlanTargetJpaRepository repository, PlanTargetReferenceValidation validation) {
        this.repository = Objects.requireNonNull(repository, "PlanTargetJpaRepository must not be null.");
        this.validation = Objects.requireNonNull(validation);
    }

    @Override
    @Transactional
    public PlanTarget save(PlanTarget model) {
        Objects.requireNonNull(model);
        var old = repository.findByIdForUpdate(model.id()).map(PlanningPersistenceMapper::toDomain).orElse(null);
        var validated = validation.validate(model, old);
        return PlanningPersistenceMapper.toDomain(repository.saveAndFlush(PlanningPersistenceMapper.toEntity(validated)));
    }

    @Override
    public Optional<PlanTarget> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }
}
