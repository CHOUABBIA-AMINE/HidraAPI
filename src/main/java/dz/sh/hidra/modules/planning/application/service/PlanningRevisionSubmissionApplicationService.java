/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningRevisionSubmissionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Atomically freezes scoped drafts and starts independently assigned Workflow review.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.port.in.SubmitPlanningRevisionUseCase;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanTargetRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanningPeriodRepositoryPort;
import dz.sh.hidra.modules.planning.domain.exception.PlanningRevisionConflictException;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import dz.sh.hidra.modules.planning.domain.value.OperationalPlanStatus;
import dz.sh.hidra.modules.workflow.application.contract.planning.PlanningWorkflowContract;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanningRevisionSubmissionApplicationService implements SubmitPlanningRevisionUseCase {
    private final OperationalPlanRepositoryPort plans;
    private final PlanRevisionRepositoryPort revisions;
    private final PlanTargetRepositoryPort targets;
    private final PlanningPeriodRepositoryPort periods;
    private final PlanningAccessService access;
    private final PlanningWorkflowContract workflow;

    public PlanningRevisionSubmissionApplicationService(OperationalPlanRepositoryPort plans,
            PlanRevisionRepositoryPort revisions, PlanTargetRepositoryPort targets,
            PlanningPeriodRepositoryPort periods, PlanningAccessService access,
            PlanningWorkflowContract workflow) {
        this.plans = Objects.requireNonNull(plans);
        this.revisions = Objects.requireNonNull(revisions);
        this.targets = Objects.requireNonNull(targets);
        this.periods = Objects.requireNonNull(periods);
        this.access = Objects.requireNonNull(access);
        this.workflow = Objects.requireNonNull(workflow);
    }

    @Override
    @Transactional
    public SubmissionView submit(String revisionId, SubmissionCommand command) {
        Objects.requireNonNull(command, "Submission command required.");
        if (revisionId == null || revisionId.isBlank() || !revisionId.equals(revisionId.strip())) {
            throw new IllegalArgumentException("Valid revision ID required.");
        }
        String planId = revisions.planIdForRevision(revisionId).orElseThrow(PlanningAccessService::unavailable);
        var plan = plans.findByIdForUpdate(planId).orElseThrow(PlanningAccessService::unavailable);
        String actor = access.planAuthor(plan);
        plan.requireDraftAuthoring();
        var period = periods.findById(plan.periodId()).orElseThrow(PlanningAccessService::unavailable);
        if (!period.allowsNewPlanRevisions()) throw conflict("Planning period is closed.");
        var revision = revisions.findByIdForUpdate(revisionId)
                .filter(r -> r.planId().equals(plan.id())).orElseThrow(PlanningAccessService::unavailable);
        revision.requireEditableDraft();
        if (!revision.id().equals(plan.currentRevisionId())) throw conflict("Revision is no longer current.");
        if (revision.workflowInstanceId() != null || revision.submittedAt() != null)
            throw conflict("Revision has already been submitted.");
        if (!Objects.equals(command.expectedPlanUpdatedAt(), plan.updatedAt())
                || !Objects.equals(command.expectedRevisionUpdatedAt(), revision.updatedAt()))
            throw conflict("Submission precondition is stale.");
        var draftTargets = targets.findByRevisionIdForUpdate(revisionId);
        if (draftTargets.isEmpty() || draftTargets.stream()
                .anyMatch(target -> target.status() != dz.sh.hidra.modules.planning.domain.value.PlanTargetStatus.DRAFT))
            throw conflict("Submission requires only complete authored DRAFT targets.");
        if (command.reviewerActorId() == null || command.reviewerActorId().isBlank()
                || actor.equals(command.reviewerActorId()))
            throw conflict("An independent assigned reviewer is required.");
        var started = workflow.startRevisionApproval(new PlanningWorkflowContract.RevisionStartCommand(
                revision.id(), command.definitionId(), command.definitionVersion(),
                command.targetTypeId(), command.workflowPurposeId(),
                command.reviewerActorId(), actor, command.correlationId()));
        Instant at = nextVersion(revision.updatedAt(), plan.updatedAt());
        var submitted = revisions.save(revision.withSubmission(actor, at, started.instanceId()));
        var submittedPlan = plans.save(plan.withLifecycle(OperationalPlanStatus.SUBMITTED,
                plan.approvedRevisionId(), at));
        return new SubmissionView(submitted.id(), submitted.status().name(), submitted.updatedAt(),
                submittedPlan.updatedAt(), started.instanceId(), started.taskId(), started.taskUpdatedAt());
    }

    private static Instant nextVersion(Instant one, Instant two) {
        Instant at = Instant.now().truncatedTo(ChronoUnit.MICROS);
        Instant max = one.isAfter(two) ? one : two;
        return at.isAfter(max) ? at : max.plusNanos(1000).truncatedTo(ChronoUnit.MICROS);
    }

    private static PlanningRevisionConflictException conflict(String message) {
        return new PlanningRevisionConflictException(message);
    }
}
