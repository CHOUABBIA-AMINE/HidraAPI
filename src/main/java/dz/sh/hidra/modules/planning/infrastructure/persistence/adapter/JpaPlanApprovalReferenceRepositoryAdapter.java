/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPlanApprovalReferenceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PlanApprovalReference.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.PlanApprovalReferenceRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.PlanApprovalReference;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanApprovalReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PlanApprovalReference.
 */
@Component
public class JpaPlanApprovalReferenceRepositoryAdapter implements PlanApprovalReferenceRepositoryPort {

    private final PlanApprovalReferenceJpaRepository repository;

    public JpaPlanApprovalReferenceRepositoryAdapter(PlanApprovalReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PlanApprovalReferenceJpaRepository must not be null.");
    }

    @Override
    public PlanApprovalReference save(PlanApprovalReference model) {
        return PlanningPersistenceMapper.toDomain(repository.save(PlanningPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<PlanApprovalReference> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }
}
