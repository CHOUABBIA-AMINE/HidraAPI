/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringPlanTargetReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Resolves historical Planning targets through an owned repository port.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.contract.monitoring.MonitoringPlanTargetReferenceContract;
import dz.sh.hidra.modules.planning.application.port.out.PlanTargetRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanningPeriodRepositoryPort;
import dz.sh.hidra.modules.planning.domain.value.OperationalPlanStatus;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import dz.sh.hidra.modules.planning.domain.value.PlanTargetStatus;
import dz.sh.hidra.modules.planning.domain.value.PlanningPeriodStatus;
import java.time.Instant;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MonitoringPlanTargetReferenceQueryService implements MonitoringPlanTargetReferenceContract {
    private final PlanTargetRepositoryPort targets;
    private final OperationalPlanRepositoryPort plans;
    private final PlanRevisionRepositoryPort revisions;
    private final PlanningPeriodRepositoryPort periods;

    public MonitoringPlanTargetReferenceQueryService(PlanTargetRepositoryPort targets) {
        this(targets, null, null, null);
    }

    @Autowired
    public MonitoringPlanTargetReferenceQueryService(PlanTargetRepositoryPort targets,
            OperationalPlanRepositoryPort plans, PlanRevisionRepositoryPort revisions,
            PlanningPeriodRepositoryPort periods) {
        this.targets = Objects.requireNonNull(targets);
        this.plans = plans;
        this.revisions = revisions;
        this.periods = periods;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<EvaluationTarget> forEvaluation(String id, String company, String type, String asset, Instant at) {
        if (plans == null) return Optional.empty();
        String revisionId = targets.revisionIdForTarget(id).orElse(null);
        if (revisionId == null) return Optional.empty();
        String planId = revisions.planIdForRevision(revisionId).orElse(null);
        if (planId == null) return Optional.empty();
        var plan = plans.findByIdForUpdate(planId).orElse(null);
        if (plan == null || plan.status() != OperationalPlanStatus.ACTIVE
                || !revisionId.equals(plan.approvedRevisionId())
                || !type.equals(plan.topologyScopeType()) || !asset.equals(plan.topologyScopeId())) {
            return Optional.empty();
        }
        var period = periods.findById(plan.periodId()).orElse(null);
        if (period == null || period.status() == PlanningPeriodStatus.CANCELLED
                || !company.equals(period.companyPartyId())
                || !inWindow(at, period.periodStart(), period.periodEnd())) return Optional.empty();
        var revision = revisions.findByIdForUpdate(revisionId).orElse(null);
        if (revision == null || !planId.equals(revision.planId()) || revision.status() != PlanRevisionStatus.APPROVED
                || revision.workflowInstanceId() == null || revision.approvedByActorId() == null
                || revision.approvedAt() == null || revision.approvedAt().isAfter(at)
                || revision.submittedByActorId() == null
                || revision.approvedByActorId().equals(revision.submittedByActorId())) return Optional.empty();
        return targets.findByIdForUpdate(id).filter(target -> revisionId.equals(target.revisionId())
                && target.status() == PlanTargetStatus.ACTIVE
                && type.equals(target.topologyAssetType()) && asset.equals(target.topologyAssetId())
                && inWindow(at, target.validFrom(), target.validTo()))
                .map(target -> new EvaluationTarget(target.id(), revisionId, revision.workflowInstanceId(),
                        revision.approvedAt(), target.telemetryPointId(), target.unitId(), target.targetValue()));
    }

    private static boolean inWindow(Instant at, Instant from, Instant until) {
        return from != null && until != null && !at.isBefore(from) && at.isBefore(until);
    }

    @Override
    public Optional<Target> resolve(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        String key = id.trim();
        return targets.findById(key).filter(t -> key.equals(t.id())).map(t -> new Target(
                t.id(), t.revisionId(), t.topologyAssetType(), t.topologyAssetId(), t.telemetryPointId(),
                t.status().name(), t.targetValue(), t.targetTextValue(), t.unitId(), t.validFrom(), t.validTo()));
    }
}
