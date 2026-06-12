/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCoatingConditionObservationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CoatingConditionObservation.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.CoatingConditionObservationRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.CoatingConditionObservation;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.CoatingConditionObservationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CoatingConditionObservation.
 */
@Component
public class JpaCoatingConditionObservationRepositoryAdapter implements CoatingConditionObservationRepositoryPort {

    private final CoatingConditionObservationJpaRepository repository;

    public JpaCoatingConditionObservationRepositoryAdapter(CoatingConditionObservationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CoatingConditionObservationJpaRepository must not be null.");
    }

    @Override
    public CoatingConditionObservation save(CoatingConditionObservation model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CoatingConditionObservation> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
