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
import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.PlanningPeriodJpaEntity;
import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.OperationalPlanJpaEntity;
import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.PlanRevisionJpaEntity;
import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.NominationJpaEntity;
import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.PlanTargetJpaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public Slice<PlanningPeriod> findPeriods(Set<String> companies, int page, int size) {
        return toSlice(periodRepository.findAll(companyScope(companies), pageable(page, size)),
                PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Optional<PlanningPeriod> findPeriodById(Set<String> companies, String id) {
        return periodRepository.findOne(companyScope(companies).and(fieldEquals("id", id)))
                .map(PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Slice<OperationalPlan> findOperationalPlans(Set<AssetScope> assets, int page, int size) {
        return toSlice(planRepository.findAll(planScope(assets), pageable(page, size)),
                PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Optional<OperationalPlan> findOperationalPlanById(Set<AssetScope> assets, String id) {
        return planRepository.findOne(planScope(assets).and(fieldEquals("id", id)))
                .map(PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Slice<PlanRevision> findRevisionsByPlanId(Set<AssetScope> assets, String planId, int page, int size) {
        return toSlice(revisionRepository.findAll(revisionScope(assets).and(fieldEquals("planId", planId)),
                pageable(page, size)), PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Optional<PlanRevision> findRevisionById(Set<AssetScope> assets, String id) {
        return revisionRepository.findOne(revisionScope(assets).and(fieldEquals("id", id)))
                .map(PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Slice<Nomination> findNominationsByRevisionId(Set<AssetScope> assets, String revisionId, int page, int size) {
        return toSlice(nominationRepository.findAll(JpaPlanningQueryAdapter.<NominationJpaEntity>descendantScope(assets)
                .and(fieldEquals("revisionId", revisionId)), pageable(page, size)), PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Nomination> findNominationById(Set<AssetScope> assets, String id) {
        return nominationRepository.findOne(JpaPlanningQueryAdapter.<NominationJpaEntity>descendantScope(assets)
                .and(fieldEquals("id", id))).map(PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Slice<PlanTarget> findTargetsByRevisionId(Set<AssetScope> assets, String revisionId, int page, int size) {
        return toSlice(targetRepository.findAll(JpaPlanningQueryAdapter.<PlanTargetJpaEntity>descendantScope(assets)
                .and(fieldEquals("revisionId", revisionId)), pageable(page, size)), PlanningPersistenceMapper::toDomain);
    }

    @Override
    public Optional<PlanTarget> findTargetById(Set<AssetScope> assets, String id) {
        return targetRepository.findOne(JpaPlanningQueryAdapter.<PlanTargetJpaEntity>descendantScope(assets)
                .and(fieldEquals("id", id))).map(PlanningPersistenceMapper::toDomain);
    }

    private static Specification<PlanningPeriodJpaEntity> companyScope(Set<String> companies) {
        return (period, query, cb) -> companies.isEmpty()
                ? cb.disjunction() : period.get("companyPartyId").in(companies);
    }

    private static Specification<OperationalPlanJpaEntity> planScope(Set<AssetScope> assets) {
        return (plan, query, cb) -> {
            if (assets.isEmpty()) {
                return cb.disjunction();
            }
            var matchingPeriod = Objects.requireNonNull(query).subquery(String.class);
            var period = matchingPeriod.from(PlanningPeriodJpaEntity.class);
            matchingPeriod.select(period.get("id")).where(planPredicate(cb, plan, period, assets));
            return cb.exists(matchingPeriod);
        };
    }

    private static Specification<PlanRevisionJpaEntity> revisionScope(Set<AssetScope> assets) {
        return (revision, query, cb) -> {
            if (assets.isEmpty()) {
                return cb.disjunction();
            }
            var matchingPlan = Objects.requireNonNull(query).subquery(String.class);
            var plan = matchingPlan.from(OperationalPlanJpaEntity.class);
            var period = matchingPlan.from(PlanningPeriodJpaEntity.class);
            matchingPlan.select(plan.get("id")).where(cb.equal(plan.get("id"), revision.get("planId")),
                    planPredicate(cb, plan, period, assets));
            return cb.exists(matchingPlan);
        };
    }

    private static <T> Specification<T> descendantScope(Set<AssetScope> assets) {
        return (descendant, query, cb) -> {
            if (assets.isEmpty()) {
                return cb.disjunction();
            }
            var matchingRevision = Objects.requireNonNull(query).subquery(String.class);
            var revision = matchingRevision.from(PlanRevisionJpaEntity.class);
            var plan = matchingRevision.from(OperationalPlanJpaEntity.class);
            var period = matchingRevision.from(PlanningPeriodJpaEntity.class);
            matchingRevision.select(revision.get("id")).where(
                    cb.equal(revision.get("id"), descendant.get("revisionId")),
                    cb.equal(plan.get("id"), revision.get("planId")), planPredicate(cb, plan, period, assets));
            return cb.exists(matchingRevision);
        };
    }

    private static Predicate planPredicate(CriteriaBuilder cb, Root<OperationalPlanJpaEntity> plan,
            Root<PlanningPeriodJpaEntity> period, Set<AssetScope> assets) {
        var idsByScope = new HashMap<CompanyAndType, List<String>>();
        for (AssetScope asset : assets) {
            var scope = new CompanyAndType(asset.companyPartyId(), asset.type());
            idsByScope.computeIfAbsent(scope, key -> new ArrayList<>()).add(asset.assetId());
        }
        var tuples = idsByScope.entrySet().stream().map(entry -> cb.and(
                cb.equal(period.get("companyPartyId"), entry.getKey().company()),
                cb.equal(plan.get("topologyScopeType"), entry.getKey().type()),
                plan.get("topologyScopeId").in(entry.getValue())
        )).toArray(Predicate[]::new);
        return cb.and(cb.equal(plan.get("periodId"), period.get("id")), cb.or(tuples));
    }

    private record CompanyAndType(String company, String type) { }

    private static <T> Specification<T> fieldEquals(String field, String value) {
        return (row, query, cb) -> cb.equal(row.get(field), value);
    }

    private static Pageable pageable(int page, int size) {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
    }

    private static <E, D> Slice<D> toSlice(Page<E> page, Function<E, D> mapper) {
        return new Slice<>(page.getContent().stream().map(mapper).toList(), page.getTotalElements());
    }
}
