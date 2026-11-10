/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningDraftApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Coordinates transactional scoped draft revision and target authoring.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.mapper.PlanningApplicationMapper;
import dz.sh.hidra.modules.planning.application.port.in.AuthorPlanningDraftUseCase;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.PlanRevisionView;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanTargetRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanningCatalogEligibilityPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanningPeriodRepositoryPort;
import dz.sh.hidra.modules.planning.domain.exception.PlanningRevisionConflictException;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.model.PlanTarget;
import dz.sh.hidra.modules.planning.domain.value.PlanningId;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import dz.sh.hidra.modules.planning.domain.value.PlanTargetStatus;
import dz.sh.hidra.modules.telemetry.application.contract.planning.PlanningTargetTelemetryContract;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlanningDraftApplicationService implements AuthorPlanningDraftUseCase {

    private final OperationalPlanRepositoryPort plans;
    private final PlanRevisionRepositoryPort revisions;
    private final PlanTargetRepositoryPort targets;
    private final PlanningPeriodRepositoryPort periods;
    private final PlanningCatalogEligibilityPort catalogs;
    private final PlanningAccessService access;
    private final PlanningTargetTelemetryContract telemetry;

    public PlanningDraftApplicationService(OperationalPlanRepositoryPort plans,
            PlanRevisionRepositoryPort revisions, PlanTargetRepositoryPort targets,
            PlanningPeriodRepositoryPort periods, PlanningCatalogEligibilityPort catalogs,
            PlanningAccessService access, PlanningTargetTelemetryContract telemetry) {
        this.plans = plans;
        this.revisions = revisions;
        this.targets = targets;
        this.periods = periods;
        this.catalogs = catalogs;
        this.access = access;
        this.telemetry = telemetry;
    }

    @Override
    public PlanRevisionView createRevision(String planId, RevisionCommand command) {
        Objects.requireNonNull(command);
        OperationalPlan plan = lockedPlan(requireId(planId));
        requireVersion(command.expectedPlanUpdatedAt(), plan.updatedAt(), "plan");
        String base = plan.currentRevisionId();
        if (base != null) {
            PlanRevision previous = revisions.findByIdForUpdate(base)
                    .filter(value -> plan.id().equals(value.planId()))
                    .orElseThrow(PlanningAccessService::unavailable);
            if (previous.status() == PlanRevisionStatus.DRAFT || previous.status() == PlanRevisionStatus.SUBMITTED) {
                throw conflict("A draft or submitted revision already exists for this plan.");
            }
        }
        if (command.changeReasonCodeId() != null) {
            catalogs.requireActive(command.changeReasonCodeId(), "REVISION_REASON");
        }
        int highest = revisions.highestRevisionNumber(plan.id());
        if ((base == null && highest > 0) || highest == Integer.MAX_VALUE) {
            throw conflict("The plan revision sequence cannot be advanced from its current history.");
        }
        int sequence = highest + 1;
        Instant at = nextVersion(plan.updatedAt());
        PlanRevision saved = revisions.save(new PlanRevision(PlanningId.newId().value(), plan.id(), sequence,
                String.format(Locale.ROOT, "R%04d", sequence), PlanRevisionStatus.DRAFT,
                command.changeReasonCodeId(), command.changeReasonText(), base,
                null, null, null, null, null, at, at));
        plans.save(plan.withCurrentRevision(saved.id(), at));
        return PlanningApplicationMapper.toRevisionView(saved);
    }

    @Override
    public TargetResult createTarget(String revisionId, TargetCommand command) {
        Objects.requireNonNull(command);
        Draft draft = lockedDraft(requireId(revisionId), command.expectedRevisionUpdatedAt());
        return saveTarget(draft, null, command);
    }

    @Override
    public TargetResult replaceTarget(String targetId, TargetCommand command) {
        Objects.requireNonNull(command);
        String id = requireId(targetId);
        String revisionId = targets.revisionIdForTarget(id).orElseThrow(PlanningAccessService::unavailable);
        Draft draft = lockedDraft(revisionId, command.expectedRevisionUpdatedAt());
        PlanTarget old = targets.findByIdForUpdate(id)
                .filter(value -> revisionId.equals(value.revisionId()))
                .orElseThrow(PlanningAccessService::unavailable);
        requireVersion(command.expectedTargetUpdatedAt(), old.updatedAt(), "target");
        if (old.status() != PlanTargetStatus.DRAFT) {
            throw conflict("Only DRAFT targets are editable.");
        }
        return saveTarget(draft, old, command);
    }

    private TargetResult saveTarget(Draft draft, PlanTarget old, TargetCommand command) {
        OperationalPlan plan = draft.plan();
        if (!plan.topologyScopeType().equals(command.topologyAssetType())
                || !plan.topologyScopeId().equals(command.topologyAssetId())) {
            throw new IllegalArgumentException("Target must use the authorized plan asset.");
        }
        var period = periods.findById(plan.periodId()).orElseThrow(PlanningAccessService::unavailable);
        if (command.validFrom() == null || command.validTo() == null
                || !command.validFrom().isBefore(command.validTo())
                || command.validFrom().isBefore(period.periodStart()) || command.validTo().isAfter(period.periodEnd())) {
            throw new IllegalArgumentException("Target window must be nonempty and contained in the planning period.");
        }
        catalogs.requireActive(command.targetTypeId(), "TARGET_TYPE");
        requirePersistableNumber(command.targetValue());
        requirePersistableNumber(command.toleranceLow());
        requirePersistableNumber(command.toleranceHigh());
        boolean numeric = command.targetValue() != null;
        if (numeric == (command.targetTextValue() != null && !command.targetTextValue().isBlank())) {
            throw new IllegalArgumentException("Exactly one numeric or text target value is required.");
        }
        if (!numeric && (command.unitId() != null || command.toleranceLow() != null || command.toleranceHigh() != null)) {
            throw new IllegalArgumentException("Text targets cannot carry a numeric unit or tolerances.");
        }
        if (command.toleranceLow() != null && command.toleranceHigh() != null
                && command.toleranceLow().compareTo(command.toleranceHigh()) > 0) {
            throw new IllegalArgumentException("Target tolerance bounds are reversed.");
        }
        telemetry.requireCompatible(command.telemetryPointId(), plan.topologyScopeType(), plan.topologyScopeId(),
                command.unitId(), numeric, command.validFrom(), command.validTo());
        Instant at = nextVersion(draft.revision().updatedAt());
        PlanTarget saved = targets.saveDraft(new PlanTarget(old == null ? PlanningId.newId().value() : old.id(),
                draft.revision().id(), command.scenarioId(), command.nominationId(), command.targetTypeId(),
                plan.topologyScopeType(), plan.topologyScopeId(), plan.topologyScopeCode(), plan.topologyScopeNameSnapshot(),
                command.telemetryPointId(), null, command.targetValue(), command.targetTextValue(), command.unitId(),
                command.toleranceLow(), command.toleranceHigh(), command.validFrom(), command.validTo(),
                command.priority(), PlanTargetStatus.DRAFT, old == null ? at : old.createdAt(), at));
        PlanRevision revision = revisions.save(draft.revision().advanceDraftVersion(at));
        return new TargetResult(PlanningApplicationMapper.toTargetView(saved), revision.updatedAt());
    }

    private Draft lockedDraft(String revisionId, Instant expectedVersion) {
        String planId = revisions.planIdForRevision(revisionId).orElseThrow(PlanningAccessService::unavailable);
        OperationalPlan plan = lockedPlan(planId);
        PlanRevision revision = revisions.findByIdForUpdate(revisionId)
                .filter(value -> plan.id().equals(value.planId()))
                .orElseThrow(PlanningAccessService::unavailable);
        revision.requireEditableDraft();
        if (!revision.id().equals(plan.currentRevisionId())) {
            throw conflict("The revision is no longer the current draft.");
        }
        requireVersion(expectedVersion, revision.updatedAt(), "revision");
        return new Draft(plan, revision);
    }

    private OperationalPlan lockedPlan(String planId) {
        OperationalPlan plan = plans.findByIdForUpdate(planId).orElseThrow(PlanningAccessService::unavailable);
        access.planAuthor(plan);
        plan.requireDraftAuthoring();
        var period = periods.findById(plan.periodId()).orElseThrow(PlanningAccessService::unavailable);
        if (!period.allowsNewPlanRevisions()) {
            throw conflict("The planning period is closed.");
        }
        return plan;
    }

    private static void requireVersion(Instant expected, Instant actual, String resource) {
        if (expected == null || !expected.equals(actual)) {
            throw conflict("The " + resource + " changed. Refetch its current version before retrying.");
        }
    }

    private static Instant nextVersion(Instant previous) {
        Instant current = Instant.now().truncatedTo(ChronoUnit.MICROS);
        return current.isAfter(previous) ? current : previous.plusNanos(1000).truncatedTo(ChronoUnit.MICROS);
    }

    private static void requirePersistableNumber(BigDecimal value) {
        if (value == null) {
            return;
        }
        try {
            if (value.setScale(6, RoundingMode.UNNECESSARY).precision() > 18) {
                throw new ArithmeticException("Numeric precision exceeded");
            }
        } catch (ArithmeticException invalid) {
            throw new IllegalArgumentException("Target numbers must be exactly representable with 12 integer and 6 fractional digits.");
        }
    }

    private static String requireId(String id) {
        if (id == null || id.isBlank() || !id.equals(id.strip())) {
            throw new IllegalArgumentException("A valid Planning identity is required.");
        }
        return id;
    }

    private static PlanningRevisionConflictException conflict(String message) {
        return new PlanningRevisionConflictException(message);
    }

    private record Draft(OperationalPlan plan, PlanRevision revision) { }
}
