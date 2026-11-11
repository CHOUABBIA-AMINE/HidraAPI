/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Orchestrates revision-scoped workflow approval while keeping planning lifecycle ownership server-side.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.port.in.PlanningApprovalUseCase;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanTargetRepositoryPort;
import dz.sh.hidra.modules.planning.domain.exception.PlanningRevisionConflictException;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.value.OperationalPlanStatus;
import dz.sh.hidra.modules.planning.domain.value.PlanTargetStatus;
import java.time.temporal.ChronoUnit;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import dz.sh.hidra.modules.workflow.application.contract.planning.PlanningWorkflowContract;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

@Service
public class PlanningApprovalApplicationService implements PlanningApprovalUseCase {

    private static final String PLANNING_MODULE = "planning";
    private static final Set<String> TERMINAL_WORKFLOW_STATES = Set.of("COMPLETED", "CANCELLED");

    private final PlanRevisionRepositoryPort revisionRepository;
    private final OperationalPlanRepositoryPort plans;
    private final PlanTargetRepositoryPort targets;
    private final PlanningAccessService access;
    private final PlanningWorkflowContract workflow;
    private final PlanningQueryUseCase queries;

    public PlanningApprovalApplicationService(
            PlanRevisionRepositoryPort revisionRepository,
            OperationalPlanRepositoryPort plans,
            PlanTargetRepositoryPort targets,
            PlanningAccessService access,
            PlanningWorkflowContract workflow,
            PlanningQueryUseCase queries
    ) {
        this.revisionRepository = Objects.requireNonNull(revisionRepository, "PlanRevisionRepositoryPort must not be null.");
        this.plans = Objects.requireNonNull(plans);
        this.targets = Objects.requireNonNull(targets);
        this.access = Objects.requireNonNull(access);
        this.workflow = Objects.requireNonNull(workflow, "PlanningWorkflowContract must not be null.");
        this.queries = Objects.requireNonNull(queries);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ApprovalView approval(String revisionId, String actorReference, Set<String> effectivePermissions) {
        queries.revision(revisionId);
        PlanRevision revision = revision(revisionId);
        PlanningWorkflowContract.InstanceView instance = validatedInstance(revision);
        if (TERMINAL_WORKFLOW_STATES.contains(normalizeState(instance.status()))) {
            return new ApprovalView(
                    revision.id(), String.valueOf(revision.status()), instance.id(), instance.status(), null, null, List.of()
            );
        }
        PlanningWorkflowContract.TaskView task = workflow.currentTask(instance.id());
        List<ActionView> actions = workflow.availableActions(task.id(), actorReference, effectivePermissions).stream()
                .map(action -> new ActionView(
                        action.transitionId(), action.decision(), action.reasonRequired(), action.commentRequired(),
                        action.requiredPermissionCode(), action.permitted()
                ))
                .toList();
        return new ApprovalView(
                revision.id(), String.valueOf(revision.status()), instance.id(), instance.status(),
                task.id(), task.updatedAt(), actions
        );
    }

    @Override
    @Transactional
    public ExecutionView execute(String revisionId, String transitionId, ExecutionCommand command) {
        Objects.requireNonNull(command, "Planning approval execution command must not be null.");
        if (revisionId == null || revisionId.isBlank() || !revisionId.equals(revisionId.strip()))
            throw new IllegalArgumentException("Valid Planning revision identity required.");
        String planId = revisionRepository.planIdForRevision(revisionId)
                .orElseThrow(PlanningAccessService::unavailable);
        OperationalPlan plan = plans.findByIdForUpdate(planId).orElseThrow(PlanningAccessService::unavailable);
        String actor = access.planReviewer(plan);
        if (!Objects.equals(actor, command.actorId()))
            throw PlanningAccessService.unavailable();
        PlanRevision revision = revisionRepository.findByIdForUpdate(revisionId)
                .filter(r -> r.planId().equals(plan.id())).orElseThrow(PlanningAccessService::unavailable);
        if (revision.status() != PlanRevisionStatus.SUBMITTED
                || plan.status() != OperationalPlanStatus.SUBMITTED
                || !revision.id().equals(plan.currentRevisionId())
                || revision.workflowInstanceId() == null
                || revision.submittedByActorId() == null
                || revision.submittedByActorId().equals(actor))
            throw conflict("Only the current submitted revision permits independent review.");
        PlanningWorkflowContract.InstanceView instance = validatedInstance(revision);
        if (TERMINAL_WORKFLOW_STATES.contains(normalizeState(instance.status())))
            throw conflict("The approval instance is already terminal.");
        PlanningWorkflowContract.TaskView task = workflow.currentTask(instance.id());
        PlanningWorkflowContract.TransitionResult result = workflow.execute(
                new PlanningWorkflowContract.TransitionCommand(
                        task.id(), transitionId, command.expectedTaskUpdatedAt(), command.reasonId(),
                        command.decisionNote(), command.commentText(), command.correlationId(), actor,
                        command.actorUsername(), command.actorDisplayName(), command.effectivePermissions()
                )
        );
        if (!Objects.equals(result.instanceId(), instance.id()))
            throw conflict("Workflow transition did not preserve the exact Planning revision binding.");
        boolean completed = "COMPLETED".equals(normalizeState(result.instanceStatus()));
        String decision = normalizeState(result.decision());
        if (!completed && !Set.of("APPROVE", "REJECT", "CORRECT", "DELEGATE", "ESCALATE",
                "REQUEST_CORRECTION", "RETURN").contains(decision))
            throw conflict("Unsupported nonterminal Planning workflow decision.");
        if (completed && !Set.of("APPROVE", "REJECT").contains(decision))
            throw conflict("Only configured terminal approve or reject can decide a revision.");
        Instant executedAt = Objects.requireNonNull(result.executedAt(), "Workflow time required.");
        Instant at = nextVersion(executedAt, plan.updatedAt(), revision.updatedAt());
        PlanRevision updated = revision;
        if (completed) {
            var currentTargets = targets.findByRevisionIdForUpdate(revision.id());
            if (currentTargets.isEmpty() || currentTargets.stream()
                    .anyMatch(t -> t.status() != PlanTargetStatus.DRAFT))
                throw conflict("Submitted targets must be nonempty and immutable before decision.");
            if ("APPROVE".equals(decision)) {
                if (plan.approvedRevisionId() != null && !plan.approvedRevisionId().equals(revision.id())) {
                    for (var prior : targets.findByRevisionIdForUpdate(plan.approvedRevisionId())) {
                        if (prior.status() == PlanTargetStatus.ACTIVE)
                            targets.save(prior.withStatus(PlanTargetStatus.SUPERSEDED,
                                    nextVersion(at, prior.updatedAt())));
                    }
                }
                for (var target : currentTargets)
                    targets.save(target.withStatus(PlanTargetStatus.ACTIVE,
                            nextVersion(at, target.updatedAt())));
            }
            updated = revisionRepository.save(revision.withDecision(
                    "APPROVE".equals(decision) ? PlanRevisionStatus.APPROVED : PlanRevisionStatus.REJECTED,
                    actor, at));
            plans.save(plan.withLifecycle("APPROVE".equals(decision) ? OperationalPlanStatus.ACTIVE
                    : OperationalPlanStatus.DRAFT,
                    "APPROVE".equals(decision) ? revision.id() : plan.approvedRevisionId(), at));
        }
        return new ExecutionView(
                updated.id(), updated.status().name(), result.instanceId(), result.instanceStatus(),
                result.transitionId(), result.decision(), result.nextTaskId(), executedAt);
    }

    private static Instant nextVersion(Instant... times) {
        Instant at = Instant.now().truncatedTo(ChronoUnit.MICROS);
        for (Instant time : times) {
            if (time != null && !at.isAfter(time))
                at = time.plusNanos(1000).truncatedTo(ChronoUnit.MICROS);
        }
        return at;
    }

    private static PlanningRevisionConflictException conflict(String message) {
        return new PlanningRevisionConflictException(message);
    }

    private PlanRevision revision(String revisionId) {
        if (revisionId == null || revisionId.isBlank()) {
            throw new IllegalArgumentException("Plan revision ID must not be blank.");
        }
        return revisionRepository.findById(revisionId.trim())
                .orElseThrow(() -> new NoSuchElementException("Unknown plan revision: " + revisionId.trim()));
    }

    private PlanningWorkflowContract.InstanceView validatedInstance(PlanRevision revision) {
        if (revision.workflowInstanceId() == null) {
            throw new IllegalStateException("Plan revision is not linked to a workflow approval instance: " + revision.id());
        }
        PlanningWorkflowContract.InstanceView instance = workflow.instance(revision.workflowInstanceId());
        if (!PLANNING_MODULE.equalsIgnoreCase(String.valueOf(instance.targetModule()))
                || !Objects.equals(revision.id(), instance.targetId())) {
            throw new IllegalStateException("Workflow instance does not target this planning revision: " + instance.id());
        }
        return instance;
    }

    private static String normalizeState(String value) {
        return value == null ? "" : value.trim().toUpperCase(Locale.ROOT);
    }
}
