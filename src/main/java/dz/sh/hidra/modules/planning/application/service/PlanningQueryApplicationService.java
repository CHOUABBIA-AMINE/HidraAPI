/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningQueryApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Implements read-only planning queries for HWEB-010.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase;
import dz.sh.hidra.modules.planning.application.mapper.PlanningApplicationMapper;
import dz.sh.hidra.modules.planning.application.port.out.PlanningQueryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanningQueryPort.Slice;
import dz.sh.hidra.modules.planning.domain.model.Nomination;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.model.PlanTarget;
import dz.sh.hidra.modules.planning.domain.model.PlanningPeriod;
import java.util.Objects;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class PlanningQueryApplicationService implements PlanningQueryUseCase {

    private static final int MAX_PAGE_SIZE = 200;
    private final PlanningQueryPort queryPort;

    private final PlanningAccessService access;

    public PlanningQueryApplicationService(PlanningQueryPort queryPort, PlanningAccessService access) {
        this.queryPort = Objects.requireNonNull(queryPort, "PlanningQueryPort must not be null.");
        this.access = Objects.requireNonNull(access);
    }

    @Override
    public Page<PlanningPeriodView> periods(int page, int size) {
        validatePage(page, size);
        return page(queryPort.findPeriods(access.readableCompanies(), page, size), page, size, this::toPeriodView);
    }

    @Override
    public PlanningPeriodView period(String id) {
        String normalizedId = requireId(id, "planning period");
        return queryPort.findPeriodById(access.readableCompanies(), normalizedId)
                .map(this::toPeriodView)
                .orElseThrow(PlanningAccessService::unavailable);
    }

    @Override
    public Page<OperationalPlanView> operationalPlans(int page, int size) {
        validatePage(page, size);
        return page(queryPort.findOperationalPlans(access.readableAssets(), page, size), page, size, this::toPlanView);
    }

    @Override
    public OperationalPlanView operationalPlan(String id) {
        String normalizedId = requireId(id, "operational plan");
        return queryPort.findOperationalPlanById(access.readableAssets(), normalizedId)
                .map(this::toPlanView)
                .orElseThrow(PlanningAccessService::unavailable);
    }

    @Override
    public Page<PlanRevisionView> revisions(String planId, int page, int size) {
        String normalizedPlanId = requireId(planId, "operational plan");
        validatePage(page, size);
        var assets = access.readableAssets();
        queryPort.findOperationalPlanById(assets, normalizedPlanId).orElseThrow(PlanningAccessService::unavailable);
        return page(queryPort.findRevisionsByPlanId(assets, normalizedPlanId, page, size), page, size, PlanningApplicationMapper::toRevisionView);
    }

    @Override
    public PlanRevisionView revision(String id) {
        String normalizedId = requireId(id, "plan revision");
        return queryPort.findRevisionById(access.readableAssets(), normalizedId)
                .map(PlanningApplicationMapper::toRevisionView)
                .orElseThrow(PlanningAccessService::unavailable);
    }

    @Override
    public Page<NominationView> nominations(String revisionId, int page, int size) {
        String normalizedRevisionId = requireId(revisionId, "plan revision");
        validatePage(page, size);
        var assets = access.readableAssets();
        queryPort.findRevisionById(assets, normalizedRevisionId).orElseThrow(PlanningAccessService::unavailable);
        return page(queryPort.findNominationsByRevisionId(assets, normalizedRevisionId, page, size), page, size, this::toNominationView);
    }

    @Override
    public NominationView nomination(String id) {
        String normalizedId = requireId(id, "nomination");
        return queryPort.findNominationById(access.readableAssets(), normalizedId)
                .map(this::toNominationView)
                .orElseThrow(PlanningAccessService::unavailable);
    }

    @Override
    public Page<PlanTargetView> targets(String revisionId, int page, int size) {
        String normalizedRevisionId = requireId(revisionId, "plan revision");
        validatePage(page, size);
        var assets = access.readableAssets();
        queryPort.findRevisionById(assets, normalizedRevisionId).orElseThrow(PlanningAccessService::unavailable);
        return page(queryPort.findTargetsByRevisionId(assets, normalizedRevisionId, page, size), page, size, PlanningApplicationMapper::toTargetView);
    }

    @Override
    public PlanTargetView target(String id) {
        String normalizedId = requireId(id, "plan target");
        return queryPort.findTargetById(access.readableAssets(), normalizedId)
                .map(PlanningApplicationMapper::toTargetView)
                .orElseThrow(PlanningAccessService::unavailable);
    }

    private void validatePage(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("page must be >= 0.");
        }
        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("size must be between 1 and 200.");
        }
    }

    private String requireId(String id, String resource) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(resource + " id must not be blank.");
        }
        return id.trim();
    }

    private <S, T> Page<T> page(Slice<S> slice, int page, int size, Function<S, T> mapper) {
        long total = slice.totalElements();
        int totalPages = total == 0 ? 0 : (int) Math.ceil((double) total / size);
        return new Page<>(
                slice.content().stream().map(mapper).toList(),
                page,
                size,
                total,
                totalPages,
                page + 1 < totalPages
        );
    }

    private PlanningPeriodView toPeriodView(PlanningPeriod period) {
        return new PlanningPeriodView(
                period.id(), period.code(), period.nameAr(), period.nameFr(), period.nameEn(), period.periodTypeId(),
                period.periodStart(), period.periodEnd(), period.timeZone(), name(period.status()), period.createdByActorId(),
                period.createdAt(), period.updatedAt(), period.companyPartyId()
        );
    }

    private OperationalPlanView toPlanView(OperationalPlan plan) {
        return new OperationalPlanView(
                plan.id(), plan.periodId(), plan.code(), plan.nameAr(), plan.nameFr(), plan.nameEn(), plan.planTypeId(),
                plan.productTypeId(), plan.topologyScopeType(), plan.topologyScopeId(), plan.topologyScopeCode(),
                plan.topologyScopeNameSnapshot(), plan.responsibleOrganizationUnitId(), name(plan.status()),
                plan.currentRevisionId(), plan.approvedRevisionId(), plan.createdByActorId(), plan.createdAt(), plan.updatedAt()
        );
    }

    private NominationView toNominationView(Nomination nomination) {
        return new NominationView(
                nomination.id(), nomination.revisionId(), nomination.scenarioId(), nomination.code(), nomination.nominationTypeId(),
                nomination.productTypeId(), nomination.quantity(), nomination.quantityUnitId(), nomination.rate(), nomination.rateUnitId(),
                nomination.sourceAssetType(), nomination.sourceAssetId(), nomination.sourceAssetCode(), nomination.destinationAssetType(),
                nomination.destinationAssetId(), nomination.destinationAssetCode(), nomination.shipperPartyId(),
                nomination.shipperPartyCodeSnapshot(), nomination.counterpartyId(), nomination.contractReferenceId(), nomination.priority(),
                name(nomination.status()), nomination.periodStart(), nomination.periodEnd(), nomination.createdAt(), nomination.updatedAt()
        );
    }

    private static String name(Enum<?> value) {
        return value == null ? null : value.name();
    }
}
