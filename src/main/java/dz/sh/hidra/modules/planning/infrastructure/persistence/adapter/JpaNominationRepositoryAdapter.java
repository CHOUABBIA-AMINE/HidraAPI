/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNominationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for Nomination.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.NominationRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.Nomination;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.NominationJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for Nomination.
 */
@Component
public class JpaNominationRepositoryAdapter implements NominationRepositoryPort {

    private final NominationJpaRepository repository;
    private final NominationReferenceValidation validation;

    public JpaNominationRepositoryAdapter(NominationJpaRepository repository,NominationReferenceValidation validation) {
        this.repository = Objects.requireNonNull(repository, "NominationJpaRepository must not be null.");
        this.validation=Objects.requireNonNull(validation);
    }

    @Override
    @Transactional
    public Nomination save(Nomination model) {
        Objects.requireNonNull(model);
        var old=repository.findByIdForUpdate(model.id()).map(PlanningPersistenceMapper::toDomain).orElse(null);
        var valid=validation.validate(model,old);
        if(repository.existsByRevisionIdAndCodeAndIdNot(valid.revisionId(),valid.code(),valid.id())) {
            throw new IllegalArgumentException("Nomination code already exists in revision.");
        }
        return PlanningPersistenceMapper.toDomain(repository.saveAndFlush(PlanningPersistenceMapper.toEntity(valid)));
    }

    @Override
    public Optional<Nomination> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }
}
