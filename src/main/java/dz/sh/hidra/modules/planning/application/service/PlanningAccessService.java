/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningAccessService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Combines Planning authorization with current Party and Topology evidence.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.identity.application.contract.planning.PlanningScopedAuthorizationContract;
import dz.sh.hidra.modules.planning.application.contract.workflow.PlanningRevisionWorkflowAuthorityContract;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import dz.sh.hidra.modules.planning.domain.value.OperationalPlanStatus;
import dz.sh.hidra.modules.identity.application.contract.planning.PlanningScopedAuthorizationContract.Access;
import dz.sh.hidra.modules.identity.application.contract.planning.PlanningScopedAuthorizationContract.Asset;
import dz.sh.hidra.modules.identity.application.contract.planning.PlanningScopedAuthorizationContract.Operation;
import dz.sh.hidra.modules.party.application.contract.planning.PlanningCompanyEligibilityContract;
import dz.sh.hidra.modules.planning.application.port.out.PlanningPeriodRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanningQueryPort.AssetScope;
import dz.sh.hidra.modules.topology.application.contract.planning.PlanningAssetOwnershipContract;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class PlanningAccessService implements PlanningRevisionWorkflowAuthorityContract {

    private final PlanningScopedAuthorizationContract identity;
    private final PlanningCompanyEligibilityContract party;
    private final PlanningAssetOwnershipContract topology;
    private final PlanningPeriodRepositoryPort periods;
    private OperationalPlanRepositoryPort plans;
    private PlanRevisionRepositoryPort revisions;

    public PlanningAccessService(PlanningScopedAuthorizationContract identity,
            PlanningCompanyEligibilityContract party, PlanningAssetOwnershipContract topology,
            PlanningPeriodRepositoryPort periods) {
        this.identity = identity;
        this.party = party;
        this.topology = topology;
        this.periods = periods;
    }

    public String periodCreator(String company, String claimedActor) {
        if (company == null || company.isBlank() || !company.equals(company.strip()) || company.length() > 80) {
            throw denied();
        }
        Access access = identity.current(Operation.PERIOD_CREATE);
        requireMatchingActor(access, claimedActor);
        if (!access.companies().contains(company) || !party.isActiveCompany(company)) {
            throw denied();
        }
        return access.actorId();
    }

    public String planCreator(String periodId, String type, String assetId, String claimedActor) {
        Access access = identity.current(Operation.PLAN_CREATE);
        requireMatchingActor(access, claimedActor);
        return requirePlanScope(access, periodId, type, assetId);
    }

    public String planAuthor(OperationalPlan plan) {
        Access access = identity.current(Operation.PLAN_AUTHOR);
        return requirePlanScope(access, plan.periodId(), plan.topologyScopeType(), plan.topologyScopeId());
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void bindReviewRepositories(OperationalPlanRepositoryPort plans, PlanRevisionRepositoryPort revisions) {
        this.plans = Objects.requireNonNull(plans);
        this.revisions = Objects.requireNonNull(revisions);
    }

    public String planReviewer(OperationalPlan plan) {
        Access access = identity.current(Operation.PLAN_REVIEW);
        return requirePlanScope(access, plan.periodId(), plan.topologyScopeType(), plan.topologyScopeId());
    }

    @Override
    public void requireReview(String revisionId, String workflowInstanceId,
            String starterActorId, String reviewerActorId) {
        var revision = revisions.findById(revisionId).orElseThrow(PlanningAccessService::unavailable);
        var plan = plans.findById(revision.planId()).orElseThrow(PlanningAccessService::unavailable);
        if (revision.status() != PlanRevisionStatus.SUBMITTED
                || plan.status() != OperationalPlanStatus.SUBMITTED
                || !revision.id().equals(plan.currentRevisionId())
                || !Objects.equals(revision.workflowInstanceId(), workflowInstanceId)
                || !Objects.equals(revision.submittedByActorId(), starterActorId)
                || reviewerActorId == null || reviewerActorId.equals(starterActorId)) {
            throw unavailable();
        }
        if (!reviewerActorId.equals(planReviewer(plan))) {
            throw denied();
        }
    }

    private String requirePlanScope(Access access, String periodId, String type, String assetId) {
        if (periodId == null || periodId.isBlank() || type == null || assetId == null
                || !access.assets().contains(new Asset(type, assetId))) {
            throw unavailable();
        }
        String owner = topology.activeOwner(type, assetId)
                .filter(access.companies()::contains)
                .filter(party::isActiveCompany)
                .orElseThrow(PlanningAccessService::unavailable);
        periods.findById(periodId.trim())
                .filter(period -> Objects.equals(owner, period.companyPartyId()))
                .orElseThrow(PlanningAccessService::unavailable);
        return access.actorId();
    }

    public Set<String> readableCompanies() {
        return activeCompanies(identity.current(Operation.PERIOD_READ));
    }

    public Set<AssetScope> readableAssets() {
        Access access = identity.current(Operation.PLAN_READ);
        Set<String> companies = activeCompanies(access);
        if (companies.isEmpty()) {
            return Set.of();
        }
        var scopes = new java.util.LinkedHashSet<AssetScope>();
        access.assets().stream().sorted(Comparator.comparing(Asset::type).thenComparing(Asset::id))
                .forEach(asset -> topology.activeOwner(asset.type(), asset.id())
                        .filter(companies::contains)
                        .ifPresent(company -> scopes.add(new AssetScope(company, asset.type(), asset.id()))));
        return scopes;
    }

    private Set<String> activeCompanies(Access access) {
        return access.companies().stream().sorted().filter(party::isActiveCompany).collect(Collectors.toUnmodifiableSet());
    }

    private static void requireMatchingActor(Access access, String claimedActor) {
        if (claimedActor != null && !access.actorId().equals(claimedActor.trim())) {
            throw denied();
        }
    }

    static NoSuchElementException unavailable() {
        return new NoSuchElementException("Planning resource unavailable.");
    }

    private static AccessDeniedException denied() {
        return new AccessDeniedException("Planning operation unavailable.");
    }
}
