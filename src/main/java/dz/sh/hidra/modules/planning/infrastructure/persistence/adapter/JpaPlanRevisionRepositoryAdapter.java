/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPlanRevisionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PlanRevision including locked mutation reads.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanRevisionJpaRepository;
import org.springframework.stereotype.Component;

import dz.sh.hidra.modules.planning.application.port.out.PlanningCatalogEligibilityPort;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Optional;

@Component
public class JpaPlanRevisionRepositoryAdapter implements PlanRevisionRepositoryPort {

    private final PlanRevisionJpaRepository repository;
    private final PlanningCatalogEligibilityPort catalogs;

    public JpaPlanRevisionRepositoryAdapter(PlanRevisionJpaRepository repository, PlanningCatalogEligibilityPort catalogs) {
        this.repository = Objects.requireNonNull(repository, "PlanRevisionJpaRepository must not be null.");
        this.catalogs = Objects.requireNonNull(catalogs);
    }

    @Override
    @Transactional
    public PlanRevision save(PlanRevision model) {
        Objects.requireNonNull(model);
        repository.findByIdForUpdate(model.id()).map(PlanningPersistenceMapper::toDomain)
                .filter(old -> old.status() == PlanRevisionStatus.APPROVED && !old.equals(model))
                .ifPresent(old -> { throw new IllegalArgumentException("Approved revisions are immutable."); });
        if (model.baseRevisionId() != null && !repository.existsById(model.baseRevisionId())) {
            throw new IllegalArgumentException("Unknown base revision: " + model.baseRevisionId());
        }
        if (model.changeReasonCodeId() != null) catalogs.requireActive(model.changeReasonCodeId(), "REVISION_REASON");
        return PlanningPersistenceMapper.toDomain(repository.saveAndFlush(PlanningPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<PlanRevision> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Optional<PlanRevision> findByIdForUpdate(String id) {
        return repository.findByIdForUpdate(id).map(PlanningPersistenceMapper::toDomain);
    }
}
