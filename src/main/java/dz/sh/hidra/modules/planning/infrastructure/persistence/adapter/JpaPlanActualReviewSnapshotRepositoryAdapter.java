/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPlanActualReviewSnapshotRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PlanActualReviewSnapshot.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.PlanActualReviewSnapshotRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.PlanActualReviewSnapshot;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanActualReviewSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PlanActualReviewSnapshot.
 */
@Component
public class JpaPlanActualReviewSnapshotRepositoryAdapter implements PlanActualReviewSnapshotRepositoryPort {

    private final PlanActualReviewSnapshotJpaRepository repository;

    public JpaPlanActualReviewSnapshotRepositoryAdapter(PlanActualReviewSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PlanActualReviewSnapshotJpaRepository must not be null.");
    }

    @Override
    public PlanActualReviewSnapshot save(PlanActualReviewSnapshot model) {
        return PlanningPersistenceMapper.toDomain(repository.save(PlanningPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<PlanActualReviewSnapshot> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }
}
