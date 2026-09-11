/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPlanningQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for read-only planning queries.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.PlanningQueryPort;
import dz.sh.hidra.modules.planning.domain.model.Nomination;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.model.PlanTarget;
import dz.sh.hidra.modules.planning.domain.model.PlanningPeriod;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.NominationJpaRepository;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.OperationalPlanJpaRepository;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanRevisionJpaRepository;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanTargetJpaRepository;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanningPeriodJpaRepository;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class JpaPlanningQueryAdapter implements PlanningQueryPort {

    private final PlanningPeriodJpaRepository periodRepository;
    private final OperationalPlanJpaRepository planRepository;
    private final PlanRevisionJpaRepository revisionRepository;
    private final NominationJpaRepository nominationRepository;
    private final PlanTargetJpaRepository targetRepository;

    public JpaPlanningQueryAdapter(
            PlanningPeriodJpaRepository periodRepository,
            OperationalPlanJpaRepository planRepository,
            PlanRevisionJpaRepository revisionRepository,
            NominationJpaRepository nominationRepository,
            PlanTargetJpaRepository targetRepository
    ) {
        this.periodRepository = Objects.requireNonNull(periodRepository, "PlanningPeriodJpaRepository must not be null.");
        this.planRepository = Objects.requireNonNull(planRepository, "OperationalPlanJpaRepository must not be null.");
        this.revisionRepository = Objects.requireNonNull(revisionRepository, "PlanRevisionJpaRepository must not be null.");
        this.nominationRepository = Objects.requireNonNull(nominationRepository, "NominationJpaRepository must not be null.");
        this.targetRepository = Objects.requireNonNull(targetRepository, "PlanTargetJpaRepository must not be null.");
    }

    @Override
    public Slice<PlanningPeriod> findPeriods(int page, int size) {
        return toSlice(periodRepository.findAll(pageable(page, size)), PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Optional<PlanningPeriod> findPeriodById(String id) {
        return periodRepository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Slice<OperationalPlan> findOperationalPlans(int page, int size) {
        return toSlice(planRepository.findAll(pageable(page, size)), PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Optional<OperationalPlan> findOperationalPlanById(String id) {
        return planRepository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Slice<PlanRevision> findRevisionsByPlanId(String planId, int page, int size) {
        return toSlice(revisionRepository.findByPlanId(planId, pageable(page, size)), PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Optional<PlanRevision> findRevisionById(String id) {
        return revisionRepository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Slice<Nomination> findNominationsByRevisionId(String revisionId, int page, int size) {
        return toSlice(nominationRepository.findByRevisionId(revisionId, pageable(page, size)), PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Nomination> findNominationById(String id) {
        return nominationRepository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Slice<PlanTarget> findTargetsByRevisionId(String revisionId, int page, int size) {
        return toSlice(targetRepository.findByRevisionId(revisionId, pageable(page, size)), PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Optional<PlanTarget> findTargetById(String id) {
        return targetRepository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }

    private static Pageable pageable(int page, int size) {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
    }

    private static <E, D> Slice<D> toSlice(Page<E> page, Function<E, D> mapper) {
        return new Slice<>(page.getContent().stream().map(mapper).toList(), page.getTotalElements());
    }
}
